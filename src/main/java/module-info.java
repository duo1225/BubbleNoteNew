module com.emma.bubblenote {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;


    opens com.emma.bubblenote to javafx.fxml;
    exports com.emma.bubblenote;
}