package model.cards.magiccard;

import model.cards.Card;
import model.cards.CardTypes;

public class MagicCard extends Card {
    protected final String icon;
    protected final MagicCardStatuses status;
    private transient boolean isSetInThisTurn;

    {
        isSetInThisTurn = false;
    }

    public MagicCard(String name, CardTypes cardType, String icon, String description, MagicCardStatuses status, int price) {
        super(name, description, cardType, price);
        this.icon = icon;
        this.status = status;
        allCards.put(name, this);
    }

    public MagicCard(MagicCard magicCard) {
        super(magicCard.name, magicCard.description, magicCard.cardType, magicCard.price);
        this.icon = magicCard.icon;
        this.status = magicCard.status;
    }

    public String getIcon() {
        return icon;
    }

    public MagicCardStatuses getStatus() {
        return status;
    }

    public void setIsSetInThisTurn(boolean setInThisTurn) {
        isSetInThisTurn = setInThisTurn;
    }

    public boolean isSetInThisTurn() {
        return isSetInThisTurn;
    }

    public String show() {
        return "Name: " + this.name +
                "\n" + this.cardType +
                "\nType: " + this.icon +
                "\nDescription: " + this.description;
    }

    @Override
    public String toString() {
        if (isCardFaceUp) return "O ";
        else return "H ";
    }
}
