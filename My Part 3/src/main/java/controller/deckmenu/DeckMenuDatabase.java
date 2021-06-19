package controller.deckmenu;

import model.*;
import model.cards.Card;
import java.util.ArrayList;

public class DeckMenuDatabase {

    public static ArrayList<Player> allPlayers = new ArrayList<>();
    public static ArrayList<Card> allCards = new ArrayList<>();
    public static ArrayList<Deck> allDecks = new ArrayList<>();

    private DeckMenuDatabase() {
    }

    private static DeckMenuDatabase instance;

    public static DeckMenuDatabase getInstance() {
        if (instance == null)
            instance = new DeckMenuDatabase();
        return instance;
    }

    public static void removeDeck(String name) {
        allDecks.removeIf(deck -> deck.getName().equals(name));
    }

    public Card getCardByName(String name) {
        for (Card card : allCards) {
            if (card.getName().equals(name))
                return card;
        }
        return null;

    }

    public Deck getDeckByName(String name) {
        for (Deck deck : allDecks) {
            if (deck.getName().equals(name))
                return deck;
        }
        return null;

    }
    // setPlayers() {file v Jason}
    // setDecks() {file v Jason}
    // loadingDatabase()
    // updatingDatabase()

}
