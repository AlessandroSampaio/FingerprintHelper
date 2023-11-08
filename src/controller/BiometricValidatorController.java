package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import utils.Biometric;
import utils.LoggerToArea;

import java.net.URL;
import java.util.ResourceBundle;

public class BiometricValidatorController implements Initializable {

    @FXML
    private AnchorPane root;

    @FXML
    private Button btInitializeCapture;

    @FXML
    private Button btStopCapture;

    @FXML
    private Button btStartCapture;

    @FXML
    private TextArea txLogArea;

    @FXML
    private ImageView fingerImageView;

    private LoggerToArea logger;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logger = new LoggerToArea(txLogArea);
        Biometric.setLogger(logger);

        btInitializeCapture.setOnAction(e -> Biometric.startScanner());
        btStopCapture.setOnAction(e -> Biometric.closeScanner());
        btStartCapture.setOnAction(e -> {
            Biometric.startCapture(fingerImageView);
//            Image biometricImage = Biometric.getBiometricImage();
//            if(biometricImage != null){
//                fingerImageView.setImage(biometricImage);
//            }
        });
    }
}
