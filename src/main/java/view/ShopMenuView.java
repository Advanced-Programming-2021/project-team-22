package view;

import controller.Utils;
import controller.shopmenu.ShopMenuController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.effect.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import model.cards.Card;

import java.util.*;

public class ShopMenuView extends Application {
    private Card selectedCard;
    private Rectangle rectangleOfSelectedCard;
    private Label labelOfSelectedCard;

    {
        selectedCard = null;
        rectangleOfSelectedCard = null;
        labelOfSelectedCard = null;
    }

    public BorderPane borderPane;
    public ScrollPane scrollPane;
    public GridPane gridPane;
    public Rectangle showSelectedCard;
    public Label money;
    public Label price;
    public Button buyButton;
    public Label message;
    public TextField cheatField;
    public ImageView imageView;
    public Button backButton;

    @Override
    public void start(Stage stage) throws Exception {
        BorderPane root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/fxml/ShopMenu.fxml")));
        Scene scene = new Scene(root, 1280, 800);
        stage.setMinHeight(scene.getHeight() + 28 /*title bar height*/);
        stage.setMinWidth(scene.getWidth());
        stage.setScene(scene);

        handleCheatField(scene);
    }

    @FXML
    public void initialize() {
        setFillForRectangle(showSelectedCard, Card.getBackImageAddress());
        handleStyleOfBuyButton();
        money.setText(String.valueOf(ShopMenuController.getLoggedInPlayer().getMoney()));
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        handleBorderPaneSetOnMouseClicked();
        handleGridPane();
        handleBuyButtonSetOnMouseClicked();
        Utils.handleBackButton(backButton);
        imageView.setImage(new Image("/images/shopmenu/_images_chara_img_pegasus.dds.png"));
    }

    private void handleBorderPaneSetOnMouseClicked() {
        borderPane.setOnMouseClicked(mouseEvent -> {
            Utils.playButtonClickSFX();
            cheatField.setVisible(false);
            cheatField.setText("");
        });
    }

    private void handleGridPane() {
        gridPane.setHgap(7);
        gridPane.setVgap(7);

        for(int i = 0; i < 4; i++) {
            ColumnConstraints column = new ColumnConstraints();
            column.setPercentWidth(136);
            gridPane.getColumnConstraints().add(column);
        }

        for(int i = 0; i < 19; i++) {
            RowConstraints row = new RowConstraints();
            row.setPercentHeight(198.346793);
            gridPane.getRowConstraints().add(row);
        }

        HashMap<String, Card> allCards = Card.getAllCards();
        ArrayList<String> nameOfCards = new ArrayList<>(allCards.keySet());
        nameOfCards.sort(String::compareTo);
        int number = 0;
        for (int i = 0; i < 19; i++) {
            for (int j = 0; j < 4; j++) {

                Card card = allCards.get(nameOfCards.get(number));
                Rectangle rectangle = new Rectangle(136, 198.346793);
                rectangle.setFill(new ImagePattern(new Image(card.getFrontImageAddress())));
                rectangle.setCursor(Cursor.HAND);

                Label label = createNumberOfAvailableCardLabel(card.getName());
                VBox vBox = new VBox();
                vBox.getChildren().add(rectangle);
                vBox.getChildren().add(label);

                setRectangleOnMouseClicked(rectangle, card, label);
                gridPane.add(vBox, j, i);
                ++number;
            }
        }
    }

    private void setRectangleOnMouseClicked(Rectangle rectangle, Card card, Label label) {
        rectangle.setOnMouseClicked(mouseEvent -> {
            Utils.playButtonClickSFX();
            selectedCard = card;
            if (rectangleOfSelectedCard != null) rectangleOfSelectedCard.setEffect(null);
            rectangleOfSelectedCard = rectangle;
            DropShadow dropShadow = new DropShadow();
            dropShadow.setColor(Color.WHITE);
            rectangle.setEffect(dropShadow);
            setFillForRectangle(showSelectedCard, card.getFrontImageAddress());
            price.setText(String.valueOf(card.getPrice()));
            handleStyleOfBuyButton();
            rectangle.requestFocus();
            message.setText("");
            labelOfSelectedCard = label;
        });
    }

    private void setFillForRectangle(Rectangle rectangle, String address) {
        rectangle.setFill(new ImagePattern(new Image(address)));
    }

    private Label createNumberOfAvailableCardLabel(String cardName) {
        Label label = new Label("Number: " + ShopMenuController.getLoggedInPlayer().getNumberOfAvailableCard(cardName));
        label.setStyle("-fx-font-family: 'American Typewriter'; -fx-text-fill: yellow");
        return label;
    }

    private void handleStyleOfBuyButton() {
        buyButton.setDisable(selectedCard == null || selectedCard.getPrice() > ShopMenuController.getLoggedInPlayer().getMoney());
    }

    private void handleBuyButtonSetOnMouseClicked() {
        buyButton.setOnMouseClicked(mouseEvent -> {
            Utils.playButtonClickSFX();
            ShopMenuController.buyACard(selectedCard);
            message.setText("Card added successfully");
            money.setText(String.valueOf(ShopMenuController.getLoggedInPlayer().getMoney()));
            labelOfSelectedCard.setText("Number: " +
                    ShopMenuController.getLoggedInPlayer().getNumberOfAvailableCard(selectedCard.getName()));
            handleStyleOfBuyButton();
            disappearCheatField(cheatField);
        });
    }

    private void handleCheatField(Scene scene) {
        TextField cheatField = (TextField) scene.lookup("#cheatField");
        KeyCombination keyCombination = new KeyCodeCombination(KeyCode.C, KeyCombination.META_DOWN, KeyCombination.SHIFT_DOWN);
        Runnable runnable = () -> cheatField.setVisible(!cheatField.isVisible());
        scene.getAccelerators().put(keyCombination, runnable);

        cheatField.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode().equals(KeyCode.ENTER)) {
                ShopMenuController.cheatCodeIncreaseMoney(cheatField.getText());
                Label money = (Label) scene.lookup("#money");
                money.setText(String.valueOf(ShopMenuController.getLoggedInPlayer().getMoney()));
                disappearCheatField(cheatField);
            }
        });
    }

    private void disappearCheatField(TextField cheatField) {
        cheatField.setVisible(false);
        cheatField.setText("");
    }
}
