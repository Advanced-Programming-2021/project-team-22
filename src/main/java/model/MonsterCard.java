package model;

import controller.DuelMenuMessages;

public class MonsterCard extends Card implements SpecialMonstersFunction {
    protected int attackLevel;
    protected int defenseLevel;
    protected Enum kindOfWarrior;
    protected boolean isAttacked = false;
    protected boolean isDefensePosition = false;
    public MonsterCard(String cardType, String name, String description, String upDown, int attackLevel, int defenseLevel, Enum kindOfWarrior) {
        super(cardType, name, description, upDown);
        setAttackLevel(attackLevel);
        setDefenseLevel(defenseLevel);
        setKindOfWarrior(kindOfWarrior);
    }


    public void setDefensePosition(boolean defensePosition) {
        isDefensePosition = defensePosition;
    }

    public boolean getIsDefensePosition(){
        return this.isDefensePosition;
    }

    public int getAttackLevel() {
        return attackLevel;
    }

    public void setAttackLevel(int attackLevel) {
        this.attackLevel = attackLevel;
    }

    public int getDefenseLevel() {
        return defenseLevel;
    }

    public void setDefenseLevel(int defenseLevel) {
        this.defenseLevel = defenseLevel;
    }

    public Enum getKindOfWarrior() {
        return kindOfWarrior;
    }

    public void setKindOfWarrior(Enum kindOfWarrior) {
        this.kindOfWarrior = kindOfWarrior;
    }

    public String print() {

        if (!this.isCardFaceUp() && this.getIsDefensePosition())
            return "DH";
        else if (this.isCardFaceUp() && this.getIsDefensePosition())
            return "DO";
        else if (this.isCardFaceUp && !this.getIsDefensePosition())
            return "OO";
        return "E ";
    }

    public void setAttacked(boolean attacked) {
        isAttacked = attacked;
    }

    public boolean getAttacked() {
        return this.isAttacked;
    }

    public void addAmountToAttack(int amount) {
        this.attackLevel += amount;
    }

    public void addAmountToDefense(int amount) {
        this.defenseLevel += amount;
    }
}