package main;

import controller.BiometricValidatorController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utils.BiometricController;

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
        BiometricValidatorController controller = new BiometricValidatorController();
        try {
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
            dialog.setOnCloseRequest(event -> {
                controller.onCloseRequest();
            });
            //scene.setFill(Color.TRANSPARENT);
            dialog.setScene(scene);
            //dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.showAndWait();
        }
    }

    @Override
    public void stop() throws Exception {
        BiometricController.encerrarCaptura();
    }
}
