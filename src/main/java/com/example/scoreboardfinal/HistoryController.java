package com.example.scoreboardfinal;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;

public class HistoryController {

    @FXML
    private ListView<String> historyListView;

    @FXML
    private Button backButton;

    @FXML
    private Button removeButton;

    @FXML
    public void initialize() {

        historyListView.getItems().setAll(MatchHistory.getHistory());
    }

    @FXML
    private void handleBack(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/scoreboardfinal/Score.fxml"));
            Parent root = loader.load();


            Stage stage = (Stage) backButton.getScene().getWindow();


            stage.getScene().setRoot(root);
            stage.setFullScreen(true);

            System.out.println("Back to Score window");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error: Unable to load the SCORE window.");
        }
    }

    @FXML
    private void handleRemove(ActionEvent event) {
        int selectedIndex = historyListView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {

            String selectedItem = historyListView.getItems().remove(selectedIndex);
            MatchHistory.removeRecord(selectedItem);
            System.out.println("Removed: " + selectedItem);
        } else {
            System.out.println("No item selected to remove.");
        }
    }
}
