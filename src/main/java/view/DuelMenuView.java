package view;

import controller.AIClass;
import controller.Utils;
import controller.duelmenu.DuelMenuController;
import controller.duelmenu.DuelMenuMessages;
import controller.duelmenu.Phases;
import model.Board;
import model.Player;
import model.cards.Card;
import model.cards.magiccard.MagicCard;
import model.cards.monstercard.MonsterCard;

import java.util.ArrayList;

public class DuelMenuView {
    private Player firstPlayer;
    private Player secondPlayer;
    private final int numberOfRounds;
    private Integer turnFlag;
    private final AIClass AIClass;

    {
        turnFlag = 0;
        AIClass = new AIClass();
    }

    public DuelMenuView(Player firstPlayer, Player secondPlayer, int numberOfRounds) {
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
        this.numberOfRounds = numberOfRounds;
    }

    public DuelMenuView(Player firstPlayer, int numberOfRounds) {
        Player AIPlayer = new Player("", "", "");
        this.firstPlayer = firstPlayer;
        this.secondPlayer = AIPlayer;
        this.numberOfRounds = numberOfRounds;
    }

    public static String findChooseOfPlayerInMiniGame(Player player) {
        System.out.println(player.getUsername() + ", please choose between stone, paper and scissor:");
        return Utils.getScanner().nextLine().trim();
    }

    public static void showGraveyard(Board board) {
        if (board.getGraveyard().size() != 0) {
            for (int i = 1; i <= board.getGraveyard().size(); i++) {
                printCard(i , board.getGraveyard().get(i));
            }
        } else System.out.println("graveyard empty");
        while (true) {
            String input = Utils.getScanner().nextLine();
            if (input.equals("back"))
                break;
        }
    }

    public static void printCard(int number, Card card) {
        System.out.println(number + ". " + card.getName() + ": " + card.getDescription());
    }

    private static void showBoard(Player player, Player opponent) {
        Board playerBoard = player.getBoard();
        Board opponentBoard = opponent.getBoard();

        System.out.println(opponent.getNickname() + ": " + opponent.getLifePoint());
        showCardsInHand(opponentBoard);
        System.out.println(opponentBoard.getDeck().getMainCards().size());
        System.out.print("    ");
        showOpponentMagicsZone(opponentBoard);
        System.out.print("    ");
        showOpponentMonstersZone(opponentBoard);
        System.out.print(opponentBoard.getGraveyard().size() + "                              ");
        if (opponentBoard.getFieldZone() == null) System.out.println("E");
        else System.out.println("O");
        System.out.println("    --------------------------");
        if (opponentBoard.getFieldZone() == null) System.out.print("E                              ");
        else System.out.print("O                               ");
        System.out.println(playerBoard.getGraveyard().size());
        System.out.print("    ");
        showMonstersZone(playerBoard);
        System.out.print("    ");
        showMagicsZone(playerBoard);
        System.out.println("                               " + playerBoard.getDeck().getMainCards().size());
        showCardsInHand(playerBoard);
        System.out.println(player.getNickname() + ": " + player.getLifePoint());
    }

    private static void showMonstersZone(Board board) {
        MonsterCard[] monsters = board.getMonstersZone();
        if (monsters[5] != null) System.out.print(monsters[5] + "    ");
        else System.out.print("E     ");

        if (monsters[3] != null) System.out.print(monsters[3] + "    ");
        else System.out.print("E     ");

        if (monsters[1] != null) System.out.print(monsters[1] + "    ");
        else System.out.print("E     ");

        if (monsters[2] != null) System.out.print(monsters[2] + "    ");
        else System.out.print("E     ");

        if (monsters[4] != null) System.out.print(monsters[4] + "    ");
        else System.out.print("E     ");

        System.out.println();
    }

    private static void showMagicsZone(Board board) {
        MagicCard[] magicsZone = board.getMagicsZone();
        if (magicsZone[5] != null) System.out.print(magicsZone[5]);
        else System.out.print("E     ");

        if (magicsZone[3] != null) System.out.print(magicsZone[3]);
        else System.out.print("E     ");

        if (magicsZone[1] != null) System.out.print(magicsZone[1]);
        else System.out.print("E     ");

        if (magicsZone[2] != null) System.out.print(magicsZone[2]);
        else System.out.print("E     ");

        if (magicsZone[4] != null) System.out.print(magicsZone[4]);
        else System.out.print("E     ");

        System.out.println();
    }

