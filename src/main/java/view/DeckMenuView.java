package view;

import controller.Utils;
import controller.deckmenu.DeckMenuController;
import controller.deckmenu.DeckMenuMessages;
import controller.deckmenu.SortDeckByName;
import model.Deck;
import model.Player;
import model.cards.Card;
import model.cards.SortCardByName;
import model.cards.magiccard.MagicCard;
import model.cards.monstercard.MonsterCard;

import java.util.ArrayList;

public class DeckMenuView {
    private final Player loggedInPlayer;

    public DeckMenuView(Player loggedInPlayer) {
        this.loggedInPlayer = loggedInPlayer;
    }

    public static void showADeck(Deck deck, boolean isMain) {
        ArrayList<MonsterCard> monsterCards = new ArrayList<>();
        ArrayList<MagicCard> magicCards = new ArrayList<>();
        DeckMenuController.separateKindOfCards(deck, isMain, monsterCards, magicCards);

        System.out.println("Deck: " + deck.getName());
        if (isMain) System.out.println("Main deck");
        else System.out.println("Side deck");
        System.out.println("Monsters:");
        for (MonsterCard monsterCard : monsterCards) showACard(monsterCard);
        System.out.println("Spell and Traps:");
        for (MagicCard magicCard : magicCards) showACard(magicCard);
    }

    private static void showACard(Card card) {
        System.out.println(card.getName() + ": " + card.getDescription());
    }

    public void deckMenuView() {
        DeckMenuController deckMenuController = new DeckMenuController(loggedInPlayer);

        while (true) {
            String command = Utils.getScanner().nextLine().trim();
            DeckMenuMessages result = deckMenuController.findCommand(command);

            System.out.print(result.getMessage());

            if (result.equals(DeckMenuMessages.EXIT_DECK_MENU)) break;
            else if (result.equals(DeckMenuMessages.SHOW_ALL_DECKS)) showAllDecks();
            else if (result.equals(DeckMenuMessages.SHOW_ALL_CARDS)) showAllCards();
        }
    }

    private void showAllDecks() {
        Deck activatedDeck = loggedInPlayer.getActivatedDeck();
        ArrayList<Deck> otherDecks = (ArrayList<Deck>) loggedInPlayer.getAllDecks().clone();
        otherDecks.remove(activatedDeck);
        otherDecks.sort(new SortDeckByName());

        System.out.println("Decks:");
        System.out.println("Active deck:");
        showADeckInShowAllDecks(activatedDeck);
        System.out.println("Other decks:");
        for (Deck deck : otherDecks) {
            showADeckInShowAllDecks(deck);
        }
    }

    private void showADeckInShowAllDecks(Deck deck) {
        if (deck != null) System.out.println(deck.getName() + ": main deck " + deck.getMainCards().size() +
                ", side deck " + deck.getSideCards().size() + ", " + deck.isValid());
    }

    private void showAllCards() {
        ArrayList<Card> allBoughtCards = new ArrayList<>(loggedInPlayer.getBoughtCards());
        for (Deck deck : loggedInPlayer.getAllDecks()) {
            allBoughtCards.addAll(deck.getMainCards());
            allBoughtCards.addAll(deck.getSideCards());
        }

        allBoughtCards.sort(new SortCardByName());
        for (Card card : allBoughtCards) showACard(card);
    }
}
