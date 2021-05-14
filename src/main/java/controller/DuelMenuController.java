package controller;

import model.Board;
import model.MonsterCard;
import model.Player;

import java.util.regex.Matcher;

public class DuelMenuController {
    //    TODO: how to handle board
//    TODO: how to handle graveyard --> ArrayList --> put it in Player class/DuelMenuController/board class??
    private Player turnPlayer;
    private Player notTurnPlayer;
    private Enum phase;

    public DuelMenuController(Player turnPlayer, Player notTurnPlayer, Enum phase) {
        setTurnPlayer(turnPlayer);
        setNotTurnPlayer(notTurnPlayer);
        setPhase(phase);
    }

    public void setTurnPlayer(Player turnPlayer) {
        this.turnPlayer = turnPlayer;
    }

    public void setNotTurnPlayer(Player notTurnPlayer) {
        this.notTurnPlayer = notTurnPlayer;
    }

    public void setPhase(Enum phase) {
        this.phase = phase;
    }

    public Enum findCommand(String command) {

    }


    private Enum cheatCodeDecreaseOpponentLifePont(String command) {
        Matcher matcher = Utils.getMatcher("^decrease --opponentLP ([0-9]+)$", command);
        if (matcher.find()) {
            notTurnPlayer.addAmountToLifePoint(-1 * Integer.parseInt(matcher.group(1)));
            return null;
        } else return DuelMenuMessages.INVALID_COMMAND_CHEAT_CODE;

    }

    private Enum cheatCodeIncreaseLifePoint(String command) {
        Matcher matcher = Utils.getMatcher("^increase --LP ([0-9]+)$", command);
        if (matcher.find()) {
            turnPlayer.addAmountToLifePoint(Integer.parseInt(matcher.group(1)));
            return null;
        } else return DuelMenuMessages.INVALID_COMMAND_CHEAT_CODE;
    }

    private Enum cheatCodeToWeenTheGame(Player turnPlayer) {
        notTurnPlayer.setLifePoint(0);
        return null;
    }

    private Enum cheatCodeIncreaseMoney(String command) {
        Matcher matcher = Utils.getMatcher("^increase --money ([0-9]+)$", command);
        if (matcher.find()) {
            turnPlayer.increaseMoney(Integer.parseInt(matcher.group(1)));
            return null;
        } else return DuelMenuMessages.INVALID_COMMAND_CHEAT_CODE;

    }

    private Enum selectCard(String command, Board playerBoard, Board opponentBoard) {
        String[] split = command.split("\\s+");
        if (split[1].equals("--monster") && split[2].equals("--opponent")) {
            return selectMonsterOpponentCard(command, playerBoard, opponentBoard);
        } else if (split[1].equals("--monster") && !split[2].equals("--opponent")) {
            return selectOwnMonsterCard(command, playerBoard);
        } else if (split[1].equals("--spell") && split[2].equals("--opponent")) {
            return selectOpponentSpellCard(command, playerBoard, opponentBoard);
        } else if (split[1].equals("--spell") && !split[2].equals("--opponent")) {
            return selectOwnMagicCard(command, playerBoard);
        } else if (split[1].equals("--field") && !split[2].equals("--opponent")) {//???????????????
        } else if (split[1].equals("--field") && split[2].equals("--opponent")) {//???????????????
        } else if (split[1].equals("--hand")) {//??????????????????
        }
        return null;

    }


    private Enum selectOwnMagicCard(String command, Board playerBoard) {
        Matcher matcher = Utils.getMatcher("^select --spell ([0-9]+)$", command);
        if (matcher.find()) {
            int numberOfChosenCard = Integer.parseInt(matcher.group(1));
            if (checkSelectSpellCard(numberOfChosenCard, playerBoard) == null) {
                playerBoard.setSelectedCard(playerBoard.getSpellsAndTrapsZone()[numberOfChosenCard]);
                playerBoard.setMyCardSelected(true);
                return DuelMenuMessages.SELECT_CARD_DONE;
            }
            return checkSelectMonsterOpponentCard(numberOfChosenCard, playerBoard);
        } else return DuelMenuMessages.INVALID_CARD_SELECT;
    }


