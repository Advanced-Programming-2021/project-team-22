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
        new Player("parsa", "123", "P");
    }

    @Test
    public void changeNickname() {
        Player player = Player.getPlayerByUsername("parsa");
        ProfileMenuController profileMenuController = new ProfileMenuController(player);
        ProfileMenuMessages result = profileMenuController.findCommand("profile change --nickname newname");
        Assertions.assertEquals(ProfileMenuMessages.CHANGE_NICKNAME_DONE, result);
        Assertions.assertEquals("newname", player.getNickname());

        result = profileMenuController.findCommand("profile change --nickname ");
        Assertions.assertEquals(ProfileMenuMessages.INVALID_COMMAND, result);

        new Player("a", "b", "c");
        result = profileMenuController.findCommand("profile change --nickname c");
        Assertions.assertEquals(ProfileMenuMessages.NOT_UNIQUE_NICKNAME, result);

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

        result = profileMenuController.findCommand("profile change --current 12 --new newPass --password");
        Assertions.assertEquals(ProfileMenuMessages.WRONG_CURRENT_PASSWORD, result);

        result = profileMenuController.findCommand("profile change ");
        Assertions.assertEquals(ProfileMenuMessages.INVALID_COMMAND, result);

        result = profileMenuController.findCommand("profile change --password --current 123 --new newPass");
        Assertions.assertEquals(ProfileMenuMessages.CHANGE_PASSWORD_DONE, result);
        Assertions.assertEquals("newPass", player.getPassword());
    }

    @Test
    public void allError() {
        ProfileMenuController profileMenuController = new ProfileMenuController(Player.getPlayerByUsername("parsa"));
        Assertions.assertEquals(ProfileMenuMessages.INVALID_COMMAND, profileMenuController.findCommand("profile"));
        Assertions.assertEquals(ProfileMenuMessages.INVALID_NAVIGATION, profileMenuController.findCommand("menu enter login menu"));
    }

    @Test
    public void checkExit() {
        ProfileMenuController profileMenuController = new ProfileMenuController(Player.getPlayerByUsername("parsa"));
        Assertions.assertEquals(ProfileMenuMessages.EXIT_PROFILE_MENU, profileMenuController.findCommand("menu exit"));
    }

    @Test
    public void findCommand(){
        ProfileMenuController profileMenuController = new ProfileMenuController(Player.getPlayerByUsername("parsa"));
        Assertions.assertEquals(ProfileMenuMessages.INVALID_COMMAND , profileMenuController.findCommand("show"));
        Assertions.assertEquals(ProfileMenuMessages.SHOW_MENU , profileMenuController.findCommand("menu show-current"));
    }

    @Test
    public void inputPattern(){
        ProfileMenuController profileMenuController = new ProfileMenuController(Player.getPlayerByUsername("parsa"));
        Assertions.assertEquals(ProfileMenuMessages.WRONG_CURRENT_PASSWORD , profileMenuController.findCommand("profile change --password --new newPass --current newPass"));
        Assertions.assertEquals(ProfileMenuMessages.WRONG_CURRENT_PASSWORD , profileMenuController.findCommand("profile change --current newPass --P --N newPass"));
        Assertions.assertEquals(ProfileMenuMessages.INVALID_COMMAND , profileMenuController.findCommand("^profile change --C newPass --N newPass --password"));
        Assertions.assertEquals(ProfileMenuMessages.WRONG_CURRENT_PASSWORD , profileMenuController.findCommand("profile change --N newPass --P --C newPass"));
        Assertions.assertEquals(ProfileMenuMessages.WRONG_CURRENT_PASSWORD , profileMenuController.findCommand("profile change --N newPass --C newPass --P"));
    }
}
