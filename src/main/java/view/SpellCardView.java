package view;

import controller.Utils;
import model.Board;
import model.Player;
import model.cards.Card;
import model.cards.magiccard.MagicCard;
import model.cards.monstercard.MonsterCard;

import java.util.ArrayList;

public class SpellCardView extends DuelMenuView {
    private SpellCardView(Player firstPlayer, Player secondPlayer) {
        super(firstPlayer, secondPlayer);
    }

    public static void showGraveyardsMonsterCards(Player turnPlayer, Player notTurnPlayer) {
//        it used for Monster Reborn spell card
        System.out.println("your graveyard monster cards:");
        showGraveyardMonsterCards(turnPlayer.getBoard(), 1);
        System.out.println("opponent graveyard monster cards:");
        showGraveyardMonsterCards(notTurnPlayer.getBoard(), Card.findNumberOfMonsterCards(notTurnPlayer.getBoard().getGraveyard()) + 1);
    }

    private static void showGraveyardMonsterCards(Board board, int startNumber) {
//        it used for Monster Reborn spell card
        int count = startNumber;
        for (int i = 0; i < board.getGraveyard().size(); i++) {
            if (Card.isMonsterCard(board.getGraveyard().get(i))) {
                printCard(count, board.getGraveyard().get(i));
                ++count;
            }
        }
        if (count == startNumber) System.out.println("There isn't any monster card.");
    }

    public static String findCardNumber() {
        System.out.println("choose a number:");
        return Utils.getScanner().nextLine();
    }

    public static void invalidNumber() {
        System.out.println("invalid number");
    }

    public static void showFieldSpellCards(ArrayList<MagicCard> magicCards) {
        System.out.println("your field spell cards:");
        if (magicCards.size() == 0) System.out.println("There isn't any field spell card.");
         else {
             for (int i = 1; i <= magicCards.size(); i++) {
                printCard(i, magicCards.get(i - 1));
            }
        }
    }

    public static void showCardsInHand(Player player) {
        System.out.println("you have these cards in hand:");
        ArrayList<Card> cardsInHand = player.getBoard().getCardsInHand();
        if (cardsInHand.size() == 0) System.out.println("There isn't any card in your hand.");
        else {
            for (int i = 1; i <= cardsInHand.size(); i++) {
                printCard(i, cardsInHand.get(i - 1));
            }
        }
    }

    public static void showMagicsZonesCards(Player turnPlayer, Player notTurnPlayer) {
        System.out.println("your spell and trap zone cards:");
        int endNumber = showMagicsZoneCards(turnPlayer, 1);
        System.out.println("opponent spell and trap zone cards:");
        showMagicsZoneCards(notTurnPlayer, endNumber);
    }

    private static int showMagicsZoneCards(Player player, int startNumber) {
        MagicCard[] magicsZone = player.getBoard().getMagicsZone();
        if (player.getBoard().isMagicsZoneEmpty()) System.out.println("There isn't any spell and trap card.");
        else {
            for (int i = 1; i < magicsZone.length; i++) {
                if (magicsZone[i] != null) {
                    printCard(startNumber, magicsZone[i]);
                    ++startNumber;
                }
            }
        }

        return startNumber;
    }

    public static String findNumberOfCardsToChoose() {
        System.out.println("How many cards do you want to choose to destroy?");
        return Utils.getScanner().nextLine();
    }

    public static void showFaceUpMonsterCards(Player turnPlayer, Player notTurnPlayer) {
        System.out.println("your face up monster cards:");
        int endNumber = showEachPlayerFaceUpMonsterCards(turnPlayer.getBoard(), 1);
        System.out.println("opponent face up monster cards:");
        showEachPlayerFaceUpMonsterCards(notTurnPlayer.getBoard(), endNumber);
    }

    private static int showEachPlayerFaceUpMonsterCards(Board board, int startNumber) {
        if (board.getNumberOfFaceUpMonsterCards() == 0) {
            System.out.println("There isn't any face up monster card.");
            return startNumber;
        }

        MonsterCard[] monstersZone = board.getMonstersZone();
        for (int i = 1; i < monstersZone.length; i++) {
            MonsterCard monsterCard = monstersZone[i];
            if (monsterCard.getCardFaceUp()) {
                printCard(startNumber, monsterCard);
                ++startNumber;
            }
        }

        return startNumber;
    }
}
