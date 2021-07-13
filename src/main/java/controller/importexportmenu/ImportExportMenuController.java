package controller.importexportmenu;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import controller.Database;
import model.Player;
import model.cards.Card;
import model.cards.magiccard.MagicCard;
import model.cards.monstercard.MonsterCard;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ImportExportMenuController {
    private static Player loggedInPlayer;

    public static Player getLoggedInPlayer() {
        return loggedInPlayer;
    }

    public static void setLoggedInPlayer(Player loggedInPlayer) {
        ImportExportMenuController.loggedInPlayer = loggedInPlayer;
    }

    public static ImportExportMenuMessages importCard(File file, String cardName, boolean isJson) {
        if (Card.getCardByName(cardName) != null) return ImportExportMenuMessages.AVAILABLE_CARD;

        try {
            if (isJson) return importAsJson(file, cardName);
            else return importAsCsv(file);

        } catch (Exception ignore) {
            return ImportExportMenuMessages.INVALID_FILE;
        }
    }

    private static ImportExportMenuMessages importAsJson(File file, String cardName) throws Exception {
        FileReader fileReader = new FileReader(file);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        MonsterCard monsterCard = gson.fromJson(fileReader, MonsterCard.class);

        if (monsterCard.getAttribute() == null) {
//                so we understand that the showSelectedCard is a magic showSelectedCard
            fileReader = new FileReader(file);
            MagicCard magicCard = gson.fromJson(fileReader, MagicCard.class);
            Card.addCardToAllCards(magicCard);
            if (isCardIncomplete(magicCard) || isMagicCardIncomplete(magicCard)) {
                Card.removeCardFromAllCards(magicCard);
                return ImportExportMenuMessages.INVALID_FILE;
            }

            String frontImageAddress = "/Project-Assets-1.0.0/Assets/Cards/SpellTrap/" +
                    Database.getNameForFrontImageAddress(cardName) + ".jpg";
            magicCard.setFrontImageAddress(frontImageAddress);
        } else {
//                so we understand that the card is a monster card
            monsterCard.createEquippedByArrayList();
            Card.addCardToAllCards(monsterCard);
            if (isCardIncomplete(monsterCard) || isMonsterCardIncomplete(monsterCard)) {
                Card.removeCardFromAllCards(monsterCard);
                return ImportExportMenuMessages.INVALID_FILE;
            }

            String frontImageAddress = "/Project-Assets-1.0.0/Assets/Cards/Monsters/" +
                    Database.getNameForFrontImageAddress(cardName) + ".jpg";
            monsterCard.setFrontImageAddress(frontImageAddress);
        }

        fileReader.close();
        return ImportExportMenuMessages.IMPORT_SUCCESSFULLY;
    }

    private static boolean isCardIncomplete(Card card) {
        return card.getName() == null || card.getDescription() == null || card.getCardType() == null;
    }

    private static boolean isMonsterCardIncomplete(MonsterCard monsterCard) {
        return monsterCard.getAttribute() == null || monsterCard.getMonsterType() == null;
    }

    private static boolean isMagicCardIncomplete(MagicCard magicCard) {
        return magicCard.getIcon() == null || magicCard.getStatus() == null;
    }

    private static ImportExportMenuMessages importAsCsv(File file) throws Exception {
        FileReader fileReader = new FileReader(file);
        CSVReader monsterCardCSVReader = new CSVReaderBuilder(fileReader).withSkipLines(1).build();

        String[] cardData = monsterCardCSVReader.readNext();
        try {
            Database.createNewMonsterCard(cardData);
        } catch (Exception exception) {
            try {
                Database.createNewMagicCard(cardData);
            } catch (Exception exception1) {
                return ImportExportMenuMessages.INVALID_FILE;
            }
        }

        return ImportExportMenuMessages.IMPORT_SUCCESSFULLY;
    }

    public static void exportCard(Card card, boolean isJson) {
        try {
            if (isJson) exportAsJson(card);
            else exportAsCsv(card);
        } catch (IOException ioException) {
            ioException.printStackTrace();
            System.exit(0);
        }
    }

    private static void exportAsJson(Card card) throws IOException {
        FileWriter fileWriter = new FileWriter("src/main/resources/cards/" + card.getName() + ".json");
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        fileWriter.write(gson.toJson(card));
        fileWriter.close();
    }

    private static void exportAsCsv(Card card) throws IOException {
        FileWriter fileWriter = new FileWriter("src/main/resources/cards/" + card.getName() + ".csv");

        if (card instanceof MonsterCard) {
            MonsterCard monsterCard = (MonsterCard) card;
            fileWriter.write("Name,Level,Attribute, Monster Type , Card Type ,Atk,Def,Description,Price\n"
                    + monsterCard.getName() + "," + monsterCard.getLevel() + "," + monsterCard.getAttribute()
                    + "," + monsterCard.getMonsterType() + "," + monsterCard.getCardType()
                    + "," + monsterCard.getAttackPoints() + "," + monsterCard.getDefensePoints()
                    + ",\"" + monsterCard.getDescription() + "\"," + monsterCard.getPrice());
        } else {
            MagicCard magicCard = (MagicCard) card;
            fileWriter.write("Name,Type ,Icon (Property),Description,Status,Price\n"
                    + magicCard.getName() + "," + magicCard.getCardType() + "," + magicCard.getIcon()
                    + ",\"" + magicCard.getDescription() + "\"," + magicCard.getStatus() + "," + magicCard.getPrice());
        }

        fileWriter.close();
    }
}
