package controller;

import com.talanlabs.avatargenerator.*;
import com.talanlabs.avatargenerator.eightbit.EightBitAvatar;
import com.talanlabs.avatargenerator.layers.backgrounds.ColorPaintBackgroundLayer;
import com.talanlabs.avatargenerator.smiley.SmileyAvatar;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import model.Player;
import model.cards.Card;
import model.cards.magiccard.MagicCard;
import model.cards.monstercard.MonsterCard;
import view.MainMenuView;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    private static Scanner scanner;
    private static Stage stage;
    private static MediaPlayer buttonClickSFX;
    private static MediaPlayer backgroundMusicPlayer;

    static {
        scanner = new Scanner(System.in);
    }

    public static Scanner getScanner() {
        return scanner;
    }

    public static Stage getStage() {
        return stage;
    }

    public static void setStage(Stage stage) {
        Utils.stage = stage;
    }

    public static Matcher getMatcher(String regex, String command) {
        return Pattern.compile(regex).matcher(command);
    }

    public static void setButtonClickSFX(MediaPlayer buttonClickSFX) {
        Utils.buttonClickSFX = buttonClickSFX;
    }

    public static void playButtonClickSFX() {
        buttonClickSFX.play();
        buttonClickSFX.setOnEndOfMedia(() -> buttonClickSFX.stop());
    }

    public static MediaPlayer getBackgroundMusicPlayer() {
        return backgroundMusicPlayer;
    }

    public static void setBackgroundMusicPlayer(MediaPlayer backgroundMusicPlayer) {
        Utils.backgroundMusicPlayer = backgroundMusicPlayer;
    }

    public static void handleBackButton(Button backButton) {
        backButton.setOnMouseClicked(mouseEvent -> {
            try {
                playButtonClickSFX();
                new MainMenuView().start(Utils.getStage());
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
    }

    public static BufferedImage createAvatar(String username) {
        double randomNumber = Math.random() * 8;
        Avatar avatar;
        if (randomNumber < 1) {
            avatar = TriangleAvatar.newAvatarBuilder().build();
        } else if (randomNumber < 2) {
            avatar = SquareAvatar.newAvatarBuilder().build();
        } else if (randomNumber < 3) {
            avatar = IdenticonAvatar.newAvatarBuilder().build();
        } else if (randomNumber < 4) {
                avatar = GitHubAvatar.newAvatarBuilder().build();
        } else if (randomNumber < 5) {
            avatar = SmileyAvatar.newEyeMouthAvatarBuilder().build();
        } else if (randomNumber < 6) {
            avatar = SmileyAvatar.newDefaultAvatarBuilder().build();
        } else if (randomNumber < 7) {
            avatar = EightBitAvatar.newMaleAvatarBuilder().build();
        } else {
            avatar = EightBitAvatar.newFemaleAvatarBuilder().build();
        }

        try {
            return avatar.create(username.hashCode());
        } catch (Exception ignored) {
            avatar = TriangleAvatar.newAvatarBuilder().build();
            return avatar.create(username.hashCode());
        }
    }

    public static Image convertToFxImage(BufferedImage image) {
        WritableImage wr = null;
        if (image != null) {
            wr = new WritableImage(image.getWidth(), image.getHeight());
            PixelWriter pw = wr.getPixelWriter();
            for (int x = 0; x < image.getWidth(); x++) {
                for (int y = 0; y < image.getHeight(); y++) {
                    pw.setArgb(x, y, image.getRGB(x, y));
                }
            }
        }

        return new ImageView(wr).getImage();
    }

    public static void showCard(String cardName) {
        Card card = Card.getCardByName(cardName);
        if (card == null) System.out.println("unavailable showSelectedCard");
        else if (card instanceof MonsterCard) System.out.println( ((MonsterCard) card).show() );
        else System.out.println( ((MagicCard) card).show() );
    }
}
