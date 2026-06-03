module ucr.lab.pg05 {
    requires javafx.controls;
    requires javafx.fxml;


    opens ucr.lab.pg05 to javafx.fxml;
    exports ucr.lab.pg05;
    exports controller;
    opens controller to javafx.fxml;
}