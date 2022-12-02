module com.example.graphesetimage {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.graphesetimage to javafx.fxml;
    exports com.example.graphesetimage;
}