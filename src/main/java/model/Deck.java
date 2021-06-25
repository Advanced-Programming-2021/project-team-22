package model;

import com.google.gson.annotations.Expose;
import model.cards.Card;

import java.util.ArrayList;

public class Deck {
    @Expose
    private final String name;
    @Expose
    private final ArrayList<Card> mainCards;
    @Expose
    private final ArrayList<Card> sideCards;

    {
        mainCards = new ArrayList<>();
        sideCards = new ArrayList<>();
    }

    public Deck(String name) {
        this.name = name;
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

    public boolean isThreeCardsAvailable(String cardName) {
        int number = 0;
        for (Card card : mainCards) {
            if (card.getName().equals(cardName)) ++number;
        }
        for (Card card : sideCards) {
            if (card.getName().equals(cardName)) ++number;
        }

        return number >= 3;
    }

    public void addCardToMainDeck(Card card) {
        if (card != null) mainCards.add(card);
    }

    public void addCardToSideDeck(Card card) {
        if (card != null) sideCards.add(card);
    }

    public Card removeCardFromMainDeck(String cardName) {
        for (Card card : mainCards) {
            if (card.getName().equals(cardName)) {
                mainCards.remove(card);
                return card;
            }
        }

        return null;
    }

    public Card removeCardFromSideDeck(String cardName) {
        for (Card card : sideCards) {
            if (card.getName().equals(cardName)) {
                sideCards.remove(card);
                return card;
            }
        }

        return null;
    }

    public String isValid() {
        if (mainCards.size() >= 40) return "valid";
        else return "invalid";
    }
}