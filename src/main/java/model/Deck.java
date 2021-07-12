package model;

import com.google.gson.annotations.Expose;
import model.cards.Card;
import model.cards.magiccard.MagicCard;
import model.cards.magiccard.MagicCardStatuses;

import java.util.ArrayList;

public class Deck {
    @Expose
    private final String name;
    @Expose
    private ArrayList<Card> mainCards;
    @Expose
    private ArrayList<Card> sideCards;

    {
        mainCards = new ArrayList<>();
        sideCards = new ArrayList<>();
    }

    public Deck(String name) {
        this.name = name;
    }

    public Deck(Deck deck) {
        this.name = deck.name;
        this.mainCards = (ArrayList<Card>) deck.mainCards.clone();
        this.sideCards = (ArrayList<Card>) deck.sideCards.clone();
    }

    public String getName() {
        return name;
    }

    public ArrayList<Card> getMainCards() {
        return mainCards;
    }

    public ArrayList<Card> getSideCards() {
        return sideCards;
    }

    public boolean isMainDeckFull() {
        return mainCards.size() >= 60;
    }

    public boolean isSideDeckFull() {
        return sideCards.size() >= 15;
    }

    public boolean isNumberOfCardsReachedToLimitation(String cardName) {
        int number = 0;
        for (Card card : mainCards) {
            if (card.getName().equals(cardName)) ++number;
        }
        for (Card card : sideCards) {
            if (card.getName().equals(cardName)) ++number;
        }

        Card card = Card.getCardByName(cardName);
        if (card instanceof MagicCard && ((MagicCard) card).getStatus().equals(MagicCardStatuses.LIMITED))
            return number >= 1;
        else return number >= 3;
    }

    public void addCardToMainDeck(Card card) {
        if (card != null) mainCards.add(card);
    }

    public void addCardToSideDeck(Card card) {
        if (card != null) sideCards.add(card);
    }

    public void removeCardFromMainDeck(Card card) {
        mainCards.remove(card);
    }

    public void removeCardFromSideDeck(Card card) {
        sideCards.remove(card);
    }

    public String isValid() {
        if (mainCards.size() >= 40) return "valid";
        else return "invalid";
    }
}