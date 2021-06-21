package view;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import controller.Utils;
import controller.deckmenu.DeckMenuController;
import controller.deckmenu.DeckMenuOutput;
import model.*;

public class DeckMenuView {
    public static Scanner scanner = Utils.getScanner();

    private final Player loggedInPlayer;

    public DeckMenuView(Player loggedInPlayer) {
        this.loggedInPlayer = loggedInPlayer;
    }

    private final String[] deckMenuRegexes = {
            "^deck create (?<name>\\w+)$",
            "^deck delete (?<name>\\w+)$",
            "^deck set-activate (?<name>\\w+)$",
            "^deck add-card (?:--card|-c) (?<cardName>.+) (?:--deck|-d) (?<deckName>.+) (?:--side|-s)$",
            "^deck add-card (?:--card|-c) (?<cardName>.+) (?:--side|-s) (?:--deck|-d) (?<deckName>.+)$",
            "^deck add-card (?:--deck|-d) (?<deckName>.+) (?:--card|-c) (?<cardName>.+) (?:--side|-s)$",
            "^deck add-card (?:--deck|-d) (?<deckName>.+) (?:--side|-s) (?:--card|-c) (?<cardName>.+)$",
            "^deck add-card (?:--side|-s) (?:--deck|-d) (?<deckName>.+) (?:--card|-c) (?<cardName>.+)$",
            "^deck add-card (?:--side|-s) (?:--card|-c) (?<cardName>.+) (?:--deck|-d) (?<deckName>.+)$",
            "^deck add-card (?:--card|-c) (?<cardName>.+) (?:--deck|-d) (?<deckName>.+)$",
            "^deck add-card (?:--deck|-d) (?<deckName>.+) (?:--card|-c) (?<cardName>.+)$",
            "^deck rm-card (?:--card|-c) (?<cardName>.+) (?:--deck|-d) (?<deckName>.+) (?:--side|-s)$",
            "^deck rm-card (?:--card|-c) (?<cardName>.+) (?:--side|-s) (?:--deck|-d) (?<deckName>.+)$",
            "^deck rm-card (?:--deck|-d) (?<deckName>.+) (?:--card|-c) (?<cardName>.+) (?:--side|-s)$",
            "^deck rm-card (?:--deck|-d) (?<deckName>.+) (?:--side|-s) (?:--card|-c) (?<cardName>.+)$",
            "^deck rm-card (?:--side|-s) (?:--deck|-d) (?<deckName>.+) (?:--card|-c) (?<cardName>.+)$",
            "^deck rm-card (?:--side|-s) (?:--card|-c) (?<cardName>.+) (?:--deck|-d) (?<deckName>.+)$",
            "^deck rm-card (?:--card|-c) (?<cardName>.+) (?:--deck|-d) (?<deckName>.+)$",
            "^deck rm-card (?:--deck|-d) (?<deckName>.+) (?:--card|-c) (?<cardName>.+)$",
            "^deck show (?:--all|-a)$",
            "^deck show (?:--deck-name|-d) (?<deckName>.+) (?:--side|-s)$",
            "^deck show (?:--side|-s) (?:--deck-name|-d) (?<deckName>.+)$",
            "^deck show (?:--deck-name|-d) (?<deckName>.+)$",
            "^menu show-current$",
            "^menu exit$"
    };

    public void runDeckMenu() {
        Matcher commandMatcher;
        String command;
        while (true) {
            command = scanner.nextLine().replaceAll("\\s+", " ");
            int whichCommand;
            for (whichCommand = 0; whichCommand < deckMenuRegexes.length; whichCommand++) {
                commandMatcher = findMatcher(command, deckMenuRegexes[whichCommand]);
                if (commandMatcher.find()) {
                    executeDeckMenuCommands(commandMatcher, whichCommand);
                    break;
                } else if (whichCommand == deckMenuRegexes.length - 1)
                    DeckMenuOutput.getInstance().showMessage("invalid command");
            }

        }
    }

    private void executeDeckMenuCommands(Matcher commandMatcher, int whichCommand) {
        DeckMenuController controller = DeckMenuController.getInstance();
        switch (whichCommand) {
            case 0:
                controller.createDeck(commandMatcher.group("name"), loggedInPlayer);
                break;
            case 1:
                controller.deleteDeck(commandMatcher.group("name"));
                break;
            case 2:
                controller.setActiveDeck(commandMatcher.group("name"), loggedInPlayer);
                break;
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
                String cardName = commandMatcher.group("cardName"),
                        deckName = commandMatcher.group("deckName");
                controller.addCardToDeck(cardName, deckName, loggedInPlayer, false);
                break;
            case 9:
            case 10:
                cardName = commandMatcher.group("cardName");
                deckName = commandMatcher.group("deckName");
                controller.addCardToDeck(cardName, deckName, loggedInPlayer, true);
                break;
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
                cardName = commandMatcher.group("cardName");
                deckName = commandMatcher.group("deckName");
                controller.removeCardFromDeck(cardName, deckName, loggedInPlayer, false);
                break;
            case 17:
            case 18:
                cardName = commandMatcher.group("cardName");
                deckName = commandMatcher.group("deckName");
                controller.removeCardFromDeck(cardName, deckName, loggedInPlayer, true);
                break;
            case 19:
                controller.showAllDecks(loggedInPlayer);
                break;
            case 20:
            case 21:
                controller.showDeck(commandMatcher.group("deckName"), loggedInPlayer, false);
                break;
            case 22:
                controller.showDeck(commandMatcher.group("deckName"), loggedInPlayer, true);
                break;
            case 23:
                DeckMenuOutput.getInstance().showMessage("Deck Menu");
                break;
            case 24:
                MainMenuView mainMenuView = new MainMenuView(loggedInPlayer);
                mainMenuView.mainMenuView();
        }
    }

    private Matcher findMatcher(String input, String regex) {

        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(input);
    }


}
