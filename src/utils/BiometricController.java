package utils;

import com.griaule.grfingerjava.*;
import javafx.animation.AnimationTimer;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.ImageView;
import model.FingerprintData;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.concurrent.atomic.AtomicReference;

public class BiometricController implements IStatusEventListener, IImageEventListener, IFingerEventListener {
    /**
     * Sdk class which will be used to extract and compare fingerprint templates
     */
    private MatchingContext fingerprintSDK;
    private FingerprintData fingerprintData = new FingerprintData();
    AtomicReference<BufferedImage> image = new AtomicReference<>();

    public void setCurrentViewer(ImageView viewer) {
        System.out.println("ImageView inicializado");
        AnimationTimer animation = new AnimationTimer() {

            @Override
            public void handle(long now) {
                BufferedImage img = image.getAndSet(null);
                if (img != null) {
                    viewer.setImage(SwingFXUtils.toFXImage(img, null));
                }
            }

        };

        animation.start();
    }

    public FingerprintData getFingerprintData() {
        return fingerprintData;
    }

    /**
     * @param s Sensor id
     */
    @Override
    public void onFingerDown(String s) {

    }

    /**
     * @param s Sensor id
     */
    @Override
    public void onFingerUp(String s) {

    }

    /**
     * @param s                Sensor id
     * @param fingerprintImage Fingerprint Image captured
     */
    @Override
    public void onImageAcquired(String s, FingerprintImage fingerprintImage) {
        System.out.println("Nova imagem adquirida");
        //Saves the captured image
        this.fingerprintData.setFingerprint(fingerprintImage);

        //TODO Mostrar a imagem obtida


        //chama o metodo que obtem a planta
        extract();
    }

    /**
     * @param idSensor Sensor id
     */
    @Override
    public void onSensorPlug(String idSensor) {
        System.out.println("Sensor " + idSensor + " conectado");
        try {
            GrFingerJava.startCapture(idSensor, this, this);
        } catch (GrFingerJavaException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param idSensor Sensor id
     */
    @Override
    public void onSensorUnplug(String idSensor) {
        try {
            System.out.println("Sensor " + idSensor + " desconectado");
            GrFingerJava.stopCapture(idSensor);
        } catch (GrFingerJavaException e) {
            e.printStackTrace();
        }
    }

    /**
     * Extracts template from current fingerprint
     */
    public void extract() {

        try {
            //Extracts template
            this.fingerprintData.setTemplate(fingerprintSDK.extract(this.fingerprintData.getFingerprint()));
            System.out.println("Template extraido : " + this.fingerprintData.getFingerprintDataLength());
            //TODO Mostra a planta na imagem
            BufferedImage bi = GrFingerJava.getBiometricImage(this.fingerprintData.getTemplate(), this.fingerprintData.getFingerprint());

            image.set(bi);
            this.fingerprintData.setBufferedImage(bi);

            //ui.showImage(GrFingerJava.getBiometricImage(template, fingerprint));
        } catch (GrFingerJavaException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param diretorio Estabelece o diretorio onde ficam as bibliotecas nativas do SDK da Griaule
     */
    public void setFingerprintSDKNativeDirectory(String diretorio) {
        File directory = new File(diretorio);

        try {
            GrFingerJava.setNativeLibrariesDirectory(directory);
            GrFingerJava.setLicenseDirectory(directory);
        } catch (GrFingerJavaException e) {
            e.printStackTrace();
        }
    }


    /**
     * Inicializa o Fingerprint SDK e habilita a captura de impressoes.
     */
    public void inicializarCaptura() {
        System.out.println("Serviço de captura de biometria iniciado");
        try {
            fingerprintSDK = new MatchingContext();
            //Inicializa a captura de impressao digital.
            GrFingerJava.initializeCapture(this);
        } catch (Exception e) {
            //Se ocorrer um erro, encerra a aplicacao.
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static void encerrarCaptura() {
        try {
            System.out.println("Encerra servico de coleta de biometria");
            //Encerra a captura de impressao digital.
            GrFingerJava.finalizeCapture();
        } catch (Exception e) {
            //Se ocorrer um erro, encerra a aplicacao.
            e.printStackTrace();
            System.exit(1);
        }
    }

    public boolean verificarImpressao(Template data) {
        try {
            System.out.println("Comparando os templates de referencia (" + data + ") com o template atual (" + this.getFingerprintData().getTemplate() + ")");

            //compara as plantas (atual vs base de dados)
            boolean coincidem = fingerprintSDK.verify(this.fingerprintData.getTemplate(), data);

            //Se correspondem, desenha o mapa de correspondencia e retorna true
            return coincidem;
        } catch (GrFingerJavaException e) {
            e.printStackTrace();
        }
        return false;
    }
}
