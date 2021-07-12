package controller.importexportmenu;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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

    public static ImportExportMenuMessages importCard(File file, String cardName) {
        try {
            FileReader fileReader = new FileReader(file);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            MonsterCard monsterCard = gson.fromJson(fileReader, MonsterCard.class);

            if (Card.getCardByName(cardName) != null) return ImportExportMenuMessages.AVAILABLE_CARD;
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
        } catch (IOException ignore) {
            return ImportExportMenuMessages.INVALID_FILE;
        }


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

    public static void exportCard(Card card) {
        try {
            FileWriter fileWriter = new FileWriter("src/main/resources/cards/" + card.getName() + ".json");
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            fileWriter.write(gson.toJson(card));
            fileWriter.close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
            System.exit(0);
        }
    }
}
