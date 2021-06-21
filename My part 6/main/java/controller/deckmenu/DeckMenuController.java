package controller.deckmenu;

import model.*;
import model.cards.Card;

import java.util.Objects;

public class DeckMenuController {

    private static DeckMenuController instance = null;

    private DeckMenuController() {
    }

    public static DeckMenuController getInstance() {
        return Objects.requireNonNullElseGet(instance, () -> (instance = new DeckMenuController()));
    }

    public void createDeck(String name, Player owner) {
        if (!DeckMenuTools.isDeckNameUnique(name))
            return;

        new Deck(name, owner , true , true);
        DeckMenuOutput.getInstance().showMessage("deck created successfully!");
    }

    public void deleteDeck(String name) {
        if (!DeckMenuTools.doesDeckExist(name)) {return;}
        // if (DeckMenuTools.isDeckNameUnique(name)) return;

        DeckMenuDatabase.removeDeck(name);
        DeckMenuOutput.getInstance().showMessage("deck deleted successfully!");

    }


    public void setActiveDeck(String name, Player player) {
        Deck deck = null;
        boolean isPermitted = DeckMenuTools.doesDeckExist(name)
                && DeckMenuTools.doesDeckBelongToPlayer(deck = DeckMenuDatabase.getInstance().getDeckByName(name), player);
        if (isPermitted) {
            player.activateADeck(deck);

            DeckMenuOutput.getInstance().showMessage("deck activated successfully!");
        }

    }

    public void addCardToDeck(String cardName, String deckName, Player player, boolean isMain) {
        Card card = null;
        Deck deck = null;
        boolean isPermitted = DeckMenuTools.doesCardExist(cardName)
                && DeckMenuTools.doesDeckExist(deckName)
                && DeckMenuTools.doesDeckBelongToPlayer(deck = DeckMenuDatabase.getInstance().getDeckByName(deckName), player)
                && ((isMain) ? DeckMenuTools.doesDeckHaveSpace(deck) : DeckMenuTools.doesSideDeckHaveSpace(deck))
                && DeckMenuTools.isNumberOfCardsInDeckLessThanFour(deck, card = DeckMenuDatabase.getInstance().getCardByName(cardName))
                && DeckMenuTools.doesPlayerHaveEnoughCards(card , player);
        if (isPermitted) {
            player.removeCardFromBoughtCards(card);
            deck.addCard(card, isMain);
            DeckMenuOutput.getInstance().showMessage("card added to deck successfully!");
        }
    }

    public void removeCardFromDeck(String cardName, String deckName, Player player, boolean isMain) {
        Card card;
        Deck deck = null;
        boolean isPermitted = DeckMenuTools.doesCardExist(cardName)
                && DeckMenuTools.doesDeckExist(deckName)
                && DeckMenuTools.doesDeckBelongToPlayer(deck = DeckMenuDatabase.getInstance().getDeckByName(deckName), player);
        if (isPermitted) {
            card = DeckMenuDatabase.getInstance().getCardByName(cardName);
            player.addCardToBoughtCards(card);
            deck.moveCardTo(player.getAllPlayerCard(),card, isMain , true);
            DeckMenuOutput.getInstance().showMessage("card removed from deck successfully!");
        }
    }

    public void showAllDecks(Player player) {
        for (Deck deck : player.getAllDeck())
            DeckMenuOutput.getInstance().showMessage(deck.toString());

    }

    public void showDeck(String name, Player player, boolean isMain) {
        if (!DeckMenuTools.doesDeckExist(name))
            return;
        Deck deck = DeckMenuDatabase.getInstance().getDeckByName(name);
        if (!DeckMenuTools.doesDeckBelongToPlayer(deck, player))
            return;
        DeckMenuOutput.getInstance().showMessage(deck.toString(isMain));

    }
}
