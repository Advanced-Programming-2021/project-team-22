package view;

import controller.scoreboardmenu.Scoreboard;
import controller.scoreboardmenu.ScoreboardOutput;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ScoreboardView {
    public static Scanner scanner = new Scanner(System.in);

    public void runScoreboard() {
        String command;
        while (true) {
            command = scanner.nextLine().replaceAll("\\s+", " ");
            switch (command) {
                case "scoreboard show":
                    Scoreboard.getInstance().showScoreboard();
                    break;
                case "menu show-current":
                    ScoreboardOutput.getInstance().showMessage("Scoreboard Menu");
                    break;
                case "menu exit":
                    MainMenuView.mainMenuView;
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