    private static void showOpponentMonstersZone(Board board) {
        MonsterCard[] monsters = board.getMonstersZone();
        if (monsters[4] != null) System.out.print(monsters[4] + "    ");
        else System.out.print("E     ");

        if (monsters[2] != null) System.out.print(monsters[2] + "    ");
        else System.out.print("E     ");

        if (monsters[1] != null) System.out.print(monsters[1] + "    ");
        else System.out.print("E     ");

        if (monsters[3] != null) System.out.print(monsters[3] + "    ");
        else System.out.print("E     ");

        if (monsters[5] != null) System.out.print(monsters[5] + "    ");
        else System.out.print("E     ");

        System.out.println();
    }

    private static void showOpponentMagicsZone(Board board) {
        MagicCard[] magicsZone = board.getMagicsZone();
        if (magicsZone[4] != null) System.out.print(magicsZone[4]);
        else System.out.print("E     ");

        if (magicsZone[2] != null) System.out.print(magicsZone[2]);
        else System.out.print("E     ");

        if (magicsZone[1] != null) System.out.print(magicsZone[1]);
        else System.out.print("E     ");

        if (magicsZone[3] != null) System.out.print(magicsZone[3]);
        else System.out.print("E     ");

        if (magicsZone[5] != null) System.out.print(magicsZone[5]);
        else System.out.print("E     ");

        System.out.println();
    }

    private static void showCardsInHand(Board board) {
        for (int i = 0; i < board.getCardsInHand().size(); i++) {
            System.out.print("C ");
        }
        System.out.println();
    }

    private static void showSelectedCard(Board board) {
        if (showCardCheck(board)) {
            System.out.println(board.getSelectedCard().getName() + " " + board.getSelectedCard().getDescription());

        }
    }

    private static boolean showCardCheck(Board board) {
        if (board.getSelectedCard() == null) {
            System.out.println("you have not selected showSelectedCard");
            return false;
        }
        if (!board.isMyCardSelected() && !board.getSelectedCard().getCardFaceUp()) {
            System.out.println("you can't see this showSelectedCard!");
            return false;
        }
        return true;
    }

    public static boolean activeMagicCardInOpponentTurn(Player notTurnPlayer, Player turnPlayer) {
        showTurnAndBoard(notTurnPlayer, turnPlayer);
        while (true) {
            System.out.println("do you want to activate your trap and spell?\nplease enter yes or no");
            String answer = Utils.getScanner().nextLine();
            if (answer.equals("yes")) return true;
            if (answer.equals("no")) return false;
        }

    }

    public static void showTurnAndBoard(Player turnPlayer, Player notTurnPlayer) {
        System.out.println("now it will be " + turnPlayer.getUsername() + " turn");
        showBoard(turnPlayer, notTurnPlayer);
    }

    public static void showNotTurnToDoMoves() {
        System.out.println("it’s not your turn to play this kind of moves");
    }

    public static void showUnavailableCard() {
        System.out.println("your entered showSelectedCard name is unavailable in your magics zone or that showSelectedCard is activated before");
    }

    public static void showCantActivateCard() {
        System.out.println("you can't activate this showSelectedCard");
    }

    public static void showActiveSuccessfullyInOpponentTurn() {
        System.out.println("spell/trap activated");
    }

    public static void showActiveUnsuccessfullyInOpponentTurn() {
        System.out.println("spell/trap didn't activate");
    }

