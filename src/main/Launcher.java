package main;

import controller.BiometricValidatorController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import utils.Biometric;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Launcher extends Application {
    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent parent = null;
        try {
            BiometricValidatorController controller = new BiometricValidatorController();
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/BiometricValidator.fxml"));
            loader.setController(controller);
            parent = loader.load();
        } catch (IOException ex) {
            Logger.getLogger(Launcher.class.getName()).log(Level.SEVERE, null, ex);
        }
        Stage dialog = new Stage();
        //dialog.initStyle(StageStyle.TRANSPARENT);
        if(parent != null) {
            Scene scene = new Scene(parent);
            //scene.setFill(Color.TRANSPARENT);
            dialog.setScene(scene);
            //dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.show();
        }
    }

    @Override
    public void stop() throws Exception {
        Biometric.closeScanner();
        Platform.exit();
    }
}
