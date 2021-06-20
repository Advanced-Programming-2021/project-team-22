package view;

import controller.profilemenu.ProfileMenuController;
import controller.profilemenu.ProfileMenuMessages;
import controller.Utils;
import model.Player;

public class ProfileMenuView {
    private final Player loggedInPlayer;

    public ProfileMenuView(Player loggedInPlayer) {
        this.loggedInPlayer = loggedInPlayer;
    }

    public void profileMenuView() {
        ProfileMenuController profileMenuController = new ProfileMenuController(loggedInPlayer);
        while (true) {
            String command = Utils.getScanner().nextLine().trim();
            ProfileMenuMessages result = profileMenuController.findCommand(command);

            if (result.equals(ProfileMenuMessages.EXIT_MENU)) break;
            else System.out.print(result.getMessage());
        }
    }
}
