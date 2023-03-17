module com.example.paintfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.paintfx to javafx.fxml;
    exports com.example.paintfx;
    exports Figurer;
    opens Figurer to javafx.fxml;
}