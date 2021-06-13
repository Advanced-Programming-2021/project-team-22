package deckmenu;

import model.*;

public class DeckMenuTools {
    public static boolean isDeckNameUnique(String name) {
        Deck deck = DeckMenuDatabase.getInstance().getDeckByName(name);
        if (deck == null)
            return true;

        DeckMenuOutput.getInstance().showMessage("deck with name " + name + " already exists");
        return false;

    }
    public static boolean doesDeckExist(String name) {
        Deck deck = DeckMenuDatabase.getInstance().getDeckByName(name);
        if (deck != null)
            return true;
        DeckMenuOutput.getInstance().showMessage("deck with name " + name + " does not exist");
        return false;

    }
    public static boolean doesDeckBelongToPlayer(Deck deck, Player player) {
        if (deck.getOwner().getUsername().equals(player.getUsername()))
            return true;
        DeckMenuOutput.getInstance().showMessage("this deck doesn't belong to you!");
        return false;
    }
    public static boolean doesDeckHaveSpace(Deck deck) {
        if (deck == null) return false;
        if (deck.getMainCards().size() < 60)
            return true;
        DeckMenuOutput.getInstance().showMessage("main deck is full!");
        return false;
    }
    public static boolean doesSideDeckHaveSpace(Deck deck) {
        if (deck == null) return false;
        if (deck.getSideCards().size() < 15)
            return true;
        DeckMenuOutput.getInstance().showMessage("side deck is full!");
        return false;
    }
    public static boolean isNumberOfCardsInDeckLessThanFour(Deck deck, Card card) {
        if (deck.getNumberOfCardsInDeck(card) < 3)
            return true;
        DeckMenuOutput.getInstance().showMessage("there are already three cards with name " + card.getName() +
                " in deck " + deck.getName() + " !");
        return false;
    }
    public static boolean isDeckAllowed(Deck deck) {
        int numberOfCardsInSideDeck = deck.getNumberOfCardsInSideDeck();
        int numberOfCardsInMainDeck = deck.getNumberOfCardsInMainDeck();
        return numberOfCardsInMainDeck <= 60 && numberOfCardsInMainDeck >= 40 && numberOfCardsInSideDeck <= 15;
    }
    public static boolean doesPlayerHaveEnoughCards(Card card, Player player) {
        if (player.getAllPlayerCard().hasCard(card, true))
            return true;
        DeckMenuOutput.getInstance().showMessage("you dont have this type of card anymore!");
        return false;
    }
    public static boolean doesCardExist(String cardName) {
        Card card = DeckMenuDatabase.getInstance().getCardByName(cardName);
        if (card != null)
            return true;
        DeckMenuOutput.getInstance().showMessage("card with name " + cardName + " does not exist");
        return false;
    }

}
