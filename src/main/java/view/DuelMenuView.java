package view;

import controller.AIClass;
import controller.Utils;
import controller.deckmenu.DeckMenuController;
import controller.duelmenu.DuelMenuController;
import controller.duelmenu.DuelMenuMessages;
import controller.duelmenu.Phases;
import controller.shopmenu.ShopMenuController;
import model.Board;
import model.Deck;
import model.Player;
import model.cards.Card;
import model.cards.magiccard.MagicCard;
import model.cards.monstercard.MonsterCard;

import java.util.Collections;

public class DuelMenuView {
    private Player firstPlayer;
    private Player secondPlayer;
    private int numberOfRounds;
    private Integer turnFlag;
    private AIClass AIClass;

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
        Player AI = new Player("", "", "");

        firstPlayer.createBoard();
        AI.createBoard();

        Collections.shuffle(firstPlayer.getActivatedDeck().getMainCards());

        addDeckToAI(AI);
        firstPlayer.getBoard().setDeck(new Deck(firstPlayer.getActivatedDeck()));
        AI.getBoard().setDeck(new Deck(AI.getActivatedDeck()));

        this.firstPlayer = firstPlayer;
        this.secondPlayer = AI;
        this.numberOfRounds = numberOfRounds;
    }

    private static void addDeckToAI(Player AI) {
        ShopMenuController shopMenuController = new ShopMenuController(AI);
        shopMenuController.findCommand("increase --M 1000000");
        int numberOfCards = 0;
        for (String cardName : Card.getAllCards().keySet()) {
            shopMenuController.findCommand("shop buy " + cardName);
            ++numberOfCards;
            if (numberOfCards == 50) break;
        }

        DeckMenuController deckMenuController = new DeckMenuController(AI);
        deckMenuController.findCommand("deck create :)");

        for (String cardName : Card.getAllCards().keySet()) {
            deckMenuController.findCommand("deck add-card --card " + cardName + " --deck :)");
            ++numberOfCards;
            if (numberOfCards == 50) break;
        }

        deckMenuController.findCommand("deck set-activate :)");
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

    private static void showBoard(Board playerBoard, Board opponentBoard) {
        showCardsInHand(opponentBoard);
        System.out.println(opponentBoard.getDeck().getMainCards().size());
        showOpponentMagicsZone(opponentBoard);
        showOpponentMonstersZone(opponentBoard);
        System.out.print(opponentBoard.getGraveyard().size() + "                ");
        if (opponentBoard.getFieldZone() == null) System.out.println("E");
        else System.out.println("O");
        System.out.println("--------------------------");
        showCardsInHand(playerBoard);
        System.out.println(playerBoard.getDeck().getMainCards().size());
        showMagicsZone(playerBoard);
        showMonstersZone(playerBoard);
        System.out.println(playerBoard.getGraveyard().size() + "                ");
        if (opponentBoard.getFieldZone() == null) System.out.println("E");
        else System.out.println("O");
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
            System.out.println("you have not selected card");
            return false;
        }
        if (!board.isMyCardSelected() && !board.getSelectedCard().getCardFaceUp()) {
            System.out.println("you can't see this card!");
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
            if (checkWinner()) giveScores();

            if (turnFlag == 0) {
                while (true) {
                    showBoard(firstPlayer.getBoard(), secondPlayer.getBoard());
                    if (turnFlag == 1) {
                        DuelMenuController.preparePlayerForNextTurn(duelMenuController.getTurnPlayer());
                        DuelMenuController.preparePlayerForNextTurn(duelMenuController.getNotTurnPlayer());
                        Player holdPlayer = duelMenuController.getTurnPlayer();
                        duelMenuController.setTurnPlayer(duelMenuController.getNotTurnPlayer());
                        duelMenuController.setNotTurnPlayer(holdPlayer);
                        break;
                    }
                    System.out.println("it is " + duelMenuController.getTurnPlayer().getNickname() + "turn");
                    if (duelMenuController.getPhase() == Phases.DRAW_PHASE && duelMenuController.getTurnPlayer().getBoard().getDeck().getMainCards().size() > 0) {
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
                        DuelMenuController.preparePlayerForNextTurn(duelMenuController.getTurnPlayer());
                        DuelMenuController.preparePlayerForNextTurn(duelMenuController.getNotTurnPlayer());
                        Player holdPlayer = duelMenuController.getTurnPlayer();
                        duelMenuController.setTurnPlayer(duelMenuController.getNotTurnPlayer());
                        duelMenuController.setNotTurnPlayer(holdPlayer);
                        break;
                    }
                    System.out.println("it is " + duelMenuController.getTurnPlayer().getNickname() + "turn");

                    if (duelMenuController.getPhase() == Phases.DRAW_PHASE && duelMenuController.getTurnPlayer().getBoard().getDeck().getMainCards().size() > 0) {
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
            if (checkWinner()) giveScores();

            if (turnFlag == 0) {
                while (true) {
                    showBoard(firstPlayer.getBoard(), secondPlayer.getBoard());
                    if (turnFlag == 1) {
                        Player holdPlayer = duelMenuController.getTurnPlayer();
                        duelMenuController.setTurnPlayer(duelMenuController.getNotTurnPlayer());
                        duelMenuController.setNotTurnPlayer(holdPlayer);
                        break;
                    }
                    System.out.println("it is AI turn");
                    if (duelMenuController.getPhase() == Phases.DRAW_PHASE && duelMenuController.getTurnPlayer().getBoard().getDeck().getMainCards().size() > 0) {
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
                    System.out.println("it is " + duelMenuController.getTurnPlayer().getNickname() + " turn");
                    if (duelMenuController.getPhase() == Phases.DRAW_PHASE && duelMenuController.getTurnPlayer().getBoard().getDeck().getMainCards().size() > 0) {
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
                System.out.println(secondPlayer.getUsername() + " won\nnew game started");
                duelMenuView();
            }
            if (secondPlayer.getLifePoint() <= 0) {
                firstPlayer.setWonRounds(firstPlayer.getWonRounds() + 1);
                secondPlayer.setMaxLifePointDuringPlay(secondPlayer.getLifePoint());
                firstPlayer.setMaxLifePointDuringPlay(firstPlayer.getLifePoint());
                System.out.println(firstPlayer.getUsername() + " won\nnew game started");
                duelMenuView();
            }
            if (firstPlayer.getWonRounds() == 2) {
                firstPlayer.increaseMoney(3000 + 3L * firstPlayer.getMaxLifePointDuringPlay());
                firstPlayer.increaseScore(3000);
                secondPlayer.increaseMoney(300);
                System.out.println(firstPlayer.getUsername() + " won");
                new MainMenuView(firstPlayer).mainMenuView();
            }
            if (secondPlayer.getWonRounds() == 2) {
                secondPlayer.increaseMoney(3000 + 3L * secondPlayer.getMaxLifePointDuringPlay());
                secondPlayer.increaseScore(3000);
                firstPlayer.increaseMoney(300);
                System.out.println(secondPlayer.getUsername() + " won");
                new MainMenuView(firstPlayer).mainMenuView();
            }
        } else if (numberOfRounds == 1) {
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
            new MainMenuView(firstPlayer).mainMenuView();
        }
    }

    private boolean checkWinner() {
        return firstPlayer.getLifePoint() <= 0 || secondPlayer.getLifePoint() <= 0 ||
                firstPlayer.getWonRounds() == 2 || secondPlayer.getWonRounds() == 2;
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