    public DuelMenuMessages duelMenuView() {
        DuelMenuController duelMenuController = new DuelMenuController();

        DuelMenuMessages resultOfInitialGame = null;
        while (resultOfInitialGame == null || !resultOfInitialGame.equals(DuelMenuMessages.SHOW_TURN_PLAYER)) {
            resultOfInitialGame = duelMenuController.initialGame(firstPlayer, secondPlayer);
            System.out.print(resultOfInitialGame.getMessage());
        }

        while (true) {
            if (checkWinner()) {
                DuelMenuMessages result = giveScores();
                if (result.equals(DuelMenuMessages.ENTER_MAIN_MENU)) break;
                else if (result.equals(DuelMenuMessages.PLAY_ANOTHER_TURN)) return DuelMenuMessages.PLAY_ANOTHER_TURN;
            }

            if (turnFlag == 0) {
                while (true) {
                    showBoard(firstPlayer, secondPlayer);
                    if (turnFlag == 1) {
                        DuelMenuController.preparePlayerForNextTurn(firstPlayer);
                        DuelMenuController.preparePlayerForNextTurn(secondPlayer);
                        Player holdPlayer = duelMenuController.getTurnPlayer();
                        duelMenuController.setTurnPlayer(duelMenuController.getNotTurnPlayer());
                        duelMenuController.setNotTurnPlayer(holdPlayer);
                        break;
                    }
                    System.out.println("it is " + duelMenuController.getTurnPlayer().getNickname() + " turn");

                    ArrayList<Card> turnPlayerMainCards = duelMenuController.getTurnPlayer().getBoard().getDeck().getMainCards();
                    if (duelMenuController.getPhase() == Phases.DRAW_PHASE && turnPlayerMainCards.size() > 0) {
                        MagicCard timeSeal = duelMenuController.getNotTurnPlayer().getBoard().getFaceUpMagicCardFromMagicsZoneByName("Time Seal");
                        if (timeSeal == null || !timeSeal.isSetInThisTurn()) {
                            int indexOfLastMainCard = turnPlayerMainCards.size() - 1;
                            duelMenuController.getTurnPlayer().getBoard().getCardsInHand().add(turnPlayerMainCards.get(indexOfLastMainCard));
                            duelMenuController.getTurnPlayer().getBoard().getDeck().getMainCards().remove(indexOfLastMainCard);
                        }

                        setPhase(duelMenuController);
                    }
                    getOrder(duelMenuController);

                }
            }
            if (turnFlag == 1) {
                while (true) {
                    showBoard(duelMenuController.getTurnPlayer(), duelMenuController.getNotTurnPlayer());
                    if (turnFlag == 0) {
                        DuelMenuController.preparePlayerForNextTurn(firstPlayer);
                        DuelMenuController.preparePlayerForNextTurn(secondPlayer);
                        Player holdPlayer = duelMenuController.getTurnPlayer();
                        duelMenuController.setTurnPlayer(duelMenuController.getNotTurnPlayer());
                        duelMenuController.setNotTurnPlayer(holdPlayer);
                        break;
                    }
                    System.out.println("it is " + duelMenuController.getTurnPlayer().getNickname() + " turn");

                    ArrayList<Card> turnPlayerMainCards = duelMenuController.getTurnPlayer().getBoard().getDeck().getMainCards();
                    if (duelMenuController.getPhase() == Phases.DRAW_PHASE && turnPlayerMainCards.size() > 0) {
                        MagicCard timeSeal = duelMenuController.getNotTurnPlayer().getBoard().getFaceUpMagicCardFromMagicsZoneByName("Time Seal");
                        if (timeSeal == null || !timeSeal.isSetInThisTurn()) {
                            int indexOfLastMainCard = turnPlayerMainCards.size() - 1;
                            duelMenuController.getTurnPlayer().getBoard().getCardsInHand().add(turnPlayerMainCards.get(indexOfLastMainCard));
                            duelMenuController.getTurnPlayer().getBoard().getDeck().getMainCards().remove(indexOfLastMainCard);
                        }

                        setPhase(duelMenuController);
                    }
                    getOrder(duelMenuController);
                }
            }

        }

        return DuelMenuMessages.EMPTY;
    }

