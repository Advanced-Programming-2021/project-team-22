module Graphic.Copy {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.desktop;
    requires com.google.gson;
    requires opencsv;
    requires javafx.media;


    opens view to javafx.fxml;
    exports view;
    exports model;
    exports controller.duelmenu;
}