package controller;

import controller.scoreboardmenu.ScoreboardMenuController;
import controller.scoreboardmenu.ScoreboardMenuMessages;
import model.Player;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;

class ScoreboardMenuControllerTest extends MenuTest {

    @Test
    void findCommandEnterAMenuMethod() {
        ScoreboardMenuMessages result = ScoreboardMenuController.findCommand("menu enter Login Menu ");
        Assertions.assertEquals(ScoreboardMenuMessages.INVALID_COMMAND, result);

        result = ScoreboardMenuController.findCommand("menu enter LOGIN Menu");
        Assertions.assertEquals(ScoreboardMenuMessages.INVALID_NAVIGATION, result);
    }

    @Test
    void findCommandTestSingleCommands() {
        ScoreboardMenuMessages result = ScoreboardMenuController.findCommand("menu exit");
        Assertions.assertEquals(ScoreboardMenuMessages.EXIT_SCOREBOARD_MENU, result);

        result = ScoreboardMenuController.findCommand("menu show-current");
        Assertions.assertEquals(ScoreboardMenuMessages.SHOW_MENU, result);

        result = ScoreboardMenuController.findCommand("menu");
        Assertions.assertEquals(ScoreboardMenuMessages.INVALID_COMMAND, result);
    }

    @Test
    void findCommandSortAllPlayersMethod() {
        ByteArrayOutputStream outContent = Utils.setByteArrayOutputStream();

        new Player("a", "123", "a").increaseScore(1000);
        new Player("a", "123", "A").increaseScore(1000);
        new Player("a", "123", "a ").increaseScore(1000);
        new Player("a", "123", "A !").increaseScore(1001);
        new Player("a", "123", "A ").increaseScore(1001);

        ScoreboardMenuController.findCommand("scoreboard show");
        String expectedResult = "1- A : 1001\n" +
                "1- A !: 1001\n" +
                "3- a: 1000\n" +
                "3- A: 1000\n" +
                "3- a : 1000\n";

        Assertions.assertEquals(expectedResult, outContent.toString());
    }
}