package controller.shopmenu;

import controller.Database;
import controller.Utils;
import model.Player;
import model.cards.Card;

import java.util.regex.Matcher;

public class ShopMenuController {
    private static Player loggedInPlayer;

    public static Player getLoggedInPlayer() {
        return loggedInPlayer;
    }

    public static void setLoggedInPlayer(Player loggedInPlayer) {
        ShopMenuController.loggedInPlayer = loggedInPlayer;
    }

    public static void buyACard(Card boughtCard) {
        loggedInPlayer.decreaseMoney(boughtCard.getPrice());
        loggedInPlayer.createCardToBoughtCards(boughtCard);
        Database.updatePlayerInformationInDatabase(loggedInPlayer);
    }

    public static void cheatCodeIncreaseMoney(String command) {
        Matcher matcher = Utils.getMatcher(ShopMenuRegexes.CHEAT_INCREASE_MONEY.getRegex(), command);
        if (matcher.find()) loggedInPlayer.increaseMoney(Integer.parseInt(matcher.group(1)));
    }
}
