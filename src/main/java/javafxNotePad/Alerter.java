package javafxNotePad;

import javafx.scene.control.Alert;

public class Alerter {

    public static void ioAlert(String text) { // overload method
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("I/O Error");
        alert.setGraphic(null);
        alert.setContentText(text);
        alert.getDialogPane().setMinSize(200, 100);
        alert.showAndWait();
    }

    public static void creditsAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About the application");
        alert.setGraphic(null);
        alert.setContentText("Creator: Mikhail Lavrov");
        alert.getDialogPane().setMinSize(200, 100);
        alert.showAndWait();
    }
}
