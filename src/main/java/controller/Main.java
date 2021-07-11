package controller;

import javafx.application.Application;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import model.Player;
import view.DuelMenuView;
import view.LoginMenuView;

import java.io.File;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
      //  Database.prepareGame();
       // handleSFX();
        //Utils.setStage(stage);
        //stage.setTitle("Yu-Gi-Oh!");
        Player player1 = new Player("1", "1", "1");
        Player player2 = new Player("2", "2", "2");
        DuelMenuView duelMenuView = new DuelMenuView(player1, player2, 1);
        duelMenuView.duelMenuView();
      // duelMenuView.start(stage);
        //new LoginMenuView().start(stage);
    }

    private static void handleSFX() {
        Media media = new Media(new File("src/main/resources/sounds/buttonClick.mp3").toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        Utils.setButtonClickSFX(mediaPlayer);
    }
}