    public DuelMenuMessages playWithAI() {
        DuelMenuController duelMenuController = new DuelMenuController();
        duelMenuController.initialGameWithAI(secondPlayer, firstPlayer);

        while (true) {
            if (checkWinner()) {
                DuelMenuMessages result = giveScores();
                if (result.equals(DuelMenuMessages.ENTER_MAIN_MENU)) break;
                else if (result.equals(DuelMenuMessages.PLAY_ANOTHER_TURN)) return DuelMenuMessages.PLAY_ANOTHER_TURN;
            }

            if (turnFlag == 0) {
                while (true) {
                    showBoard(secondPlayer, firstPlayer);
                    if (turnFlag == 1) {
                        DuelMenuController.preparePlayerForNextTurn(firstPlayer);
                        DuelMenuController.preparePlayerForNextTurn(secondPlayer);
                        Player holdPlayer = duelMenuController.getTurnPlayer();
                        duelMenuController.setTurnPlayer(duelMenuController.getNotTurnPlayer());
                        duelMenuController.setNotTurnPlayer(holdPlayer);
                        break;
                    }
                    System.out.println("it is AI turn");

                    ArrayList<Card> turnPlayerMainCards = duelMenuController.getTurnPlayer().getBoard().getDeck().getMainCards();
                    if (duelMenuController.getPhase() == Phases.DRAW_PHASE && turnPlayerMainCards.size() > 0) {
                        MagicCard timeSeal = duelMenuController.getNotTurnPlayer().getBoard().getFaceUpMagicCardFromMagicsZoneByName("Time Seal");
                        if (timeSeal == null || !timeSeal.isSetInThisTurn()) {
                            int indexOfLastMainCard = turnPlayerMainCards.size() - 1;
                            duelMenuController.getTurnPlayer().getBoard().getCardsInHand().add(turnPlayerMainCards.get(indexOfLastMainCard));
                            duelMenuController.getTurnPlayer().getBoard().getDeck().getMainCards().remove(indexOfLastMainCard);
                        }

                        setPhase(duelMenuController);
                    }
                    String command = AIClass.getOrder(secondPlayer.getBoard(), firstPlayer.getBoard(), secondPlayer, firstPlayer, duelMenuController.getPhase());
                    DuelMenuMessages result = duelMenuController.findCommand(command);
                    System.out.print(result.getMessage());
                    setPhase(duelMenuController);
                }
            }
            if (turnFlag == 1) {
                while (true) {
                    showBoard(duelMenuController.getTurnPlayer(), duelMenuController.getNotTurnPlayer());
                    if (turnFlag == 0) {
                        DuelMenuController.preparePlayerForNextTurn(firstPlayer);
                        DuelMenuController.preparePlayerForNextTurn(secondPlayer);
                        Player holdPlayer = duelMenuController.getTurnPlayer();
                        duelMenuController.setTurnPlayer(duelMenuController.getNotTurnPlayer());
                        duelMenuController.setNotTurnPlayer(holdPlayer);
                        break;
                    }
                    System.out.println("it is " + duelMenuController.getTurnPlayer().getNickname() + " turn");

                    ArrayList<Card> turnPlayerMainCards = duelMenuController.getTurnPlayer().getBoard().getDeck().getMainCards();
                    if (duelMenuController.getPhase() == Phases.DRAW_PHASE && turnPlayerMainCards.size() > 0) {
                        MagicCard timeSeal = duelMenuController.getNotTurnPlayer().getBoard().getFaceUpMagicCardFromMagicsZoneByName("Time Seal");
                        if (timeSeal == null || !timeSeal.isSetInThisTurn()) {
                            int indexOfLastMainCard = turnPlayerMainCards.size() - 1;
                            duelMenuController.getTurnPlayer().getBoard().getCardsInHand().add(turnPlayerMainCards.get(indexOfLastMainCard));
                            duelMenuController.getTurnPlayer().getBoard().getDeck().getMainCards().remove(indexOfLastMainCard);
                        }

                        setPhase(duelMenuController);
                    }
                    getOrder(duelMenuController);
                }
            }

        }

        return DuelMenuMessages.EMPTY;
    }

