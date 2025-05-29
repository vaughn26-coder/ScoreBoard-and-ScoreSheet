package com.example.scoreboardfinal;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class RegisterController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private Label registerMessageLabel;

    @FXML
    private Button registerButton;

    @FXML
    private Button backToLoginButton;

    @FXML
    private void handleRegister(ActionEvent event) {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();
        String confirmPassword = confirmPasswordField.getText().trim();

        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            registerMessageLabel.setText("Please fill in all fields.");
            return;
        }

        if (!password.equals(confirmPassword)) {
            registerMessageLabel.setText("Passwords do not match.");
            return;
        }


        if (LoginController.registerUser(username, password)) {
            registerMessageLabel.setText("Registration successful!");
            openLoginWindow(event);
        } else {
            registerMessageLabel.setText("Username already taken.");
        }
    }



    @FXML
    private void openLoginWindow(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/scoreboardfinal/LOGIN.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Use setRoot instead of setScene to minimize flicker
            stage.getScene().setRoot(root);
            stage.setFullScreen(true);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error: Unable to load the LOGIN window.");
        }
    }
}
