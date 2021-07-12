package view;

import controller.Utils;
import controller.deckmenu.DeckMenuController;
import controller.deckmenu.DeckMenuMessages;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.Blend;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Deck;
import model.cards.Card;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;

public class DeckMenuView extends Application {
    private Deck selectedDeck;
    private ImageView imageViewOfSelectedDeck;
    private Label deckInformationOfSelectedDeck;
    private ImageView imageViewOfActivatedDeck;
    private Card draggedCard;
    private Timeline showingDeckInformationTimeline;

    {
        selectedDeck = null;
        imageViewOfSelectedDeck = null;
        deckInformationOfSelectedDeck = null;
        imageViewOfActivatedDeck = null;
        draggedCard = null;
        showingDeckInformationTimeline = null;
    }

    public BorderPane borderPane;
    public ScrollPane deckScrollPane;
    public GridPane deckGridPane;
    public ScrollPane allCardsScrollPane;
    public GridPane allCardsGridPane;
    public GridPane mainCards;
    public Label mainCardsMessage;
    public GridPane sideCards;
    public Label sideCardsMessage;

    public TextField createDeckField;
    public Button createDeckButton;
    public Label createDeckMessage;
    public Button deleteDeckButton;
    public Label deleteDeckMessage;
    public Button activateDeckButton;
    public Label activateDeckMessage;

    public Button backButton;

    @Override
    public void start(Stage stage) throws Exception {
        BorderPane root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/fxml/DeckMenu.fxml")));
        Scene scene = new Scene(root, 1410, 860);
        stage.setMinHeight(scene.getHeight() + 28 /*title bar height*/);
        stage.setMinWidth(scene.getWidth());
        stage.setScene(scene);
        stage.sizeToScene();

        createCreateDeckShortcut(scene);
    }

