package controller.deckmenu;

import controller.Database;
import model.Deck;
import model.Player;
import model.cards.Card;
import model.cards.magiccard.MagicCard;
import model.cards.magiccard.MagicCardStatuses;

public class DeckMenuController {
    private static Player loggedInPlayer;

    public static Player getLoggedInPlayer() {
        return loggedInPlayer;
    }

    public static void setLoggedInPlayer(Player loggedInPlayer) {
        DeckMenuController.loggedInPlayer = loggedInPlayer;
    }

    public static DeckMenuMessages createDeck(String deckName) {
        if (deckName.equals("")) return DeckMenuMessages.INVALID_DECK_NAME;

        if (loggedInPlayer.getDeckByName(deckName) != null) {
            DeckMenuMessages.setAvailableDeck(deckName);
            return DeckMenuMessages.AVAILABLE_DECK;
        }

        loggedInPlayer.addDeckToAllDecks(new Deck(deckName));
        return DeckMenuMessages.DECK_CREATED;
    }

    public static DeckMenuMessages deleteDeck(Deck deck) {
        if (deck == null) return DeckMenuMessages.UNAVAILABLE_SELECTION;

        loggedInPlayer.getBoughtCards().addAll(deck.getMainCards());
        loggedInPlayer.getBoughtCards().addAll(deck.getSideCards());
        loggedInPlayer.removeDeckFromAllDecks(deck);
        if (loggedInPlayer.getActivatedDeck() != null && loggedInPlayer.getActivatedDeck().equals(deck))
            loggedInPlayer.setActivatedDeck(null);

        Database.updatePlayerInformationInDatabase(loggedInPlayer);
        return DeckMenuMessages.DECK_DELETED;
    }

    public static DeckMenuMessages activateADeck(Deck deck) {
        if (deck == null) return DeckMenuMessages.UNAVAILABLE_SELECTION;

        loggedInPlayer.setActivatedDeck(deck);
        Database.updatePlayerInformationInDatabase(loggedInPlayer);
        return DeckMenuMessages.DECK_ACTIVATED;
    }

    public static DeckMenuMessages addCard(Card card, Deck deck, boolean isMain, boolean isBoughtCard) {
        if (isMain && deck.isMainDeckFull()) return DeckMenuMessages.FULL_MAIN_DECK;
        if (!isMain && deck.isSideDeckFull()) return DeckMenuMessages.FULL_SIDE_DECK;

        if (isBoughtCard && deck.isNumberOfCardsReachedToLimitation(card.getName())) {
            if (card instanceof MagicCard && ((MagicCard) card).getStatus().equals(MagicCardStatuses.LIMITED)) {
                DeckMenuMessages.setOneCardAvailable(card.getName(), deck.getName());
                return DeckMenuMessages.ONE_CARD_AVAILABLE;
            } else {
                DeckMenuMessages.setThreeCardsAvailable(card.getName(), deck.getName());
                return DeckMenuMessages.THREE_CARDS_AVAILABLE;
            }
        }

        if (isMain) {
            deck.addCardToMainDeck(card);
            if (isBoughtCard) loggedInPlayer.removeCardFromBoughtCards(card.getName());
            else deck.removeCardFromSideDeck(card);
        } else {
            deck.addCardToSideDeck(card);
            if (isBoughtCard) loggedInPlayer.removeCardFromBoughtCards(card.getName());
            else deck.removeCardFromMainDeck(card);
        }

        Database.updatePlayerInformationInDatabase(loggedInPlayer);
        return DeckMenuMessages.CARD_ADDED;
    }

    public static DeckMenuMessages removeCard(Card card, Deck deck, boolean isMain) {
        if (isMain) deck.removeCardFromMainDeck(card);
        else deck.removeCardFromSideDeck(card);

        loggedInPlayer.getBoughtCards().add(card);
        Database.updatePlayerInformationInDatabase(loggedInPlayer);
        return DeckMenuMessages.CARD_REMOVED;
    }
}
