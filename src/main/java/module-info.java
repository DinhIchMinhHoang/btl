module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.jfoenix;
    requires java.desktop;
    requires javafx.base;

    opens com.example.demo to javafx.fxml, com.jfoenix;

    exports com.example.demo;
}
