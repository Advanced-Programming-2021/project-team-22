package controller.loginmenu;

public enum LoginMenuMessages {
    USER_CREATED("user created successfully!"),
    USERNAME_EXISTS("user with username <username> already exists"),
    NICKNAME_EXISTS("user with nickname <nickname> already exists"),
    USER_LOGGED_IN("user logged in successfully!"),
    UNMATCHED_USERNAME_AND_PASSWORD("Username and password didn't match!"),
    INVALID_USERNAME("your entered username is invalid"),
    INVALID_NICKNAME("your entered nickname is invalid"),
    INVALID_PASSWORD("your entered password is invalid");

    private String message;

    LoginMenuMessages(String message) {
        this.message = message;
    }

    public static void setUsername(String username) {
        USERNAME_EXISTS.message = "user with username " + username + " already exists";
    }

    public static void setNickname(String nickname) {
        NICKNAME_EXISTS.message = "user with nickname " + nickname + " already exists";
    }

    public String getMessage() {
        return message;
    }
}
