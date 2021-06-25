package controller.scoreboardmenu;

import controller.MenuRegexes;
import controller.Utils;
import model.Player;
import view.ScoreboardMenuView;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class ScoreboardMenuController {
    public static ScoreboardMenuMessages findCommand(String command) {

        if (command.startsWith("menu enter")) return enterAMenu(command);
        else if (command.equals("menu exit")) return ScoreboardMenuMessages.EXIT_SCOREBOARD_MENU;
        else if (command.equals("menu show-current")) return ScoreboardMenuMessages.SHOW_MENU;
        else if (command.equals("scoreboard show")) return sortAllPlayers();

        return ScoreboardMenuMessages.INVALID_COMMAND;
    }

    private static ScoreboardMenuMessages enterAMenu(String command) {
        Matcher matcher = Utils.getMatcher(MenuRegexes.ENTER_A_MENU.getRegex(), command);
        if (!matcher.find()) return ScoreboardMenuMessages.INVALID_COMMAND;

        return ScoreboardMenuMessages.INVALID_NAVIGATION;
    }

    private static ScoreboardMenuMessages sortAllPlayers() {
        ArrayList<Player> allPlayers = Player.getAllPlayers();

        for (int i = 0; i < allPlayers.size(); i++) {
            for (int j = i + 1; j < allPlayers.size(); j++) {
                if (allPlayers.get(i).compareTo(allPlayers.get(j)) < 0) {
                    Player tempPlayer = allPlayers.get(i);
                    allPlayers.remove(i);
                    allPlayers.add(i, allPlayers.get(j - 1));
                    allPlayers.remove(j);
                    allPlayers.add(j , tempPlayer);
                }
            }
        }

        ScoreboardMenuView.showScoreboard(allPlayers);
        return ScoreboardMenuMessages.EMPTY;
    }
}
