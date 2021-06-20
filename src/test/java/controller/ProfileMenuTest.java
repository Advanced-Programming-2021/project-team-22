package controller;

import controller.profilemenu.ProfileMenuMessages;
import model.Player;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import controller.profilemenu.ProfileMenuController;

public class ProfileMenuTest extends MenuTest {

    @BeforeAll
    static void createPlayer() {
        String userName = "parsa";
        String password = "123";
        String nickname = "P";
        new Player(userName, password, nickname);
    }

    @Test
    public void changeName() {
        Player player = Player.getPlayerByUsername("parsa");
        ProfileMenuController profileMenuController = new ProfileMenuController(player);
        ProfileMenuMessages result = profileMenuController.findCommand("profile change --nickname newname");
        Assertions.assertEquals(ProfileMenuMessages.CHANGE_NICKNAME_DONE, result);
        Assertions.assertEquals("newname", player.getNickname());
        result = profileMenuController.findCommand("profile change --nickname P");
        Assertions.assertEquals(ProfileMenuMessages.CHANGE_NICKNAME_DONE, result);
    }

    @Test
    public void changePassword() {
        Player player = Player.getPlayerByUsername("parsa");

        ProfileMenuController profileMenuController = new ProfileMenuController(player);
        ProfileMenuMessages result = profileMenuController.findCommand("profile change --password --current 12 --new newPass");
        Assertions.assertEquals(ProfileMenuMessages.WRONG_CURRENT_PASSWORD, result);
        Assertions.assertEquals("123", player.getPassword());

        result = profileMenuController.findCommand("profile change --password --current 123 --new newPass");
        Assertions.assertEquals(ProfileMenuMessages.CHANGE_PASSWORD_DONE, result);
        Assertions.assertEquals("newPass", player.getPassword());
    }

    @Test
    public void allError() {
        ProfileMenuController profileMenuController = new ProfileMenuController(Player.getPlayerByUsername("parsa"));
        Assertions.assertEquals(ProfileMenuMessages.INVALID_COMMAND, profileMenuController.findCommand("profile"));
        Assertions.assertEquals(ProfileMenuMessages.CANT_NAVIGATE_MENU, profileMenuController.findCommand("loginmenu enter"));
    }

    @Test
    public void checkExit() {
        ProfileMenuController profileMenuController = new ProfileMenuController(Player.getPlayerByUsername("parsa"));
        Assertions.assertEquals(ProfileMenuMessages.EXIT_MENU , profileMenuController.findCommand("menu exit"));
    }
}
