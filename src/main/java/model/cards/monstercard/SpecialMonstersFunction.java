package model.cards.monstercard;

import controller.duelmenu.DuelMenuMessages;
import model.Board;
import model.Player;

public interface SpecialMonstersFunction {
    default DuelMenuMessages attack(Player attackingPlayer, Player opponentPlayer, int numberToAttack) {
        Board attackingPlayerBoard = attackingPlayer.getBoard();
        Board opponentPlayerBoard = opponentPlayer.getBoard();
        MonsterCard attackingCard = (MonsterCard) attackingPlayerBoard.getSelectedCard();
        MonsterCard opponentCard = opponentPlayerBoard.getMonstersZone()[numberToAttack];

        if (opponentCard.defense(attackingPlayer, opponentPlayer, attackingCard, opponentCard, numberToAttack) != null) {

            switch (opponentCard.toString()) {
                case "OO":
                    if (attackingCard.attackPoints > opponentCard.attackPoints) {
                        opponentPlayer.decreaseLifePoint(attackingCard.attackPoints - opponentCard.attackPoints);
                        opponentPlayerBoard.getGraveyard().add(opponentCard);
                        opponentPlayerBoard.getMonstersZone()[numberToAttack] = null;
                        DuelMenuMessages.setOpponentGotDamageInAttack(attackingCard.attackPoints - opponentCard.attackPoints);
                        return DuelMenuMessages.OPPONENT_GOT_DAMAGE_IN_ATTACK;
                    } else if (attackingCard.attackPoints == opponentCard.attackPoints) {
                        opponentPlayerBoard.getGraveyard().add(opponentCard);
                        opponentPlayerBoard.getMonstersZone()[numberToAttack] = null;
                        attackingPlayerBoard.getGraveyard().add(attackingCard);
                        deleteMonsterFromZone(attackingCard, attackingPlayerBoard.getMonstersZone());
                        return DuelMenuMessages.BOTH_CARDS_GET_DESTROYED;
                    } else {
//                    so we can conclude that attackingCard.attackPoints < opponentCard.attackPoints
                        attackingPlayerBoard.getGraveyard().add(attackingCard);
                        deleteMonsterFromZone(attackingCard, attackingPlayerBoard.getMonstersZone());
                        attackingPlayer.decreaseLifePoint(opponentCard.attackPoints - attackingCard.attackPoints);
                        DuelMenuMessages.setAttackingPlayerCardDestroyed(opponentCard.attackPoints - attackingCard.attackPoints);
                        return DuelMenuMessages.ATTACKING_PLAYER_CARD_DESTROYED;
                    }

                case "DO":
                    if (attackingCard.attackPoints > opponentCard.attackPoints) {
                        opponentPlayerBoard.getGraveyard().add(opponentCard);
                        opponentPlayerBoard.getMonstersZone()[numberToAttack] = null;
                        return DuelMenuMessages.DEFENSE_POSITION_MONSTER_DESTROYED;
                    } else if (attackingCard.attackPoints == opponentCard.attackPoints) {
                        return DuelMenuMessages.NO_CARD_DESTROYED;
                    } else {
//                    so we can conclude that attackingCard.attackPoints < opponentCard.attackPoints
                        attackingPlayerBoard.getGraveyard().add(attackingCard);
                        deleteMonsterFromZone(attackingCard, attackingPlayerBoard.getMonstersZone());
                        attackingPlayer.decreaseLifePoint(opponentCard.attackPoints - attackingCard.attackPoints);
                        DuelMenuMessages.setReceiveDamageByAttackingToDefenseCard(opponentCard.attackPoints - attackingCard.attackPoints);
                        return DuelMenuMessages.RECEIVE_DAMAGE_BY_ATTACKING_TO_DEFENSE_CARD;
                    }
                case "DH":
                    break;
            }

            return null;

        } else
            return opponentCard.defense(attackingPlayer, opponentPlayer, attackingCard, opponentCard, numberToAttack);
    }

