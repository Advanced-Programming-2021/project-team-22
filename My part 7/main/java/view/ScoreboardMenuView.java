package view;

import controller.Utils;
import controller.scoreboardmenu.Scoreboard;
import controller.scoreboardmenu.ScoreboardOutput;
import model.Player;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ScoreboardMenuView {
    public static Scanner scanner = Utils.getScanner();

    public void runScoreboard() {
        String command;
        while (true) {
            command = scanner.nextLine().replaceAll("\\s+", " ");
            if (command.equals("menu exit")) break;
            switch (command) {
                case "scoreboard show":
                    Scoreboard.getInstance().showScoreboard();
                    break;
                case "menu show-current":
                    ScoreboardOutput.getInstance().showMessage("Scoreboard Menu");
                    break;
                default:
                    ScoreboardOutput.getInstance().showMessage("invalid command");
            }
        }
    }

    private Matcher findMatcher(String input, String regex) {

        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(input);
    }
}
