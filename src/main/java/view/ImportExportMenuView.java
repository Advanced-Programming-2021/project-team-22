package view;

import controller.Utils;
import controller.importexportmenu.ImportExportMenuController;
import controller.importexportmenu.ImportExportMenuMessages;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.cards.Card;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class ImportExportMenuView extends Application {
    private Card selectedCard;
    private Rectangle rectangleOfSelectedCard;

    {
        selectedCard = null;
        rectangleOfSelectedCard = null;
    }

    public Button importCardButton;
    public Button exportCardButton;
    public Label message;
    public GridPane gridPane;
    public ScrollPane scrollPane;
    public Rectangle card;
    public Button backButton;

    @Override
    public void start(Stage stage) throws Exception {
        BorderPane root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/fxml/ImportExportMenu.fxml")));
        Scene scene = new Scene(root, 1280, 800);
        stage.setMinHeight(scene.getHeight() + 28 /*title bar height*/);
        stage.setMinWidth(scene.getWidth());
        stage.setScene(scene);
        stage.sizeToScene();
    }

    @FXML
    public void initialize() {
        handleGridPane();
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        handleExportCardButton();
        handleImportCardButton();

        Utils.handleBackButton(backButton);
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

                setRectangleOnMouseClicked(rectangle, card);
                gridPane.add(rectangle, j, i);
                ++number;
            }
        }
    }

    private void setRectangleOnMouseClicked(Rectangle rectangle, Card card) {
        rectangle.setOnMouseClicked(mouseEvent -> {
            if (ImportExportMenuController.getLoggedInPlayer().isPlaySFX()) Utils.playButtonClickSFX();

            selectedCard = card;
            if (rectangleOfSelectedCard != null) rectangleOfSelectedCard.setEffect(null);
            rectangleOfSelectedCard = rectangle;
            DropShadow dropShadow = new DropShadow();
            dropShadow.setColor(Color.WHITE);
            rectangle.setEffect(dropShadow);
            rectangle.requestFocus();
            message.setText("");
            this.card.setVisible(false);
            exportCardButton.setDisable(false);
        });
    }

    private void handleExportCardButton() {
        exportCardButton.setOnMouseClicked(mouseEvent -> {
            if (ImportExportMenuController.getLoggedInPlayer().isPlaySFX()) Utils.playButtonClickSFX();
            message.setText("");
            card.setVisible(false);

            if (gridPane.isVisible()) {
                ImportExportMenuController.exportCard(selectedCard);
                message.setText("exported successfully");
            } else {
                exportCardButton.setDisable(true);
            }

            gridPane.setVisible(!gridPane.isVisible());
        });
    }

    private void handleImportCardButton() {
        FileChooser fileChooser = new FileChooser();

        importCardButton.setOnMouseClicked(e -> {
            gridPane.setVisible(false);
            message.setText("");
            card.setVisible(false);

            File selectedFile = fileChooser.showOpenDialog(Utils.getStage());
            fileChooser.setInitialDirectory(new File("src/main/resources/cards"));
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Json Files", "*.json"));

            String fileName = selectedFile.getName();
            String cardName = fileName.substring(0, fileName.length() - 5 /*.json length*/);
            ImportExportMenuMessages result = ImportExportMenuController.importCard(selectedFile, cardName);
            message.setText(result.getMessage());

            if (result.equals(ImportExportMenuMessages.IMPORT_SUCCESSFULLY)) {
                Image image = new Image(Card.getAllCards().get(cardName).getFrontImageAddress());
                card.setFill(new ImagePattern(image));
                card.setVisible(true);
            }
        });

    }
}
