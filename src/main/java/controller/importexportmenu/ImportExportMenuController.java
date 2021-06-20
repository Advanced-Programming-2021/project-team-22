package controller.importexportmenu;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import controller.Utils;
import model.cards.Card;
import model.cards.magiccard.MagicCard;
import model.cards.monstercard.MonsterCard;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;

public class ImportExportMenuController {
    public static ImportExportMenuMessages findCommand(String command) {

        if (command.startsWith("menu enter")) return enterAMenu(command);
        else if (command.equals("menu exit")) return ImportExportMenuMessages.EXIT_IMPORT_EXPORT_MENU;
        else if (command.equals("menu show-current")) return ImportExportMenuMessages.SHOW_MENU;
        else if (command.startsWith("import card")) return importCard(command);
        else if (command.startsWith("export card")) return exportCard(command);

        return ImportExportMenuMessages.INVALID_COMMAND;
    }

    private static ImportExportMenuMessages enterAMenu(String command) {
        Matcher matcher = Utils.getMatcher(ImportExportMenuRegexes.ENTER_A_MENU.getRegex(), command);
        if (!matcher.find()) return ImportExportMenuMessages.INVALID_COMMAND;

        return ImportExportMenuMessages.INVALID_NAVIGATION;
    }

    private static ImportExportMenuMessages importCard(String command) {
        Matcher matcher = Utils.getMatcher(ImportExportMenuRegexes.IMPORT_CARD.getRegex(), command);
        if (!matcher.find()) return ImportExportMenuMessages.INVALID_COMMAND;

        String cardName = matcher.group(1);
        try {
            FileReader fileReader = new FileReader("src/database/cards/" + cardName + ".json");
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            MonsterCard monsterCard = gson.fromJson(fileReader, MonsterCard.class);

            if (Card.getCardByName(cardName) != null) return ImportExportMenuMessages.AVAILABLE_CARD;
            if (monsterCard.getAttribute() == null) {
//                so we understand that the card is a magic card
                fileReader = new FileReader("src/database/cards/" + cardName + ".json");
                MagicCard magicCard = gson.fromJson(fileReader, MagicCard.class);
                Card.addCardToAllCards(magicCard);
                if (isCardIncomplete(magicCard) || isMagicCardIncomplete(magicCard))
                    return ImportExportMenuMessages.INVALID_FILE;
            } else {
//                so we understand that the card is a monster card
                monsterCard.createEquippedByArrayList();
                Card.addCardToAllCards(monsterCard);
                if (isCardIncomplete(monsterCard) || isMonsterCardIncomplete(monsterCard))
                    return ImportExportMenuMessages.INVALID_FILE;
            }
            fileReader.close();
        } catch (IOException ignore) {
            return ImportExportMenuMessages.UNAVAILABLE_FILE;
        }


        return ImportExportMenuMessages.EMPTY;
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

    private static ImportExportMenuMessages exportCard(String command) {
        Matcher matcher = Utils.getMatcher(ImportExportMenuRegexes.EXPORT_CARD.getRegex(), command);
        if (!matcher.find()) return ImportExportMenuMessages.INVALID_COMMAND;

        String cardName = matcher.group(1);
        Card card = Card.getCardByName(cardName);
        if (card == null) return ImportExportMenuMessages.UNAVAILABLE_CARD;
        try {
            FileWriter fileWriter = new FileWriter("src/database/cards/" + cardName + ".json");
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            fileWriter.write(gson.toJson(card));
            fileWriter.close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
            System.exit(0);
        }


        return ImportExportMenuMessages.EMPTY;
    }
}
