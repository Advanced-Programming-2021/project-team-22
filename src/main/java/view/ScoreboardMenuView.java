package view;

import controller.Utils;
import controller.scoreboardmenu.ScoreboardMenuController;
import controller.scoreboardmenu.ScoreboardMenuMessages;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Player;

import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;

public class ScoreboardMenuView extends Application {

    private Pane root;
    private Scene scene;
    private GridPane gridPane;
    private Player loggedPlayer;

    public ScoreboardMenuView(Player player) {
        loggedPlayer = player;
    }


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        URL url = getClass().getResource("/view/fxml/ScoreBoard.fxml");
        try {
            root = FXMLLoader.load(url);
        } catch (Exception e) {
            System.out.println("cant load board!");
        }
        scene = new Scene(root, 700, 600);
        setPlayers();
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void setPlayers() {
        ScrollPane scrollPane = (ScrollPane) scene.lookup("scrollPane");
        int numberOfPlayers = Player.getAllPlayers().size();
        int count = 20;
        if (numberOfPlayers < count)
            count = numberOfPlayers;
        Comparator<Player> compareByScore = Comparator
                .comparing(Player::getScore).reversed()
                .thenComparing(Player::getNickname);
        int scoreCompare = 0;
        int lastRank = 0;
        Player.getAllPlayers().sort(compareByScore);

        for (int i = 0; i < count; i++) {

            Label labelRank = new Label();
            if (Player.getAllPlayers().get(i).getScore() > scoreCompare) {
                lastRank = i + 1;
            }
            showScoreGenerate(gridPane, lastRank, i, labelRank, Player.getAllPlayers().get(i));
        }

        scrollPane.setContent(gridPane);
    }


    private void showScoreGenerate(GridPane gridPane, int lastRank, int i, Label labelRank, Player player) {
        labelRank.setText(String.valueOf(lastRank));
        Label labelName = new Label();
        labelName.setText(Player.getAllPlayers().get(i).getNickname());
        Label labelScore = new Label();
        labelScore.setText(String.valueOf(Player.getAllPlayers().get(i).getScore()));
        gridPane.add(labelRank, 0, i);
        gridPane.add(labelName, 1, i);
        gridPane.add(labelScore, 2, i);
        BackgroundFill background_fill = new BackgroundFill(Color.GOLD,
                CornerRadii.EMPTY, Insets.EMPTY);

        Background background = new Background(background_fill);
        if (player.getNickname().equals(loggedPlayer.getNickname())) {
            labelRank.setBackground(background);
            labelName.setBackground(background);
            labelScore.setBackground(background);
        }

    }

}
