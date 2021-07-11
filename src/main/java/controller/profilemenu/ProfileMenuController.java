package controller.profilemenu;

import controller.Database;
import controller.MenuRegexes;
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

    public ProfileMenuMessages findCommand(String command) {
        if (command.startsWith("menu enter")) return enterAMenu(command);
        else if (command.equals("menu exit")) return ProfileMenuMessages.EXIT_PROFILE_MENU;
        else if (command.equals("menu show-current")) return ProfileMenuMessages.SHOW_MENU;
        else if (command.startsWith("profile change --nickname ")) return changeNickname(command);
        else if (command.startsWith("profile change ")) return changePassword(command);

        return ProfileMenuMessages.INVALID_COMMAND;
    }

    private static ProfileMenuMessages enterAMenu(String command) {
        Matcher matcher = Utils.getMatcher(MenuRegexes.ENTER_A_MENU.getRegex(), command);
        if (!matcher.find()) return ProfileMenuMessages.INVALID_COMMAND;

        return ProfileMenuMessages.INVALID_NAVIGATION;
    }

    private ProfileMenuMessages changeNickname(String command) {
        Matcher matcher = Utils.getMatcher(ProfileMenuRegexes.CHANGE_NICKNAME.getRegex(), command);

        ProfileMenuMessages holdEnum = checkChangeNickName(matcher);

        if (holdEnum == null) {
            loggedInPlayer.setNickname(matcher.group(1));
            Database.updatePlayerInformationInDatabase(loggedInPlayer);
            return ProfileMenuMessages.CHANGE_NICKNAME_DONE;
        }
        return holdEnum;
    }

    private ProfileMenuMessages checkChangeNickName(Matcher matcher) {
        if (matcher.find()) {
            String nickname = matcher.group(1);
            if (Player.isNicknameExist(nickname)) {
                ProfileMenuMessages.setNickname(nickname);
                return ProfileMenuMessages.NOT_UNIQUE_NICKNAME;
            }
            return null;
        } else return ProfileMenuMessages.INVALID_COMMAND;

    }

    private ProfileMenuMessages changePassword(String command) {
        String currentPassword, newPassword;
        Matcher matcher;
        if ((matcher = Utils.getMatcher(ProfileMenuRegexes.CHANGE_PASSWORD_FIRST_PATTERN.getRegex(), command)).find()) {
            currentPassword = matcher.group(1);
            newPassword = matcher.group(2);
        } else if ((matcher = Utils.getMatcher(ProfileMenuRegexes.CHANGE_PASSWORD_SECOND_PATTERN.getRegex(), command)).find()) {
            currentPassword = matcher.group(2);
            newPassword = matcher.group(1);
        } else if ((matcher = Utils.getMatcher(ProfileMenuRegexes.CHANGE_PASSWORD_THIRD_PATTERN.getRegex(), command)).find()) {
            currentPassword = matcher.group(1);
            newPassword = matcher.group(2);
        } else if ((matcher = Utils.getMatcher(ProfileMenuRegexes.CHANGE_PASSWORD_FOURTH_PATTERN.getRegex(), command)).find()) {
            currentPassword = matcher.group(1);
            newPassword = matcher.group(2);
        } else if ((matcher = Utils.getMatcher(ProfileMenuRegexes.CHANGE_PASSWORD_FIFTH_PATTERN.getRegex(), command)).find()) {
            currentPassword = matcher.group(2);
            newPassword = matcher.group(1);
        } else if ((matcher = Utils.getMatcher(ProfileMenuRegexes.CHANGE_PASSWORD_SIXTH_PATTERN.getRegex(), command)).find()) {
            currentPassword = matcher.group(2);
            newPassword = matcher.group(1);
        } else return ProfileMenuMessages.INVALID_COMMAND;

        ProfileMenuMessages holdEnum = checkChangePassword(currentPassword, newPassword);

        if (holdEnum == null) {
            loggedInPlayer.setPassword(newPassword);
            Database.updatePlayerInformationInDatabase(loggedInPlayer);
            return ProfileMenuMessages.CHANGE_PASSWORD_DONE;
        }

        return holdEnum;
    }

    private ProfileMenuMessages checkChangePassword(String currentPassword, String newPassword) {
        if (!loggedInPlayer.getPassword().equals(currentPassword)) return ProfileMenuMessages.WRONG_CURRENT_PASSWORD;
        if (currentPassword.equals(newPassword)) return ProfileMenuMessages.SAME_PASSWORD;
        return null;
    }
}
