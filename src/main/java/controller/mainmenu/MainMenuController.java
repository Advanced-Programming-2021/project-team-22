package controller.mainmenu;

import controller.MenuRegexes;
import controller.Utils;
import controller.duelmenu.DuelMenuMessages;
import model.Player;
import view.*;

import java.util.regex.Matcher;

public class MainMenuController {
    private final Player loggedInPlayer;

    public MainMenuController(Player loggedInPlayer) {
        this.loggedInPlayer = loggedInPlayer;
    }

    public MainMenuMessages findCommand(String command) {

        if (command.startsWith("menu enter")) return enterAMenu(command);
        else if (command.equals("menu exit")) return MainMenuMessages.EXIT_MAIN_MENU;
        else if (command.equals("menu show-current")) return MainMenuMessages.SHOW_MENU;
        else if (command.equals("user logout")) return MainMenuMessages.USER_LOGGED_OUT;
        else if (command.startsWith("duel")) return enterDuelMenu(command);

        return MainMenuMessages.INVALID_COMMAND;
    }

    private MainMenuMessages enterAMenu(String command) {
        Matcher matcher = Utils.getMatcher(MenuRegexes.ENTER_A_MENU.getRegex(), command);
        if (!matcher.find()) return MainMenuMessages.INVALID_COMMAND;

        String menu = matcher.group(1);
        if (menu.equalsIgnoreCase("Login")) {
            return MainMenuMessages.INVALID_NAVIGATION;

        } else if (menu.equalsIgnoreCase("Main")) {
            return MainMenuMessages.INVALID_NAVIGATION;

        } else if (menu.equalsIgnoreCase("Duel")) {
            return MainMenuMessages.INVALID_NAVIGATION;

        } else if (menu.equalsIgnoreCase("Deck")) {
            DeckMenuView deckMenuView = new DeckMenuView(loggedInPlayer);
            deckMenuView.deckMenuView();

        } else if (menu.equalsIgnoreCase("Scoreboard"))
            ScoreboardMenuView.scoreboardMenuView();

        else if (menu.equalsIgnoreCase("Profile")) {
            ProfileMenuView profileMenuView = new ProfileMenuView(loggedInPlayer);
            profileMenuView.profileMenuView();

        } else if (menu.equalsIgnoreCase("Shop")) {
            ShopMenuView shopMenuView = new ShopMenuView(loggedInPlayer);
            shopMenuView.shopMenuView();

        } else if (menu.equalsIgnoreCase("ImportExport")) {
            ImportExportMenuView importExportMenuView = new ImportExportMenuView();
            importExportMenuView.ImportExportMenuView();
        }

        return MainMenuMessages.EMPTY;
    }

    private MainMenuMessages enterDuelMenu(String command) {
        Matcher matcher;
        String opponentPlayerCommand, rounds;
        if (( matcher = Utils.getMatcher(MainMenuRegexes.ENTER_DUEL_MENU_FIRST_PATTERN.getRegex(), command) ).find() ||
                ( matcher = Utils.getMatcher(MainMenuRegexes.ENTER_DUEL_MENU_THIRD_PATTERN.getRegex(), command) ).find() ||
                ( matcher = Utils.getMatcher(MainMenuRegexes.ENTER_DUEL_MENU_FOURTH_PATTERN.getRegex(), command) ).find()) {
            opponentPlayerCommand = matcher.group(1);
            rounds = matcher.group(2);
        } else if (( matcher = Utils.getMatcher(MainMenuRegexes.ENTER_DUEL_MENU_SECOND_PATTERN.getRegex(), command) ).find() ||
                ( matcher = Utils.getMatcher(MainMenuRegexes.ENTER_DUEL_MENU_FIFTH_PATTERN.getRegex(), command) ).find() ||
                ( matcher = Utils.getMatcher(MainMenuRegexes.ENTER_DUEL_MENU_SIXTH_PATTERN.getRegex(), command) ).find()) {
            rounds = matcher.group(1);
            opponentPlayerCommand = matcher.group(2);
        } else {
            return MainMenuMessages.INVALID_COMMAND;
        }

        if (opponentPlayerCommand != null) {
            String opponentPlayerUsername = opponentPlayerCommand.substring(14);
            Player opponentPlayer = Player.getPlayerByUsername(opponentPlayerUsername);
            if (opponentPlayer == null) {
                return MainMenuMessages.UNAVAILABLE_USERNAME;
            }

            if (opponentPlayer.equals(loggedInPlayer)) return MainMenuMessages.SAME_USERNAME;

            if (loggedInPlayer.getActivatedDeck() == null) {
                MainMenuMessages.setUnavailableActiveDeck(loggedInPlayer.getUsername());
                return MainMenuMessages.UNAVAILABLE_ACTIVE_DECK;
            } else if (opponentPlayer.getActivatedDeck() == null) {
                MainMenuMessages.setUnavailableActiveDeck(opponentPlayerUsername);
                return MainMenuMessages.UNAVAILABLE_ACTIVE_DECK;
            }

            if (loggedInPlayer.getActivatedDeck().isValid().equals("invalid")) {
                MainMenuMessages.setInvalidDeck(loggedInPlayer.getUsername());
                return MainMenuMessages.INVALID_DECK;
            } else if (opponentPlayer.getActivatedDeck().isValid().equals("invalid")) {
                MainMenuMessages.setInvalidDeck(opponentPlayerUsername);
                return MainMenuMessages.INVALID_DECK;
            }

            if (rounds.equals("1")) {
                DuelMenuView duelMenuView = new DuelMenuView(loggedInPlayer, opponentPlayer, 1);
                duelMenuView.duelMenuView();

            } else if (rounds.equals("3")) {
                DuelMenuView duelMenuView = new DuelMenuView(loggedInPlayer, opponentPlayer, 3);
                DuelMenuMessages result = DuelMenuMessages.PLAY_ANOTHER_TURN;
           //     while (result.equals(DuelMenuMessages.PLAY_ANOTHER_TURN)) {
               //     result = duelMenuView.duelMenuView();
             //   }

            } else {
                return MainMenuMessages.INVALID_ROUNDS_NUMBER;
            }

        } else {
            if (loggedInPlayer.getActivatedDeck() == null) {
                MainMenuMessages.setUnavailableActiveDeck(loggedInPlayer.getUsername());
                return MainMenuMessages.UNAVAILABLE_ACTIVE_DECK;
            }
            if (loggedInPlayer.getActivatedDeck().isValid().equals("invalid")) {
                MainMenuMessages.setInvalidDeck(loggedInPlayer.getUsername());
                return MainMenuMessages.INVALID_DECK;
            }

            if (rounds.equals("1")) {
                DuelMenuView duelMenuView = new DuelMenuView(loggedInPlayer, 1);
                duelMenuView.playWithAI();

            } else if (rounds.equals("3")) {
                DuelMenuView duelMenuView = new DuelMenuView(loggedInPlayer, 3);
                DuelMenuMessages result = DuelMenuMessages.PLAY_ANOTHER_TURN;
                while (result.equals(DuelMenuMessages.PLAY_ANOTHER_TURN)) {
                    result = duelMenuView.playWithAI();
                }

            } else {
                return MainMenuMessages.INVALID_ROUNDS_NUMBER;
            }
        }

        return MainMenuMessages.EMPTY;
    }
}
