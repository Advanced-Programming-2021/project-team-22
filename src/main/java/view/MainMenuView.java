package view;

import controller.Utils;
import controller.deckmenu.DeckMenuController;
import controller.mainmenu.MainMenuController;
import controller.mainmenu.MainMenuMessages;
import controller.profilemenu.ProfileMenuController;
import controller.shopmenu.ShopMenuController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.Objects;

public class MainMenuView extends Application {
    public BorderPane borderPane;
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
            Utils.playButtonClickSFX();
            borderPane.requestFocus();
        });
        handleLogOutButton();
        handleShopButton();
        handleDeckButton();
        handleProfileButton();
    }

    private void handleLogOutButton() {
        logOutButton.setOnMouseClicked(mouseEvent -> {
            try {
                Utils.playButtonClickSFX();
                new LoginMenuView().start(Utils.getStage());
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
    }

    private void handleShopButton() {
        shopButton.setOnMouseClicked(mouseEvent -> {
            try {
                Utils.playButtonClickSFX();
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
                Utils.playButtonClickSFX();
                DeckMenuController.setLoggedInPlayer(MainMenuController.getLoggedInPlayer());
                new DeckMenuView().start(Utils.getStage());
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
    }

    private void handleProfileButton() {
        profileButton.setOnMouseClicked(mouseEvent -> {
            try {
                Utils.playButtonClickSFX();
                ProfileMenuController.setLoggedInPlayer(MainMenuController.getLoggedInPlayer());
                new ProfileMenuView().start(Utils.getStage());
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
    }
}
