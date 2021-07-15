package controller.scoreboardmenu;

import model.Player;

import java.util.ArrayList;

public class ScoreboardMenuController {
    private static Player loggedInPlayer;

    public static Player getLoggedInPlayer() {
        return loggedInPlayer;
    }

    public static void setLoggedInPlayer(Player loggedInPlayer) {
        ScoreboardMenuController.loggedInPlayer = loggedInPlayer;
    }

    public static ArrayList<Player> sortAllPlayers() {
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

        addRankToPlayers(allPlayers);
        return allPlayers;
    }

    private static void addRankToPlayers(ArrayList<Player> allPlayers) {
        int rank = 1;
        for (int i = 0; i < allPlayers.size(); i++) {
            Player player = allPlayers.get(i);
            if (i != 0 && player.getScore() != allPlayers.get(i - 1).getScore()) rank = i + 1;
            player.setRank(rank);
        }
    }
}
