package controller.mainmenu;

import controller.MenuRegexes;
import controller.Utils;
import model.Player;
import view.DefineStarter1;
import view.DuelMenuView;

import java.util.regex.Matcher;

public class MainMenuController {
    private static Player loggedInPlayer;

    public static Player getLoggedInPlayer() {
        return loggedInPlayer;
    }

    public static void setLoggedInPlayer(Player loggedInPlayer) {
        MainMenuController.loggedInPlayer = loggedInPlayer;
    }

    public MainMenuMessages findCommand(String command) {

        if (command.startsWith("menu enter")) return enterAMenu(command);
        else if (command.equals("menu exit")) return MainMenuMessages.EXIT_MAIN_MENU;
        else if (command.equals("menu show-current")) return MainMenuMessages.SHOW_MENU;
        else if (command.equals("user logout")) return MainMenuMessages.USER_LOGGED_OUT;
//        else if (command.startsWith("duel")) return enterDuelMenu(command);

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
//            DeckMenuView deckMenuView = new DeckMenuView(loggedInPlayer);
//            deckMenuView.deckMenuView();

        } else if (menu.equalsIgnoreCase("Scoreboard")) {
//            ScoreboardMenuView.scoreboardMenuView();

        } else if (menu.equalsIgnoreCase("Profile")) {
//            ProfileMenuView profileMenuView = new ProfileMenuView(loggedInPlayer);
//            profileMenuView.profileMenuView();

        } else if (menu.equalsIgnoreCase("Shop")) {
//            ShopMenuView shopMenuView = new ShopMenuView(loggedInPlayer);
//            shopMenuView.shopMenuView();

        } else if (menu.equalsIgnoreCase("ImportExport")) {
//            ImportExportMenuView importExportMenuView = new ImportExportMenuView();
//            importExportMenuView.ImportExportMenuView();
        }

        return MainMenuMessages.EMPTY;
    }

    public static MainMenuMessages enterDuelMenu(String opponentPlayerUsername, String rounds, boolean playWithAI) {
        if (!playWithAI) {
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
                System.out.println(loggedInPlayer.getUsername());
                DuelMenuView duelMenuView = new DuelMenuView(loggedInPlayer, opponentPlayer, 1);
                try {
                    DefineStarter1.duelMenuView = duelMenuView;
                    new DefineStarter1().start(Utils.getStage());
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else if (rounds.equals("3")) {
                DuelMenuView duelMenuView = new DuelMenuView(loggedInPlayer, opponentPlayer, 3);
                try {
                    DefineStarter1.duelMenuView = duelMenuView;
                    new DefineStarter1().start(Utils.getStage());
                } catch (Exception e) {
                    e.printStackTrace();
                }


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
                try {
                    duelMenuView.start(Utils.getStage());
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else if (rounds.equals("3")) {
                DuelMenuView duelMenuView = new DuelMenuView(loggedInPlayer, 3);
                try {
                    duelMenuView.start(Utils.getStage());
                } catch (Exception e) {
                    e.printStackTrace();
                }


            } else {
                return MainMenuMessages.INVALID_ROUNDS_NUMBER;
            }
        }

        return MainMenuMessages.EMPTY;
    }
}
