package controller;

import model.Board;
import model.cards.monstercard.MonsterCard;
import model.Player;

public class AIClass {

//    public static String getOrder(Board machineBoard, Board playerBoard, Player AIPlayer, Player humanPlayer, Enum phaseOfGame) {
//        if () {//TODO PUT A CONDITION THAT PHASE IS BATTLE PHASE
//            int numberOfMonsterToAttack = -1;
//            selectMachineMonsterCardToAttack(machineBoard, AIPlayer);
//            if (canAttackToFaceUpMonster(machineBoard, playerBoard) != -1) {
//                return "attack" + canAttackToFaceUpMonster(machineBoard, playerBoard);
//            } else if (canAttackToFaceDownCard(playerBoard) != -1) {
//                return "attack" + canAttackToFaceDownCard(playerBoard);
//            }
//        }
//    }
//
//    private static int canAttackToFaceDownCard(Board playerBoard) {
//        MonsterCard[] monsterArray = playerBoard.getMonstersZone();
//        for (int i = 1; i <= 5; i++) {
//            if (monsterArray[i].print().equals("DH"))
//                return i;
//        }
//        return -1;
//    }
//
//    private static void selectMachineMonsterCardToAttack(Board board, Player player) {
//        MonsterCard[] monsterArray = board.getMonstersZone();
//        MonsterCard monsterCard = monsterArray[1];
//        for (int i = 2; i <= 5; i++) {
//            if (monsterCard.getAttackLevel() < monsterArray[i].getAttackLevel())
//                monsterCard = monsterArray[i];
//            board.setMyCardSelected(true);
//            board.setSelectedCard(monsterCard);
//        }
//    }
//
//    private static int canAttackToFaceUpMonster(Board machineBoard, Board playerBoard) {
//        MonsterCard[] monsterArray = playerBoard.getMonstersZone();
//        MonsterCard monsterToAttack = (MonsterCard) machineBoard.getSelectedCard();
//        for (int i = 1; i <= 5; i++) {
//            if (monsterArray[i].print().equals("OO") && monsterArray[i].getAttackLevel() < monsterToAttack.getAttackLevel())
//                return i;
//        }
//
//        for (int i = 1; i <= 5; i++) {
//            if (monsterArray[i].print().equals("DO") && monsterArray[i].getDefenseLevel() < monsterToAttack.getAttackLevel()) {
//            }
//            return i;
//        }
//        return -1;
//    }
//
//    public static String getRandomMove() {
//
//    }
}