    default DuelMenuMessages defense(Player attackingPlayer, Player opponentPlayer, MonsterCard attackingCard,
                                     MonsterCard opponentCard, int numberToAttack) {
        Board attackingPlayerBoard = attackingPlayer.getBoard();
        Board opponentPlayerBoard = opponentPlayer.getBoard();

        switch (opponentCard.getName()) {
            case "Command knight":
                commandKnightFunction(opponentPlayerBoard);
                break;
            case "Yomi Ship":
                return yomiShipFunction(attackingPlayerBoard, attackingCard, opponentCard);
            case "Suijin":
                return suijinFunction(attackingCard);
            case "Marshmallon":
                return marshmallonFunction(attackingPlayer);
            case "Texchanger":
                return texchangerFunction(opponentCard);
            case "Exploder Dragon":
                return exploderDragon(attackingPlayer, opponentPlayer, attackingCard, opponentCard, numberToAttack);
        }
        return null;
    }

    default DuelMenuMessages texchangerFunction(MonsterCard opponentCard) {
        if (!opponentCard.isPowerUsed()) {
            opponentCard.setPowerUsed(true);
            // choosing a card ehzar???????
            return DuelMenuMessages.ATTACK_CANCELED;
        }
        return null;
    }

    default DuelMenuMessages commandKnightFunction(Board opponentPlayerBoard) {
        for (int i = 1; i <= 5; i++) {
            if (opponentPlayerBoard.getMonstersZone()[i] != null && !opponentPlayerBoard.getMonstersZone()[i].getName().equals("Command knight")) {
                return DuelMenuMessages.YOU_CANT_ATTACK_TO_THIS_CARD;
            }
        }
        return null;
    }

    default DuelMenuMessages yomiShipFunction(Board attackingPlayerBoard, MonsterCard attackingCard, MonsterCard opponentCard) {
        if (opponentCard.toString().equals("OO") && attackingCard.attackPoints > opponentCard.attackPoints) {
            attackingPlayerBoard.getGraveyard().add(attackingCard);
            deleteMonsterFromZone(attackingCard, attackingPlayerBoard.getMonstersZone());
        }
        if (opponentCard.toString().equals("DO") || opponentCard.toString().equals("DH") && attackingCard.attackPoints > opponentCard.defensePoints) {
            attackingPlayerBoard.getGraveyard().add(attackingCard);
            deleteMonsterFromZone(attackingCard, attackingPlayerBoard.getMonstersZone());
        }
        return null;
    }

    default DuelMenuMessages suijinFunction(MonsterCard attackingCard) {
//        attackingCard.setAttackLevel(0);//TODO: undo it!!
        return null;
    }

    default DuelMenuMessages marshmallonFunction(Player attackingPlayer) {
        attackingPlayer.decreaseLifePoint(1000);
        return null;
    }

    default DuelMenuMessages exploderDragon(Player attackingPlayer, Player opponentPlayer, MonsterCard attackingCard,
                                            MonsterCard opponentCard, int number) {
        Board attackingPlayerBoard = attackingPlayer.getBoard();
        Board opponentPlayerBoard = opponentPlayer.getBoard();

        if (opponentCard.toString().equals("OO") && attackingCard.attackPoints > opponentCard.attackPoints) {
            attackingPlayerBoard.getGraveyard().add(attackingCard);
            opponentPlayerBoard.getGraveyard().add(opponentCard);
            attackingPlayerBoard.getMonstersZone()[number] = null;
            deleteMonsterFromZone(attackingCard, attackingPlayerBoard.getMonstersZone());
            return DuelMenuMessages.BOTH_CARDS_GET_DESTROYED;
        }
        if (opponentCard.toString().equals("DO") || opponentCard.toString().equals("DH") && attackingCard.attackPoints > opponentCard.defensePoints) {
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
