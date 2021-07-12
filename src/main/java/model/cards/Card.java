package model.cards;

import com.google.gson.annotations.Expose;
import model.cards.monstercard.MonsterCard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

public class Card {
    protected static HashMap<String, Card> allCards;
    protected static transient String backImageAddress;

    static {
         allCards = new HashMap<>();
        backImageAddress = "/Project-Assets-1.0.0/Assets/Cards/Monsters/Unknown.jpg";
    }

    @Expose
    protected final String name;
    protected final String description;
    protected final CardTypes cardType;
    protected final int price;
    //    if this boolean equals "false" so we can conclude that showSelectedCard is "face down"
    protected transient boolean isCardFaceUp;
    protected transient boolean isPowerUsed;
    protected transient String frontImageAddress;

    {
        isCardFaceUp = false;
        isPowerUsed = false;
    }

    public Card(String name, String description, CardTypes cardType, int price, String frontImageAddress) {
        this.name = name;
        this.description = description;
        this.cardType = cardType;
        this.price = price;
        this.frontImageAddress = frontImageAddress;
    }

    public static Card getCardByName(String name) {
        return allCards.get(name);
    }

    public static boolean isMonsterCard(Card card) {
        try {
            MonsterCard monsterCard = (MonsterCard) card;
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    public static int findNumberOfMonsterCards(ArrayList<Card> cards) {
        int numberOfMonsterCards = 0;
        for (Card card : cards) {
            if (Card.isMonsterCard(card)) ++numberOfMonsterCards;
        }
        return numberOfMonsterCards;
    }

    public static void addCardToAllCards(Card card) {
        allCards.put(card.getName(), card);
    }

    public static void removeCardFromAllCards(Card card) {
        allCards.remove(card.getName());
    }

    public static String getBackImageAddress() {
        return backImageAddress;
    }

    public static HashMap<String, Card> getAllCards() {
        return allCards;
    }

    public String getName() {
        return name;
    }

    public CardTypes getCardType() {
        return cardType;
    }

    public int getPrice() {
        return price;
    }

    public void setPowerUsed(boolean powerUsed) {
        isPowerUsed = powerUsed;
    }

    public boolean isPowerUsed() {
        return isPowerUsed;
    }

    public String getDescription() {
        return description;
    }

    public Boolean getCardFaceUp() {
        return isCardFaceUp;
    }

    public void setCardFaceUp(Boolean cardFaceUp) {
        isCardFaceUp = cardFaceUp;
    }

    public String getFrontImageAddress() {
        return frontImageAddress;
    }
}
