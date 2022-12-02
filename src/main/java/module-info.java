module com.example.graphesetimage {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.logging;


    opens com.example.graphesetimage to javafx.fxml;
    exports com.example.graphesetimage;
}