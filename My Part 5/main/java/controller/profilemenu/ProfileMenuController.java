package controller.profilemenu;

import controller.Database;
import controller.Utils;
import model.Player;

import java.util.regex.Matcher;

public class ProfileMenuController {
    private final Player loggedInPlayer;

    public ProfileMenuController(Player loggedInPlayer) {
        this.loggedInPlayer = loggedInPlayer;
    }

    public ProfileMenuMessages findCommand(String command) {
        String[] split = command.split("\\s+");
        if (split.length < 2) {
            return ProfileMenuMessages.INVALID_COMMAND;
        } else if (split[1].equals("enter")) {
            return ProfileMenuMessages.CANT_NAVIGATE_MENU;
        } else if (split[1].equals("exit")) {
            return ProfileMenuMessages.EXIT_MENU;
        } else if (split[1].equals("show")) {
            return ProfileMenuMessages.PROFILE_MENU;
        } else if (split[2].equals("--nickname")) {
            return changeNickname(command);
        } else if (command.startsWith("profile change")) {
            return changePassword(command);
        }

        return ProfileMenuMessages.INVALID_COMMAND;
    }

    public ProfileMenuMessages changeNickname(String command) {
        Matcher matcher = Utils.getMatcher(ProfileMenuRegexes.CHANGE_NICKNAME.getRegex(), command);

        ProfileMenuMessages holdEnum = checkChangeNickName(matcher);

        if (holdEnum == null) {
            loggedInPlayer.setNickname(matcher.group(1));
            Database.updatePlayerInformationInDatabase(loggedInPlayer);
            return ProfileMenuMessages.CHANGE_NICKNAME_DONE;
        }
        return holdEnum;
    }

    public ProfileMenuMessages checkChangeNickName(Matcher matcher) {
        if (matcher.find()) {
            String nickname = matcher.group(1);
            if (Player.isNicknameExist(nickname)) {
                ProfileMenuMessages.setNickname(nickname);
                return ProfileMenuMessages.NOT_UNIQUE_NICKNAME;
            }
            return null;
        } else return ProfileMenuMessages.INVALID_COMMAND;

    }

    public ProfileMenuMessages changePassword(String command) {
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

    public ProfileMenuMessages checkChangePassword(String currentPassword, String newPassword) {
        if (!loggedInPlayer.getPassword().equals(currentPassword)) return ProfileMenuMessages.WRONG_CURRENT_PASSWORD;
        if (currentPassword.equals(newPassword)) return ProfileMenuMessages.SAME_PASSWORD;
        return null;
    }
}
