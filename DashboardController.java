package com.example.quizapp.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class DashboardController {
    @FXML private Label welcomeLabel;

    public void initialize() {
        welcomeLabel.setText("Welcome to the Quiz App Dashboard!");
    }
}
