package controller.profilemenu;

import controller.Database;
import controller.Utils;
import model.Player;

import java.util.regex.Matcher;

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
}
