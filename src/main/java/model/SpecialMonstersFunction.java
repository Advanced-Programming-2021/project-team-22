package model;

import controller.DuelMenuMessages;

public interface SpecialMonstersFunction {
    default DuelMenuMessages attack(Player attackingPlayer, Player opponentPlayer, Board attackingPlayerBoard, Board opponentPlayerBoard, int numberToAttack) {
        MonsterCard attackingCard = (MonsterCard) attackingPlayerBoard.getSelectedCard();
        MonsterCard opponentCard = opponentPlayerBoard.getMonstersZone()[numberToAttack];
        if (opponentCard.defense(attackingPlayer, opponentPlayer, attackingPlayerBoard, opponentPlayerBoard, attackingCard, opponentCard, numberToAttack) != null) {
            if (opponentCard.print().equals("OO")) {
                if (attackingCard.getAttackLevel() > opponentCard.getAttackLevel()) {
                    opponentPlayer.addAmountToLifePoint(opponentCard.getAttackLevel() - attackingCard.getAttackLevel());
                    opponentPlayerBoard.getGraveyard().add(opponentCard);
                    opponentPlayerBoard.getMonstersZone()[numberToAttack] = null;
                    DuelMenuMessages.setOpponentGotDamageInAttack(attackingCard.getAttackLevel() - opponentCard.getAttackLevel());
                    return DuelMenuMessages.OPPONENT_GOT_DAMAGE_IN_ATTACK;
                }
                if (attackingCard.getAttackLevel() == opponentCard.getAttackLevel()) {
                    opponentPlayerBoard.getGraveyard().add(opponentCard);
                    opponentPlayerBoard.getMonstersZone()[numberToAttack] = null;
                    attackingPlayerBoard.getGraveyard().add(attackingCard);
                    deleteMonsterFromZone(attackingCard, attackingPlayerBoard.getMonstersZone());
                    return DuelMenuMessages.BOTH_CARDS_GET_DESTROYED;
                }
                if (attackingCard.getAttackLevel() < opponentCard.getAttackLevel()) {
                    attackingPlayerBoard.getGraveyard().add(attackingCard);
                    deleteMonsterFromZone(attackingCard, attackingPlayerBoard.getMonstersZone());
                    attackingPlayer.addAmountToLifePoint(attackingCard.getAttackLevel() - opponentCard.getAttackLevel());
                    DuelMenuMessages.setAttackingPlayerCardDestroyed(opponentCard.getAttackLevel() - attackingCard.getAttackLevel());
                    return DuelMenuMessages.ATTACKING_PLAYER_CARD_DESTROYED;
                }

            }
            if (opponentCard.print().equals("DO")) {
                if (attackingCard.getAttackLevel() > opponentCard.getDefenseLevel()) {
                    opponentPlayerBoard.getGraveyard().add(opponentCard);
                    opponentPlayerBoard.getMonstersZone()[numberToAttack] = null;
                    return DuelMenuMessages.DEFENSE_POSITION_MONSTER_DESTROYED;
                }
                if (attackingCard.getAttackLevel() == opponentCard.getDefenseLevel()) {
                    return DuelMenuMessages.NO_CARD_DESTROYED;
                }
                if (attackingCard.getAttackLevel() < opponentCard.getDefenseLevel()) {
                    attackingPlayerBoard.getGraveyard().add(attackingCard);
                    deleteMonsterFromZone(attackingCard, attackingPlayerBoard.getMonstersZone());
                    attackingPlayer.addAmountToLifePoint(attackingCard.getAttackLevel() - opponentCard.getAttackLevel());
                    DuelMenuMessages.setReceiveDamageByAttackingToDefenseCard(opponentCard.getAttackLevel() - attackingCard.getAttackLevel());
                    return DuelMenuMessages.RECEIVE_DAMAGE_BY_ATTACKING_TO_DEFENSE_CARD;
                }
            }
            if (opponentCard.print().equals("DH")) {
            }

            return null;
        } else
            return opponentCard.defense(attackingPlayer, opponentPlayer, attackingPlayerBoard, opponentPlayerBoard, attackingCard, opponentCard, numberToAttack);
    }