    private Enum selectOpponentSpellCard(String command, Board playerBoard, Board opponentBoard) {
        Matcher matcher = Utils.getMatcher("^select --spell --opponent ([0-9]+)$", command);
        if (matcher.find()) {
            int numberOfChosenCard = Integer.parseInt(matcher.group(1));
            if (checkSelectSpellCard(numberOfChosenCard, opponentBoard) == null) {
                playerBoard.setSelectedCard(opponentBoard.getSpellsAndTrapsZone()[numberOfChosenCard]);
                playerBoard.setMyCardSelected(false);
                return DuelMenuMessages.SELECT_CARD_DONE;
            }
            return checkSelectMonsterOpponentCard(numberOfChosenCard, playerBoard);
        } else return DuelMenuMessages.INVALID_CARD_SELECT;
    }

    private Enum checkSelectSpellCard(int numberOfChosenCard, Board board) {
        if (numberOfChosenCard > 5 || numberOfChosenCard < 1) {
            return DuelMenuMessages.INVALID_CARD_SELECT;
        }
        if (board.getSpellsAndTrapsZone()[numberOfChosenCard] == null) {
            return DuelMenuMessages.NO_CARD_FOUND_IN_THE_POSITION;
        }
        return null;
    }

    private Enum selectOwnMonsterCard(String command, Board playerBoard) {
        Matcher matcher = Utils.getMatcher("^select --monster ([0-9]+)$", command);
        if (matcher.find()) {
            int numberOfChosenCard = Integer.parseInt(matcher.group(1));
            if (checkSelectMonsterOpponentCard(numberOfChosenCard, playerBoard) == null) {
                playerBoard.setSelectedCard(playerBoard.getMonstersZone()[numberOfChosenCard]);
                playerBoard.setMyCardSelected(true);
                return DuelMenuMessages.SELECT_CARD_DONE;
            }
            return checkSelectMonsterOpponentCard(numberOfChosenCard, playerBoard);
        } else return DuelMenuMessages.INVALID_CARD_SELECT;
    }


    Enum checkSelectMonsterOpponentCard(int numberOfChosenCard, Board board) {
        if (numberOfChosenCard > 5 || numberOfChosenCard < 1) {
            return DuelMenuMessages.INVALID_CARD_SELECT;
        }

        if (board.getMonstersZone()[numberOfChosenCard] == null) {
            return DuelMenuMessages.NO_CARD_FOUND_IN_THE_POSITION;
        }

        return null;
    }

    private Enum selectMonsterOpponentCard(String command, Board playerBoard, Board opponentBoard) {
        Matcher matcher = Utils.getMatcher("select --monster --opponent ([0-9]+)", command);
        if (matcher.find()) {
            int numberOfChosenCard = Integer.parseInt(matcher.group(1));
            if (checkSelectMonsterOpponentCard(numberOfChosenCard, opponentBoard) == null) {
                playerBoard.setSelectedCard(opponentBoard.getMonstersZone()[numberOfChosenCard]);
                playerBoard.setMyCardSelected(false);
                return DuelMenuMessages.SELECT_CARD_DONE;
            }
            return checkSelectMonsterOpponentCard(numberOfChosenCard, opponentBoard);

        } else return DuelMenuMessages.INVALID_CARD_SELECT;
    }

    private Enum disSelectCard(Board board) {
        if (checkDisSelectCard(board) == null) {
            board.setSelectedCard(null);
            return DuelMenuMessages.DIS_SELECTED;
        } else return checkDisSelectCard(board);
    }

    private Enum checkDisSelectCard(Board board) {
        if (board.getSelectedCard() == null)
            return DuelMenuMessages.NOT_SELECTED_CARD;
    }

    private Enum deselectCard(String command) {
// TODO
    }

    private Enum checkDeselectCard(String command) {

    }

    private Enum summonMonster(String command) {
//        TODO: maybe change the name to --> summonCard
    }

