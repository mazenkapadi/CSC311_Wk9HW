module com.example.csc311_wk9hw {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.csc311_wk9hw to javafx.fxml;
    exports com.example.csc311_wk9hw;
}