package com.example.quizapp.ui;

import com.example.quizapp.dao.UserDAO;
import com.example.quizapp.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

public class LoginController {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label messageLabel;

    private UserDAO userDAO = new UserDAO();

    @FXML
    protected void onLogin(ActionEvent event) {
        try {
            String username = usernameField.getText();
            char[] password = passwordField.getText().toCharArray();
            User user = userDAO.login(username, password);

            if (user != null) {
                messageLabel.setText("Login successful!");
                Stage stage = (Stage) usernameField.getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/quizapp/dashboard.fxml"));
                stage.setScene(new Scene(loader.load()));
            } else {
                messageLabel.setText("Invalid credentials.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            messageLabel.setText("Error: " + e.getMessage());
        }
    }
}
