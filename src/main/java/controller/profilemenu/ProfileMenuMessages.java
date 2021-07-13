package controller.profilemenu;

public enum ProfileMenuMessages {
    WRONG_OLD_PASSWORD("current password is invalid"),
    EMPTY_NEW_PASSWORD("your entered new password is empty"),
    SAME_PASSWORDS("please enter a new password"),
    PASSWORD_CHANGED("password changed successfully!"),
    EMPTY_NICKNAME("your entered nickname is empty"),
    AVAILABLE_NICKNAME("user with nickname  <nickname>  already exists"),
    NICKNAME_CHANGED("nickname changed successfully!");

    private String message;

    ProfileMenuMessages(String message) {
        this.message = message;
    }

    public static void setNickname(String nickname) {
        ProfileMenuMessages.AVAILABLE_NICKNAME.message = "user with nickname " + nickname + " already exists";
    }

    public String getMessage() {
        return message;
    }
}
