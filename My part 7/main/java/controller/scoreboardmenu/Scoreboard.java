package controller.scoreboardmenu;

import java.util.ArrayList;
import model.*;

public class Scoreboard {

    private Scoreboard() {
    }

    private static Scoreboard instance;

    public static Scoreboard getInstance() {
        if (instance == null)
            instance = new Scoreboard();
        return instance;
    }

    public void showScoreboard() {
        int counter = 1;
        int index = 0;
        long previousScore = -1;
        StringBuilder output = new StringBuilder();
        ArrayList<Player> allUsers = Player.getAllPlayers();
        allUsers.sort(Player::compareTo);
        for (Player player : allUsers) {


            if (player.getScore() != previousScore) {
                index += counter;
                counter = 1;
            } else counter++;
            output.append(index).append(". ").append(player.getNickname()).append(": ").append(player.getScore()).append("\n");
            previousScore = player.getScore();

        }
        ScoreboardOutput.getInstance().showMessage(output.toString());
    }

}
