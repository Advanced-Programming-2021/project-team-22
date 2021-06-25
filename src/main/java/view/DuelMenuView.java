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

public class DuelMenuView {
    private Player firstPlayer;
    private Player secondPlayer;
    private Phases phase;
    private int numberOfRounds;
    private Integer turnFlag = 0;
    private AIClass aiClass = new AIClass();

    public DuelMenuView(Player firstPlayer, Player secondPlayer, int numberOfRounds) {
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
        this.numberOfRounds = numberOfRounds;
    }

    public DuelMenuView(Player firstPlayer, int numberOfRounds) {
        Player secondPlayer = new Player("ai", "ai", "ai");
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
        this.numberOfRounds = numberOfRounds;
    }

    public static String findChooseOfPlayerInMiniGame(Player player) {
        System.out.println(player.getUsername() + ", please choose between stone, paper and scissor:");
        return Utils.getScanner().nextLine().trim();
    }

    public static void showGraveyard(Board board) {
        if (board.getGraveyard().size() != 0) {
            for (int i = 1; i <= board.getGraveyard().size(); i++) {
                printCard(i, board.getGraveyard().get(i));
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

    private static void showBoard(Board playerBoard, Board opponentBoard) {
        showCardsInHand(opponentBoard);
//        showLeftCardDeck(opponentBoard);//??????????????????????
        showOpponentMagicsZone(opponentBoard);
        showOpponentMonstersZone(opponentBoard);
        showGraveyard(opponentBoard);
        System.out.println("--------------------------");
        showCardsInHand(playerBoard);
//        showLeftCardDeck(playerBoard);//????????
        showMagicsZone(playerBoard);
        showMonstersZone(playerBoard);
        showGraveyard(playerBoard);
    }

    private static void showMonstersZone(Board board) {
        MonsterCard[] monsters = board.getMonstersZone();
        if (monsters[5] != null) {
            System.out.print(monsters[5].toString() + "    ");
        } else System.out.print("E     ");
        if (monsters[3] != null) {
            System.out.print(monsters[3].toString() + "    ");
        } else System.out.print("E     ");
        if (monsters[1] != null) {
            System.out.print(monsters[1].toString() + "    ");
        } else System.out.print("E     ");
        if (monsters[2] != null) {
            System.out.print(monsters[2].toString() + "    ");
        } else System.out.print("E     ");
        if (monsters[4] != null) {
            System.out.print(monsters[4].toString() + "    ");
        } else System.out.print("E     ");
        System.out.println();
    }

    private static void showMagicsZone(Board board) {
        MagicCard[] magicsZone = board.getMagicsZone();
        if (magicsZone[5] != null) {
            System.out.print(magicsZone[5].toString());
        } else System.out.print("E     ");
        if (magicsZone[3] != null) {
            System.out.print(magicsZone[3].toString());
        } else System.out.print("E     ");
        if (magicsZone[1] != null) {
            System.out.print(magicsZone[1].toString());
        } else System.out.print("E     ");
        if (magicsZone[2] != null) {
            System.out.print(magicsZone[2].toString());
        } else System.out.print("E     ");
        if (magicsZone[4] != null) {
            System.out.print(magicsZone[4].toString());
        } else System.out.print("E     ");
        System.out.println();
    }

    private static void showOpponentMonstersZone(Board board) {
        MonsterCard[] monsters = board.getMonstersZone();
        if (monsters[4] != null) {
            System.out.print(monsters[4].toString() + "    ");
        } else System.out.print("E     ");
        if (monsters[2] != null) {
            System.out.print(monsters[2].toString() + "    ");
        } else System.out.print("E     ");
        if (monsters[1] != null) {
            System.out.print(monsters[1].toString() + "    ");
        } else System.out.print("E     ");
        if (monsters[3] != null) {
            System.out.print(monsters[3].toString() + "    ");
        } else System.out.print("E     ");
        if (monsters[5] != null) {
            System.out.print(monsters[5].toString() + "    ");
        } else System.out.print("E     ");
        System.out.println();
    }

    private static void showOpponentMagicsZone(Board board) {
        MagicCard[] magicsZone = board.getMagicsZone();
        if (magicsZone[4] != null) {
            System.out.print(magicsZone[4].toString());
        } else System.out.print("E     ");
        if (magicsZone[2] != null) {
            System.out.print(magicsZone[2].toString());
        } else System.out.print("E     ");
        if (magicsZone[1] != null) {
            System.out.print(magicsZone[1].toString());
        } else System.out.print("E     ");
        if (magicsZone[3] != null) {
            System.out.print(magicsZone[3].toString());
        } else System.out.print("E     ");
        if (magicsZone[5] != null) {
            System.out.print(magicsZone[5].toString());
        } else System.out.print("E     ");
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
            System.out.println("you have not selected card");
            return false;
        }
        if (!board.isMyCardSelected() && !board.getSelectedCard().getCardFaceUp()) {
            System.out.println("you can't see this card!");
            return false;
        }
        return true;
    }

    public static boolean activeMagicCardInOpponentTurn(Player notTurnPlayer) {
        showTurnAndBoard(notTurnPlayer);
        while (true) {
            System.out.println("do you want to activate your trap and spell?\nplease enter yes or no");
            String answer = Utils.getScanner().nextLine();
            if (answer.equals("yes")) return true;
            if (answer.equals("no")) return false;
        }

    }

    public static void showTurnAndBoard(Player turnPlayer , Player notTurnPlayer) {
        System.out.println("now it will be " + turnPlayer.getUsername() + " turn");
        showBoard(turnPlayer.getBoard(), notTurnPlayer.getBoard());
    }

    public static void showNotTurnToDoMoves() {
        System.out.println("it’s not your turn to play this kind of moves");
    }

    public static void showUnavailableCard() {
        System.out.println("your entered card name is unavailable in your magics zone or that card is activated before");
    }

    public static void showCantActivateCard() {
        System.out.println("you can't activate this card");
    }

    public static void showActiveSuccessfullyInOpponentTurn() {
        System.out.println("spell/trap activated");
    }

    public static void showActiveUnsuccessfullyInOpponentTurn() {
        System.out.println("spell/trap didn't activate");
    }

    public void duelMenuView() {
        DuelMenuController duelMenuController = new DuelMenuController();

        DuelMenuMessages resultOfInitialGame = null;
        while (resultOfInitialGame == null || !resultOfInitialGame.equals(DuelMenuMessages.SHOW_TURN_PLAYER)) {
            resultOfInitialGame = duelMenuController.initialGame(firstPlayer, secondPlayer);
            System.out.print(resultOfInitialGame.getMessage());
        }

        while (true) {
            if (checkWinner()) {
                giveScores();
            }
            if (turnFlag == 0) {
                while (true) {
                    showBoard(firstPlayer.getBoard(), secondPlayer.getBoard());
                    if (turnFlag == 1) {
                        Player holdPlayer = duelMenuController.getTurnPlayer();
                        duelMenuController.setTurnPlayer(duelMenuController.getNotTurnPlayer());
                        duelMenuController.setNotTurnPlayer(holdPlayer);
                        break;
                    }
                    System.out.println("it is " + duelMenuController.getTurnPlayer().getNickname() + "turn");
                    if (phase == Phases.DRAW_PHASE && duelMenuController.getTurnPlayer().getBoard().getDeck().getMainCards().size() > 0) {
                        duelMenuController.getTurnPlayer().getBoard().getCardsInHand().add(duelMenuController.getTurnPlayer().getBoard().getDeck().getMainCards().get(0));
                        duelMenuController.getTurnPlayer().getBoard().getDeck().getMainCards().remove(0);
                        setPhase(duelMenuController);
                    }
                    getOrder(duelMenuController);

                }
            }
            if (turnFlag == 1) {
                while (true) {
                    showBoard(duelMenuController.getTurnPlayer().getBoard(), duelMenuController.getNotTurnPlayer().getBoard());
                    if (turnFlag == 0) {
                        Player holdPlayer = duelMenuController.getTurnPlayer();
                        duelMenuController.setTurnPlayer(duelMenuController.getNotTurnPlayer());
                        duelMenuController.setNotTurnPlayer(holdPlayer);
                        break;
                    }
                    System.out.println("it is " + duelMenuController.getTurnPlayer().getNickname() + "turn");

                    if (phase == Phases.DRAW_PHASE && duelMenuController.getTurnPlayer().getBoard().getDeck().getMainCards().size() > 0) {
                        duelMenuController.getTurnPlayer().getBoard().getCardsInHand().add(duelMenuController.getTurnPlayer().getBoard().getDeck().getMainCards().get(0));
                        duelMenuController.getTurnPlayer().getBoard().getDeck().getMainCards().remove(0);
                        setPhase(duelMenuController);

                    }
                    getOrder(duelMenuController);
                }
            }

        }
    }

    public void playWithAi() {
        DuelMenuController duelMenuController = new DuelMenuController();
        while (true) {
            if (checkWinner()) {
                giveScores();
            }
            if (turnFlag == 0) {
                while (true) {
                    showBoard(firstPlayer.getBoard(), secondPlayer.getBoard());
                    if (turnFlag == 1) {
                        Player holdPlayer = duelMenuController.getTurnPlayer();
                        duelMenuController.setTurnPlayer(duelMenuController.getNotTurnPlayer());
                        duelMenuController.setNotTurnPlayer(holdPlayer);
                        break;
                    }
                    System.out.println("it is " + duelMenuController.getTurnPlayer().getNickname() + "turn");

                    if (phase == Phases.DRAW_PHASE && duelMenuController.getTurnPlayer().getBoard().getDeck().getMainCards().size() > 0) {
                        duelMenuController.getTurnPlayer().getBoard().getCardsInHand().add(duelMenuController.getTurnPlayer().getBoard().getDeck().getMainCards().get(0));
                        duelMenuController.getTurnPlayer().getBoard().getDeck().getMainCards().remove(0);
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
                    showBoard(duelMenuController.getTurnPlayer().getBoard(), duelMenuController.getNotTurnPlayer().getBoard());
                    if (turnFlag == 0) {
                        Player holdPlayer = duelMenuController.getTurnPlayer();
                        duelMenuController.setTurnPlayer(duelMenuController.getNotTurnPlayer());
                        duelMenuController.setNotTurnPlayer(holdPlayer);
                        break;
                    }
                    System.out.println("it is " + duelMenuController.getTurnPlayer().getNickname() + "turn");

                    if (phase == Phases.DRAW_PHASE && duelMenuController.getTurnPlayer().getBoard().getDeck().getMainCards().size() > 0) {
                        duelMenuController.getTurnPlayer().getBoard().getCardsInHand().add(duelMenuController.getTurnPlayer().getBoard().getDeck().getMainCards().get(0));
                        duelMenuController.getTurnPlayer().getBoard().getDeck().getMainCards().remove(0);
                        setPhase(duelMenuController);

                    }
                    getOrder(duelMenuController);
                }
            }

        }
    }

    private void giveScores() {
        if (numberOfRounds == 3) {
            if (firstPlayer.getLifePoint() <= 0) {
                secondPlayer.setWonRounds(secondPlayer.getWonRounds() + 1);
                secondPlayer.setMaxLifePointDuringPlay(secondPlayer.getLifePoint());
                firstPlayer.setMaxLifePointDuringPlay(firstPlayer.getLifePoint());
                duelMenuView();
            }
            if (secondPlayer.getLifePoint() <= 0) {
                firstPlayer.setWonRounds(firstPlayer.getWonRounds() + 1);
                secondPlayer.setMaxLifePointDuringPlay(secondPlayer.getLifePoint());
                firstPlayer.setMaxLifePointDuringPlay(firstPlayer.getLifePoint());
                duelMenuView();
            }
            if (firstPlayer.getWonRounds() == 2) {
                firstPlayer.increaseMoney(3000 + 3L * firstPlayer.getMaxLifePointDuringPlay());
                firstPlayer.increaseScore(3000);
                secondPlayer.increaseMoney(300);
                new MainMenuView(firstPlayer).mainMenuView();
            }
            if (secondPlayer.getWonRounds() == 2) {
                secondPlayer.increaseMoney(3000 + 3L * secondPlayer.getMaxLifePointDuringPlay());
                secondPlayer.increaseScore(3000);
                firstPlayer.increaseMoney(300);
                new MainMenuView(firstPlayer).mainMenuView();
            }
        } else if (numberOfRounds == 1) {
            if (firstPlayer.getLifePoint() <= 0) {
                secondPlayer.increaseScore(1000);
                secondPlayer.increaseMoney(secondPlayer.getLifePoint() + 1000);
                firstPlayer.increaseMoney(100);
            }
            if (secondPlayer.getLifePoint() <= 0) {
                firstPlayer.increaseScore(1000);
                firstPlayer.increaseMoney(firstPlayer.getLifePoint() + 1000);
                secondPlayer.increaseMoney(100);
            }
            new MainMenuView(firstPlayer).mainMenuView();
        }
    }

    private boolean checkWinner() {
        return firstPlayer.getLifePoint() <= 0 || secondPlayer.getLifePoint() <= 0;
    }

    private void getOrder(DuelMenuController duelMenuController) {
        System.out.println("this is turn of" + duelMenuController.getTurnPlayer().getUsername());
        String command = Utils.getScanner().nextLine().trim();
        if (command.equals("next Phase")) {
            setPhase(duelMenuController);
        }
        if (command.equals("show graveyard"))
            showGraveyard(duelMenuController.getTurnPlayer().getBoard());
        if (command.equals("show selected"))
            showSelectedCard(duelMenuController.getTurnPlayer().getBoard());
        DuelMenuMessages result = duelMenuController.findCommand(command);
        System.out.print(result.getMessage());
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

    }
}
