package model.cards.monstercard;

import model.Board;
import model.cards.Card;
import model.cards.CardTypes;
import model.cards.magiccard.MagicCard;

import java.util.ArrayList;

public class MonsterCard extends Card implements SpecialMonstersFunction {
    protected final short level;
    protected final MonsterCardAttributes attribute;
    protected final String monsterType;
    protected int attackPoints;
    protected int defensePoints;
    protected transient ArrayList<MagicCard> equippedBy;
//    if this boolean equals "false" so we can conclude that showSelectedCard is in attack position
    protected transient boolean isDefensePosition;
    protected transient boolean isAttacked;
    private transient boolean isPositionChangedInThisTurn;

    {
        equippedBy = new ArrayList<>();
        isDefensePosition = false;
        isAttacked = false;
        isPositionChangedInThisTurn = false;
    }

    public MonsterCard(String name, short level, MonsterCardAttributes attribute, String monsterType, CardTypes cardType,
                       int attackPoints, int defensePoints, String description, int price, String frontImageAddress) {
        super(name, description, cardType, price, frontImageAddress);
        this.level = level;
        this.attribute = attribute;
        this.monsterType = monsterType;
        setAttackPoints(attackPoints);
        setDefensePoints(defensePoints);
        allCards.put(name, this);
    }

    public MonsterCard(MonsterCard monsterCard) {
        super(monsterCard.name, monsterCard.description, monsterCard.cardType, monsterCard.price, monsterCard.frontImageAddress);
        this.level = monsterCard.level;
        this.attribute = monsterCard.attribute;
        this.monsterType = monsterCard.monsterType;
        this.attackPoints = monsterCard.attackPoints;
        this.defensePoints = monsterCard.defensePoints;
    }

    public short getLevel() {
        return level;
    }

    public boolean isPositionChangedInThisTurn() {
        return isPositionChangedInThisTurn;
    }

    public void setPositionChangedInThisTurn(boolean positionChangedInThisTurn) {
        isPositionChangedInThisTurn = positionChangedInThisTurn;
    }

    public MonsterCardAttributes getAttribute() {
        return attribute;
    }

    public String getMonsterType() {
        return monsterType;
    }

    public int getAttackPoints() {
        return attackPoints;
    }

    public void setAttackPoints(int attackPoints) {
        this.attackPoints = attackPoints;
    }

    public int getDefensePoints() {
        return defensePoints;
    }

    public void setDefensePoints(int defensePoints) {
        this.defensePoints = defensePoints;
    }

    public boolean isAttacked() {
        return isAttacked;
    }

    public void setAttacked(boolean attacked) {
        isAttacked = attacked;
    }

    public boolean isDefensePosition() {
        return isDefensePosition;
    }

    public void setDefensePosition(boolean defensePosition) {
        isDefensePosition = defensePosition;
    }

    public ArrayList<MagicCard> getEquippedBy() {
        return equippedBy;
    }

    public void addToEquippedBy(MagicCard equippedBy) {
        this.equippedBy.add(equippedBy);
    }

    public void increaseAttackPoints(int amount) {
        attackPoints += amount;
    }

    public void decreaseAttackPoints(int amount) {
        attackPoints -= amount;
    }

    public void increaseDefencePoints(int amount) {
        defensePoints += amount;
    }

    public void decreaseDefencePoints(int amount) {
        defensePoints -= amount;
    }

    public void createEquippedByArrayList() {
        equippedBy = new ArrayList<>();
    }

    public void addEquippedByToGraveyard(Board board) {
        board.getGraveyard().addAll(equippedBy);
        equippedBy.clear();
    }

    public String show() {
        return "Name: " + this.name +
                "\nLevel: " + this.level +
                "\nType: " + this.monsterType +
                "\nATK: " + this.attackPoints +
                "\nDEF: " + this.defensePoints +
                "\nDescription: " + this.description;
    }

    @Override
    public String toString() {
        if (!this.getCardFaceUp() && this.isDefensePosition) return "DH    ";
        else if (this.getCardFaceUp() && this.isDefensePosition) return "DO    ";
        else if (this.getCardFaceUp() && !this.isDefensePosition) return "OO    ";
        return "E     ";
    }
}
