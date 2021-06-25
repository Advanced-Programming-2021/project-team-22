package view;

import controller.Utils;
import controller.scoreboardmenu.ScoreboardMenuController;
import controller.scoreboardmenu.ScoreboardMenuMessages;
import model.Player;

import java.util.ArrayList;

public class ScoreboardMenuView {
    public static void scoreboardMenuView() {
        while (true) {
            String command = Utils.getScanner().nextLine().trim();
            ScoreboardMenuMessages result = ScoreboardMenuController.findCommand(command);

            System.out.println(result.getMessage());

            if (result.equals(ScoreboardMenuMessages.EXIT_SCOREBOARD_MENU)) break;
        }
    }

    public static void showScoreboard(ArrayList<Player> allPlayers) {
        int rank = 1;
        for (int i = 0; i < allPlayers.size(); i++) {
            Player player = allPlayers.get(i);
            if (i != 0 && player.getScore() != allPlayers.get(i - 1).getScore()) rank = i + 1;
            System.out.println(rank + "- " + player.getNickname() + ": " + player.getScore());
        }
    }
}
