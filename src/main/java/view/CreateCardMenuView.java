package view;

import controller.Utils;
import controller.createcardmenu.CreateCardMenuController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class CreateCardMenuView extends Application {
    public Button monsterCardButton;
    public Button spellCardButton;
    public Button trapCardButton;
    public ComboBox effects;
    public Button chooseImageButton;
    public Button calculatePriceButton;
    public Label price;
    public Label money;
    public Button confirmButton;

    public Rectangle monsterCard;
    public TextField monsterCardName;
    public MenuButton monsterCardAttribute;
    public TextField monsterCardLevel;
    public TextField monsterCardMonsterType;
    public TextField monsterCardCardType;
    public TextArea monsterCardDescription;
    public TextField monsterCardAttack;
    public TextField monsterCardDefense;

    public Rectangle magicCard;
    public TextField magicCardName;
    public TextField magicCardIcon;
    public TextArea magicCardDescription;
    public MenuButton magicCardStatus;

    public Button backButton;

    @Override
    public void start(Stage stage) throws Exception {
        BorderPane root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/fxml/CreateCardMenu.fxml")));
        Scene scene = new Scene(root, 1280, 800);
        stage.setMinHeight(scene.getHeight() + 28 /*title bar height*/);
        stage.setMinWidth(scene.getWidth());
        stage.setScene(scene);
        stage.sizeToScene();
    }

    @FXML
    public void initialize() {
        handleMonsterCardButton();
        handleSpellCardButton();
        handleTrapCardButton();

        handleMenuButton(monsterCardAttribute);
        handleMenuButton(magicCardStatus);

        handleChooseImageButton();

        Utils.handleBackButton(backButton);

    }

    private void setVisibilityOfMonsterCard(boolean visibility) {
        monsterCard.setVisible(visibility);
        monsterCardName.setVisible(visibility);
        monsterCardAttribute.setVisible(visibility);
        monsterCardLevel.setVisible(visibility);
        monsterCardMonsterType.setVisible(visibility);
        monsterCardCardType.setVisible(visibility);
        monsterCardDescription.setVisible(visibility);
        monsterCardAttack.setVisible(visibility);
        monsterCardDefense.setVisible(visibility);

        effects.setVisible(visibility);
        chooseImageButton.setVisible(visibility);
        calculatePriceButton.setVisible(visibility);
        price.setVisible(visibility);
    }

    private void setVisibilityOfMagicCard(boolean visibility) {
        magicCard.setVisible(visibility);
        magicCardName.setVisible(visibility);
        magicCardIcon.setVisible(visibility);
        magicCardDescription.setVisible(visibility);
        magicCardStatus.setVisible(visibility);

        effects.setVisible(visibility);
        chooseImageButton.setVisible(visibility);
        calculatePriceButton.setVisible(visibility);
        price.setVisible(visibility);
    }

    private void handleMonsterCardButton() {
        monsterCardButton.setOnMouseClicked(mouseEvent -> {
            if (CreateCardMenuController.getLoggedInPlayer().isPlaySFX()) Utils.playButtonClickSFX();

            monsterCardAttribute.setText("Attribute");
            monsterCard.setFill(new ImagePattern(new Image("/Project-Assets-1.0.0/Assets/Cards/Monsters/AlexandriteDragon.jpg")));
            setVisibilityOfMagicCard(false);
            setVisibilityOfMonsterCard(true);

            monsterCardButton.setDisable(true);
            spellCardButton.setDisable(false);
            trapCardButton.setDisable(false);
        });
    }

    private void handleSpellCardButton() {
        spellCardButton.setOnMouseClicked(mouseEvent -> {
            if (CreateCardMenuController.getLoggedInPlayer().isPlaySFX()) Utils.playButtonClickSFX();

            magicCardStatus.setText("Status");
            magicCard.setFill(new ImagePattern(new Image("/Project-Assets-1.0.0/Assets/Cards/SpellTrap/AdvancedRitualArt.jpg")));
            setVisibilityOfMonsterCard(false);
            setVisibilityOfMagicCard(true);

            monsterCardButton.setDisable(false);
            spellCardButton.setDisable(true);
            trapCardButton.setDisable(false);
        });
    }

    private void handleTrapCardButton() {
        trapCardButton.setOnMouseClicked(mouseEvent -> {
            if (CreateCardMenuController.getLoggedInPlayer().isPlaySFX()) Utils.playButtonClickSFX();

            magicCardStatus.setText("Status");
            magicCard.setFill(new ImagePattern(new Image("/Project-Assets-1.0.0/Assets/Cards/SpellTrap/CallOfTheHaunted.jpg")));
            setVisibilityOfMonsterCard(false);
            setVisibilityOfMagicCard(true);

            monsterCardAttribute.setText("Attribute");
            monsterCardButton.setDisable(false);
            spellCardButton.setDisable(false);
            trapCardButton.setDisable(true);
        });
    }

    private void handleMenuButton(MenuButton menuButton) {
        menuButton.setOnMouseClicked(mouseEvent -> {
            if (CreateCardMenuController.getLoggedInPlayer().isPlaySFX()) Utils.playButtonClickSFX();
        });

        for (MenuItem menuItem : menuButton.getItems()) {
            menuItem.setOnAction(actionEvent -> {
                if (CreateCardMenuController.getLoggedInPlayer().isPlaySFX()) Utils.playButtonClickSFX();
                menuButton.setText(menuItem.getText());
            });
        }
    }

    private void handleChooseImageButton() {
        chooseImageButton.setOnMouseClicked(mouseEvent -> {
            if (CreateCardMenuController.getLoggedInPlayer().isPlaySFX()) Utils.playButtonClickSFX();

            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JPG Files (*.jpg)", "*.jpg"));

            if (monsterCard.isVisible())
                fileChooser.setInitialDirectory(new File("src/main/resources/Project-Assets-1.0.0/Assets/Cards/Monsters"));
            else
                fileChooser.setInitialDirectory(new File("src/main/resources/Project-Assets-1.0.0/Assets/Cards/SpellTrap"));

            File selectedFile = fileChooser.showOpenDialog(Utils.getStage());
            if (selectedFile == null) return;

            try {
                BufferedImage bufferedImage = ImageIO.read(selectedFile);

                if (monsterCard.isVisible()) monsterCard.setFill(new ImagePattern(Utils.convertToFxImage(bufferedImage)));
                else magicCard.setFill(new ImagePattern(Utils.convertToFxImage(bufferedImage)));
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
    }

    private void handleCalculatePriceButton() {
        calculatePriceButton.setOnMouseClicked(mouseEvent -> {
            if (CreateCardMenuController.getLoggedInPlayer().isPlaySFX()) Utils.playButtonClickSFX();

            if (monsterCard.isVisible()) {

            }
        });
    }

    private boolean isMonsterCardValid() {
        if (monsterCardName.getText().equals("")) return false;
        if (monsterCardAttribute.getText().equals("Attribute")) return false;

        try {
            int level = Integer.parseInt(monsterCardLevel.getText());
            if (level < 1 || level > 12) return false;
        } catch (Exception exception) {
            return false;
        }

        if (!monsterCardMonsterType.getText().equalsIgnoreCase("Aqua") && !monsterCardMonsterType.getText().equalsIgnoreCase("Beast") &&
                !monsterCardMonsterType.getText().equalsIgnoreCase("Beast-Warrior") && !monsterCardMonsterType.getText().equalsIgnoreCase("Cyberse") &&
                !monsterCardMonsterType.getText().equalsIgnoreCase("Dinosaur") && !monsterCardMonsterType.getText().equalsIgnoreCase("Divine-Beast") &&
                !monsterCardMonsterType.getText().equalsIgnoreCase("Dragon") && !monsterCardMonsterType.getText().equalsIgnoreCase("Fairy") &&
                !monsterCardMonsterType.getText().equalsIgnoreCase("Fiend") && !monsterCardMonsterType.getText().equalsIgnoreCase("Fish") &&
                !monsterCardMonsterType.getText().equalsIgnoreCase("Insect") && !monsterCardMonsterType.getText().equalsIgnoreCase("Machine") &&
                !monsterCardMonsterType.getText().equalsIgnoreCase("Plant") && !monsterCardMonsterType.getText().equalsIgnoreCase("Psychic") &&
                !monsterCardMonsterType.getText().equalsIgnoreCase("Pyro") && !monsterCardMonsterType.getText().equalsIgnoreCase("Reptile") &&
                !monsterCardMonsterType.getText().equalsIgnoreCase("Rock") && !monsterCardMonsterType.getText().equalsIgnoreCase("Sea Serpent") &&
                !monsterCardMonsterType.getText().equalsIgnoreCase("Spellcaster") && !monsterCardMonsterType.getText().equalsIgnoreCase("Thunder") &&
                !monsterCardMonsterType.getText().equalsIgnoreCase("Warrior") && !monsterCardMonsterType.getText().equalsIgnoreCase("Winged Beast") &&
                !monsterCardMonsterType.getText().equalsIgnoreCase("Wyrm") && !monsterCardMonsterType.getText().equalsIgnoreCase("Zombie")) return false;

        return true;
    }
}
