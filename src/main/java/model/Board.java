package model;

import controller.duelmenu.DuelMenuMessages;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import model.cards.Card;
import model.cards.magiccard.MagicCard;
import model.cards.monstercard.MonsterCard;
import view.DuelMenuView;

import java.awt.*;
import java.util.ArrayList;

public class Board {
    private MonsterCard[] monstersZone;
    private MagicCard[] magicsZone;
    private ArrayList<Card> graveyard;
    private ArrayList<Card> cardsInHand;
    private Deck deck;
    private MagicCard fieldZone;
    private Card selectedCard;
//    if this boolean equals "false" so we can conclude that opponent showSelectedCard selected or nothing selected
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
        this.deck = deck;
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
            if (monstersZone[i] != null && monstersZone[i].getName().equals(cardName) && !isCardFaceUp) isCardFaceUp = monstersZone[i].getCardFaceUp();
        }
        for (int i = 1; i < magicsZone.length; i++) {
            if (magicsZone[i] != null && magicsZone[i].getName().equals(cardName) && !isCardFaceUp) isCardFaceUp = magicsZone[i].getCardFaceUp();
        }
        return isCardFaceUp;
    }

    public void addSpellCardToFieldZone(MagicCard spellCard) {
        MagicCard previousFieldZone = fieldZone;
        if (previousFieldZone != null) graveyard.add(previousFieldZone);
        setFieldZone(spellCard);
        cardsInHand.remove(spellCard);
    }

    public void addMagicCardToMagicsZone(MagicCard magicCard) {
        for (int i = 1; i < magicsZone.length; i++) {
            if (magicsZone[i] == null) {
                magicsZone[i] = magicCard;
                cardsInHand.remove(magicCard);

                Image image = new Image(Card.getBackImageAddress());
                DuelMenuView.getOwnMagicRectangles()[i].setFill(new ImagePattern(image));
                return;
            }
        }
    }

    public void setMonsterCardInMonstersZone(MonsterCard monsterCard) {
        for (int i = 1; i < monstersZone.length; i++) {
            if (monstersZone[i] == null) {
                monstersZone[i] = monsterCard;
                cardsInHand.remove(monsterCard);

                Image image = new Image(Card.getBackImageAddress());
                DuelMenuView.getOwnMonsterRectangles()[i].setFill(new ImagePattern(image));
                return;
            }
        }
    }

    public boolean isCardAvailableInMonstersZone(MonsterCard monsterCard) {
        for (int i = 1; i < monstersZone.length; i++) {
            if (monstersZone[i] != null && monstersZone[i].equals(monsterCard)) return true;
        }
        return false;
    }

    public int getNumberOfFaceUpMonsterCards() {
        int numberOfFaceUpMonsterCards = 0;
        for (int i = 1; i < monstersZone.length; i++) {
            if (monstersZone[i] != null && monstersZone[i].getCardFaceUp()) ++numberOfFaceUpMonsterCards;
        }

        return numberOfFaceUpMonsterCards;
    }

    public int getNumberOfWarriorMonsterCards() {
        int numberOfWarriorMonsterCards = 0;
        for (int i = 1; i < monstersZone.length; i++) {
            if (monstersZone[i] != null && monstersZone[i].getMonsterType().equals("Warrior")) ++numberOfWarriorMonsterCards;
        }

        return numberOfWarriorMonsterCards;
    }

    public void moveMagicCardToGraveyard(MagicCard magicCard) {
//        used for normal and ritual spell cards
        for (int i = 1; i < magicsZone.length; i++) {
            if (magicsZone[i] != null && magicsZone[i].equals(magicCard)) {
                magicsZone[i] = null;
                break;
            }
        }

        cardsInHand.remove(magicCard);
        if (selectedCard.equals(magicCard)) selectedCard = null;

        magicCard.setIsSetInThisTurn(false);
        magicCard.setCardFaceUp(false);
        magicCard.setPowerUsed(false);
        graveyard.add(magicCard);
    }

    public MagicCard getFaceDownMagicCardFromMagicsZoneByName(String cardName) {
//        this function returns the first faceDown showSelectedCard
        for (int i = 1; i < magicsZone.length; i++) {
            if (magicsZone[i] != null && magicsZone[i].getName().equals(cardName) && !magicsZone[i].getCardFaceUp())
                return magicsZone[i];
        }
        return null;
    }

    public MagicCard getFaceUpMagicCardFromMagicsZoneByName(String cardName) {
//        this function returns the first faceUp showSelectedCard
        for (int i = 1; i < magicsZone.length; i++) {
            if (magicsZone[i] != null && magicsZone[i].getName().equals(cardName) && magicsZone[i].getCardFaceUp())
                return magicsZone[i];
        }
        return null;
    }

    public void removeAttackPositionMonsterCards() {
        for (int i = 1; i < monstersZone.length; i++) {
            if (monstersZone[i] != null && !monstersZone[i].isDefensePosition()) {
                monstersZone[i].setCardFaceUp(false);
                monstersZone[i].setPowerUsed(false);
                graveyard.add(monstersZone[i]);
                monstersZone[i] = null;
            }
        }

    }

    public boolean isMonsterZoneFull() {
        for (int i = 1; i <= 5; i++) {
            if (monstersZone[i] == null) return false;
        }
        return true;
    }

    public boolean isMonsterZoneEmpty() {
        for (int i = 1; i <= 5; i++) {
            if (monstersZone[i] != null) return true;
        }
        return false;
    }

    public boolean isThereOneMonsterForTribute() {
        for (int i = 1; i <= 5; i++) {
            if (monstersZone[i] != null)
                return true;
        }
        return false;
    }

    public boolean isThereMonsterCardInAddress(int address) {
        return monstersZone[address] != null;
    }

    public void setSummonCardOnMonsterZone() {
        removeSelectedCardFromHand();
        for (int i = 1; i <= 5; i++) {
            if (monstersZone[i] == null) {
                monstersZone[i] = (MonsterCard) selectedCard;
                monstersZone[i].setCardFaceUp(true);
                monstersZone[i].setDefensePosition(false);
                break;
            }
        }
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
        return count >= 2;
    }

    public void removeTribute(int address) {
        graveyard.add(monstersZone[address]);
        monstersZone[address] = null;
    }
}
