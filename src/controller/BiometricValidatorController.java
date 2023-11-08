package controller;

import com.griaule.grfingerjava.Template;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import utils.BiometricController;

import java.net.URL;
import java.util.ResourceBundle;

public class BiometricValidatorController implements Initializable {

    @FXML
    private AnchorPane root;

    @FXML
    private Button btSaveFingerprintData;

    @FXML
    private Button btInitialize;

    @FXML
    private Button btCompare;

    @FXML
    private ImageView lastFingerPrint;

    @FXML
    private ImageView currentFingerprint;

    private Template currentTemplate;

    private final BiometricController biometricController;

    public BiometricValidatorController() {
        this.biometricController = new BiometricController();
        this.biometricController.setFingerprintSDKNativeDirectory("C:\\Program Files (x86)\\Griaule\\Fingerprint SDK 2009\\bin");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        biometricController.setCurrentViewer(this.currentFingerprint);
        biometricController.inicializarCaptura();
//        Biometric.setLogger(logger);

//        btInitializeCapture.setOnAction(e -> Biometric.startScanner());
//        btStopCapture.setOnAction(e -> Biometric.closeScanner());
//        btStartCapture.setOnAction(e -> {
//            Biometric.startCapture(currentFingerprint);
//            Image biometricImage = Biometric.getBiometricImage();
//            if(biometricImage != null){
//                fingerImageView.setImage(biometricImage);
//            }
//        });

//        btInitialize.setOnAction(e -> biometricController.inicializarCaptura());
        btSaveFingerprintData.setOnAction(e -> {
            currentTemplate = new Template(biometricController.getFingerprintData().getTemplate().getData());
            lastFingerPrint.setImage(biometricController.getFingerprintData().getImage());
            System.out.println("Template salvo : " + currentTemplate.getData().length);
        });
        btCompare.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Resposta");
            if(biometricController.verificarImpressao(currentTemplate)){
                alert.setContentText("As digitais conferem");
            }else{
                alert.setContentText("As digitais não conferem");
            }
            alert.show();
        });
    }

    public void onCloseRequest(){
        System.out.println("Controller onCloseRequest called;");
        BiometricController.encerrarCaptura();
    }
}
