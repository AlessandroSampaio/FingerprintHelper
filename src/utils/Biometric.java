package utils;

import com.griaule.grfingerjava.*;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.concurrent.atomic.AtomicReference;

public class Biometric {
    private static boolean scannerClosed = true;

    private static LoggerToArea _logger;

    static {
        GrFingerJava.setNativeLibrariesDirectory(new File("C:\\Program Files (x86)\\Griaule\\Fingerprint SDK 2009\\bin"));
    }

    public static void setLogger(LoggerToArea logger) {
        _logger = logger;
    }

    public static void startScanner() {
        try {
            GrFingerJava.initializeCapture(new IStatusEventListener() {
                @Override
                public void onSensorPlug(String s) {
                    _logger.log("Sensor Plugged: " + s);
                }

                @Override
                public void onSensorUnplug(String s) {
                    _logger.log("Sensor Unplugged: " + s);
                }
            });
            scannerClosed = false;
        } catch (GrFingerJavaException e) {
            _logger.log(e.getMessage());
            e.printStackTrace();
        }
    }

    public static void closeScanner() {
        if (scannerClosed) {
            return;
        }
        try {
            _logger.log("Finalizing capture.");
            GrFingerJava.finalizeCapture();
            scannerClosed = true;
        } catch (GrFingerJavaException e) {
            _logger.log(e.getMessage());
            e.printStackTrace();
        }
    }

    public static Image getBiometricImage() {
        if (scannerClosed) {
            _logger.log("Scanner não inicializado");
            return null;
        }
        _logger.log("Starts reading");
        byte[] data = new byte[1024];
        Template template = new Template(Template.MEDIUM_QUALITY, data);
        FingerprintImage image = new FingerprintImage(220, 300, 300);
        try {
            BufferedImage bufferedImage = GrFingerJava.getBiometricImage(template, image);

            return SwingFXUtils.toFXImage(bufferedImage, null);
        } catch (GrFingerJavaException e) {
            _logger.log(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public static void startCapture(ImageView viewer) {
        _logger.log("Captura Inicializada");

        try {
            AtomicReference<BufferedImage> image = new AtomicReference<>();

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

            GrFingerJava.startCapture("Futronic",
                    new IFingerEventListener() {
                        @Override
                        public void onFingerDown(String s) {
//                            Platform.runLater(() -> _logger.log("Finger down"));
                        }

                        @Override
                        public void onFingerUp(String s) {
//                            Platform.runLater(() -> _logger.log("Finger up"));
                            image.set(null);

                        }
                    },
                    (s, fingerprintImage) -> {
                        try {
                            image.set(fingerprintImage);
//                            Platform.runLater(() -> {
//                                _logger.log("Imagem capturada : ");
//                                viewer.setImage(SwingFXUtils.toFXImage(fingerprintImage, null));
//                            });
                        }catch (Exception e){
                            _logger.log("Falha na geração da imagem : " + e.getMessage());
                        }

                    });
        } catch (GrFingerJavaException e) {
            _logger.log(e.getMessage());
            e.printStackTrace();
        }
    }
}