    @FXML
    public void initialize() {
        handleDecks();
        handleMainCards();
        handleSideCards();
        handleBoughtCards();
        handleCreateDeckButton();
        handleDeleteDeckButton();
        handleActivateDeckButton();
        Utils.handleBackButton(backButton);
        createDeckField.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode().equals(KeyCode.ENTER)) createDeckButton.fire();
        });
    }

    public void createCreateDeckShortcut(Scene scene) {
        KeyCombination keyCombination = new KeyCodeCombination(KeyCode.N, KeyCombination.META_DOWN);
        Runnable runnable = () -> ((Button) scene.lookup("#createDeckButton")).fire();
        scene.getAccelerators().put(keyCombination, runnable);
    }

    private void handleDecks() {
        deckScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        deckScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        deckGridPane.getChildren().clear();
        deckGridPane.getColumnConstraints().clear();
        deckGridPane.getRowConstraints().clear();
        deckGridPane.setHgap(7);
        deckGridPane.setVgap(7);

        ArrayList<Deck> decks = DeckMenuController.getLoggedInPlayer().getAllDecks();
        if (decks.size() == 0) {
            deckGridPane.setStyle("-fx-background-image: url('/images/deckmenu/Chara002.dds13.png'); -fx-background-size: stretch;");
            return;
        }
        deckGridPane.setStyle("-fx-background-color: transparent;");

        for(int i = 0; i < 2; i++) {
            ColumnConstraints column = new ColumnConstraints();
            column.setPrefWidth(91);
            deckGridPane.getColumnConstraints().add(column);
        }

        decks.sort(Comparator.comparing(Deck::getName));
        int rows = (int) Math.ceil((double) decks.size() / 2);
        for(int i = 0; i < rows; i++) {
            RowConstraints row = new RowConstraints();
            row.setPrefHeight(60);
            deckGridPane.getRowConstraints().add(row);
        }

        int number = 0;
        out:
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < 2; j++) {
                if (number >= decks.size()) break out;
                Deck deck = decks.get(number);

                ImageView imageView = new ImageView();
                imageView.setFitHeight(60);
                imageView.setFitWidth(91);
                imageView.setCursor(Cursor.HAND);

                setImageOfDeck(imageView, deck);
                Insets labelInsets = new Insets(35, 5, 0, 5);
                Label deckName = setShowingDeckName(deck, labelInsets);
                setOnMouseKeyPressedForDeck(imageView);
                Label deckInformation = setShowingDeckInformation(imageView, deck, deckName, labelInsets);
                imageView.setOnMouseClicked(mouseEvent -> setOnMouseClickedForDeck(deck, imageView, deckInformation));

                deckGridPane.add(imageView, j, i);
                deckGridPane.add(deckName, j, i);
                deckGridPane.add(deckInformation, j, i);
                ++number;
            }
        }
    }

    private void setImageOfDeck(ImageView imageView, Deck deck) {
        Deck activatedDeck = DeckMenuController.getLoggedInPlayer().getActivatedDeck();

        if (activatedDeck == null) imageViewOfActivatedDeck = null;
        if (deck.equals(activatedDeck)) {
            imageViewOfActivatedDeck = imageView;
            imageView.setImage(new Image("/images/deckmenu/whiteDeck.png"));
        } else imageView.setImage(new Image("/images/deckmenu/yellowDeck.png"));
    }

    private Label setShowingDeckName(Deck deck, Insets labelInsets) {
        Label deckName = new Label(deck.getName());
        deckName.setMouseTransparent(true);
        deckName.setPadding(labelInsets);
        deckName.setCursor(Cursor.HAND);
        deckName.setStyle("-fx-text-fill: #0048ff; -fx-font-family: 'American Typewriter'; -fx-font-size: 13px");

        return deckName;
    }

    private void setOnMouseClickedForDeck(Deck deck, ImageView imageView, Label deckInformation) {
        if (DeckMenuController.getLoggedInPlayer().isPlaySFX()) Utils.playButtonClickSFX();
        if (imageViewOfSelectedDeck != null) imageViewOfSelectedDeck.setEffect(null);

        selectedDeck = deck;
        imageViewOfSelectedDeck = imageView;
        deckInformationOfSelectedDeck = deckInformation;
        imageView.requestFocus();

        imageView.setEffect(new Bloom());
        showMainCards(deck);
        showSideCards(deck);

        createDeckMessage.setText("");
        createDeckField.setVisible(false);
        createDeckField.clear();
        deleteDeckMessage.setText("");
        activateDeckMessage.setText("");
        mainCardsMessage.setText("");
        sideCardsMessage.setText("");
    }

    private void setOnMouseKeyPressedForDeck(ImageView imageView) {
        imageView.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode().equals(KeyCode.BACK_SPACE))
                deleteDeckButton.fire();
        });
    }

    private Label setShowingDeckInformation(ImageView imageView, Deck deck, Label deckName, Insets labelInsets) {
        Label deckInformation = new Label(getDeckInformation(deck));
        deckInformation.setMouseTransparent(true);
        deckInformation.setVisible(false);
        deckInformation.setPadding(labelInsets);
        deckInformation.setCursor(Cursor.HAND);
        deckInformation.setStyle("-fx-text-fill: #00709a; -fx-font-family: 'American Typewriter'; -fx-font-size: 13px");
        deckInformation.setEffect(new Blend());

        imageView.setOnMouseEntered(mouseEvent -> {
            showingDeckInformationTimeline = new Timeline(new KeyFrame(Duration.seconds(1), actionEvent -> {
                deckInformation.setVisible(true);
                deckName.setVisible(false);
            }));
            showingDeckInformationTimeline.play();
        });

        imageView.setOnMouseExited(mouseEvent -> {
            showingDeckInformationTimeline.stop();
            deckInformation.setVisible(false);
            deckName.setVisible(true);

        });

        return deckInformation;
    }

    private String getDeckInformation(Deck deck) {
        return "M: " + deck.getMainCards().size() + "    S: " + deck.getSideCards().size();
    }

    private void handleMainCards() {
        mainCards.setVgap(7);
        mainCards.setHgap(7);

        for(int i = 0; i < 12; i++) {
            ColumnConstraints column = new ColumnConstraints();
            column.setPrefWidth(78.9375);
            mainCards.getColumnConstraints().add(column);
        }

        for(int i = 0; i < 5; i++) {
            RowConstraints row = new RowConstraints();
            row.setPrefHeight(115.125);
            mainCards.getRowConstraints().add(row);
        }
    }

    private void handleSideCards() {
        sideCards.setHgap(7);

        for(int i = 0; i < 15; i++) {
            ColumnConstraints column = new ColumnConstraints();
            column.setPrefWidth(61);
            sideCards.getColumnConstraints().add(column);
        }

        for(int i = 0; i < 1; i++) {
            RowConstraints row = new RowConstraints();
            row.setPrefHeight(115.125);
            sideCards.getRowConstraints().add(row);
        }
    }

    private void showMainCards(Deck deck) {
        mainCards.getChildren().clear();
        mainCards.getColumnConstraints().clear();
        mainCards.getRowConstraints().clear();
        if (deck == null) return;
        deck.getMainCards().sort(Comparator.comparing(Card::getName));

        int number = 0;
        out:
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 12; j++) {
                if (number >= deck.getMainCards().size()) break out;
                Card card = deck.getMainCards().get(number);

                ImageView imageView = new ImageView();
                imageView.setFitWidth(78.9375);
                imageView.setFitHeight(115.125);
                imageView.setImage(new Image(card.getFrontImageAddress()));
                imageView.setCursor(Cursor.HAND);

                handleDragAndDropFromGridPane(imageView, card);

                mainCards.add(imageView, j, i);
                ++number;
            }
        }

        deckInformationOfSelectedDeck.setText(getDeckInformation(deck));
    }

    private void showSideCards(Deck deck) {
        sideCards.getChildren().clear();
        sideCards.getColumnConstraints().clear();
        sideCards.getRowConstraints().clear();
        if (deck == null) return;
        deck.getSideCards().sort(Comparator.comparing(Card::getName));

        int number = 0;
        for (int i = 0; i < 15; i++) {
            if (number >= deck.getSideCards().size()) break;
            Card card = deck.getSideCards().get(number);

            ImageView imageView = new ImageView();
            imageView.setFitWidth(61);
            imageView.setFitHeight(115.125);
            imageView.setImage(new Image(card.getFrontImageAddress()));
            imageView.setCursor(Cursor.HAND);

            handleDragAndDropFromGridPane(imageView, card);

            sideCards.add(imageView, i, 1);
            ++number;
        }

        deckInformationOfSelectedDeck.setText(getDeckInformation(deck));
    }

    private void handleBoughtCards() {
        allCardsScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        allCardsScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        allCardsGridPane.getChildren().clear();
        allCardsGridPane.getColumnConstraints().clear();
        allCardsGridPane.getRowConstraints().clear();
        allCardsGridPane.setVgap(7);

        ArrayList<Card> shownBoughtCards = DeckMenuController.getLoggedInPlayer().getDifferentBoughCards();
        if (shownBoughtCards.size() == 0) {
            allCardsGridPane.setStyle("-fx-background-image: url('/images/deckmenu/Chara005.dds8.png'); -fx-background-size: stretch;");
            return;
        }
        allCardsGridPane.setStyle("-fx-background-color: transparent;");

        for(int i = 0; i < 1; i++) {
            ColumnConstraints column = new ColumnConstraints();
            column.setPrefWidth(105.25);
            allCardsGridPane.getColumnConstraints().add(column);
        }

        for(int i = 0; i < shownBoughtCards.size(); i++) {
            RowConstraints row = new RowConstraints();
            row.setPrefHeight(170);
            allCardsGridPane.getRowConstraints().add(row);
        }

        for (int i = 0; i < shownBoughtCards.size(); i++) {

            Card card = shownBoughtCards.get(i);

            ImageView imageView = new ImageView();
            imageView.setFitWidth(105.25);
            imageView.setFitHeight(153.5);
            imageView.setImage(new Image(card.getFrontImageAddress()));
            imageView.setCursor(Cursor.HAND);

            int numberOfAvailableCard = DeckMenuController.getLoggedInPlayer().getNumberOfAvailableCard(card.getName());
            Label label = new Label("Number: " + numberOfAvailableCard);
            label.setStyle("-fx-font-family: 'American Typewriter'; -fx-text-fill: yellow");

            VBox vBox = new VBox();
            vBox.getChildren().add(imageView);
            vBox.getChildren().add(label);

            allCardsGridPane.add(vBox, 1, i);
            handleDragAndDropFromBoughtCards(imageView, card);
        }
    }

    private void handleCreateDeckButton() {
        createDeckButton.setOnAction(mouseEvent -> {
            if (DeckMenuController.getLoggedInPlayer().isPlaySFX()) Utils.playButtonClickSFX();

            if (!createDeckField.isVisible()) {
                createDeckField.setVisible(true);
                createDeckField.requestFocus();
            } else {
                DeckMenuMessages result = DeckMenuController.createDeck(createDeckField.getText());
                createDeckMessage.setText(result.getMessage());
                if (result.equals(DeckMenuMessages.DECK_CREATED)) {
                    createDeckField.setVisible(false);
                    createDeckField.clear();
                    handleDecks();
                }
            }

            deleteDeckMessage.setText("");
            activateDeckMessage.setText("");
            mainCardsMessage.setText("");
            sideCardsMessage.setText("");
        });
    }

    private void handleDeleteDeckButton() {
        deleteDeckButton.setOnAction(mouseEvent -> {
            if (DeckMenuController.getLoggedInPlayer().isPlaySFX()) Utils.playButtonClickSFX();

            DeckMenuMessages result = DeckMenuController.deleteDeck(selectedDeck);
            deleteDeckMessage.setText(result.getMessage());
            if (result.equals(DeckMenuMessages.DECK_DELETED)) {
                handleDecks();
                selectedDeck = null;
                imageViewOfSelectedDeck = null;
                showMainCards(null);
                showSideCards(null);
                handleBoughtCards();
            }

            createDeckMessage.setText("");
            createDeckField.setVisible(false);
            createDeckField.clear();
            activateDeckMessage.setText("");
            mainCardsMessage.setText("");
            sideCardsMessage.setText("");
        });
    }

    private void handleActivateDeckButton() {
        activateDeckButton.setOnMouseClicked(mouseEvent -> {
            if (DeckMenuController.getLoggedInPlayer().isPlaySFX()) Utils.playButtonClickSFX();

            DeckMenuMessages result = DeckMenuController.activateADeck(selectedDeck);
            activateDeckMessage.setText(result.getMessage());
            if (result.equals(DeckMenuMessages.DECK_ACTIVATED)) {
                if (imageViewOfActivatedDeck != null)
                    imageViewOfActivatedDeck.setImage(new Image("/images/deckmenu/yellowDeck.png"));
                imageViewOfActivatedDeck = imageViewOfSelectedDeck;
                imageViewOfActivatedDeck.setImage(new Image("/images/deckmenu/whiteDeck.png"));
            }

            createDeckMessage.setText("");
            createDeckField.setVisible(false);
            createDeckField.clear();
            deleteDeckMessage.setText("");
            mainCardsMessage.setText("");
            sideCardsMessage.setText("");
        });
    }

    private void handleDragAndDropFromBoughtCards(ImageView imageView, Card card) {
        setOnDragDetected(imageView, card);

        handleSetOnDragOver(mainCards);
        handleSetOnDragOver(sideCards);

        handleSetOnDragEntered(mainCards);
        handleSetOnDragEntered(sideCards);

        handleSetOnDragExitedForDeckCards(mainCards);
        handleSetOnDragExitedForDeckCards(sideCards);

        handleSetOnDragDroppedForDeckCards(mainCards);
        handleSetOnDragDroppedForDeckCards(sideCards);

        handleSetOnDragDone(imageView);
    }

    private void setOnDragDetected(ImageView imageView, Card card) {
        imageView.setOnDragDetected(event -> {
            Dragboard dragboard = imageView.startDragAndDrop(TransferMode.ANY);

            ClipboardContent content = new ClipboardContent();
            content.putImage(imageView.getImage());
            dragboard.setContent(content);
            imageView.setCursor(Cursor.CLOSED_HAND);

            draggedCard = card;

            sideCardsMessage.setText("");
            mainCardsMessage.setText("");
            createDeckMessage.setText("");
            deleteDeckMessage.setText("");
            activateDeckMessage.setText("");

            event.consume();
        });
    }

    private void handleSetOnDragOver(GridPane gridPane) {
        gridPane.setOnDragOver(event -> {
            if (event.getGestureSource() != gridPane && selectedDeck != null) {
                if (gridPane == allCardsGridPane && getAllCardsDragCondition(event) ||
                        gridPane == mainCards && getMainCardsDragCondition(event) ||
                        gridPane == sideCards && getSideCardsDragCondition(event))
                    event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            }

            event.consume();
        });
    }

    private void handleSetOnDragEntered(GridPane gridPane) {
        gridPane.setOnDragEntered(event -> {
            if (selectedDeck != null) {
                if (gridPane == allCardsGridPane && getAllCardsDragCondition(event) ||
                        gridPane == mainCards && getMainCardsDragCondition(event) ||
                        gridPane == sideCards && getSideCardsDragCondition(event)) {
                    ColorAdjust blackout = new ColorAdjust();
                    blackout.setBrightness(-0.3);
                    gridPane.setEffect(blackout);
                }
            } else {
                String message = DeckMenuMessages.UNAVAILABLE_SELECTION.getMessage();
                if (gridPane == mainCards) {
                    mainCardsMessage.setText(message);
                    sideCardsMessage.setText("");
                } else if (gridPane == sideCards) {
                    sideCardsMessage.setText(message);
                    mainCardsMessage.setText("");
                } else {
                    sideCardsMessage.setText("");
                    mainCardsMessage.setText("");
                }

                createDeckMessage.setText("");
                deleteDeckMessage.setText("");
                activateDeckMessage.setText("");
            }

            event.consume();
        });
    }

    private void handleSetOnDragExitedForDeckCards(GridPane gridPane) {
        gridPane.setOnDragExited(event -> {
            gridPane.setEffect(null);

            event.consume();
        });
    }

    private void handleSetOnDragDroppedForDeckCards(GridPane gridPane) {
        gridPane.setOnDragDropped(event -> {
            boolean success = false;

            mainCardsMessage.setText("");
            sideCardsMessage.setText("");
            createDeckMessage.setText("");
            deleteDeckMessage.setText("");
            activateDeckMessage.setText("");

            ImageView imageView = (ImageView) event.getGestureSource();
            String message;
            if (gridPane == allCardsGridPane && getAllCardsDragCondition(event)) {
                success = true;

                if (isImageViewAvailableInGridPane(imageView, mainCards)) {
                    message = DeckMenuController.removeCard(draggedCard, selectedDeck, true).getMessage();
                    mainCardsMessage.setText(message);
                } else {
                    message = DeckMenuController.removeCard(draggedCard, selectedDeck, false).getMessage();
                    sideCardsMessage.setText(message);
                }
            } else if (gridPane == mainCards && getMainCardsDragCondition(event)) {
                success = true;

                if (isImageViewAvailableInAllCardsGridPane(imageView))
                    message = DeckMenuController.addCard(draggedCard, selectedDeck, true, true).getMessage();
                else message = DeckMenuController.addCard(draggedCard, selectedDeck, true, false).getMessage();

                mainCardsMessage.setText(message);
            } else if (gridPane == sideCards && getSideCardsDragCondition(event)) {
                success = true;

                if (isImageViewAvailableInAllCardsGridPane(imageView))
                    message = DeckMenuController.addCard(draggedCard, selectedDeck, false, true).getMessage();
                else message = DeckMenuController.addCard(draggedCard, selectedDeck, false, false).getMessage();

                sideCardsMessage.setText(message);
            }

            showMainCards(selectedDeck);
            showSideCards(selectedDeck);
            handleBoughtCards();
            event.setDropCompleted(success);
            event.consume();
        });
    }

    private void handleSetOnDragDone(ImageView imageView) {
        imageView.setOnDragDone(event -> {
            imageView.setCursor(Cursor.HAND);
            event.consume();
        });
    }

    private void handleDragAndDropFromGridPane(ImageView imageView, Card card) {
        setOnDragDetected(imageView, card);

        handleSetOnDragOver(allCardsGridPane);
        handleSetOnDragEntered(allCardsGridPane);
        handleSetOnDragExitedForDeckCards(allCardsGridPane);
        handleSetOnDragDroppedForDeckCards(allCardsGridPane);
        handleSetOnDragDone(imageView);
    }

    private boolean getMainCardsDragCondition(DragEvent dragEvent) {
        ImageView imageView = (ImageView) dragEvent.getGestureSource();

        if (isImageViewAvailableInAllCardsGridPane(imageView)) return true;
        return sideCards.getChildren().contains((ImageView) dragEvent.getGestureSource());
    }

    private boolean getSideCardsDragCondition(DragEvent dragEvent) {
        ImageView imageView = (ImageView) dragEvent.getGestureSource();

        if (isImageViewAvailableInAllCardsGridPane(imageView)) return true;
        return mainCards.getChildren().contains((ImageView) dragEvent.getGestureSource());
    }

    private boolean isImageViewAvailableInAllCardsGridPane(ImageView imageView) {
        for (Node node : allCardsGridPane.getChildren()) {
            VBox vBox = (VBox) node;
            if (vBox.getChildren().contains(imageView)) return true;
        }
        return false;
    }

    private boolean getAllCardsDragCondition(DragEvent dragEvent) {
        ImageView imageView = (ImageView) dragEvent.getGestureSource();

        return isImageViewAvailableInGridPane(imageView, mainCards) ||
                isImageViewAvailableInGridPane(imageView, sideCards);
    }

    private boolean isImageViewAvailableInGridPane(ImageView imageView, GridPane gridPane) {
        return gridPane.getChildren().contains(imageView);
    }
}
