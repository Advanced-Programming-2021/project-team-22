package view;

import controller.Utils;
import controller.mainmenu.MainMenuController;
import controller.mainmenu.MainMenuMessages;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Player;

import java.util.Objects;

public class MainMenuView extends Application {
    private Player loggedInPlayer;

    public BorderPane borderPane;
    public Button duelButton;
    public Button deckButton;
    public Button scoreboardButton;
    public Button profileButton;
    public Button shopButton;
    public Button importExportButton;
    public Button logOutButton;

    public void setLoggedInPlayer(Player loggedInPlayer) {
        this.loggedInPlayer = loggedInPlayer;
    }

    public void mainMenuView() {
        MainMenuController mainMenuController = new MainMenuController(loggedInPlayer);
        while (true) {
            String command = Utils.getScanner().nextLine().trim();
            MainMenuMessages result = mainMenuController.findCommand(command);

            System.out.print(result.getMessage());

            if (result.equals(MainMenuMessages.EXIT_MAIN_MENU) ||
            result.equals(MainMenuMessages.USER_LOGGED_OUT)) break;
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        BorderPane root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/fxml/MainMenu.fxml")));
        Scene scene = new Scene(root, 1280, 800);
        stage.setMinHeight(scene.getHeight() + 28 /*title bar height*/);
        stage.setMinWidth(scene.getWidth());
        stage.setScene(scene);
    }

    @FXML
    public void initialize() {
        borderPane.setOnMouseClicked(mouseEvent -> {
            Utils.playButtonClickSFX();
            borderPane.requestFocus();
        });
        handleLogOutButton();
        handleShopButton();
    }

    private void handleLogOutButton() {
        logOutButton.setOnMouseClicked(mouseEvent -> {
            try {
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
                ShopMenuView shopMenuView = new ShopMenuView();
                shopMenuView.setLoggedInPlayer(loggedInPlayer);
                shopMenuView.start(Utils.getStage());
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
    }
}
