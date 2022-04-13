package com.example.tourplanner;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    public void onTourCreateClicked(ActionEvent actionEvent) {
    }

    public void onTourRemoveClicked(ActionEvent actionEvent) {
    }

    public void onTourEditClicked(ActionEvent actionEvent) {
    }

    public void onShowRouteClicked(ActionEvent actionEvent) {
    }

    public void onTourLogCreateClicked(ActionEvent actionEvent) {
    }

    public void onTourLogRemoveClicked(ActionEvent actionEvent) {
    }

    public void onTourLogEditClicked(ActionEvent actionEvent) {
    }
}