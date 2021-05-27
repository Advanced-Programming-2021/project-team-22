import controller.ProfileMenuMessages;
import model.Player;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import controller.ProfileMenuController;
import controller.ProfileMenuMessages;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.file.FileSystemException;

public class ProfileMenuTest {
    @Test
    public void changeName() {
        String userName = "parsa";
        String password = "123";
        String nickname = "P";
        Player player = new Player(userName, password, nickname);
        ProfileMenuController profileMenuController = new ProfileMenuController(player);
        Assertions.assertEquals(ProfileMenuMessages.CHANGE_NICKNAME_DONE, profileMenuController.findCommand("profile change --nickname newname"));
        Assertions.assertEquals("newname", player.getNickname());
    }

    @Test
    public void changePassword() {
        String userName = "parsa";
        String password = "123";
        String nickname = "P";
        Player player = new Player(userName, password, nickname);
        ProfileMenuController profileMenuController = new ProfileMenuController(player);
        Assertions.assertEquals(ProfileMenuMessages.WRONG_CURRENT_PASSWORD, profileMenuController.findCommand("profile change --password --current 12 --new newPass"));
        Assertions.assertEquals("123", player.getPassword());

        Assertions.assertEquals(ProfileMenuMessages.CHANGE_PASSWORD_DONE, profileMenuController.findCommand("profile change --password --current 123 --new newPass"));
        Assertions.assertEquals("newPass", player.getPassword());

    }

    @Test
    public void allError() {
        String userName = "parsa";
        String password = "123";
        String nickname = "P";
        Player player = new Player(userName, password, nickname);
        ProfileMenuController profileMenuController = new ProfileMenuController(player);
        Assertions.assertEquals(ProfileMenuMessages.INVALID_COMMAND, profileMenuController.findCommand("profile"));
        Assertions.assertEquals(ProfileMenuMessages.CANT_NAVIGATE_MENU, profileMenuController.findCommand("loginmenu enter"));
    }

    @Test
    public void checkExit(){
        String userName = "parsa";
        String password = "123";
        String nickname = "P";
        Player player = new Player(userName, password, nickname);
        ProfileMenuController profileMenuController = new ProfileMenuController(player);
        Assertions.assertEquals(ProfileMenuMessages.EXIT_MENU , profileMenuController.findCommand("menu exit"));
    }
    @Test
    public void printTest () {
        System.out.print(" chetori?");
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        outContent.reset();
        System.out.print(" chetori?");

        System.out.println("asd");
        Assertions.assertEquals(" chetori?", outContent.toString());
    }
}
