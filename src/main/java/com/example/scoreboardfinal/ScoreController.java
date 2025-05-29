package com.example.scoreboardfinal;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Optional;

public class ScoreController {

    @FXML private Label LEFTSCORE, RIGHTSCORE, TIME, SHOOTCLOCK;
    @FXML private Button LEFTPLUS, LEFTMINUS, RIGHTPLUS, RIGHTMINUS, TIMEBUTTON, Shootclock;
    @FXML private Button EndGameButton, changeCourtButton, HISTORY, LogoutButton;
    @FXML private Button Q1, Q2, Q3, Q4;
    @FXML private Button LEFTFOUL, LEFTTOL, RIGHTFOUL, RIGHTTOL;

    @FXML private Label LEFTTEAM, RIGHTTEAM;

    private int leftScore = 0;
    private int rightScore = 0;
    private int leftFoul = 5;
    private int rightFoul = 5;
    private int leftTOL = 5;
    private int rightTOL = 5;
    private int seconds = 600;
    private int shootClockSeconds = 25;
    private Timeline timer;
    private Timeline shootClockTimer;

    private String leftTeamName = "Left Team";
    private String rightTeamName = "Right Team";

    @FXML
    private Button ARROW;

    @FXML
    private void handleArrowClick(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        String currentStyle = clickedButton.getStyle();

        if (currentStyle.contains("green")) {
            clickedButton.setStyle("-fx-background-color: transparent; -fx-border-color: red; -fx-border-radius: 10;");
        } else {
            clickedButton.setStyle("-fx-background-color: green; -fx-border-color: red;-fx-border-radius: 10;");
        }
    }



    @FXML
    public void initialize() {
        updateScores();
        updateFoulTOLLabels();
        updateTimeLabel();
        updateShootClock();
        LEFTTEAM.setText(leftTeamName);
        RIGHTTEAM.setText(rightTeamName);

        LEFTTEAM.setOnMouseClicked(e -> renameTeam(true));
        RIGHTTEAM.setOnMouseClicked(e -> renameTeam(false));
    }

