package view;

import controller.Utils;
import controller.shopmenu.ShopMenuController;
import controller.shopmenu.ShopMenuMessages;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import model.Player;
import model.cards.Card;

import java.awt.*;
import java.util.*;

public class ShopMenuView extends Application {
    private Player loggedInPlayer;

    public BorderPane borderPane;
    public GridPane gridPane;

    public void setLoggedInPlayer(Player loggedInPlayer) {
        this.loggedInPlayer = loggedInPlayer;
    }

    private static void showListOfCards() {
        TreeMap<String, Integer> listOfCards = Card.getListOfCards();
        for (String cardName : listOfCards.keySet()) {
            System.out.println(cardName + ": " + listOfCards.get(cardName));
        }
    }

    public void shopMenuView() {
        ShopMenuController shopMenuController = new ShopMenuController(loggedInPlayer);

        while (true) {
            String command = Utils.getScanner().nextLine().trim();
            ShopMenuMessages result = shopMenuController.findCommand(command);

            System.out.print(result.getMessage());

            if (result.equals(ShopMenuMessages.EXIT_SHOP_MENU)) break;
            else if (result.equals(ShopMenuMessages.SHOW_ALL_CARDS)) showListOfCards();
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        BorderPane root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/fxml/ShopMenu.fxml")));
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

        gridPane.getChildren().clear();
        gridPane.getColumnConstraints().clear();
        gridPane.getRowConstraints().clear();

        for(int i = 0; i < 2 * 100; i++) {
            ColumnConstraints column = new ColumnConstraints();
            column.setPercentWidth((gridPane.getMaxWidth() * 100) / (2 * 100 + 1));
            gridPane.getColumnConstraints().add(column);
        }

        for(int i = 0; i < 2 * 100; i++) {
            RowConstraints row = new RowConstraints();
            row.setPercentHeight((gridPane.getMaxHeight() * 100) / (2 * 100 + 1));
            gridPane.getRowConstraints().add(row);
        }

        HashMap<String, Card> hashMap = Card.getAllCards();
        Set<String> names = hashMap.keySet();
        ArrayList<String> arrayList = new ArrayList<>(names);
        int number = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 19; j++) {


//                new ImagePattern(new Image("/Project-Assets-1.0.0/Assets/Cards/SpellTrap/CallOfTheHaunted.jpg"));

                Rectangle rectangle = new Rectangle(70, 150);
                rectangle.setCursor(Cursor.HAND);
                rectangle.setFill(new ImagePattern(new Image(hashMap.get(arrayList.get(number)).getBackImageAddress())));
                ++number;
                gridPane.add(rectangle, j, i);

            }
        }
    }
}
