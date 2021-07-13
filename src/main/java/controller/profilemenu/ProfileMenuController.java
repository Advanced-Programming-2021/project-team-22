package controller.profilemenu;

import controller.Database;
import controller.Utils;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import model.Player;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ProfileMenuController {
    private static Player loggedInPlayer;

    public static Player getLoggedInPlayer() {
        return loggedInPlayer;
    }

    public static void setLoggedInPlayer(Player loggedInPlayer) {
        ProfileMenuController.loggedInPlayer = loggedInPlayer;
    }

    public static ProfileMenuMessages changeNickname(String nickname) {
        if (nickname.equals("")) return ProfileMenuMessages.EMPTY_NICKNAME;
        if (Player.isNicknameExist(nickname)) {
            ProfileMenuMessages.setNickname(nickname);
            return ProfileMenuMessages.AVAILABLE_NICKNAME;
        }

        loggedInPlayer.setNickname(nickname);
        Database.updatePlayerInformationInDatabase(loggedInPlayer);
        return ProfileMenuMessages.NICKNAME_CHANGED;
    }

    public static ProfileMenuMessages changePassword(String currentPassword, String newPassword) {
        if (!loggedInPlayer.getPassword().equals(currentPassword)) return ProfileMenuMessages.WRONG_OLD_PASSWORD;
        if (newPassword.equals("")) return ProfileMenuMessages.EMPTY_NEW_PASSWORD;
        if (currentPassword.equals(newPassword)) return ProfileMenuMessages.SAME_PASSWORDS;

        loggedInPlayer.setPassword(newPassword);
        Database.updatePlayerInformationInDatabase(loggedInPlayer);
        return ProfileMenuMessages.PASSWORD_CHANGED;
    }

    public static Image changeAvatar() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Json Files", "*.png"));
        fileChooser.setInitialDirectory(new File("src/main/resources/preparedAvatars"));
        File selectedFile = fileChooser.showOpenDialog(Utils.getStage());
        if (selectedFile == null) return null;

        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(selectedFile);
            loggedInPlayer.setAvatar(bufferedImage);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        Database.updatePlayerInformationInDatabase(loggedInPlayer);
        return Utils.convertToFxImage(bufferedImage);
    }
}
