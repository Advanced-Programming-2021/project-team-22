package controller.createcardmenu;

import model.Player;

public class CreateCardMenuController {
    private static Player loggedInPlayer;

    public static Player getLoggedInPlayer() {
        return loggedInPlayer;
    }

    public static void setLoggedInPlayer(Player loggedInPlayer) {
        CreateCardMenuController.loggedInPlayer = loggedInPlayer;
    }
}
