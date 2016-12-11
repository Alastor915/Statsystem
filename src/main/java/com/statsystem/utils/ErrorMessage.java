package com.statsystem.utils;


import javafx.scene.control.*;

public class ErrorMessage {

    public static void showErrorMessage(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
