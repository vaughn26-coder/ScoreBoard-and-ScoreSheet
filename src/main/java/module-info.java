module com.example.scoreboardfinal {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.scoreboardfinal to javafx.fxml;
    exports com.example.scoreboardfinal;
}