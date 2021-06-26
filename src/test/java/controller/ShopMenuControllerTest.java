package controller;

import controller.loginmenu.LoginMenuController;
import controller.shopmenu.ShopMenuController;
import controller.shopmenu.ShopMenuMessages;
import model.Player;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;

class ShopMenuControllerTest extends MenuTest {

    @BeforeAll
    static void createPlayer() {
        LoginMenuController.findCommand("user create --username John --P 12345 --nickname Johny");
    }

    @Test
    void findCommandEnterAMenuMethod() {
        ShopMenuController shopMenuController = new ShopMenuController(Player.getPlayerByUsername("John"));

        ShopMenuMessages result = shopMenuController.findCommand("menu enter Login Menu ");
        Assertions.assertEquals(ShopMenuMessages.INVALID_COMMAND, result);

        result = shopMenuController.findCommand("menu enter LOGIN Menu");
        Assertions.assertEquals(ShopMenuMessages.INVALID_NAVIGATION, result);

        result = shopMenuController.findCommand("menu enter main Menu");
        Assertions.assertEquals(ShopMenuMessages.INVALID_NAVIGATION, result);

        result = shopMenuController.findCommand("menu enter DuEl MEnu");
        Assertions.assertEquals(ShopMenuMessages.INVALID_NAVIGATION, result);

        result = shopMenuController.findCommand("menu enter DECk MeNu");
        Assertions.assertEquals(ShopMenuMessages.INVALID_NAVIGATION, result);

        result = shopMenuController.findCommand("menu enter Scoreboard MENU");
        Assertions.assertEquals(ShopMenuMessages.INVALID_NAVIGATION, result);

        result = shopMenuController.findCommand("menu enter profilE MeNU");
        Assertions.assertEquals(ShopMenuMessages.INVALID_NAVIGATION, result);

        result = shopMenuController.findCommand("menu enter ShoP Menu");
        Assertions.assertEquals(ShopMenuMessages.INVALID_NAVIGATION, result);

        result = shopMenuController.findCommand("menu enter ImportExport Menu");
        Assertions.assertEquals(ShopMenuMessages.INVALID_NAVIGATION, result);
    }

    @Test
    void findCommandTestSingleCommands() {
        ShopMenuController shopMenuController = new ShopMenuController(Player.getPlayerByUsername("John"));

        ShopMenuMessages result = shopMenuController.findCommand("menu exit");
        Assertions.assertEquals(ShopMenuMessages.EXIT_SHOP_MENU, result);

        result = shopMenuController.findCommand("menu show-current");
        Assertions.assertEquals(ShopMenuMessages.SHOW_MENU, result);

        result = shopMenuController.findCommand("menu");
        Assertions.assertEquals(ShopMenuMessages.INVALID_COMMAND, result);
    }

    @Test
    void findCommandBuyACardMethod() {
        ShopMenuController shopMenuController = new ShopMenuController(Player.getPlayerByUsername("John"));

        ShopMenuMessages result = shopMenuController.findCommand("shop buy:)");
        Assertions.assertEquals(ShopMenuMessages.INVALID_COMMAND, result);

        result = shopMenuController.findCommand("shop buy A");
        Assertions.assertEquals(ShopMenuMessages.UNAVAILABLE_CARD, result);

        result = shopMenuController.findCommand("shop buy Battle OX");
        Assertions.assertEquals(ShopMenuMessages.EMPTY, result);
        shopMenuController.findCommand("increase --money 2900");
    }

    @Test
    void findCommandShowCardMethod() {
        ShopMenuController shopMenuController = new ShopMenuController(Player.getPlayerByUsername("John"));

        ByteArrayOutputStream outContent = Utils.setByteArrayOutputStream();
        ShopMenuMessages result = shopMenuController.findCommand("card show Battle OX");
        Assertions.assertEquals(ShopMenuMessages.EMPTY, result);

        String expectedResult = "Name: Battle OX\n" +
                "Level: 4\n" +
                "Type: Beast-Warrior\n" +
                "ATK: 1700\n" +
                "DEF: 1000\n" +
                "Description: A monster with tremendous power, it destroys enemies with a swing of its axe.\n";

        Assertions.assertEquals(expectedResult, outContent.toString());
    }

    @Test
    void findCommandCheatCodeIncreaseMoneyMethod() {
        Player player = Player.getPlayerByUsername("John");
        ShopMenuController shopMenuController = new ShopMenuController(player);

        ShopMenuMessages result = shopMenuController.findCommand("increase --money");
        Assertions.assertEquals(ShopMenuMessages.INVALID_COMMAND, result);

        shopMenuController.findCommand("increase --money 1");
        Assertions.assertEquals(100001, player.getMoney());
    }
}