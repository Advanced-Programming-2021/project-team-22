package controller;

import javafx.scene.control.Button;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import model.Player;
import model.cards.Card;
import model.cards.magiccard.MagicCard;
import model.cards.monstercard.MonsterCard;
import view.MainMenuView;

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

    static {
        scanner = new Scanner(System.in);
    }

    public static Scanner getScanner() {
        return scanner;
    }

    public static void resetScanner(String input) {
        scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
    }

    public static ByteArrayOutputStream setByteArrayOutputStream() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        return outContent;
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

    public static void showCard(String cardName) {
        Card card = Card.getCardByName(cardName);
        if (card == null) System.out.println("unavailable showSelectedCard");
        else if (card instanceof MonsterCard) System.out.println( ((MonsterCard) card).show() );
        else System.out.println( ((MagicCard) card).show() );
    }

    public static void playButtonClickSFX() {
        buttonClickSFX.play();
        buttonClickSFX.setOnEndOfMedia(() -> buttonClickSFX.stop());
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
}