    private void renameTeam(boolean isLeftTeam) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Rename Team");
        dialog.setHeaderText("Enter new team name:");
        dialog.setContentText("Team Name:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(name -> {
            if (!name.trim().isEmpty()) {
                if (isLeftTeam) {
                    leftTeamName = name.trim();
                    LEFTTEAM.setText(leftTeamName);
                } else {
                    rightTeamName = name.trim();
                    RIGHTTEAM.setText(rightTeamName);
                }
            }
        });
    }

    private void updateScores() {
        LEFTSCORE.setText(String.format("%02d", leftScore));
        RIGHTSCORE.setText(String.format("%02d", rightScore));
    }

    private void updateFoulTOLLabels() {
        LEFTFOUL.setText(String.valueOf(leftFoul));
        LEFTTOL.setText(String.valueOf(leftTOL));
        RIGHTFOUL.setText(String.valueOf(rightFoul));
        RIGHTTOL.setText(String.valueOf(rightTOL));
    }

    private void updateTimeLabel() {
        int minutes = seconds / 60;
        int secs = seconds % 60;
        TIME.setText(String.format("%02d:%02d", minutes, secs));
    }

    private void updateShootClock() {
        SHOOTCLOCK.setText(String.format("%02d", shootClockSeconds));
    }

    @FXML private void incrementLeftScore() { leftScore++; updateScores(); }
    @FXML private void decrementLeftScore() { if (leftScore > 0) leftScore--; updateScores(); }
    @FXML private void incrementRightScore() { rightScore++; updateScores(); }
    @FXML private void decrementRightScore() { if (rightScore > 0) rightScore--; updateScores(); }

    @FXML
    private void startCountdown() {
        if (timer == null) {
            timer = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
                if (seconds > 0) {
                    seconds--;
                    updateTimeLabel();
                } else {
                    timer.stop();
                    endGame();
                }
            }));
            timer.setCycleCount(Timeline.INDEFINITE);
            timer.play();
        } else {
            if (timer.getStatus() == Timeline.Status.RUNNING) {
                timer.pause();
                if (shootClockTimer != null) shootClockTimer.pause();
            } else {
                timer.play();
                if (shootClockTimer != null) shootClockTimer.play();
            }
        }

        if (shootClockTimer == null) {
            shootClockTimer = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
                if (shootClockSeconds > 0) {
                    shootClockSeconds--;
                    updateShootClock();
                } else {
                    SHOOTCLOCK.setStyle("-fx-text-fill: red;");
                    shootClockTimer.stop();
                }
            }));
            shootClockTimer.setCycleCount(Timeline.INDEFINITE);
            shootClockTimer.play();
        }
    }

    @FXML
    private void handleShootClock() {
        shootClockSeconds = 25;
        updateShootClock();
        SHOOTCLOCK.setStyle("-fx-text-fill: black;");

        if (shootClockTimer != null) shootClockTimer.stop();

        shootClockTimer = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            if (shootClockSeconds > 0) {
                shootClockSeconds--;
                updateShootClock();
            } else {
                SHOOTCLOCK.setStyle("-fx-text-fill: red;");
                shootClockTimer.stop();
            }
        }));
        shootClockTimer.setCycleCount(Timeline.INDEFINITE);
        shootClockTimer.play();
    }

    @FXML
    private void manualTimeInput() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Set Time");
        dialog.setHeaderText("Enter time in seconds:");
        dialog.setContentText("Seconds:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(input -> {
            try {
                int inputSeconds = Integer.parseInt(input);
                if (inputSeconds >= 0) {
                    seconds = inputSeconds;
                    updateTimeLabel();
                    if (timer != null) timer.stop();
                    startCountdown();
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid time input.");
            }
        });
    }

    @FXML private void decrementLeftFoul() { leftFoul--; if (leftFoul < 0) leftFoul = 5; updateFoulTOLLabels(); }
    @FXML private void decrementRightFoul() { rightFoul--; if (rightFoul < 0) rightFoul = 5; updateFoulTOLLabels(); }
    @FXML private void decrementLeftTOL() { leftTOL--; if (leftTOL < 0) leftTOL = 5; updateFoulTOLLabels(); }
    @FXML private void decrementRightTOL() { rightTOL--; if (rightTOL < 0) rightTOL = 5; updateFoulTOLLabels(); }

    @FXML
    private void handleQuarter(ActionEvent event) {
        Button btn = (Button) event.getSource();
        String currentStyle = btn.getStyle();
        if (currentStyle.contains("white")) {
            btn.setStyle("-fx-background-color: red; -fx-border-color: red; -fx-border-width: 1px;");
        } else {
            btn.setStyle("-fx-background-color: white; -fx-border-color: red; -fx-border-width: 1px;");
        }
    }

    @FXML
    private void endGame() {
        String result = (leftScore > rightScore) ? leftTeamName + " Wins" : rightTeamName + " Wins";

        String record = String.format("Time: %s | %s: %d | %s: %d | Result: %s",
                TIME.getText(), leftTeamName, leftScore, rightTeamName, rightScore, result);

        MatchHistory.addRecord(record);

        leftScore = rightScore = 0;
        leftFoul = rightFoul = 5;
        leftTOL = rightTOL = 5;
        seconds = 600;

        updateScores();
        updateFoulTOLLabels();
        updateTimeLabel();
    }

    @FXML
    private void changeCourt() {
        // Swap scores
        int tempScore = leftScore;
        leftScore = rightScore;
        rightScore = tempScore;

        // Swap fouls
        int tempFoul = leftFoul;
        leftFoul = rightFoul;
        rightFoul = tempFoul;

        // Swap TOL
        int tempTOL = leftTOL;
        leftTOL = rightTOL;
        rightTOL = tempTOL;

        // Swap team names
        String tempTeam = LEFTTEAM.getText();
        LEFTTEAM.setText(RIGHTTEAM.getText());
        RIGHTTEAM.setText(tempTeam);

        updateScores();
        updateFoulTOLLabels();
    }


    @FXML
    private void openHISTORYWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/scoreboardfinal/History.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) HISTORY.getScene().getWindow();
            stage.getScene().setRoot(root);
            stage.setFullScreen(true);
            System.out.println("History Window opened");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error: Unable to load the History window.");
        }
    }

    @FXML
    private void openLoginWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/scoreboardfinal/Login.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) LogoutButton.getScene().getWindow();
            stage.getScene().setRoot(root);
            stage.setFullScreen(true);
            System.out.println("Login Window opened");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error: Unable to load the Login window.");
        }
    }

    @FXML
    private void handleLogoutButton(ActionEvent event) {
        openLoginWindow();
    }
}