    default DuelMenuMessages defense(Player attackingPlayer, Player opponentPlayer, Board attackingPlayerBoard, Board opponentPlayerBoard, MonsterCard attackingCard, MonsterCard opponentCard, int numberToAttack) {
        if (opponentCard.getName().equals("Command knight")) {
            commandKnightFunction(opponentPlayerBoard);
        } else if (opponentCard.getName().equals("Yomi Ship")) {
            return yomiShipFunction(attackingPlayerBoard, attackingCard, opponentCard);
        } else if (opponentCard.getName().equals("Suijin")) {
            return suijinFunction(attackingCard);
        } else if (opponentCard.getName().equals("Marshmallon")) {
            return marshmallonFunction(attackingPlayer);
        } else if (opponentCard.getName().equals("Texchanger")) {
            return texchangerFunction(opponentCard);
        } else if (opponentCard.getName().equals("Exploder Dragon")) {
            return exploderDragon(attackingPlayer, opponentPlayer, attackingPlayerBoard, opponentPlayerBoard, attackingCard, opponentCard, numberToAttack);
        }
        return null;
    }

    default DuelMenuMessages texchangerFunction(MonsterCard opponentCard) {
        if (!opponentCard.getIsPowerUsed()) {
            opponentCard.setPowerUsed(true);
            // choosing a card ehzar???????
            return DuelMenuMessages.ATTACK_CANCELED;
        }
        return null;
    }

    default DuelMenuMessages commandKnightFunction(Board opponentPlayerBoard) {
        int i = 1;
        for (i = 1; i <= 5; i++) {
            if (opponentPlayerBoard.getMonstersZone()[i] != null && !opponentPlayerBoard.getMonstersZone()[i].getName().equals("Command knight")) {
                return DuelMenuMessages.YOU_CANT_ATTACK_TO_THIS_CARD;
            }
        }
        return null;
    }

    default DuelMenuMessages yomiShipFunction(Board attackingPlayerBoard, MonsterCard attackingCard, MonsterCard opponentCard) {
        if (opponentCard.print().equals("OO") && attackingCard.attackLevel > opponentCard.getAttackLevel()) {
            attackingPlayerBoard.getGraveyard().add(attackingCard);
            deleteMonsterFromZone(attackingCard, attackingPlayerBoard.getMonstersZone());
        }
        if (opponentCard.print().equals("DO") || opponentCard.print().equals("DH") && attackingCard.attackLevel > opponentCard.getDefenseLevel()) {
            attackingPlayerBoard.getGraveyard().add(attackingCard);
            deleteMonsterFromZone(attackingCard, attackingPlayerBoard.getMonstersZone());
        }
        return null;
    }

    default DuelMenuMessages suijinFunction(MonsterCard attackingCard) {
        attackingCard.setAttackLevel(0);
        return null;
    }

    default DuelMenuMessages marshmallonFunction(Player attackingPlayer) {
        attackingPlayer.addAmountToLifePoint(-1000);
        return null;
    }

    default DuelMenuMessages exploderDragon(Player attackingPlayer, Player opponentPlayer, Board attackingPlayerBoard, Board opponentPlayerBoard, MonsterCard attackingCard, MonsterCard opponentCard, int number) {
        if (opponentCard.print().equals("OO") && attackingCard.attackLevel > opponentCard.getAttackLevel()) {
            attackingPlayerBoard.getGraveyard().add(attackingCard);
            opponentPlayerBoard.getGraveyard().add(opponentCard);
            attackingPlayerBoard.getMonstersZone()[number] = null;
            deleteMonsterFromZone(attackingCard, attackingPlayerBoard.getMonstersZone());
            return DuelMenuMessages.BOTH_CARDS_GET_DESTROYED;
        }
        if (opponentCard.print().equals("DO") || opponentCard.print().equals("DH") && attackingCard.attackLevel > opponentCard.getDefenseLevel()) {
            attackingPlayerBoard.getGraveyard().add(attackingCard);
            opponentPlayerBoard.getGraveyard().add(opponentCard);
            attackingPlayerBoard.getMonstersZone()[number] = null;
            deleteMonsterFromZone(attackingCard, attackingPlayerBoard.getMonstersZone());
            return DuelMenuMessages.BOTH_CARDS_GET_DESTROYED;
        }
        return null;
    }

    default void deleteMonsterFromZone(MonsterCard monster, MonsterCard[] monstersZone) {
        for (int i = 1; i < monstersZone.length; i++) {
            if (monstersZone[i].equals(monster)) {
                monstersZone[i] = null;
                break;
            }
        }
    }
}