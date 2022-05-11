module com.emma.bubblenote {
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.emma.bubblenote to javafx.fxml;
    exports com.emma.bubblenote;
}