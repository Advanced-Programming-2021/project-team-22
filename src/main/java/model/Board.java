package model;

import java.util.ArrayList;

public class Board {


    private MonsterCard[] monstersZone;
    private TraoAndSpell[] spellsAndTrapsZone;
    private ArrayList<Card> graveyard;
    private Deck deck;
    //  private MagicCard fieldZone;//TODO: maybe it should be from these classes --> Spell / fieldSpell
    private Card selectedCard;
    protected boolean isMyCardSelected;

    //  private Card selectedOpponentCard;


    {
        monstersZone = new MonsterCard[6];
        spellsAndTrapsZone = new TraoAndSpell[6];
        graveyard = new ArrayList<>();
        //  selectedOpponentCard = null;
        selectedCard = null;
    }

  /*  public void setSelectedOpponentCard(Card selectedOpponentCard) {
        this.selectedOpponentCard = selectedOpponentCard;
    }*/

    public void setMyCardSelected(boolean myCardSelected) {
        isMyCardSelected = myCardSelected;
    }

    public boolean getIsMyCardSelected() {
        return isMyCardSelected;
    }

    public void setSelectedCard(Card selectedOwnCard) {
        this.selectedCard = selectedOwnCard;
    }

    /*public Card getSelectedOpponentCard() {
        return selectedOpponentCard;
    }*/

    public Card getSelectedCard() {
        return selectedCard;
    }

    public ArrayList<Card> getGraveyard() {
        return graveyard;
    }

    public TraoAndSpell[] getSpellsAndTrapsZone() {
        return spellsAndTrapsZone;
    }

    public MonsterCard[] getMonstersZone() {
        return monstersZone;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;//TODO: maybe we should have a copy of deck in duel menu --> if all changes don't apply in main deck
    }

}
