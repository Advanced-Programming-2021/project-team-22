package model;

import model.cards.Card;
import model.cards.magiccard.MagicCard;
import model.cards.monstercard.MonsterCard;

import java.util.ArrayList;

public class Board {
    private MonsterCard[] monstersZone;
    private MagicCard[] magicsZone;
    private ArrayList<Card> graveyard;
    private ArrayList<Card> cardsInHand;
    private Deck deck;
    private MagicCard fieldZone;//TODO: maybe it should be from these classes --> Spell / fieldSpell
    private Card selectedCard;
    //    if this boolean equals "false" so we can conclude that opponent card selected or nothing selected
    private boolean isMyCardSelected;
    private boolean isACardInHandSelected;

    {
        monstersZone = new MonsterCard[6];
        magicsZone = new MagicCard[6];
        graveyard = new ArrayList<>();
        cardsInHand = new ArrayList<>();
        fieldZone = null;
        isMyCardSelected = false;
        isACardInHandSelected = false;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;//TODO: maybe we should have a copy of deck in duel menu --> if all changes don't apply in main deck
    }

    public MonsterCard[] getMonstersZone() {
        return monstersZone;
    }

    public MagicCard[] getMagicsZone() {
        return magicsZone;
    }

    public ArrayList<Card> getGraveyard() {
        return graveyard;
    }

    public ArrayList<Card> getCardsInHand() {
        return cardsInHand;
    }

    public MagicCard getFieldZone() {
        return fieldZone;
    }

    public Card getSelectedCard() {
        return selectedCard;
    }

    public Deck getDeck() {
        return deck;
    }

    public void setSelectedCard(Card selectedCard) {
        this.selectedCard = selectedCard;
    }

    public void setFieldZone(MagicCard fieldZone) {
        this.fieldZone = fieldZone;
    }

    public boolean isMyCardSelected() {
        return isMyCardSelected;
    }

    public void setMyCardSelected(boolean myCardSelected) {
        isMyCardSelected = myCardSelected;
    }

    public boolean isACardInHandSelected() {
        return isACardInHandSelected;
    }

    public void setACardInHandSelected(boolean ACardInHandSelected) {
        isACardInHandSelected = ACardInHandSelected;
    }

    public boolean isMagicsZoneFull() {
        return getNumberOfFullPartsOfMagicsZone() == 5;
    }

    public boolean isMagicsZoneEmpty() {
        return getNumberOfFullPartsOfMagicsZone() == 0;
    }

    public int getNumberOfFullPartsOfMagicsZone() {
        int numberOfFullPartsOfMagicsZone = 0;
        for (int i = 1; i < magicsZone.length; i++) {
            if (magicsZone[i] != null) ++numberOfFullPartsOfMagicsZone;
        }
        return numberOfFullPartsOfMagicsZone;
    }

    public boolean isCardFaceUp(String cardName) {
//        if cardName isn't available, then this method returns false
        boolean isCardFaceUp = false;
        for (int i = 1; i < monstersZone.length; i++) {
            if (monstersZone[i].getName().equals(cardName) && !isCardFaceUp)
                isCardFaceUp = monstersZone[i].getCardFaceUp();
        }
        for (int i = 1; i < magicsZone.length; i++) {
            if (magicsZone[i].getName().equals(cardName) && !isCardFaceUp) isCardFaceUp = magicsZone[i].getCardFaceUp();
        }
        return isCardFaceUp;
    }

    public void addSpellCardToFieldZone(MagicCard spellCard) {
        MagicCard previousFieldZone = fieldZone;
        if (previousFieldZone != null) graveyard.add(previousFieldZone);
        setFieldZone(spellCard);
        cardsInHand.remove(spellCard);
    }

    public boolean addMagicCardToMagicsZone(MagicCard magicCard) {
        for (int i = 1; i < magicsZone.length; i++) {
            if (magicsZone[i] == null) {
                magicsZone[i] = magicCard;
                cardsInHand.remove(magicCard);
                return true;
            }
        }
        return false;
    }

    public boolean isCardAvailableInMonstersZone(MonsterCard monsterCard) {
        for (int i = 1; i < monstersZone.length; i++) {
            if (monstersZone[i].equals(monsterCard)) return true;
        }
        return false;
    }

    public int getNumberOfFaceUpMonsterCards() {
        int numberOfFaceUpMonsterCards = 0;
        for (int i = 1; i < monstersZone.length; i++) {
            if (monstersZone[i].getCardFaceUp()) ++numberOfFaceUpMonsterCards;
        }

        return numberOfFaceUpMonsterCards;
    }

    public int getNumberOfWarriorMonsterCards() {
        int numberOfWarriorMonsterCards = 0;
        for (int i = 1; i < monstersZone.length; i++) {
            if (monstersZone[i].getMonsterType().equals("Warrior")) ++numberOfWarriorMonsterCards;
        }

        return numberOfWarriorMonsterCards;
    }

    public boolean isMonsterZoneFull() {
        for (int i = 1; i <= 5; i++) {
            if (monstersZone[i] == null)
                return false;
        }
        return true;
    }

    public boolean isThereOneMonsterForTribute() {
        for (int i = 1; i <= 5; i++) {
            if (monstersZone[i] != null)
                return true;
        }
        return false;
    }

    public boolean isThereMonsterCardInAddress(int address) {
        if (monstersZone[address] == null)
            return false;
        else return true;
    }

    public void setSummonCardOnMonsterZone() {
        removeSelectedCardFromHand();
        for (int i = 1; i <= 5; i++) {
            if (monstersZone[i] == null) {
                monstersZone[i] = (MonsterCard) selectedCard;
                monstersZone[i].setCardFaceUp(false);//TODO check attack or deffensive???
                break;
            }
        }
        selectedCard = null;
    }

    private void removeSelectedCardFromHand() {
        for (int i = 0; i < cardsInHand.size(); i++) {
            if (cardsInHand.get(i) == selectedCard) {
                cardsInHand.remove(i);
                break;
            }
        }
    }

    public boolean isThereTwoMonsterForTribute() {
        int count = 0;
        for (int i = 1; i <= 5; i++) {
            if (monstersZone[i] != null)
                count++;
        }
        if (count >= 2)
            return true;
        return false;
    }

    public void removeTribute(int address) {
        graveyard.add(monstersZone[address]);
        monstersZone[address] = null;
    }
}
