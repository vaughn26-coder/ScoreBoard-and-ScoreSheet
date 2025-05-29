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
import java.util.HashMap;
import java.util.Map;

public class LoginController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    // Store registered users: username -> password
    private static final Map<String, String> registeredUsers = new HashMap<>();

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label loginMessageLabel;

    @FXML
    private Button loginButton;

    @FXML
    private Button signUpButton;


    @FXML
    private void loginButtonClicked(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (registeredUsers.containsKey(username) && registeredUsers.get(username).equals(password)) {
            openScoreWindow(event);
        } else {
            loginMessageLabel.setText("Invalid username or password");
        }
    }


    private void openScoreWindow(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/scoreboardfinal/Score.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();


            stage.getScene().setRoot(root);
            stage.setFullScreen(true);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error: Unable to load the SCORE window.");
        }
    }


    @FXML
    private void openRegisterWindow(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/scoreboardfinal/Register.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();


            stage.getScene().setRoot(root);
            stage.setFullScreen(true);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error: Unable to load the REGISTER window.");
        }
    }


    public static boolean registerUser(String username, String password) {
        if (username == null || password == null || username.isEmpty() || password.isEmpty())
            return false;

        if (registeredUsers.containsKey(username)) {
            return false; // User already exists
        } else {
            registeredUsers.put(username, password);
            return true;
        }
    }
}
