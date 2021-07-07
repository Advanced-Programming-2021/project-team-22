package view;

import controller.AIClass;
import controller.Utils;
import controller.duelmenu.DuelMenuController;
import controller.duelmenu.DuelMenuMessages;
import controller.duelmenu.Phases;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Board;
import model.Player;
import model.cards.Card;
import model.cards.magiccard.MagicCard;
import model.cards.monstercard.MonsterCard;

import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class DuelMenuView {
    private static Rectangle[] ownMonsterRectangles = new Rectangle[6];
    private static Rectangle[] ownMagicRectangles = new Rectangle[6];
    private static Rectangle[] opponentMonsterRectangles = new Rectangle[6];
    private static Rectangle[] opponentMagicRectangles = new Rectangle[6];
    private static Rectangle[] ownCardsInHand = new Rectangle[5];
    private static Rectangle[] opponentCardsInHand = new Rectangle[5];
    private Button playOrStopMusic;
    private Button setPause;
    private ImageView imageView;
    Stage stage;
    private Player firstPlayer;
    private Player secondPlayer;
    private final int numberOfRounds;
    private Integer turnFlag;
    private final AIClass AIClass;
    private Pane root;
    private DuelMenuController duelMenuController;
    private javafx.scene.control.ScrollPane scrollPane;
    private boolean pause;

    {
        pause = false;
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
        System.out.println("--------------------------");
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
        showBoard(turnPlayer, notTurnPlayer);
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

    public DuelMenuMessages duelMenuView() {
        DuelMenuController duelMenuController = new DuelMenuController();
        this.duelMenuController = duelMenuController;

        DuelMenuMessages resultOfInitialGame = null;
        while (resultOfInitialGame == null || !resultOfInitialGame.equals(DuelMenuMessages.SHOW_TURN_PLAYER)) {
            resultOfInitialGame = duelMenuController.initialGame(firstPlayer, secondPlayer);
            System.out.print(resultOfInitialGame.getMessage());
        }


        URL url = getClass().getResource("/view/Board.fxml");
        try {
            root = FXMLLoader.load(url);
        } catch (Exception e) {
            System.out.println("cant load board!");
        }
        Scene scene = new Scene(root, 700, 600);
        setScene(scene);
        stage.setScene(scene);
        stage.show();

        if (firstPlayer.equals(duelMenuController.getTurnPlayer()))
            turnFlag = 1;

        while (true) {
            if (checkWinner()) {
                DuelMenuMessages result = giveScores();
                if (result.equals(DuelMenuMessages.ENTER_MAIN_MENU)) break;
                else if (result.equals(DuelMenuMessages.PLAY_ANOTHER_TURN))
                    return DuelMenuMessages.PLAY_ANOTHER_TURN;
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
                    // getOrder(duelMenuController);
//TODO get order by network
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
                    // getOrder(duelMenuController);
                    //TODO gets order by graphic page
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
                            //TODO here added grahpic
                            // opponentCardsInHand[ duelMenuController.getTurnPlayer().getBoard().getCardsInHand().size()].setFill();
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
                            //TODO here added grahpic
                            // ownCardsInHand[ duelMenuController.getTurnPlayer().getBoard().getCardsInHand().size()].setFill();
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
        if (shoWinnerPopup(firstPlayer, secondPlayer)) return true;
        if (shoWinnerPopup(secondPlayer, firstPlayer)) return true;
        return false;
    }

    private boolean shoWinnerPopup(Player firstPlayer, Player secondPlayer) {
        if (firstPlayer.getLifePoint() <= 0 || secondPlayer.getWonRounds() == 2) {
            AtomicInteger flag = new AtomicInteger();
            Label label = new Label(secondPlayer.getNickname() + "is winner of this round!");
            Popup popup = new Popup();
            popup.getContent().add(label);
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.seconds(1), e -> {
                        if (flag.get() == 0) {
                            flag.getAndIncrement();
                            popup.show(stage);
                        }
                        if (flag.get() == 1) {
                            popup.hide();
                        }
                    })
            );
            timeline.setCycleCount(2);
            timeline.play();
            return true;
        }
        return false;
    }

    private void getOrder(DuelMenuController duelMenuController) {
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
            //TODO send a message to server!!!
        }
    }

    public void showOwnGrave() {
        GridPane gridPane = new GridPane();
        ArrayList<Card> graveyard = duelMenuController.getTurnPlayer().getBoard().getGraveyard();
        for (int i = 0; i < graveyard.size(); i++) {
            //TODO add photo address Image img = new Image(getClass().getResource().toExternalForm());
            //rectangle.setFill(new ImagePattern(img));
        }
        scrollPane.setContent(gridPane);
    }


    public void showCheatText(Scene scene) {
        scene.lookup("#cheatcodeMenu").setVisible(true);
        TextField textField = (TextField) scene.lookup("#cheatcodeText");
        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                DuelMenuMessages result = duelMenuController.findCommand(textField.getText());
                scene.lookup("#cheatcodeMenu").setVisible(false);
            }
        };

        textField.setOnAction(event);
    }

    public void setScene(Scene scene) {
        scrollPane = (ScrollPane) scene.lookup("#graveYard");
        scrollPane.setVisible(false);
        scene.lookup("#graveYard").setVisible(false);
        scene.lookup("#settingsMenu").setVisible(false);
        scene.lookup("#cheatcodeMenu").setVisible(false);

        ownMonsterRectangles[1] = (Rectangle) scene.lookup("#ownMonster1");
        ownMonsterRectangles[2] = (Rectangle) scene.lookup("#ownMonster2");
        ownMonsterRectangles[3] = (Rectangle) scene.lookup("#ownMonster3");
        ownMonsterRectangles[4] = (Rectangle) scene.lookup("#ownMonster4");
        ownMonsterRectangles[5] = (Rectangle) scene.lookup("#ownMonster5");

        ownMagicRectangles[1] = (Rectangle) scene.lookup("#ownMagic1");
        ownMagicRectangles[2] = (Rectangle) scene.lookup("#ownMagic2");
        ownMagicRectangles[3] = (Rectangle) scene.lookup("#ownMagic3");
        ownMagicRectangles[4] = (Rectangle) scene.lookup("#ownMagic4");
        ownMagicRectangles[5] = (Rectangle) scene.lookup("#ownMagic5");

        opponentMonsterRectangles[1] = (Rectangle) scene.lookup("#opponentMonster1");
        opponentMonsterRectangles[2] = (Rectangle) scene.lookup("#opponentMonster2");
        opponentMonsterRectangles[3] = (Rectangle) scene.lookup("#opponentMonster3");
        opponentMonsterRectangles[4] = (Rectangle) scene.lookup("#opponentMonster4");
        opponentMonsterRectangles[5] = (Rectangle) scene.lookup("#opponentMonster5");

        opponentMagicRectangles[1] = (Rectangle) scene.lookup("#opponentMagic1");
        opponentMagicRectangles[2] = (Rectangle) scene.lookup("#opponentMagic2");
        opponentMagicRectangles[3] = (Rectangle) scene.lookup("#opponentMagic3");
        opponentMagicRectangles[4] = (Rectangle) scene.lookup("#opponentMagic4");
        opponentMagicRectangles[5] = (Rectangle) scene.lookup("#opponentMagic5");
        imageView = (ImageView) scene.lookup("#showSelectedCard");

        ownCardsInHand[1] = (Rectangle) scene.lookup("#ownHandCard1");
        ownCardsInHand[2] = (Rectangle) scene.lookup("#ownHandCard2");
        ownCardsInHand[3] = (Rectangle) scene.lookup("#ownHandCard3");
        ownCardsInHand[4] = (Rectangle) scene.lookup("#ownHandCard4");

        opponentCardsInHand[1] = (Rectangle) scene.lookup("#opponentHandCard1");
        opponentCardsInHand[2] = (Rectangle) scene.lookup("#opponentHandCard2");
        opponentCardsInHand[3] = (Rectangle) scene.lookup("#opponentHandCard3");
        opponentCardsInHand[4] = (Rectangle) scene.lookup("#opponentHandCard4");


        KeyCombination keyCombination = KeyCombination.keyCombination("CTRL+SHIFT+U");
        Runnable runnable = () -> showCheatText(scene);
        scene.getAccelerators().put(keyCombination, runnable);


        scene.lookup("#settingsMenuButton").addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        scene.lookup("#settingsMenu").setVisible(true);
                    }
                });
        scene.lookup("#closeSettings").addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        scene.lookup("#settingsMenu").setVisible(false);
                    }
                });
        playOrStopMusic = (Button) scene.lookup("#musicButton");
        setPause = (Button) scene.lookup("#pauseGame");
        playOrStopMusic.setOnMouseClicked(this::setMusic);
        setPause.setOnMouseClicked(this::pauseGame);
        scene.lookup("#graveYard").addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {//TODO add start to show button
                    @Override
                    public void handle(MouseEvent e) {
                        scene.lookup("#graveYard").setVisible(false);
                    }
                });


        scene.lookup("#ownMonster1").addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        System.out.println("gfd");
                        if (!pause) {
                            DuelMenuMessages result = duelMenuController.findCommand("select --monster 1");
                        }
                        scene.lookup("#changeMonster1").setVisible(true);


                    }
                });
        scene.lookup("#changeMonster1").addEventHandler(MouseEvent.MOUSE_EXITED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        System.out.println("ppp");
                        scene.lookup("#changeMonster1").setVisible(false);
                    }
                });
        scene.lookup("#changeMonster2").addEventHandler(MouseEvent.MOUSE_EXITED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        System.out.println("ppp");
                        scene.lookup("#changeMonster2").setVisible(false);
                    }
                });
        scene.lookup("#ownMonster2").addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        System.out.println("gfd");
                        if (!pause) {
                            DuelMenuMessages result = duelMenuController.findCommand("select --monster 2");
                        }
                        scene.lookup("#changeMonster2").setVisible(true);


                    }
                });
        scene.lookup("#changeMonster3").addEventHandler(MouseEvent.MOUSE_EXITED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        System.out.println("ppp");
                        scene.lookup("#changeMonster3").setVisible(false);
                    }
                });
        scene.lookup("#ownMonster3").addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        System.out.println("gfd");
                        if (!pause) {
                            DuelMenuMessages result = duelMenuController.findCommand("select --monster 3");
                        }
                        scene.lookup("#changeMonster3").setVisible(true);


                    }
                });
        scene.lookup("#changeMonster4").addEventHandler(MouseEvent.MOUSE_EXITED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        System.out.println("ppp");

                        scene.lookup("#changeMonster4").setVisible(false);
                    }
                });
        scene.lookup("#ownMonster4").addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        System.out.println("gfd");
                        if (!pause) {
                            DuelMenuMessages result = duelMenuController.findCommand("select --monster 4");
                        }
                        scene.lookup("#changeMonster4").setVisible(true);


                    }
                });
        scene.lookup("#changeMonster5").addEventHandler(MouseEvent.MOUSE_EXITED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        System.out.println("ppp");
                        scene.lookup("#changeMonster5").setVisible(false);
                    }
                });
        scene.lookup("#ownMonster5").addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        System.out.println("gfd");
                        if (!pause) {
                            DuelMenuMessages result = duelMenuController.findCommand("select --monster 5");
                        }
                        scene.lookup("#changeMonster5").setVisible(true);


                    }
                });


        scene.lookup("#ownMagic1").addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        System.out.println("gfd");
                        if (!pause) {
                            DuelMenuMessages result = duelMenuController.findCommand("select --spell 1");
                        }
                        scene.lookup("#activeMagic1").setVisible(true);


                    }
                });
        scene.lookup("#activeMagic1").addEventHandler(MouseEvent.MOUSE_EXITED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        System.out.println("ppp");

                        scene.lookup("#activeMagic1").setVisible(false);
                    }
                });
        scene.lookup("#ownMagic2").addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        System.out.println("gfd");
                        if (!pause) {
                            DuelMenuMessages result = duelMenuController.findCommand("select --spell 2");
                        }
                        scene.lookup("#activeMagic2").setVisible(true);


                    }
                });
        scene.lookup("#activeMagic2").addEventHandler(MouseEvent.MOUSE_EXITED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        System.out.println("ppp");

                        scene.lookup("#activeMagic2").setVisible(false);
                    }
                });
        scene.lookup("#ownMagic3").addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        System.out.println("gfd");
                        if (!pause) {
                            DuelMenuMessages result = duelMenuController.findCommand("select --spell 3");
                        }
                        scene.lookup("#activeMagic3").setVisible(true);


                    }
                });
        scene.lookup("#activeMagic3").addEventHandler(MouseEvent.MOUSE_EXITED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        System.out.println("ppp");
                        scene.lookup("#activeMagic3").setVisible(false);
                    }
                });
        scene.lookup("#ownMagic4").addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        System.out.println("gfd");
                        if (!pause) {
                            DuelMenuMessages result = duelMenuController.findCommand("select --spell 4");
                        }
                        scene.lookup("#activeMagic4").setVisible(true);


                    }
                });
        scene.lookup("#activeMagic4").addEventHandler(MouseEvent.MOUSE_EXITED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        System.out.println("ppp");
                        scene.lookup("#activeMagic4").setVisible(false);
                    }
                });
        scene.lookup("#ownMagic5").addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        System.out.println("gfd");
                        if (!pause) {
                            DuelMenuMessages result = duelMenuController.findCommand("select --spell 5");
                        }
                        //TODO show selected card
                        scene.lookup("#activeMagic5").setVisible(true);


                    }
                });
        scene.lookup("#activeMagic5").addEventHandler(MouseEvent.MOUSE_EXITED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        System.out.println("ppp");
                        scene.lookup("#activeMagic5").setVisible(false);
                    }
                });


        scene.lookup("#opponentMagic5").addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        System.out.println("gfd");
                        if (!pause) {
                            DuelMenuMessages result = duelMenuController.findCommand("select --spell --opponent 5");
                        }
                        //TODO show selected card


                    }
                });
        scene.lookup("#opponentMagic4").addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        System.out.println("gfd");
                        if (!pause) {
                            DuelMenuMessages result = duelMenuController.findCommand("select --spell --opponent 4");
                        }
                        //TODO show selected card


                    }
                });
        scene.lookup("#opponentMagic3").addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        System.out.println("gfd");
                        if (!pause) {
                            DuelMenuMessages result = duelMenuController.findCommand("select --spell --opponent 3");
                        }
                        //TODO show selected card


                    }
                });
        scene.lookup("#opponentMagic2").addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        System.out.println("gfd");
                        if (!pause) {
                            DuelMenuMessages result = duelMenuController.findCommand("select --spell --opponent 2");
                        }
                        //TODO show selected card


                    }
                });
        scene.lookup("#opponentMagic1").addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        System.out.println("gfd");
                        if (!pause) {
                            DuelMenuMessages result = duelMenuController.findCommand("select --spell --opponent 1");
                        }
                        //TODO show selected card


                    }
                });

        //-------------------------------------------------handle attack button


        scene.lookup("#opponentMonster1").addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        System.out.println("gfd");
                        if (!pause) {
                            DuelMenuMessages result = duelMenuController.findCommand("select --monster --opponent 1");
                        }
                        scene.lookup("#attackToOpponentMonster1").setVisible(true);


                    }
                });
        scene.lookup("#attackToOpponentMonster1").addEventHandler(MouseEvent.MOUSE_EXITED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        System.out.println("ppp");
                        scene.lookup("#attackToOpponentMonster1").setVisible(false);
                    }
                });
        scene.lookup("#opponentMonster2").addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        System.out.println("gfd");
                        if (!pause) {
                            DuelMenuMessages result = duelMenuController.findCommand("select --monster --opponent 2");
                        }
                        scene.lookup("#attackToOpponentMonster2").setVisible(true);


                    }
                });
        scene.lookup("#attackToOpponentMonster2").addEventHandler(MouseEvent.MOUSE_EXITED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        System.out.println("ppp");
                        scene.lookup("#attackToOpponentMonster2").setVisible(false);
                    }
                });
        scene.lookup("#opponentMonster3").addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        System.out.println("gfd");
                        if (!pause) {
                            DuelMenuMessages result = duelMenuController.findCommand("select --monster --opponent 3");
                        }
                        scene.lookup("#attackToOpponentMonster3").setVisible(true);


                    }
                });
        scene.lookup("#attackToOpponentMonster3").addEventHandler(MouseEvent.MOUSE_EXITED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        System.out.println("ppp");
                        scene.lookup("#attackToOpponentMonster3").setVisible(false);
                    }
                });
        scene.lookup("#opponentMonster4").addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        System.out.println("gfd");
                        if (!pause) {
                            DuelMenuMessages result = duelMenuController.findCommand("select --monster --opponent 4");
                        }
                        scene.lookup("#attackToOpponentMonster4").setVisible(true);


                    }
                });
        scene.lookup("#attackToOpponentMonster4").addEventHandler(MouseEvent.MOUSE_EXITED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        System.out.println("ppp");
                        scene.lookup("#attackToOpponentMonster4").setVisible(false);
                    }
                });
        scene.lookup("#opponentMonster5").addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        System.out.println("gfd");
                        if (!pause) {
                            DuelMenuMessages result = duelMenuController.findCommand("select --monster --opponent 5");
                        }
                        scene.lookup("#attackToOpponentMonster5").setVisible(true);


                    }
                });
        scene.lookup("#attackToOpponentMonster5").addEventHandler(MouseEvent.MOUSE_EXITED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        System.out.println("ppp");
                        scene.lookup("#attackToOpponentMonster5").setVisible(false);
                    }
                });

        scene.lookup("#attackToOpponentMonster1").addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        System.out.println("gfd");
                        if (!pause) {
                            DuelMenuMessages result = duelMenuController.findCommand("attack 1");
                        }
                    }
                });
        scene.lookup("#attackToOpponentMonster2").addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        System.out.println("gfd");
                        if (!pause) {
                            DuelMenuMessages result = duelMenuController.findCommand("attack 2");
                        }
                    }
                });
        scene.lookup("#attackToOpponentMonster3").addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        System.out.println("gfd");
                        if (!pause) {
                            DuelMenuMessages result = duelMenuController.findCommand("attack 3");
                        }
                    }
                });
        scene.lookup("#attackToOpponentMonster4").addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        System.out.println("gfd");
                        if (!pause) {
                            DuelMenuMessages result = duelMenuController.findCommand("attack 4");
                        }
                    }
                });
        scene.lookup("#attackToOpponentMonster5").addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        System.out.println("gfd");
                        if (!pause) {
                            DuelMenuMessages result = duelMenuController.findCommand("attack 5");
                        }
                    }
                });


        scene.lookup("#activeMagic1").addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        System.out.println("ppp");
                        if (!pause) {//TODO ask about order
                            DuelMenuMessages result = duelMenuController.findCommand("");
                        }
                    }
                });
        scene.lookup("#activeMagic2").addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        System.out.println("ppp");
                        if (!pause) {//TODO ask about order
                            DuelMenuMessages result = duelMenuController.findCommand("");
                        }
                    }
                });
        scene.lookup("#activeMagic3").addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        System.out.println("ppp");
                        if (!pause) {//TODO ask about order
                            DuelMenuMessages result = duelMenuController.findCommand("");
                        }
                    }
                });
        scene.lookup("#activeMagic4").addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        System.out.println("ppp");
                        if (!pause) {//TODO ask about order
                            DuelMenuMessages result = duelMenuController.findCommand("");
                        }
                    }
                });
        scene.lookup("#activeMagic5").addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        System.out.println("ppp");
                        if (!pause) {//TODO ask about order
                            DuelMenuMessages result = duelMenuController.findCommand("");
                        }
                    }
                });


        scene.lookup("#nextPhaseButton").addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        System.out.println("ppp");
                        if (!pause) {
                            setPhase(duelMenuController);
                        }
                    }
                });


    }

    private void pauseGame(MouseEvent mouseEvent) {
        if (pause)
            pause = false;
        if (!pause)
            pause = true;
    }

    private void setMusic(MouseEvent mouseEvent) {
    }
}