    private Enum checkSummonMonster(String command) {
//        TODO: maybe change the name to --> checkSummonCard
    }

    private void victimize() {
//        TODO: handle it!
    }

    private Enum setAMonster(String command) {

    }

    private Enum checkSetAMonster(String command) {

    }

    private Enum changePosition(String command) {

    }

    private Enum checkChangePosition(String command) {

    }

    private void updateGraveyard() {
//        TODO: handle it!
    }

    private Enum flipSummon(String command) {

    }

    private Enum checkFlipSummon(String command) {

    }

    private Enum attack(String command, Player attackingPlayer, Player opponentPlayer, Board attackingPlayerBoard, Board opponentPlayerBoard) {
        Matcher matcher = Utils.getMatcher("attack ([0-9]+)", command);
        if (matcher.find()) {
            int numberOfChosenCard = Integer.parseInt(matcher.group(1));
            if (checkAttackMonsterCard(attackingPlayerBoard, opponentPlayerBoard, numberOfChosenCard) == null) {
                MonsterCard attackingMonster = (MonsterCard) attackingPlayerBoard.getSelectedCard();
                return attackingMonster.attack(attackingPlayer, opponentPlayer, attackingPlayerBoard, opponentPlayerBoard, numberOfChosenCard);
            }
            return checkAttackMonsterCard(attackingPlayerBoard, opponentPlayerBoard, numberOfChosenCard);

        } else return DuelMenuMessages.INVALID_CARD_SELECT;
    }


    private Enum checkAttackMonsterCard(Board attackingPlayerBoard, Board opponentPlayerBoard, int numberOfChosenCard) {
        if (attackingPlayerBoard.getSelectedCard() == null || !attackingPlayerBoard.getIsMyCardSelected())
            return DuelMenuMessages.NOT_SELECTED_CARD;
        if (attackingPlayerBoard.getSelectedCard() instanceof MonsterCard)
            return DuelMenuMessages.CANT_ATTACK_WITH_CARD;
        //TODO check battle phase
        MonsterCard card = (MonsterCard) attackingPlayerBoard.getSelectedCard();
        if (card.getAttacked())
            return DuelMenuMessages.ATTACKED_BEFORE;
        if (opponentPlayerBoard.getMonstersZone()[numberOfChosenCard] == null)
            return DuelMenuMessages.NO_CARD_FOUND_IN_THE_POSITION;

    }


    private Enum directAttack(String command, Board board, Enum phase, Player player) {
        Enum messages = null;
        messages = checkDirectAttack(command, board, phase);
        if (!messages.equals(null))
            return messages;
        else {
            MonsterCard card = (MonsterCard) board.getSelectedCard();
            player.addAmountToLifePoint(card.getAttackLevel());
            return DuelMenuMessages.DIRECT_ATTACK_DONE;
        }
    }

    private Enum checkDirectAttack(String command, Board board, Enum phase) {
        if (board.getSelectedCard().equals(null) || !board.getIsMyCardSelected())
            return DuelMenuMessages.NOT_SELECTED_CARD;
        if (phase.equals(phase.))
            return DuelMenuMessages.NOT_SUITABLE_PHASE;
        if (board.getSelectedCard() instanceof MonsterCard) {
            MonsterCard card = (MonsterCard) board.getSelectedCard();
            if (card.getAttacked())
                return DuelMenuMessages.ATTACKED_BEFORE;
        } else return DuelMenuMessages.CANT_ATTACK_WITH_CARD;
        return null;
    }

    private Enum activeASpellCard(String command) {

    }

    private Enum checkActiveASpellCard(String command) {

    }

    private Enum setASpell(String command) {

    }

    private Enum checkSetASpell(String command) {

    }

//    TODO: handle activeASpellInOpponentTurn
//    TODO: -----------------------------------------------
//    TODO: we are not sure about under functions

    private Enum ritualSummon(String command) {

    }

    private Enum specialSummon(String command) {

    }

    private Player/*or Enum*/ checkWinner() {

    }

//    TODO: -----------------------------------------------

}
