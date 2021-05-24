package model;

import java.util.ArrayList;
import java.util.TreeMap;

public class Card {
    protected static ArrayList<Card> allCards = new ArrayList<>();
    protected String cardType;
    protected String name;
    protected String description;
   // protected String upDown;
    protected boolean isPowerUsed = false;
    protected transient boolean isCardFaceUp = false;
    public Card(String cardType, String name, String description, String upDown) {
        setCardType(cardType);
        setName(name);
        setDescription(description);
        allCards.add(this);
    }



    public static ArrayList<Card> getAllCards() {
        return allCards;
    }

    public static void setAllCards(ArrayList<Card> allCards) {
        Card.allCards = allCards;
    }

    public static Card getCardByName(String name) {
        for (Card card : allCards) {
            if (card.getName().equals(name)) return card;
        }
        return null;
    }


    public String getDescription() {
        return description;
    }

    public void setPowerUsed(boolean powerUsed) {
        isPowerUsed = powerUsed;
    }
    public boolean getIsPowerUsed(){
        return this.isPowerUsed;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCardFaceUp(boolean b){
        isCardFaceUp = b;
    }

    public boolean isCardFaceUp() {
        return isCardFaceUp;
    }

    //public void callCard(){}

}
