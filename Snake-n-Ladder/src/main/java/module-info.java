module com.example.snakenladder {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.snakenladder to javafx.fxml;
    exports com.example.snakenladder;
}