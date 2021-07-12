module Graphic.Copy {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.desktop;
    requires com.google.gson;
    requires opencsv;
    requires javafx.media;
    requires avatar.generator;
    requires avatar.generator.smiley;
    requires avatar.generator.EightBitAvatar;


    opens view to javafx.fxml;
    exports view;
    exports model;
    exports model.cards.magiccard;
    exports controller.duelmenu;
    exports controller;
}