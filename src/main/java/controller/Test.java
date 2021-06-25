package controller;

import model.Player;

import java.util.ArrayList;

public class Test {
    public static void main(String[] args) {
//        Database.prepareGame();
//        new Player("a", "123", "a").increaseScore(1000);
//        new Player("a", "123", "A").increaseScore(1000);
//        new Player("a", "123", "a ").increaseScore(1000);
//        new Player("a", "123", "A !").increaseScore(1001);
//        new Player("a", "123", "A ").increaseScore(1001);
//
//        ArrayList<Player> allPlayers = Player.getAllPlayers();
//        for (int i = 0; i < allPlayers.size(); i++) {
//            for (int j = i + 1; j < allPlayers.size(); j++) {
//                if (allPlayers.get(i).compareTo(allPlayers.get(j)) < 0) {
//                    Player tempPlayer = allPlayers.get(i);
//                    allPlayers.remove(i);
//                    allPlayers.add(i, allPlayers.get(j - 1));
//                    allPlayers.remove(j);
//                    allPlayers.add(j , tempPlayer);
//                }
//            }
//        }
//
//        showScoreboard(allPlayers);
    }

    public static void showScoreboard(ArrayList<Player> allPlayers) {
        int rank = 1;
        for (int i = 0; i < allPlayers.size(); i++) {
            if (i != 0 && allPlayers.get(i).getScore() != allPlayers.get(i - 1).getScore()) rank = i + 1;
            showPlayer(allPlayers.get(i), rank);
        }
    }

    private static void showPlayer(Player player, int rank) {
        System.out.println(rank + "- " + player.getNickname() + ": " + player.getScore());
    }
}
