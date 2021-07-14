package controller;

import javafx.application.Application;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import view.LoginMenuView;

import java.io.File;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Database.prepareGame();
        handleMusic();
        handleSFX();
        Utils.setStage(stage);
        stage.setTitle("Yu-Gi-Oh!");
        new LoginMenuView().start(stage);
    }

    private static void handleMusic() {
        double randomNumber = Math.random();
        Media media;
        if (randomNumber < 0.5)
            media = new Media(new File("C:\\Users\\ASUS\\IdeaProjects\\Yu.Gi.Oh.game\\src\\main\\resources\\sounds\\background1.mp3").toURI().toString());
        else media = new Media(new File("C:\\Users\\ASUS\\IdeaProjects\\Yu.Gi.Oh.game\\src\\main\\resources\\sounds\\background2.mp3").toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.play();
        Utils.setBackgroundMusicPlayer(mediaPlayer);
    }

    private static void handleSFX() {
        Media media = new Media(new File("C:\\Users\\ASUS\\IdeaProjects\\Yu.Gi.Oh.game\\src\\main\\resources\\sounds\\buttonClick.mp3").toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        Utils.setButtonClickSFX(mediaPlayer);
    }
}
