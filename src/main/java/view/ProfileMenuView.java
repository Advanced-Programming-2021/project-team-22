package view;

import controller.Utils;
import controller.profilemenu.ProfileMenuController;
import controller.profilemenu.ProfileMenuMessages;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Side;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.switchbutton.SwitchButton;
import model.switchbutton.SwitchButtonType;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class ProfileMenuView extends Application {
    public VBox musicVBox;
    public VBox sfxVBox;

    public HBox showUsername;
    public HBox showNickname;
    public ImageView leftImage;

    public BorderPane borderPane;
    public Button backButton;
    public Circle avatar;
    public Label EditAvatar;

    public TextField nickname;
    public ContextMenu nicknameContextMenu;
    public Button changeNicknameButton;

    public PasswordField oldPassword;
    public ContextMenu oldPasswordContextMenu;
    public PasswordField newPassword;
    public ContextMenu newPasswordContextMenu;
    public Button changePasswordButton;

    @Override
    public void start(Stage stage) throws Exception {
        BorderPane root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/fxml/ProfileMenu.fxml")));
        Scene scene = new Scene(root, 1280, 800);
        stage.setMinHeight(scene.getHeight() + 28 /*title bar height*/);
        stage.setMinWidth(scene.getWidth());
        stage.setScene(scene);
        stage.sizeToScene();
    }

    @FXML
    public void initialize() {
        handleMusicSwitchButton();
        handleSFXSwitchButton();

        handlePasswordFields();
        handleNicknameField();

        setChangePasswordButtonOnMouseClicked();
        setChangeNicknameButtonOnMouseClicked();

        handleSwitchPasswordFieldsWithShortcut();
        handleSwitchNicknameFieldWithShortcut();

        setBorderPaneOnMouseClicked();

        generalNodeSetOnMouseClicked(oldPassword);
        generalNodeSetOnMouseClicked(newPassword);
        generalNodeSetOnMouseClicked(nickname);

        handleAvatar();
        handleEditAvatar();

        handleShowUsername();
        handleShowNickName();

        leftImage.setImage(new Image("images/profilemenu/Chara002.dds20.png"));

        Utils.handleBackButton(backButton);
    }

    private void handleMusicSwitchButton() {
        SwitchButton musicSwitchButton = new SwitchButton(ProfileMenuController.getLoggedInPlayer().isPlayMusic(), SwitchButtonType.MUSIC);
        musicSwitchButton.setCursor(Cursor.HAND);
        musicVBox.getChildren().add(musicSwitchButton);
    }

    private void handleSFXSwitchButton() {
        SwitchButton sfxSwitchButton = new SwitchButton(ProfileMenuController.getLoggedInPlayer().isPlaySFX(), SwitchButtonType.SFX);
        sfxSwitchButton.setCursor(Cursor.HAND);
        sfxVBox.getChildren().add(sfxSwitchButton);
    }

    private void handlePasswordFields() {
        oldPassword.setMaxWidth(Utils.getStage().getWidth() * 0.3);
        newPassword.setMaxWidth(Utils.getStage().getWidth() * 0.3);
        oldPassword.managedProperty().bind(changePasswordButton.focusTraversableProperty().not());
        newPassword.managedProperty().bind(changePasswordButton.focusTraversableProperty().not());
    }

    private void handleNicknameField() {
        nickname.setMaxWidth(Utils.getStage().getWidth() * 0.3);
        nickname.managedProperty().bind(changeNicknameButton.focusTraversableProperty().not());
    }

    private void setChangePasswordButtonOnMouseClicked() {
        changePasswordButton.setOnMouseClicked(mouseEvent -> {
            if (ProfileMenuController.getLoggedInPlayer().isPlaySFX()) Utils.playButtonClickSFX();
            hideNicknameField();

            if (changePasswordButton.getText().equals("Change Password")) {
                changePasswordButton.setText("Confirm");
                oldPassword.requestFocus();
            }

            if (oldPassword.isVisible() && changePasswordButton.getText().equals("Confirm")) changePassword();

            oldPassword.managedProperty().bind(changePasswordButton.focusTraversableProperty());
            oldPassword.visibleProperty().bind(changePasswordButton.focusTraversableProperty());

            newPassword.managedProperty().bind(changePasswordButton.focusTraversableProperty());
            newPassword.visibleProperty().bind(changePasswordButton.focusTraversableProperty());
        });
    }

    private void changePassword() {
        String oldPasswordText = oldPassword.getText();
        String newPasswordText = newPassword.getText();
        ProfileMenuMessages profileMenuMessages = ProfileMenuController.changePassword(oldPasswordText, newPasswordText);

        switch (profileMenuMessages) {
            case WRONG_OLD_PASSWORD:
                showErrorOfField(oldPasswordContextMenu, oldPassword, ProfileMenuMessages.WRONG_OLD_PASSWORD.getMessage());
                break;
            case EMPTY_NEW_PASSWORD:
                showErrorOfField(newPasswordContextMenu, newPassword, ProfileMenuMessages.EMPTY_NEW_PASSWORD.getMessage());
                break;
            case SAME_PASSWORDS:
                showErrorOfField(newPasswordContextMenu, newPassword, ProfileMenuMessages.SAME_PASSWORDS.getMessage());
                break;
            case PASSWORD_CHANGED:
                setBackgroundColor(oldPassword, "green");
                setBackgroundColor(newPassword, "green");
                changePasswordButton.setText("Done!");
                oldPassword.setDisable(true);
                newPassword.setDisable(true);
                new Timeline(new KeyFrame(Duration.seconds(1), actionEvent -> hidePasswordFields())).play();
        }
    }

    private void setChangeNicknameButtonOnMouseClicked() {
        changeNicknameButton.setOnMouseClicked(mouseEvent -> {
            if (ProfileMenuController.getLoggedInPlayer().isPlaySFX()) Utils.playButtonClickSFX();
            hidePasswordFields();

            if (changeNicknameButton.getText().equals("Change Nickname")) {
                changeNicknameButton.setText("Confirm");
                nickname.requestFocus();
            }

            if (nickname.isVisible() && changeNicknameButton.getText().equals("Confirm")) changeNickname();

            nickname.managedProperty().bind(changeNicknameButton.focusTraversableProperty());
            nickname.visibleProperty().bind(changeNicknameButton.focusTraversableProperty());
        });
    }

    private void changeNickname() {
        ProfileMenuMessages profileMenuMessages = ProfileMenuController.changeNickname(nickname.getText());

        switch (profileMenuMessages) {
            case EMPTY_NICKNAME:
            case AVAILABLE_NICKNAME:
                showErrorOfField(nicknameContextMenu, nickname, profileMenuMessages.getMessage());
                break;
            case NICKNAME_CHANGED:
                setBackgroundColor(nickname, "green");
                changeNicknameButton.setText("Done!");
                nickname.setDisable(true);
                handleLastIndexOfShowNickName();

                new Timeline(new KeyFrame(Duration.seconds(1), actionEvent -> hideNicknameField())).play();
        }
    }

    private void showErrorOfField(ContextMenu contextMenu, Node node, String label) {
        contextMenu.getItems().clear();
        contextMenu.getItems().add(new MenuItem(label));
        contextMenu.show(node, Side.RIGHT, 4, 0);
        setBackgroundColor(node, "red");
    }

    private void handleSwitchPasswordFieldsWithShortcut() {
        oldPassword.setOnKeyPressed(event -> {
            if(event.getCode().equals(KeyCode.ENTER) || event.getCode().equals(KeyCode.TAB)
                    || event.getCode().equals(KeyCode.DOWN) || event.getCode().equals(KeyCode.UP))
                newPassword.requestFocus();
        });

        newPassword.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode().equals(KeyCode.TAB) || keyEvent.getCode().equals(KeyCode.UP) ||
                    keyEvent.getCode().equals(KeyCode.DOWN)) oldPassword.requestFocus();
            else if (keyEvent.getCode().equals(KeyCode.ENTER)) {
                changePasswordButton.requestFocus();
                changePassword();
            }
        });
    }

    private void handleSwitchNicknameFieldWithShortcut() {
        nickname.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode().equals(KeyCode.ENTER)) {
                changeNicknameButton.requestFocus();
                changeNickname();
            }
        });
    }

    private void setBorderPaneOnMouseClicked() {
        borderPane.setOnMouseClicked(mouseEvent -> {
            if (ProfileMenuController.getLoggedInPlayer().isPlaySFX()) Utils.playButtonClickSFX();
            borderPane.requestFocus();
            hidePasswordFields();
            hideNicknameField();
        });
    }

    private void hidePasswordFields() {
        oldPassword.clear();
        newPassword.clear();
        oldPassword.setDisable(false);
        newPassword.setDisable(false);

        setBackgroundColor(oldPassword, "white");

        oldPassword.managedProperty().bind(changePasswordButton.focusTraversableProperty().not());
        oldPassword.visibleProperty().bind(changePasswordButton.focusTraversableProperty().not());

        newPassword.managedProperty().bind(changePasswordButton.focusTraversableProperty().not());
        newPassword.visibleProperty().bind(changePasswordButton.focusTraversableProperty().not());

        changePasswordButton.setText("Change Password");
        setBackgroundColor(newPassword, "white");
    }

    private void hideNicknameField() {
        nickname.clear();
        nickname.setDisable(false);

        nickname.managedProperty().bind(changeNicknameButton.focusTraversableProperty().not());
        nickname.visibleProperty().bind(changeNicknameButton.focusTraversableProperty().not());

        changeNicknameButton.setText("Change Nickname");
        setBackgroundColor(nickname, "white");
    }

    private void setBackgroundColor(Node node, String color) {
        node.setStyle("-fx-background-color: " + color);
    }

    private void generalNodeSetOnMouseClicked(Node node) {
        node.setOnMouseClicked(mouseEvent -> {
            if (ProfileMenuController.getLoggedInPlayer().isPlaySFX()) Utils.playButtonClickSFX();
            setBackgroundColor(node, "white");
        });
    }

    private void handleAvatar() {
        Image image = Utils.convertToFxImage(ProfileMenuController.getLoggedInPlayer().getAvatar());
        avatar.setFill(new ImagePattern(image));
        avatar.setCursor(Cursor.HAND);

        avatar.setOnMouseEntered(mouseEvent -> EditAvatar.setVisible(true));
        avatar.setOnMouseExited(mouseEvent -> EditAvatar.setVisible(false));

        avatar.setOnMouseClicked(mouseEvent -> {
            Image changedImage = ProfileMenuController.changeAvatar();
            if (changedImage != null) avatar.setFill(new ImagePattern(changedImage));
        });
    }

    private void handleEditAvatar() {
        DropShadow dropShadow = new DropShadow();
        dropShadow.setColor(Color.BLACK);
        dropShadow.setInput(new Bloom());

        EditAvatar.setEffect(dropShadow);
        EditAvatar.setMouseTransparent(true);
    }

    private void handleShowUsername() {
        ObservableList<Node> children = showUsername.getChildren();
        ((Label) children.get(children.size() - 1)).setText(ProfileMenuController.getLoggedInPlayer().getUsername());
        handleFadeTransition(children);
    }

    private void handleShowNickName() {
        handleLastIndexOfShowNickName();
        handleFadeTransition(showNickname.getChildren());
    }

    private void handleLastIndexOfShowNickName() {
        ObservableList<Node> children = showNickname.getChildren();
        ((Label) children.get(children.size() - 1)).setText(ProfileMenuController.getLoggedInPlayer().getNickname());
    }

    private void handleFadeTransition(ObservableList<Node> observableList) {
        double delay = 0;
        for (Node node : observableList) {
            FadeTransition fadeTransition = new FadeTransition();
            fadeTransition.setNode(node);
            fadeTransition.setDelay(Duration.seconds(delay));
            fadeTransition.setDuration(Duration.seconds(2.0));
            fadeTransition.setFromValue(0);
            fadeTransition.setToValue(1);

            fadeTransition.play();
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    node.setVisible(true);
                }
            }, (int) (delay * 1000));
            delay += 0.4;
        }
    }
}
