package model.cards.magiccard;

import model.cards.Card;
import model.cards.CardTypes;

public class MagicCard extends Card {
    protected final String icon;
    protected final MagicCardStatuses status;

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

    public void print() {
//        TODO: handle it --> «this.equals(null)» have error    and    «System.out.print» should be in the view
//        if (this.equals(null))
//            System.out.print("E     ");
//        else if (isCardFaceUp)
//            System.out.print("O     ");
//        else System.out.print("H     ");
    }
}
