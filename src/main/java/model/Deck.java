package model;

import controller.deckmenu.DeckMenuDatabase;
import model.*;
import controller.deckmenu.*;
import model.cards.Card;

import java.util.ArrayList;

public class Deck {
    public ArrayList<Card> mainCards = new ArrayList<>();
    public ArrayList<Card> sideCards = new ArrayList<>();
    Player owner;
    String name;
    DeckType type;
    Boolean isActive = false;
    Boolean IsValid;

    public Deck(String name, Player owner, boolean hasSideDeck, boolean shouldBeSaved) {
        this.name = name;
        if (shouldBeSaved)
            DeckMenuDatabase.allDecks.add(this);
        if (!hasSideDeck)
            sideCards = null;
        this.owner = owner;
    }

    public Deck(String name, Player owner) {
        this.name = name;
        sideCards = null;
        this.owner = owner;
    }

    public Deck(String name) {
        this.name = name;
        sideCards = null;
    }
    public Deck() {
        sideCards = null;
    }

    public void updateOwnerDecks() {
        String deckType = (name.length() > 16) ? name.substring(name.length() - 16) : "";
        if (deckType.equals(".purchased-cards"))
            owner.setAllPlayerCard(this);
        else {
            owner.getAllDeck().add(this);
            if (this.isActive)
                owner.setActiveDeck(this);
        }
    }

    public ArrayList<Card> getMainCards() {
        if (mainCards == null)
            return (mainCards = new ArrayList<>());
        return mainCards;
    }

    public void setMainCards(ArrayList<Card> mainCards) {
        this.mainCards = mainCards;
    }

    public ArrayList<Card> getSideCards() {
        return sideCards;
    }

    public void setSideCards(ArrayList<Card> sideCards) {
        this.sideCards = sideCards;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setActivation(Boolean active) {
        isActive = active;
    }

    public void setValid(Boolean valid) {
        IsValid = valid;
    }

    public void setType(DeckType type) {
        this.type = type;
    }

    public void addCard(Card card, boolean shouldBeAddedToMain) {
        if (shouldBeAddedToMain)
            mainCards.add(card);
        else
            sideCards.add(card);
        if (card != null)
            card.setCurrentDeck(this);
    }

    public void addCard(Card card) {
        mainCards.add(card);
        if (card != null && !name.equals("selected collected deck"))
            card.setCurrentDeck(this);
    }

    public void moveCardTo(Deck destination, Card card, boolean isMainForOrigin, boolean isMainForDestination) {
        removeCard(card, isMainForOrigin);
        destination.addCard(card, isMainForDestination);
    }

    public void moveCardToForGame(Deck destination, Card card, boolean isMainForOrigin, boolean isMainForDestination) {
        removeCardForGame(card, isMainForOrigin);
        destination.addCard(card, isMainForDestination);
    }

    public boolean hasCard(Card card, boolean isMain) {
        if (isMain) {
            for (Card cardInMain : mainCards)
                if (cardInMain.getName().equals(card.getName()))
                    return true;

        } else
            for (Card cardInSide : sideCards)
                if (cardInSide.getName().equals(card.getName())) {
                    return true;
                }
        return false;
    }

    public void removeCard(Card card, boolean shouldBeRemovedFromMain) {
        if (shouldBeRemovedFromMain) {
            for (Card cardInMain : mainCards)
                if (cardInMain.getName().equals(card.getName())) {
                    mainCards.remove(cardInMain);
                    return;
                }
        } else
            for (Card cardInSide : sideCards)
                if (cardInSide.getName().equals(card.getName())) {
                    sideCards.remove(cardInSide);
                    return;
                }
    }

    public void removeCardForGame(Card card, boolean shouldBeRemovedFromMain) {
        if (shouldBeRemovedFromMain)
            mainCards.remove(card);
        else
            sideCards.remove(card);
    }

    public int getNumberOfCardsInDeck(Card card) {
        int count = 0;
        for (Card cardInDeck : mainCards) {
            if (cardInDeck.getName().equals(card.getName()))
                count++;
        }
        for (Card cardInDeck : sideCards) {
            if (cardInDeck.getName().equals(card.getName()))
                count++;
        }
        return count;
    }

    public int getNumberOfCardsInMainDeck() {
        return mainCards.size();
    }

    public int getNumberOfCardsInSideDeck() {
        return sideCards.size();
    }

    public void updateCurrentDeck() {
        if (mainCards != null)
            for (Card card : mainCards)
                if (card != null)
                    card.setCurrentDeck(this);
        if (sideCards != null)
            for (Card card : sideCards)
                if (card != null)
                    card.setCurrentDeck(this);
    }
}