    private DuelMenuMessages giveScores() {
        if (numberOfRounds == 3) {
            if (firstPlayer.getLifePoint() <= 0) {
                secondPlayer.setWonRounds(secondPlayer.getWonRounds() + 1);
                secondPlayer.setMaxLifePointDuringPlay(secondPlayer.getLifePoint());
                firstPlayer.setMaxLifePointDuringPlay(firstPlayer.getLifePoint());
                System.out.println(secondPlayer.getUsername() + " won\nnew game started");
                return DuelMenuMessages.PLAY_ANOTHER_TURN;
            }
            if (secondPlayer.getLifePoint() <= 0) {
                firstPlayer.setWonRounds(firstPlayer.getWonRounds() + 1);
                secondPlayer.setMaxLifePointDuringPlay(secondPlayer.getLifePoint());
                firstPlayer.setMaxLifePointDuringPlay(firstPlayer.getLifePoint());
                System.out.println(firstPlayer.getUsername() + " won\nnew game started");
                return DuelMenuMessages.PLAY_ANOTHER_TURN;
            }
            if (firstPlayer.getWonRounds() == 2) {
                firstPlayer.increaseMoney(3000 + 3L * firstPlayer.getMaxLifePointDuringPlay());
                firstPlayer.increaseScore(3000);
                secondPlayer.increaseMoney(300);
                System.out.println(firstPlayer.getUsername() + " won");
                DuelMenuController.preparePlayerForNextGame(firstPlayer);
                DuelMenuController.preparePlayerForNextGame(secondPlayer);
                return DuelMenuMessages.ENTER_MAIN_MENU;
            }
            if (secondPlayer.getWonRounds() == 2) {
                secondPlayer.increaseMoney(3000 + 3L * secondPlayer.getMaxLifePointDuringPlay());
                secondPlayer.increaseScore(3000);
                firstPlayer.increaseMoney(300);
                System.out.println(secondPlayer.getUsername() + " won");
                DuelMenuController.preparePlayerForNextGame(firstPlayer);
                DuelMenuController.preparePlayerForNextGame(secondPlayer);
                return DuelMenuMessages.ENTER_MAIN_MENU;
            }
        } else {
//            so we can conclude that numberOfRounds == 1
            if (firstPlayer.getLifePoint() <= 0) {
                secondPlayer.increaseScore(1000);
                secondPlayer.increaseMoney(secondPlayer.getLifePoint() + 1000);
                firstPlayer.increaseMoney(100);
                System.out.println(secondPlayer.getUsername() + " won");
            }
            if (secondPlayer.getLifePoint() <= 0) {
                firstPlayer.increaseScore(1000);
                firstPlayer.increaseMoney(firstPlayer.getLifePoint() + 1000);
                secondPlayer.increaseMoney(100);
                System.out.println(firstPlayer.getUsername() + " won");
            }
            DuelMenuController.preparePlayerForNextGame(firstPlayer);
            DuelMenuController.preparePlayerForNextGame(secondPlayer);
            return DuelMenuMessages.ENTER_MAIN_MENU;
        }

        return DuelMenuMessages.EMPTY;
    }

    private boolean checkWinner() {
        return firstPlayer.getLifePoint() <= 0 || secondPlayer.getLifePoint() <= 0 ||
                firstPlayer.getWonRounds() == 2 || secondPlayer.getWonRounds() == 2;
    }

    private void getOrder(DuelMenuController duelMenuController) {
        String command = Utils.getScanner().nextLine().trim();

        switch (command) {
            case "next phase":
                setPhase(duelMenuController);
                break;
            case "show graveyard":
                showGraveyard(duelMenuController.getTurnPlayer().getBoard());
                break;
            case "show selected":
                showSelectedCard(duelMenuController.getTurnPlayer().getBoard());
                break;
            default:
                System.out.print(duelMenuController.findCommand(command).getMessage());
                break;
        }
    }

    public void setPhase(DuelMenuController duelMenuController) {
        if (duelMenuController.getPhase().equals(Phases.DRAW_PHASE)) {
            duelMenuController.setPhase(Phases.STANDBY_PHASE);
        } else if (duelMenuController.getPhase().equals(Phases.STANDBY_PHASE)) {
            duelMenuController.setPhase(Phases.MAIN_PHASE_1);
        } else if (duelMenuController.getPhase().equals(Phases.MAIN_PHASE_1)) {
            duelMenuController.setPhase(Phases.BATTLE_PHASE);
        } else if (duelMenuController.getPhase().equals(Phases.BATTLE_PHASE)) {
            duelMenuController.setPhase(Phases.MAIN_PHASE_2);
        } else if (duelMenuController.getPhase().equals(Phases.MAIN_PHASE_2)) {
            duelMenuController.setPhase(Phases.END_PHASE);
        } else if (duelMenuController.getPhase().equals(Phases.END_PHASE)) {
            duelMenuController.setPhase(Phases.DRAW_PHASE);
            turnFlag++;
            turnFlag %= 2;
        }

        System.out.println(duelMenuController.getPhase());
    }
}
