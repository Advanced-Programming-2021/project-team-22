package controller.loginmenu;

import controller.Utils;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.stage.Popup;
import javafx.stage.Stage;
import model.Player;

import java.util.Timer;
import java.util.TimerTask;

public class LoginMenuController {
    public static LoginMenuMessages createUser(String username, String nickname, String password) {
        if (Player.getPlayerByUsername(username) != null) {
            LoginMenuMessages.setUsername(username);
            return LoginMenuMessages.USERNAME_EXISTS;
        }
        if (Player.isNicknameExist(nickname)) {
            LoginMenuMessages.setNickname(nickname);
            return LoginMenuMessages.NICKNAME_EXISTS;
        }
        if (username.equals("")) return LoginMenuMessages.INVALID_USERNAME;
        if (nickname.equals("")) return LoginMenuMessages.INVALID_NICKNAME;
        if (password.equals("")) return LoginMenuMessages.INVALID_PASSWORD;

        new Player(username, password, nickname);
        return LoginMenuMessages.USER_CREATED;
    }

    public static LoginMenuMessages loginUser(String username, String password) {
        if (!Player.isPasswordCorrect(username, password)) return LoginMenuMessages.UNMATCHED_USERNAME_AND_PASSWORD;
        return LoginMenuMessages.USER_LOGGED_IN;
    }

    public static void createCloseWindowShortcut(Scene scene) {
        KeyCombination keyCombination = new KeyCodeCombination(KeyCode.W, KeyCombination.META_DOWN);
        Runnable runnable = LoginMenuController::exitGame;
        scene.getAccelerators().put(keyCombination, runnable);
    }

    public static void exitGame() {
        showExitPopup();
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                System.exit(0);
            }
        }, 2000);
    }

    private static void showExitPopup() {
        Popup popup = createPopup("hope to see you again :)", "popup", "/view/css/LoginMenu.css");
        Stage stage = Utils.getStage();
        popup.setOnShown(e -> {
            popup.setX(stage.getX() + stage.getWidth() / 2 - popup.getWidth() / 2);
            popup.setY(stage.getY() + 2 * popup.getHeight());
        });
        popup.show(Utils.getStage());
    }

    public static Popup createPopup(String message, String styleClass, String address) {
        Popup popup = new Popup();
        popup.setAutoFix(true);
        popup.setAutoHide(true);
        popup.setHideOnEscape(true);
        Label label = new Label(message);
        label.getStylesheets().add(address);
        label.getStyleClass().add(styleClass);
        popup.getContent().add(label);
        return popup;
    }
}
