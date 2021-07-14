package view;

import controller.Utils;
import controller.deckmenu.DeckMenuController;
import controller.duelmenu.DuelMenuController;
import controller.importexportmenu.ImportExportMenuController;
import controller.mainmenu.MainMenuController;
import controller.mainmenu.MainMenuMessages;
import controller.profilemenu.ProfileMenuController;
import controller.scoreboardmenu.ScoreboardMenuController;
import controller.shopmenu.ShopMenuController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.util.Objects;

public class MainMenuView extends Application {
    public BorderPane borderPane;
    public TextField username;
    public TextField numberOfRounds;
    public Button playWithAIButton;
    public Label result;

    public Button duelButton;
    public Button deckButton;
    public Button scoreboardButton;
    public Button profileButton;
    public Button shopButton;
    public Button importExportButton;
    public Button logOutButton;

    @Override
    public void start(Stage stage) throws Exception {
        BorderPane root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/fxml/MainMenu.fxml")));
        Scene scene = new Scene(root, 1280, 800);
        stage.setMinHeight(scene.getHeight() + 28 /*title bar height*/);
        stage.setMinWidth(scene.getWidth());
        stage.setScene(scene);
        stage.sizeToScene();
    }

    @FXML
    public void initialize() {
        borderPane.setOnMouseClicked(mouseEvent -> {
            if (MainMenuController.getLoggedInPlayer().isPlaySFX()) Utils.playButtonClickSFX();
            borderPane.requestFocus();
        });

        handleDuelButton();
        handlePlayWithAIButton();
        handleDeckButton();
        handleScoreboardButton();
        handleProfileButton();
        handleShopButton();
        handleImportExportButton();
        handleLogOutButton();

        handleBackgroundMusic();
    }

    private void handleDuelButton() {
        duelButton.setOnMouseClicked(mouseEvent -> {
            try {
                if (MainMenuController.getLoggedInPlayer().isPlaySFX()) Utils.playButtonClickSFX();

                MainMenuMessages result;
                if (username.isVisible()) {
                    result = MainMenuController.enterDuelMenu(username.getText(), numberOfRounds.getText(), false);
                    playWithAIButton.setVisible(false);
                    this.result.setText(result.getMessage());
                } else playWithAIButton.setVisible(true);

                username.clear();
                numberOfRounds.clear();
                username.setVisible(!username.isVisible());
                numberOfRounds.setVisible(!numberOfRounds.isVisible());
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
    }

    private void handlePlayWithAIButton() {
        playWithAIButton.setOnMouseClicked(mouseEvent -> {
            try {
                if (MainMenuController.getLoggedInPlayer().isPlaySFX()) Utils.playButtonClickSFX();

                MainMenuMessages result = MainMenuController.enterDuelMenu(username.getText(), numberOfRounds.getText(), true);
                this.result.setText(result.getMessage());

                username.clear();
                numberOfRounds.clear();
                username.setVisible(false);
                numberOfRounds.setVisible(false);
                playWithAIButton.setVisible(false);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
    }

    private void handleLogOutButton() {
        logOutButton.setOnMouseClicked(mouseEvent -> {
            try {
                if (MainMenuController.getLoggedInPlayer().isPlaySFX()) Utils.playButtonClickSFX();

                new LoginMenuView().start(Utils.getStage());
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
    }

    private void handleShopButton() {
        shopButton.setOnMouseClicked(mouseEvent -> {
            try {
                if (MainMenuController.getLoggedInPlayer().isPlaySFX()) Utils.playButtonClickSFX();

                ShopMenuController.setLoggedInPlayer(MainMenuController.getLoggedInPlayer());
                new ShopMenuView().start(Utils.getStage());
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
    }

    private void handleDeckButton() {
        deckButton.setOnMouseClicked(mouseEvent -> {
            try {
                if (MainMenuController.getLoggedInPlayer().isPlaySFX()) Utils.playButtonClickSFX();

                DeckMenuController.setLoggedInPlayer(MainMenuController.getLoggedInPlayer());
                new DeckMenuView().start(Utils.getStage());
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
    }

    private void handleScoreboardButton() {
        scoreboardButton.setOnMouseClicked(mouseEvent -> {
            try {
                if (MainMenuController.getLoggedInPlayer().isPlaySFX()) Utils.playButtonClickSFX();

                ScoreboardMenuController.setLoggedInPlayer(MainMenuController.getLoggedInPlayer());
                new ScoreboardMenuView().start(Utils.getStage());
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
    }

    private void handleProfileButton() {
        profileButton.setOnMouseClicked(mouseEvent -> {
            try {
                if (MainMenuController.getLoggedInPlayer().isPlaySFX()) Utils.playButtonClickSFX();

                ProfileMenuController.setLoggedInPlayer(MainMenuController.getLoggedInPlayer());
                new ProfileMenuView().start(Utils.getStage());
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
    }

    private void handleImportExportButton() {
        importExportButton.setOnMouseClicked(mouseEvent -> {
            try {
                if (MainMenuController.getLoggedInPlayer().isPlaySFX()) Utils.playButtonClickSFX();

                ImportExportMenuController.setLoggedInPlayer(MainMenuController.getLoggedInPlayer());
                new ImportExportMenuView().start(Utils.getStage());
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
    }

    private void handleBackgroundMusic() {
        MediaPlayer backgroundMusicPlayer = Utils.getBackgroundMusicPlayer();
        if (MainMenuController.getLoggedInPlayer().isPlayMusic() && backgroundMusicPlayer.isMute()) {
            backgroundMusicPlayer.stop();
            backgroundMusicPlayer.setMute(false);
            backgroundMusicPlayer.play();
        } else if (!MainMenuController.getLoggedInPlayer().isPlayMusic() && !backgroundMusicPlayer.isMute()) {
            backgroundMusicPlayer.setMute(true);
        }
    }
}
