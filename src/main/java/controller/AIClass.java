package controller;

import controller.duelmenu.Phases;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import model.Board;
import model.cards.Card;
import model.cards.CardTypes;
import model.cards.magiccard.MagicCard;
import model.cards.monstercard.MonsterCard;
import model.Player;
import view.DuelMenuView;

import java.util.ArrayList;

public class AIClass {

    public static String getOrder(Board machineBoard, Board playerBoard, Player AIPlayer, Player humanPlayer, Phases phaseOfGame) {

        if ((phaseOfGame == Phases.MAIN_PHASE_1 || phaseOfGame == Phases.MAIN_PHASE_2)
                && !AIPlayer.getBoard().isMagicsZoneFull()) {

            ArrayList<Card> cardsInHand = AIPlayer.getBoard().getCardsInHand();
            for (Card card : cardsInHand) {
                if (card.getCardType().equals(CardTypes.SPELL) && !((MagicCard) card).getIcon().equals("Quick-play")) {
                    AIPlayer.getBoard().setSelectedCard(card);
                    return "set";
                }
            }

        }

        if (phaseOfGame == Phases.MAIN_PHASE_1) {
            if (checkExistMonstereInHand(machineBoard)) {
                if (canSummonWithoutTribute(machineBoard) != -1) {
                    setSummonedMonster(canSummonWithoutTribute(machineBoard), machineBoard);
                } else if (canSummonWithOneTribute(machineBoard) != -1) {
                    machineBoard.setSelectedCard(machineBoard.getCardsInHand().get(canSummonWithoutTribute(machineBoard)));
                    machineBoard.setACardInHandSelected(true);
                    machineBoard.setMyCardSelected(true);
                    payTribute(machineBoard);
                    setSummonedMonster(canSummonWithOneTribute(machineBoard), machineBoard);
                } else if (canSummonWithTwoTribute(machineBoard) != -1) {
                    payTribute(machineBoard);
                    payTribute(machineBoard);
                    setSummonedMonster(canSummonWithTwoTribute(machineBoard), machineBoard);
                }
            }

        }


        if (phaseOfGame == Phases.BATTLE_PHASE && AIPlayer.getBoard().isMonsterZoneEmpty()) {
            int numberOfMonsterToAttack = -1;
            selectMachineMonsterCardToAttack(machineBoard, AIPlayer);
            if (canAttackToFaceUpMonster(machineBoard, playerBoard) != -1) {
                return "attack " + canAttackToFaceUpMonster(machineBoard, playerBoard);
            } else if (canAttackToFaceDownCard(playerBoard) != -1) {
                return "attack " + canAttackToFaceDownCard(playerBoard);
            }
        }

        return "increase --LP 0";
    }

    private static void payTribute(Board machineBoard) {
        int level = 20;
        int index = 0;
        for (int i = 1; i <= 5; i++) {
            if (machineBoard.getMonstersZone()[i].getLevel() < level) {//TODO check!!
                level = machineBoard.getMonstersZone()[i].getLevel();
                index = i;
            }
        }
        machineBoard.getGraveyard().add(machineBoard.getMonstersZone()[index]);
        machineBoard.getMonstersZone()[index] = null;
    }

    private static void setSummonedMonster(int canSummonWithoutTribute, Board machineBoard) {
        for (int i = 1; i <= 5; i++) {
            if (machineBoard.getMonstersZone()[i] == null) {
                machineBoard.getMonstersZone()[i] = (MonsterCard) machineBoard.getCardsInHand().get(canSummonWithoutTribute);
                Image img = new Image(AIClass.class.getResource(machineBoard.getCardsInHand().get(canSummonWithoutTribute).getFrontImageAddress()).toExternalForm());
                DuelMenuView.getOwnMonsterRectangles()[i].setFill(new ImagePattern(img));
                machineBoard.getCardsInHand().remove(canSummonWithoutTribute);
                DuelMenuView.upToDateHand();
                break;
            }
        }
    }

    private static int canSummonWithTwoTribute(Board machineBoard) {
        for (int i = 0; i < machineBoard.getCardsInHand().size(); i++) {
            if (machineBoard.getCardsInHand().get(i) instanceof MonsterCard && checkSpace(machineBoard) && checkTribute(machineBoard, 2))
                return i;
        }
        return -1;
    }

    private static boolean checkTribute(Board machineBoard, int tributeNumber) {
        int count = 0;
        for (int i = 0; i < machineBoard.getMonstersZone().length; i++) {
            if (machineBoard.getMonstersZone()[i] != null)
                count++;
        }
        if (count >= tributeNumber)
            return true;
        return false;
    }

    private static boolean checkSpace(Board machineBoard) {
        for (int i = 0; i < machineBoard.getMonstersZone().length; i++) {
            if (machineBoard.getMonstersZone()[i] == null)
                return true;
        }
        return false;
    }

    private static int canSummonWithOneTribute(Board machineBoard) {
        for (int i = 0; i < machineBoard.getCardsInHand().size(); i++) {
            if (machineBoard.getCardsInHand().get(i) instanceof MonsterCard && ((MonsterCard) machineBoard.getCardsInHand().get(i)).getLevel() <= 6 && checkSpace(machineBoard) && checkTribute(machineBoard, 2))
                return i;
        }
        return -1;
    }

    private static int canSummonWithoutTribute(Board machineBoard) {
        for (int i = 0; i < machineBoard.getCardsInHand().size(); i++) {
            if (machineBoard.getCardsInHand().get(i) instanceof MonsterCard && ((MonsterCard) machineBoard.getCardsInHand().get(i)).getLevel() <= 4 && checkSpace(machineBoard))
                return i;
        }
        return -1;
    }

    private static boolean checkExistMonstereInHand(Board machineBoard) {
        for (int i = 0; i < machineBoard.getCardsInHand().size(); i++) {
            if (machineBoard.getCardsInHand().get(i) instanceof MonsterCard)
                return true;
        }
        return false;
    }

    private static int canAttackToFaceDownCard(Board playerBoard) {
        MonsterCard[] monsterArray = playerBoard.getMonstersZone();
        for (int i = 1; i <= 5; i++) {
            if (monsterArray[i].toString().equals("DH"))
                return i;
        }
        return -1;
    }

    private static void selectMachineMonsterCardToAttack(Board board, Player player) {
        MonsterCard[] monsterArray = board.getMonstersZone();
        MonsterCard monsterCard = monsterArray[1];
        for (int i = 2; i <= 5; i++) {
            if (monsterCard != null && monsterArray[i] != null &&
                    monsterCard.getAttackPoints() < monsterArray[i].getAttackPoints())
                monsterCard = monsterArray[i];
            board.setMyCardSelected(true);
            board.setSelectedCard(monsterCard);
        }
    }

    private static int canAttackToFaceUpMonster(Board machineBoard, Board playerBoard) {
        MonsterCard[] monsterArray = playerBoard.getMonstersZone();
        MonsterCard monsterToAttack = (MonsterCard) machineBoard.getSelectedCard();
        for (int i = 1; i <= 5; i++) {
            if (monsterToAttack != null && monsterArray[i] != null && monsterArray[i].toString().equals("OO") &&
                    monsterArray[i].getAttackPoints() < monsterToAttack.getAttackPoints())
                return i;
        }

        for (int i = 1; i <= 5; i++) {
            if (monsterToAttack != null && monsterArray[i] != null && monsterArray[i].toString().equals("DO") &&
                    monsterArray[i].getAttackPoints() < monsterToAttack.getAttackPoints()) {
            }
            return i;
        }
        return -1;
    }


}
