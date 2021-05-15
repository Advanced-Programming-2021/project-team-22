import controller.ProfileMenuMessages;
import model.Player;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import controller.ProfileMenuController;
import controller.ProfileMenuMessages;

import java.nio.file.FileSystemException;

public class ProfileMenuTest {
    @Test
    public void changeName() throws FileSystemException {
        String userName = "parsa";
        String password = "123";
        String nickname = "P";
        Player player = new Player(userName, password, nickname);
        ProfileMenuController profileMenuController = new ProfileMenuController(player);
        Assertions.assertEquals(ProfileMenuMessages.CHANGE_NICKNAME_DONE, profileMenuController.findCommand("profile change --nickname newname"));
        Assertions.assertEquals("newname", player.getNickname());
    }

    @Test
    public void changePassword() throws FileSystemException {
        String userName = "parsa";
        String password = "123";
        String nickname = "P";
        Player player = new Player(userName, password, nickname);
        ProfileMenuController profileMenuController = new ProfileMenuController(player);
        Assertions.assertEquals(ProfileMenuMessages.CHANGE_PASSWORD_DONE, profileMenuController.findCommand("profile change --password --current 123 --new newPass"));
        Assertions.assertEquals("123", player.getPassword());

    }
}
