module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.jfoenix;
    requires java.desktop;
    requires javafx.base;

    opens com.example.demo to javafx.fxml, com.jfoenix;

    exports com.example.demo;
    exports com.example.demo.dictionary;
    opens com.example.demo.dictionary to com.jfoenix, javafx.fxml;
    exports com.example.demo.game;
    opens com.example.demo.game to com.jfoenix, javafx.fxml;
    exports com.example.demo.common;
    opens com.example.demo.common to com.jfoenix, javafx.fxml;
    exports com.example.demo.auth;
    opens com.example.demo.auth to com.jfoenix, javafx.fxml;
    exports com.example.demo.translate;
    opens com.example.demo.translate to com.jfoenix, javafx.fxml;
}
