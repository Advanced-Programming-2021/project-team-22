package view;

import controller.Utils;
import controller.loginmenu.LoginMenuController;
import controller.loginmenu.LoginMenuMessages;
import controller.mainmenu.MainMenuController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Player;

public class LoginMenuView extends Application {
    public BorderPane borderPane;
    public TextField username;
    public TextField nickname;
    public PasswordField invisiblePassword;
    public TextField visiblePassword;
    public Button signUpButton;
    public Button logInButton;
    public Button exitGameButton;
    public CheckBox checkBox;
    public Label message;

    @Override
    public void start(Stage stage) throws Exception {
        BorderPane root = FXMLLoader.load(getClass().getResource("/view/fxml/LoginMenu.fxml"));
        Scene scene = new Scene(root, 1280, 800);
        stage.setMinHeight(scene.getHeight() + 28 /*title bar height*/);
        stage.setMinWidth(scene.getWidth());
        stage.setScene(scene);
        stage.sizeToScene();

        LoginMenuController.createCloseWindowShortcut(scene);
        stage.show();
    }

    @FXML
    public void initialize() {
        exitGameButton.setOnMouseClicked(mouseEvent -> {
            Utils.playButtonClickSFX();
            LoginMenuController.exitGame();
        });
        exitGameButton.setOnAction(mouseEvent -> exitGameButton.setStyle("-fx-text-fill: red"));
        borderPane.setOnMouseClicked(mouseEvent -> {
            Utils.playButtonClickSFX();
            borderPane.requestFocus();
        });
        handleVisibilityOfPasswordField();
        signUpButton.setOnMouseClicked(mouseEvent -> {
            Utils.playButtonClickSFX();
            signUp();
        });
        logInButton.setOnMouseClicked(mouseEvent -> {
            Utils.playButtonClickSFX();
            logIn();
        });
        checkBox.setCursor(Cursor.HAND);
    }

    private void handleVisibilityOfPasswordField() {
        checkBox.setOnMouseClicked(mouseEvent -> {
            Utils.playButtonClickSFX();

            visiblePassword.managedProperty().bind(checkBox.selectedProperty());
            visiblePassword.visibleProperty().bind(checkBox.selectedProperty());

            invisiblePassword.managedProperty().bind(checkBox.selectedProperty().not());
            invisiblePassword.visibleProperty().bind(checkBox.selectedProperty().not());

            visiblePassword.textProperty().bindBidirectional(invisiblePassword.textProperty());
        });
    }

    private void signUp() {
        LoginMenuMessages loginMenuMessages;
        if (checkBox.isSelected())
            loginMenuMessages = LoginMenuController.createUser(username.getText(), nickname.getText(), visiblePassword.getText());
        else
            loginMenuMessages = LoginMenuController.createUser(username.getText(), nickname.getText(), invisiblePassword.getText());

        message.setText(loginMenuMessages.getMessage());
    }

    private void logIn() {
        LoginMenuMessages loginMenuMessages;
        String username = this.username.getText();
        if (checkBox.isSelected())
            loginMenuMessages = LoginMenuController.loginUser(username, visiblePassword.getText());
        else
            loginMenuMessages = LoginMenuController.loginUser(username, invisiblePassword.getText());

        if (!loginMenuMessages.equals(LoginMenuMessages.USER_LOGGED_IN)) message.setText(loginMenuMessages.getMessage());
        else {
            try {
                MainMenuController.setLoggedInPlayer(Player.getPlayerByUsername(username));
                new MainMenuView().start(Utils.getStage());
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }

    }
}
