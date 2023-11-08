package utils;

import javafx.application.Platform;
import javafx.scene.control.TextArea;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class LoggerToArea {

    private static DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm:ss");

    private final TextArea loggerArea;

    public LoggerToArea(TextArea loggerArea) {
        this.loggerArea = loggerArea;
    }

    public void log(String message) {
        Platform.runLater(() -> {
            loggerArea.appendText(
                    LocalDateTime.now().format(df)
                            + " : "
                            + message
                            + ".\n"
            );
        });
    }
}
