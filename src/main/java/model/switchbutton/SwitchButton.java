package model.switchbutton;

import controller.Database;
import controller.Utils;
import controller.profilemenu.ProfileMenuController;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import model.Player;

public class SwitchButton extends StackPane {
    private final Rectangle back = new Rectangle(30, 10, Color.RED);
    private final Button button = new Button();
    private String buttonStyleOff = "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 0.2, 0.0, 0.0, 2); -fx-background-color: WHITE;";
    private String buttonStyleOn = "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 0.2, 0.0, 0.0, 2); -fx-background-color: #00893d;";
    private boolean state;

    public SwitchButton(boolean state, SwitchButtonType switchButtonType) {
        this.state = state;
        init(state);
        button.setFocusTraversable(false);

        if (switchButtonType.equals(SwitchButtonType.MUSIC)) {
            EventHandler<Event> musicClick = getMusicClick();
            setOnMouseClicked(musicClick);
            button.setOnMouseClicked(musicClick);
        } else if (switchButtonType.equals(SwitchButtonType.SFX)) {
            EventHandler<Event> sfxClick = getSFXClick();
            setOnMouseClicked(sfxClick);
            button.setOnMouseClicked(sfxClick);
        }
    }

    private void init(boolean state) {
        getChildren().addAll(back, button);
        setMinSize(30, 15);
        back.maxWidth(30);
        back.minWidth(30);
        back.maxHeight(10);
        back.minHeight(10);
        back.setArcHeight(back.getHeight());
        back.setArcWidth(back.getHeight());
        double r = 2.0;
        button.setShape(new Circle(r));
        button.setMaxSize(15, 15);
        button.setMinSize(15, 15);
        handleState(state);
    }

    public EventHandler<Event> getMusicClick() {
        return e -> {
            Player player = ProfileMenuController.getLoggedInPlayer();
            MediaPlayer backgroundMusicPlayer = Utils.getBackgroundMusicPlayer();
            if (player.isPlayMusic()) {
                backgroundMusicPlayer.setMute(true);
                player.setPlayMusic(false);
            } else {
                backgroundMusicPlayer.stop();
                backgroundMusicPlayer.setMute(false);
                backgroundMusicPlayer.play();
                player.setPlayMusic(true);
            }

            Database.updatePlayerInformationInDatabase(player);
            handleState(!this.state);
            state = !state;
        };
    }

    private void handleState(boolean state) {
        if (state) {
            button.setStyle(buttonStyleOn);
            back.setFill(Color.valueOf("#80C49E"));
            setAlignment(button, Pos.CENTER_RIGHT);
        } else {
            button.setStyle(buttonStyleOff);
            back.setFill(Color.valueOf("#ced5da"));
            setAlignment(button, Pos.CENTER_LEFT);
        }
    }

    public EventHandler<Event> getSFXClick() {
        return e -> {
            Player player = ProfileMenuController.getLoggedInPlayer();
            player.setPlaySFX(!player.isPlaySFX());

            Database.updatePlayerInformationInDatabase(player);
            handleState(!this.state);
            state = !state;
        };
    }

    public Button getButton() {
        return button;
    }
}