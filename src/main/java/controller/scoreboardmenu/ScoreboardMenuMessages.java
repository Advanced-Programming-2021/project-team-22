package controller.scoreboardmenu;

public enum ScoreboardMenuMessages {
    INVALID_NAVIGATION("menu navigation is not possible\n"),
    EXIT_SCOREBOARD_MENU(""),
    SHOW_MENU("Scoreboard Menu\n"),
    EMPTY(""),
    INVALID_COMMAND("invalid command\n");

    private final String message;

    ScoreboardMenuMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
