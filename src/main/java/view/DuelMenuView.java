package view;

import controller.AIClass;
import controller.Utils;
import controller.duelmenu.DuelMenuController;
import controller.duelmenu.DuelMenuMessages;
import controller.duelmenu.Phases;
import controller.mainmenu.MainMenuController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Board;
import model.Player;
import model.cards.Card;
import model.cards.magiccard.MagicCard;
import model.cards.monstercard.MonsterCard;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import static javafx.scene.layout.BackgroundPosition.CENTER;
import static javafx.scene.layout.BackgroundRepeat.NO_REPEAT;
import static javafx.scene.layout.BackgroundRepeat.REPEAT;
import static javafx.scene.layout.BackgroundSize.*;
import static javafx.scene.paint.Color.DODGERBLUE;

public class DuelMenuView extends Application {
    private static Scene scene;
    private static Rectangle[] ownMonsterRectangles = new Rectangle[6];
    private static Rectangle[] ownMagicRectangles = new Rectangle[6];
    private static Rectangle[] opponentMonsterRectangles = new Rectangle[6];
    private static Rectangle[] opponentMagicRectangles = new Rectangle[6];
    private static Rectangle[] ownCardsInHand = new Rectangle[5];
    private static Rectangle[] opponentCardsInHand = new Rectangle[5];
    private static Rectangle ownFieldzone;
    private static Rectangle opponentFieldzone;
    private Rectangle drawPhase;
    private Rectangle standbyPhase;
    private Rectangle mainPhase1;
    private Rectangle battlePhase;
    private Rectangle mainPhase2;
    private Rectangle endPhase;
    private Button playOrStopMusic;
    private Button setPause;
    private Rectangle imageView;
    Stage stage;
    private boolean isPlayingWithAI;
    private boolean isAITurn;
    private Player firstPlayer;
    private Player secondPlayer;
    private int numberOfRounds;
    private Integer turnFlag;
    private final AIClass AIClass;
    private Pane root;
    private static DuelMenuController duelMenuController;
    private static javafx.scene.control.ScrollPane scrollPane;
    private boolean pause;
    private MediaPlayer mediaPlayer;
    private Rectangle ownAvatar;
    private Rectangle opponentAvatar;
    Label ownUsername;
    Label ownNickname;
    Label opponentName;
    Label opponentNickname;
    private Timeline timeline;

    {
        isAITurn = false;
        isPlayingWithAI = false;
        pause = false;
        turnFlag = 0;
        AIClass = new AIClass();
    }

    public DuelMenuView() {
    }

    public int getNumberOfRounds() {
        return numberOfRounds;
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
        this.isPlayingWithAI = true;
    }

    public static void setDuelMenuController(DuelMenuController duelMenuController) {
        DuelMenuView.duelMenuController = duelMenuController;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        if (isPlayingWithAI) {
            DuelMenuController duelMenuController = new DuelMenuController();
            DuelMenuView.duelMenuController = duelMenuController;
            DuelMenuView.duelMenuController.initialGameWithAI(secondPlayer, firstPlayer);
        } else {//TODO handle sang kagaz gechi!

        }
        duelMenuController.setPhase(Phases.DRAW_PHASE);
        URL url = getClass().getResource("/view/fxml/duelmenu/Board.fxml");
        try {
            root = FXMLLoader.load(url);
        } catch (Exception e) {
            System.out.println("cant load board!");
        }
        scene = new Scene(root, 700, 600);
        setScene(scene);
        primaryStage.setScene(scene);
        primaryStage.show();

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
        if (board == null)
            return;
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

    public static Rectangle getOwnFieldzone() {
        return ownFieldzone;
    }

    public Player getFirstPlayer() {
        return firstPlayer;
    }

    public Player getSecondPlayer() {
        return secondPlayer;
    }

    public static Rectangle getOpponentFieldzone() {
        return opponentFieldzone;
    }

    // public ImageView getImageView() {

    //   return imageView;
    //}

    public static Rectangle[] getOpponentCardsInHand() {
        return opponentCardsInHand;
    }

    public static Rectangle[] getOpponentMagicRectangles() {
        return opponentMagicRectangles;
    }

    public static Rectangle[] getOpponentMonsterRectangles() {
        return opponentMonsterRectangles;
    }

    public static Rectangle[] getOwnCardsInHand() {
        return ownCardsInHand;
    }

    public static Rectangle[] getOwnMagicRectangles() {
        return ownMagicRectangles;
    }

    public static Rectangle[] getOwnMonsterRectangles() {
        return ownMonsterRectangles;
    }

    public static DuelMenuController getDuelMenuController() {
        return duelMenuController;
    }

    public static ScrollPane getScrollPane() {
        return scrollPane;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public static void setOpponentCardsInHand(Rectangle[] opponentCardsInHand) {
        DuelMenuView.opponentCardsInHand = opponentCardsInHand;
    }

    public static void setOpponentMagicRectangles(Rectangle[] opponentMagicRectangles) {
        DuelMenuView.opponentMagicRectangles = opponentMagicRectangles;
    }

    public static void setOpponentMonsterRectangles(Rectangle[] opponentMonsterRectangles) {
        DuelMenuView.opponentMonsterRectangles = opponentMonsterRectangles;
    }

    public static void setOwnCardsInHand(Rectangle[] ownCardsInHand) {
        DuelMenuView.ownCardsInHand = ownCardsInHand;
    }

    public static void setOwnMagicRectangles(Rectangle[] ownMagicRectangles) {
        DuelMenuView.ownMagicRectangles = ownMagicRectangles;
    }

    public static void setOwnMonsterRectangles(Rectangle[] ownMonsterRectangles) {
        DuelMenuView.ownMonsterRectangles = ownMonsterRectangles;
    }


    public static void upToDateHand() {
        for (int i = 0; i < 4; i++) {
            if (i < duelMenuController.getTurnPlayer().getBoard().getCardsInHand().size() && duelMenuController.getTurnPlayer().getBoard().getCardsInHand().get(i) != null) {
                Image img = new Image(DuelMenuView.class.getResource(duelMenuController.getTurnPlayer().getBoard().getCardsInHand().get(i).getFrontImageAddress()).toExternalForm());
                ownCardsInHand[i + 1].setFill(new ImagePattern(img));
            } else {
                ownCardsInHand[i + 1].setFill(DODGERBLUE);
            }

        }
    }

    public void updateAIHand() {
        for (int i = 0; i < 4; i++) {
            if (i < secondPlayer.getBoard().getCardsInHand().size() && secondPlayer.getBoard().getCardsInHand().get(i) != null) {
                Image img = new Image(getClass().getResource(Card.getBackImageAddress()).toExternalForm());
                opponentCardsInHand[i + 1].setFill(new ImagePattern(img));
            } else {
                opponentCardsInHand[i + 1].setFill(DODGERBLUE);
            }

        }
    }


    public void changeRectanglesInPlayWithAI() {
        Rectangle[] holdRectangle = DuelMenuView.getOpponentMonsterRectangles();
        DuelMenuView.setOpponentMonsterRectangles(DuelMenuView.getOwnMonsterRectangles());
        DuelMenuView.setOwnMonsterRectangles(holdRectangle);
        holdRectangle = DuelMenuView.getOpponentMagicRectangles();
        DuelMenuView.setOpponentMagicRectangles(DuelMenuView.getOwnMagicRectangles());
        DuelMenuView.setOwnMagicRectangles(holdRectangle);
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
            Timeline timeline1 = new Timeline(
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
            timeline1.setCycleCount(2);
            timeline1.play();
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


    public void handleAI(DuelMenuController duelMenuController) {
        changeRectanglesInPlayWithAI();
        if (checkWinner()) {
            DuelMenuMessages result = giveScores();
            if (result.equals(DuelMenuMessages.ENTER_MAIN_MENU)) {
                MainMenuController.setLoggedInPlayer(firstPlayer);
                timeline.pause();
                timeline = null;
                try {
                    new MainMenuView().start(Utils.getStage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        DropShadow shadow = new DropShadow();
        ArrayList<Card> turnPlayerMainCards = duelMenuController.getTurnPlayer().getBoard().getDeck().getMainCards();
        if (duelMenuController.getPhase().equals(Phases.DRAW_PHASE)) {
            if (turnPlayerMainCards.size() > 0) {
                MagicCard timeSeal = duelMenuController.getNotTurnPlayer().getBoard().getFaceUpMagicCardFromMagicsZoneByName("Time Seal");
                if (duelMenuController.getTurnPlayer().getBoard().getCardsInHand().size() < 4 && (timeSeal == null || !timeSeal.isSetInThisTurn())) {
                    int indexOfLastMainCard = turnPlayerMainCards.size() - 1;
                    duelMenuController.getTurnPlayer().getBoard().getCardsInHand().add(turnPlayerMainCards.get(indexOfLastMainCard));
                    duelMenuController.getTurnPlayer().getBoard().getDeck().getMainCards().remove(indexOfLastMainCard);
                    int indexOfAddedCard = duelMenuController.getTurnPlayer().getBoard().getCardsInHand().size() - 1;
                    Image img = new Image(getClass().getResource(duelMenuController.getTurnPlayer().getBoard().getCardsInHand().get(indexOfAddedCard).getFrontImageAddress()).toExternalForm());
                    getOpponentCardsInHand()[indexOfAddedCard + 1].setFill(new ImagePattern(img));
                }
            }
            drawPhase.setEffect(null);
            standbyPhase.setEffect(shadow);
            String command = controller.AIClass.getOrder(secondPlayer.getBoard(), firstPlayer.getBoard(), secondPlayer, firstPlayer, duelMenuController.getPhase());
            DuelMenuMessages result = duelMenuController.findCommand(command);
            duelMenuController.setPhase(Phases.STANDBY_PHASE);
            updateAIHand();
        }
        if (duelMenuController.getPhase().equals(Phases.STANDBY_PHASE)) {
            standbyPhase.setEffect(null);
            mainPhase1.setEffect(shadow);
            String command = controller.AIClass.getOrder(secondPlayer.getBoard(), firstPlayer.getBoard(), secondPlayer, firstPlayer, duelMenuController.getPhase());
            DuelMenuMessages result = duelMenuController.findCommand(command);
            duelMenuController.setPhase(Phases.MAIN_PHASE_1);
            updateAIHand();

        }
        if (duelMenuController.getPhase().equals(Phases.MAIN_PHASE_1)) {
            mainPhase1.setEffect(null);
            battlePhase.setEffect(shadow);
            String command = controller.AIClass.getOrder(secondPlayer.getBoard(), firstPlayer.getBoard(), secondPlayer, firstPlayer, duelMenuController.getPhase());
            DuelMenuMessages result = duelMenuController.findCommand(command);
            duelMenuController.setPhase(Phases.BATTLE_PHASE);
            updateAIHand();

        }
        if (duelMenuController.getPhase().equals(Phases.BATTLE_PHASE)) {
            battlePhase.setEffect(null);
            mainPhase2.setEffect(shadow);
            String command = controller.AIClass.getOrder(secondPlayer.getBoard(), firstPlayer.getBoard(), secondPlayer, firstPlayer, duelMenuController.getPhase());
            DuelMenuMessages result = duelMenuController.findCommand(command);
            duelMenuController.setPhase(Phases.MAIN_PHASE_2);
            updateAIHand();

        }
        if (duelMenuController.getPhase().equals(Phases.MAIN_PHASE_2)) {
            mainPhase2.setEffect(null);
            endPhase.setEffect(shadow);
            String command = controller.AIClass.getOrder(secondPlayer.getBoard(), firstPlayer.getBoard(), secondPlayer, firstPlayer, duelMenuController.getPhase());
            DuelMenuMessages result = duelMenuController.findCommand(command);
            duelMenuController.setPhase(Phases.END_PHASE);
            updateAIHand();

        }
        if (duelMenuController.getPhase().equals(Phases.END_PHASE)) {
            showBoard(secondPlayer, firstPlayer);
            endPhase.setEffect(null);
            drawPhase.setEffect(shadow);
            duelMenuController.setPhase(Phases.DRAW_PHASE);
            DuelMenuController.preparePlayerForNextTurn(firstPlayer);
            DuelMenuController.preparePlayerForNextTurn(secondPlayer);
            Player holdPlayer = duelMenuController.getTurnPlayer();
            duelMenuController.setTurnPlayer(duelMenuController.getNotTurnPlayer());
            duelMenuController.setNotTurnPlayer(holdPlayer);
            changeRectanglesInPlayWithAI();
            updateAIHand();

        }

    }


    public void setPhase(DuelMenuController duelMenuController) {
        if (checkWinner()) {
            DuelMenuMessages result = giveScores();
            if (result.equals(DuelMenuMessages.ENTER_MAIN_MENU)) {
                timeline.pause();
                timeline = null;
                MainMenuController.setLoggedInPlayer(firstPlayer);
                try {
                    new MainMenuView().start(Utils.getStage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        DropShadow shadow = new DropShadow();
        ArrayList<Card> turnPlayerMainCards = duelMenuController.getTurnPlayer().getBoard().getDeck().getMainCards();
        if (duelMenuController.getPhase().equals(Phases.DRAW_PHASE)) {
            if (turnPlayerMainCards.size() > 0) {
                MagicCard timeSeal = duelMenuController.getNotTurnPlayer().getBoard().getFaceUpMagicCardFromMagicsZoneByName("Time Seal");
                if (duelMenuController.getTurnPlayer().getBoard().getCardsInHand().size() < 4 && (timeSeal == null || !timeSeal.isSetInThisTurn())) {
                    int indexOfLastMainCard = turnPlayerMainCards.size() - 1;
                    duelMenuController.getTurnPlayer().getBoard().getCardsInHand().add(turnPlayerMainCards.get(indexOfLastMainCard));
                    duelMenuController.getTurnPlayer().getBoard().getDeck().getMainCards().remove(indexOfLastMainCard);
                    int indexOfAddedCard = duelMenuController.getTurnPlayer().getBoard().getCardsInHand().size() - 1;
                    Image img = new Image(getClass().getResource(duelMenuController.getTurnPlayer().getBoard().getCardsInHand().get(indexOfAddedCard).getFrontImageAddress()).toExternalForm());
                    getOwnCardsInHand()[indexOfAddedCard + 1].setFill(new ImagePattern(img));
                }
            }
            drawPhase.setEffect(null);
            standbyPhase.setEffect(shadow);
            duelMenuController.setPhase(Phases.STANDBY_PHASE);
        } else if (duelMenuController.getPhase().equals(Phases.STANDBY_PHASE)) {
            standbyPhase.setEffect(null);
            mainPhase1.setEffect(shadow);
            duelMenuController.setPhase(Phases.MAIN_PHASE_1);
        } else if (duelMenuController.getPhase().equals(Phases.MAIN_PHASE_1)) {
            mainPhase1.setEffect(null);
            battlePhase.setEffect(shadow);
            duelMenuController.setPhase(Phases.BATTLE_PHASE);
        } else if (duelMenuController.getPhase().equals(Phases.BATTLE_PHASE)) {
            battlePhase.setEffect(null);
            mainPhase2.setEffect(shadow);
            duelMenuController.setPhase(Phases.MAIN_PHASE_2);
        } else if (duelMenuController.getPhase().equals(Phases.MAIN_PHASE_2)) {
            mainPhase2.setEffect(null);
            endPhase.setEffect(shadow);
            duelMenuController.setPhase(Phases.END_PHASE);
        } else if (duelMenuController.getPhase().equals(Phases.END_PHASE)) {
            endPhase.setEffect(null);
            drawPhase.setEffect(shadow);
            duelMenuController.setPhase(Phases.DRAW_PHASE);
            DuelMenuController.preparePlayerForNextTurn(firstPlayer);
            DuelMenuController.preparePlayerForNextTurn(secondPlayer);
            Player holdPlayer = duelMenuController.getTurnPlayer();
            duelMenuController.setTurnPlayer(duelMenuController.getNotTurnPlayer());
            duelMenuController.setNotTurnPlayer(holdPlayer);
            if (isPlayingWithAI) {
                handleAI(duelMenuController);
            } else {
                changeRectangles();
            }
        }
    }


    public void showOwnGrave() {
        GridPane gridPane = new GridPane();
        ArrayList<Card> graveyard = duelMenuController.getTurnPlayer().getBoard().getGraveyard();
        addNodesToGridpane(gridPane, graveyard);
    }

    public void showOpponentGrave() {
        GridPane gridPane = new GridPane();
        ArrayList<Card> graveyard = duelMenuController.getNotTurnPlayer().getBoard().getGraveyard();
        addNodesToGridpane(gridPane, graveyard);
    }

    private void addNodesToGridpane(GridPane gridPane, ArrayList<Card> graveyard) {
        for (int i = 0; i < graveyard.size(); i++) {
            Rectangle rectangle = new Rectangle();
            Image img = new Image(graveyard.get(i).getFrontImageAddress());
            rectangle.setFill(new ImagePattern(img));
            rectangle.setHeight(150);
            rectangle.setWidth(100);
            gridPane.add(rectangle, 0, i);
        }
        scrollPane.setContent(gridPane);
        scrollPane.setVisible(true);
        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                scrollPane.setVisible(false);
                scrollPane.setContent(null);
            }
        };
        scrollPane.setOnMouseClicked(this::hideGraveyard);
    }

    public void hideGraveyard(MouseEvent mouseEvent) {
        scrollPane.setVisible(false);
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

        Button directAttackButton = (Button) scene.lookup("#directAttack");
        scene.lookup("#directAttack").addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        if (!pause) {
                            DuelMenuMessages result = duelMenuController.findCommand("attack direct");
                        }

                    }
                });

        scrollPane = (ScrollPane) scene.lookup("#graveYard");
        scrollPane.setVisible(false);
        scene.lookup("#graveYard").setVisible(false);
        scene.lookup("#settingsMenu").setVisible(false);
        scene.lookup("#cheatcodeMenu").setVisible(false);

        ownUsername = (Label) scene.lookup("#ownUsername");
        ownNickname = (Label) scene.lookup("#ownNiackname");
        opponentName = (Label) scene.lookup("#opponentName");
        opponentNickname = (Label) scene.lookup("#opponentNickname");

        Label ownLPLabel = (Label) scene.lookup("#ownLP");
        Label opponentLPLabel = (Label) scene.lookup("#opponentLP");

        if (isPlayingWithAI) {
            ownUsername.setText(firstPlayer.getUsername());
            ownNickname.setText(firstPlayer.getNickname());
            opponentName.setText(secondPlayer.getUsername());
            opponentNickname.setText(secondPlayer.getNickname());
            ownAvatar = (Rectangle) scene.lookup("#ownImage");
            Image image = Utils.convertToFxImage(firstPlayer.getAvatar());
            ownAvatar.setFill(new ImagePattern(image));
            opponentAvatar = (Rectangle) scene.lookup("#opponentImage");
            image = Utils.convertToFxImage(secondPlayer.getAvatar());
            opponentAvatar.setFill(new ImagePattern(image));

        } else {
            ownUsername.setText(duelMenuController.getTurnPlayer().getUsername());
            ownNickname.setText(duelMenuController.getTurnPlayer().getNickname());
            opponentName.setText(duelMenuController.getNotTurnPlayer().getUsername());
            opponentNickname.setText(duelMenuController.getNotTurnPlayer().getNickname());
            ownAvatar = (Rectangle) scene.lookup("#ownImage");
            Image image = Utils.convertToFxImage(duelMenuController.getTurnPlayer().getAvatar());
            ownAvatar.setFill(new ImagePattern(image));
            opponentAvatar = (Rectangle) scene.lookup("#opponentImage");
            image = Utils.convertToFxImage(duelMenuController.getNotTurnPlayer().getAvatar());
            opponentAvatar.setFill(new ImagePattern(image));

        }


        ownFieldzone = (Rectangle) scene.lookup("#ownFieldzone");
        opponentFieldzone = (Rectangle) scene.lookup("#opponentFieldzone");


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
        imageView = (Rectangle) scene.lookup("#showSelectedCard");

        ownCardsInHand[1] = (Rectangle) scene.lookup("#ownHandCard1");
        ownCardsInHand[2] = (Rectangle) scene.lookup("#ownHandCard2");
        ownCardsInHand[3] = (Rectangle) scene.lookup("#ownHandCard3");
        ownCardsInHand[4] = (Rectangle) scene.lookup("#ownHandCard4");

        opponentCardsInHand[1] = (Rectangle) scene.lookup("#opponentHandCard1");
        opponentCardsInHand[2] = (Rectangle) scene.lookup("#opponentHandCard2");
        opponentCardsInHand[3] = (Rectangle) scene.lookup("#opponentHandCard3");
        opponentCardsInHand[4] = (Rectangle) scene.lookup("#opponentHandCard4");


        drawPhase = (Rectangle) scene.lookup("#drawPhase");
        standbyPhase = (Rectangle) scene.lookup("#standbyPhase");
        mainPhase1 = (Rectangle) scene.lookup("#mainPhase1");
        battlePhase = (Rectangle) scene.lookup("#battlePhase");
        mainPhase2 = (Rectangle) scene.lookup("#mainPhase2");
        endPhase = (Rectangle) scene.lookup("#endPhase");

        Image img = new Image(getClass().getResource("/images/duelmenu/drawPhase.png").toExternalForm());
        drawPhase.setFill(new ImagePattern(img));
        img = new Image(getClass().getResource("/images/duelmenu/standbyPhase.png").toExternalForm());
        standbyPhase.setFill(new ImagePattern(img));
        img = new Image(getClass().getResource("/images/duelmenu/mainPhase.png").toExternalForm());
        mainPhase1.setFill(new ImagePattern(img));
        img = new Image(getClass().getResource("/images/duelmenu/battlePhase.png").toExternalForm());
        battlePhase.setFill(new ImagePattern(img));
        img = new Image(getClass().getResource("/images/duelmenu/mainPhase2.png").toExternalForm());
        mainPhase2.setFill(new ImagePattern(img));
        img = new Image(getClass().getResource("/images/duelmenu/endPhase.png").toExternalForm());
        endPhase.setFill(new ImagePattern(img));


        timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), e -> {
                    opponentLPLabel.setText(String.valueOf(duelMenuController.getNotTurnPlayer().getLifePoint()));
                    ownLPLabel.setText(String.valueOf(duelMenuController.getTurnPlayer().getLifePoint()));
                    if (duelMenuController.getNotTurnPlayer().getLifePoint() > 5000) {
                        opponentLPLabel.setStyle("-fx-background-color: #008000");
                    } else if (duelMenuController.getNotTurnPlayer().getLifePoint() > 2000) {
                        opponentLPLabel.setStyle("-fx-background-color: #FFA500");
                    } else {
                        opponentLPLabel.setStyle("-fx-background-color: #FF6347");
                    }
                    if (duelMenuController.getTurnPlayer().getLifePoint() > 5000) {
                        ownLPLabel.setStyle("-fx-background-color: #008000");
                    } else if (duelMenuController.getTurnPlayer().getLifePoint() > 2000) {
                        ownLPLabel.setStyle("-fx-background-color: #FFA500");
                    } else {
                        ownLPLabel.setStyle("-fx-background-color: #FF6347");
                    }
                })
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();


        KeyCombination keyCombination = KeyCombination.keyCombination("CTRL+SHIFT+C");
        Runnable runnable = () -> showCheatText(scene);
        scene.getAccelerators().put(keyCombination, runnable);


        scene.lookup("#ownFieldzone").addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        if (!pause) {
                            DuelMenuMessages result = duelMenuController.findCommand("select --field");
                        }
                        if (showCardCheck(duelMenuController.getTurnPlayer().getBoard())) {
                            //  imageView = new ImageView(new Image(getClass().getResource(duelMenuController.getTurnPlayer().getBoard().getSelectedCard().getFrontImageAddress()).toExternalForm()));
                        }
                        scene.lookup("#activeOwnFielzone").setVisible(true);
                    }
                });

        scene.lookup("#activeOwnFielzone").addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        if (!pause) {
                            DuelMenuMessages result = duelMenuController.findCommand("activate effect");
                        }
                        scene.lookup("#opponentFieldzone").setVisible(false);
                    }
                });
        scene.lookup("#activeOwnFielzone").addEventHandler(MouseEvent.MOUSE_EXITED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        scene.lookup("#activeOwnFielzone").setVisible(false);
                    }
                });

        scene.lookup("#ownGraveyard").addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        if (!pause)
                            showOwnGrave();
                        else System.out.println("pause");
                    }
                });
        scene.lookup("#opponentGraveyard").addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        showOpponentGrave();
                    }
                });


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
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        scene.lookup("#graveYard").setVisible(false);
                    }
                });


        scene.lookup("#ownMonster1").addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        if (!pause) {
                            DuelMenuMessages result = duelMenuController.findCommand("select --monster 1");
                        }
                        if (showCardCheck(duelMenuController.getTurnPlayer().getBoard())) {
                            Image image = new Image(duelMenuController.getTurnPlayer().getBoard().getSelectedCard().getFrontImageAddress());
                            imageView.setFill(new ImagePattern(image));
                        }
                        scene.lookup("#changeMonster1").setVisible(true);


                    }
                });
        scene.lookup("#changeMonster1").addEventHandler(MouseEvent.MOUSE_EXITED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        scene.lookup("#changeMonster1").setVisible(false);
                    }
                });

        scene.lookup("#changeMonster1").addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        if (!pause) {
                            DuelMenuMessages result;
                            if (duelMenuController.getTurnPlayer().getBoard().getMonstersZone()[1] != null &&
                                    duelMenuController.getTurnPlayer().getBoard().getMonstersZone()[1].isDefensePosition())
                                result = duelMenuController.findCommand("set --position attack");
                            else if (duelMenuController.getTurnPlayer().getBoard().getMonstersZone()[1] != null &&
                                    !duelMenuController.getTurnPlayer().getBoard().getMonstersZone()[1].isDefensePosition())
                                result = duelMenuController.findCommand("set --position defense");
                        }

                        scene.lookup("#changeMonster1").setVisible(false);
                    }
                });

        scene.lookup("#changeMonster2").addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        if (!pause) {
                            DuelMenuMessages result;
                            if (duelMenuController.getTurnPlayer().getBoard().getMonstersZone()[2] != null &&
                                    duelMenuController.getTurnPlayer().getBoard().getMonstersZone()[2].isDefensePosition())
                                result = duelMenuController.findCommand("set --position attack");
                            else if (duelMenuController.getTurnPlayer().getBoard().getMonstersZone()[2] != null &&
                                    !duelMenuController.getTurnPlayer().getBoard().getMonstersZone()[2].isDefensePosition())
                                result = duelMenuController.findCommand("set --position defense");
                        }

                        scene.lookup("#changeMonster2").setVisible(false);
                    }
                });

        scene.lookup("#changeMonster3").addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        if (!pause) {
                            DuelMenuMessages result;
                            if (duelMenuController.getTurnPlayer().getBoard().getMonstersZone()[3] != null &&
                                    duelMenuController.getTurnPlayer().getBoard().getMonstersZone()[3].isDefensePosition())
                                result = duelMenuController.findCommand("set --position attack");
                            else if (duelMenuController.getTurnPlayer().getBoard().getMonstersZone()[3] != null &&
                                    !duelMenuController.getTurnPlayer().getBoard().getMonstersZone()[3].isDefensePosition())
                                result = duelMenuController.findCommand("set --position defense");
                        }

                        scene.lookup("#changeMonster3").setVisible(false);
                    }
                });

        scene.lookup("#changeMonster4").addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        if (!pause) {
                            DuelMenuMessages result;
                            if (duelMenuController.getTurnPlayer().getBoard().getMonstersZone()[4] != null &&
                                    duelMenuController.getTurnPlayer().getBoard().getMonstersZone()[4].isDefensePosition())
                                result = duelMenuController.findCommand("set --position attack");
                            else if (duelMenuController.getTurnPlayer().getBoard().getMonstersZone()[4] != null &&
                                    !duelMenuController.getTurnPlayer().getBoard().getMonstersZone()[4].isDefensePosition())
                                result = duelMenuController.findCommand("set --position defense");
                        }

                        scene.lookup("#changeMonster4").setVisible(false);
                    }
                });

        scene.lookup("#changeMonster5").addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        if (!pause) {
                            DuelMenuMessages result;
                            if (duelMenuController.getTurnPlayer().getBoard().getMonstersZone()[5] != null &&
                                    duelMenuController.getTurnPlayer().getBoard().getMonstersZone()[5].isDefensePosition())
                                result = duelMenuController.findCommand("set --position attack");
                            else if (duelMenuController.getTurnPlayer().getBoard().getMonstersZone()[5] != null &&
                                    !duelMenuController.getTurnPlayer().getBoard().getMonstersZone()[5].isDefensePosition())
                                result = duelMenuController.findCommand("set --position defense");
                        }

                        scene.lookup("#changeMonster5").setVisible(false);
                    }
                });

        scene.lookup("#changeMonster2").addEventHandler(MouseEvent.MOUSE_EXITED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        scene.lookup("#changeMonster2").setVisible(false);
                    }
                });
        scene.lookup("#ownMonster2").addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        if (!pause) {
                            DuelMenuMessages result = duelMenuController.findCommand("select --monster 2");
                        }
                        if (showCardCheck(duelMenuController.getTurnPlayer().getBoard())) {
                            Image image = new Image(duelMenuController.getTurnPlayer().getBoard().getSelectedCard().getFrontImageAddress());
                            imageView.setFill(new ImagePattern(image));
                        }
                        scene.lookup("#changeMonster2").setVisible(true);


                    }
                });
        scene.lookup("#changeMonster3").addEventHandler(MouseEvent.MOUSE_EXITED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        scene.lookup("#changeMonster3").setVisible(false);
                    }
                });
        scene.lookup("#ownMonster3").addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        if (!pause) {
                            DuelMenuMessages result = duelMenuController.findCommand("select --monster 3");
                        }
                        if (showCardCheck(duelMenuController.getTurnPlayer().getBoard())) {
                            Image image = new Image(duelMenuController.getTurnPlayer().getBoard().getSelectedCard().getFrontImageAddress());
                            imageView.setFill(new ImagePattern(image));
                        }
                        scene.lookup("#changeMonster3").setVisible(true);


                    }
                });
        scene.lookup("#changeMonster4").addEventHandler(MouseEvent.MOUSE_EXITED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {

                        scene.lookup("#changeMonster4").setVisible(false);
                    }
                });
        scene.lookup("#ownMonster4").addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        if (!pause) {
                            DuelMenuMessages result = duelMenuController.findCommand("select --monster 4");
                        }
                        if (showCardCheck(duelMenuController.getTurnPlayer().getBoard())) {
                            Image image = new Image(duelMenuController.getTurnPlayer().getBoard().getSelectedCard().getFrontImageAddress());
                            imageView.setFill(new ImagePattern(image));
                        }
                        scene.lookup("#changeMonster4").setVisible(true);


                    }
                });
        scene.lookup("#changeMonster5").addEventHandler(MouseEvent.MOUSE_EXITED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        scene.lookup("#changeMonster5").setVisible(false);
                    }
                });
        scene.lookup("#ownMonster5").addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        if (!pause) {
                            DuelMenuMessages result = duelMenuController.findCommand("select --monster 5");
                        }
                        if (showCardCheck(duelMenuController.getTurnPlayer().getBoard())) {
                            Image image = new Image(duelMenuController.getTurnPlayer().getBoard().getSelectedCard().getFrontImageAddress());
                            imageView.setFill(new ImagePattern(image));
                        }
                        scene.lookup("#changeMonster5").setVisible(true);


                    }
                });


        scene.lookup("#ownMagic1").addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        if (!pause) {
                            DuelMenuMessages result = duelMenuController.findCommand("select --spell 1");
                        }
                        if (showCardCheck(duelMenuController.getTurnPlayer().getBoard())) {
                            Image image = new Image(duelMenuController.getTurnPlayer().getBoard().getSelectedCard().getFrontImageAddress());
                            imageView.setFill(new ImagePattern(image));
                        }
                        scene.lookup("#activeMagic1").setVisible(true);


                    }
                });
        scene.lookup("#activeMagic1").addEventHandler(MouseEvent.MOUSE_EXITED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {

                        scene.lookup("#activeMagic1").setVisible(false);
                    }
                });
        scene.lookup("#ownMagic2").addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        if (!pause) {
                            DuelMenuMessages result = duelMenuController.findCommand("select --spell 2");
                        }
                        if (showCardCheck(duelMenuController.getTurnPlayer().getBoard())) {
                            Image image = new Image(duelMenuController.getTurnPlayer().getBoard().getSelectedCard().getFrontImageAddress());
                            imageView.setFill(new ImagePattern(image));
                        }
                        scene.lookup("#activeMagic2").setVisible(true);


                    }
                });
        scene.lookup("#activeMagic2").addEventHandler(MouseEvent.MOUSE_EXITED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        scene.lookup("#activeMagic2").setVisible(false);
                    }
                });
        scene.lookup("#ownMagic3").addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        if (!pause) {
                            DuelMenuMessages result = duelMenuController.findCommand("select --spell 3");
                        }
                        if (showCardCheck(duelMenuController.getTurnPlayer().getBoard())) {
                            Image image = new Image(duelMenuController.getTurnPlayer().getBoard().getSelectedCard().getFrontImageAddress());
                            imageView.setFill(new ImagePattern(image));
                        }
                        scene.lookup("#activeMagic3").setVisible(true);


                    }
                });
        scene.lookup("#activeMagic3").addEventHandler(MouseEvent.MOUSE_EXITED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        scene.lookup("#activeMagic3").setVisible(false);
                    }
                });
        scene.lookup("#ownMagic4").addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        if (!pause) {
                            DuelMenuMessages result = duelMenuController.findCommand("select --spell 4");
                        }
                        if (showCardCheck(duelMenuController.getTurnPlayer().getBoard())) {
                            Image image = new Image(duelMenuController.getTurnPlayer().getBoard().getSelectedCard().getFrontImageAddress());
                            imageView.setFill(new ImagePattern(image));
                        }
                        scene.lookup("#activeMagic4").setVisible(true);


                    }
                });
        scene.lookup("#activeMagic4").addEventHandler(MouseEvent.MOUSE_EXITED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        scene.lookup("#activeMagic4").setVisible(false);
                    }
                });
        scene.lookup("#ownMagic5").addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        if (!pause) {
                            DuelMenuMessages result = duelMenuController.findCommand("select --spell 5");
                        }
                        if (showCardCheck(duelMenuController.getTurnPlayer().getBoard())) {
                            Image image = new Image(duelMenuController.getTurnPlayer().getBoard().getSelectedCard().getFrontImageAddress());
                            imageView.setFill(new ImagePattern(image));
                        }
                        scene.lookup("#activeMagic5").setVisible(true);


                    }
                });
        scene.lookup("#activeMagic5").addEventHandler(MouseEvent.MOUSE_EXITED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        scene.lookup("#activeMagic5").setVisible(false);
                    }
                });


        scene.lookup("#opponentMagic5").addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        if (!pause) {
                            DuelMenuMessages result = duelMenuController.findCommand("select --spell --opponent 5");
                        }
                        if (showCardCheck(duelMenuController.getTurnPlayer().getBoard())) {
                            Image image = new Image(duelMenuController.getTurnPlayer().getBoard().getSelectedCard().getFrontImageAddress());
                            imageView.setFill(new ImagePattern(image));
                        }


                    }
                });
        scene.lookup("#opponentMagic4").addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        if (!pause) {
                            DuelMenuMessages result = duelMenuController.findCommand("select --spell --opponent 4");
                        }
                        if (showCardCheck(duelMenuController.getTurnPlayer().getBoard())) {
                            Image image = new Image(duelMenuController.getTurnPlayer().getBoard().getSelectedCard().getFrontImageAddress());
                            imageView.setFill(new ImagePattern(image));
                        }


                    }
                });
        scene.lookup("#opponentMagic3").addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        if (!pause) {
                            DuelMenuMessages result = duelMenuController.findCommand("select --spell --opponent 3");
                        }
                        if (showCardCheck(duelMenuController.getTurnPlayer().getBoard())) {
                            Image image = new Image(duelMenuController.getTurnPlayer().getBoard().getSelectedCard().getFrontImageAddress());
                            imageView.setFill(new ImagePattern(image));
                        }


                    }
                });
        scene.lookup("#opponentMagic2").addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        if (!pause) {
                            DuelMenuMessages result = duelMenuController.findCommand("select --spell --opponent 2");
                        }
                        if (showCardCheck(duelMenuController.getTurnPlayer().getBoard())) {
                            Image image = new Image(duelMenuController.getTurnPlayer().getBoard().getSelectedCard().getFrontImageAddress());
                            imageView.setFill(new ImagePattern(image));
                        }


                    }
                });
        scene.lookup("#opponentMagic1").addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        if (!pause) {
                            DuelMenuMessages result = duelMenuController.findCommand("select --spell --opponent 1");
                        }
                        if (showCardCheck(duelMenuController.getTurnPlayer().getBoard())) {
                            Image image = new Image(duelMenuController.getTurnPlayer().getBoard().getSelectedCard().getFrontImageAddress());
                            imageView.setFill(new ImagePattern(image));
                        }


                    }
                });


        scene.lookup("#ownHandCard1").addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        if (!pause) {
                            DuelMenuMessages result = duelMenuController.findCommand("select --hand 1");
                        }
                        if (showCardCheck(duelMenuController.getTurnPlayer().getBoard())) {
                            Image image = new Image(duelMenuController.getTurnPlayer().getBoard().getSelectedCard().getFrontImageAddress());
                            imageView.setFill(new ImagePattern(image));
                            // ownMonsterRectangles[1].setFill(new ImagePattern(image));
                        }
                        scene.lookup("#setOwnHandCard1").setVisible(true);
                        scene.lookup("#summonOwnCard1").setVisible(true);
                    }
                });
        scene.lookup("#ownHandCard2").addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        if (!pause) {
                            DuelMenuMessages result = duelMenuController.findCommand("select --hand 2");
                        }
                        if (showCardCheck(duelMenuController.getTurnPlayer().getBoard())) {
                            Image image = new Image(duelMenuController.getTurnPlayer().getBoard().getSelectedCard().getFrontImageAddress());
                            imageView.setFill(new ImagePattern(image));
                        }
                        scene.lookup("#setOwnHandCard2").setVisible(true);
                        scene.lookup("#summonOwnCard2").setVisible(true);
                    }
                });
        scene.lookup("#ownHandCard3").addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        if (!pause) {
                            DuelMenuMessages result = duelMenuController.findCommand("select --hand 3");
                        }
                        if (showCardCheck(duelMenuController.getTurnPlayer().getBoard())) {
                            Image image = new Image(duelMenuController.getTurnPlayer().getBoard().getSelectedCard().getFrontImageAddress());
                            imageView.setFill(new ImagePattern(image));
                        }
                        scene.lookup("#setOwnHandCard3").setVisible(true);
                        scene.lookup("#summonOwnCard3").setVisible(true);
                    }
                });
        scene.lookup("#ownHandCard4").addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        if (!pause) {
                            DuelMenuMessages result = duelMenuController.findCommand("select --hand 4");
                        }
                        if (showCardCheck(duelMenuController.getTurnPlayer().getBoard())) {
                            Image image = new Image(duelMenuController.getTurnPlayer().getBoard().getSelectedCard().getFrontImageAddress());
                            imageView.setFill(new ImagePattern(image));
                        }
                        scene.lookup("#setOwnHandCard4").setVisible(true);
                        scene.lookup("#summonOwnCard4").setVisible(true);
                    }
                });


        scene.lookup("#summonOwnCard1").addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        if (!pause) {

                            DuelMenuMessages result = duelMenuController.findCommand("summon");
                        }

                        scene.lookup("#setOwnHandCard1").setVisible(false);
                        scene.lookup("#summonOwnCard1").setVisible(false);

                    }
                });
        scene.lookup("#summonOwnCard2").addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        if (!pause) {
                            DuelMenuMessages result = duelMenuController.findCommand("summon");
                        }

                        scene.lookup("#setOwnHandCard2").setVisible(false);
                        scene.lookup("#summonOwnCard2").setVisible(false);

                    }
                });
        scene.lookup("#summonOwnCard3").addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        if (!pause) {
                            DuelMenuMessages result = duelMenuController.findCommand("summon");
                        }

                        scene.lookup("#setOwnHandCard3").setVisible(false);
                        scene.lookup("#summonOwnCard3").setVisible(false);

                    }
                });
        scene.lookup("#summonOwnCard4").addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        if (!pause) {
                            DuelMenuMessages result = duelMenuController.findCommand("summon");
                        }

                        scene.lookup("#setOwnHandCard4").setVisible(false);
                        scene.lookup("#summonOwnCard4").setVisible(false);

                    }
                });
        scene.lookup("#setOwnHandCard1").addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        if (!pause) {
                            DuelMenuMessages result = duelMenuController.findCommand("set");
                        }

                        scene.lookup("#setOwnHandCard1").setVisible(false);
                        scene.lookup("#summonOwnCard1").setVisible(false);

                    }
                });
        scene.lookup("#setOwnHandCard2").addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        if (!pause) {
                            DuelMenuMessages result = duelMenuController.findCommand("set");
                        }

                        scene.lookup("#setOwnHandCard2").setVisible(false);
                        scene.lookup("#summonOwnCard2").setVisible(false);

                    }
                });
        scene.lookup("#setOwnHandCard3").addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        if (!pause) {
                            DuelMenuMessages result = duelMenuController.findCommand("set");
                        }

                        scene.lookup("#setOwnHandCard3").setVisible(false);
                        scene.lookup("#summonOwnCard3").setVisible(false);

                    }
                });
        scene.lookup("#setOwnHandCard4").addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {

                        if (!pause) {
                            DuelMenuMessages result = duelMenuController.findCommand("set");
                        }

                        scene.lookup("#setOwnHandCard4").setVisible(false);
                        scene.lookup("#summonOwnCard4").setVisible(false);

                    }
                });


        scene.lookup("#setOwnHandCard1").addEventHandler(MouseEvent.MOUSE_EXITED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {

                        scene.lookup("#setOwnHandCard1").setVisible(false);
                    }
                });
        scene.lookup("#setOwnHandCard2").addEventHandler(MouseEvent.MOUSE_EXITED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {

                        scene.lookup("#setOwnHandCard2").setVisible(false);
                    }
                });
        scene.lookup("#setOwnHandCard3").addEventHandler(MouseEvent.MOUSE_EXITED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {

                        scene.lookup("#setOwnHandCard3").setVisible(false);
                    }
                });
        scene.lookup("#setOwnHandCard4").addEventHandler(MouseEvent.MOUSE_EXITED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        scene.lookup("#setOwnHandCard4").setVisible(false);
                    }
                });


        scene.lookup("#summonOwnCard1").addEventHandler(MouseEvent.MOUSE_EXITED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        scene.lookup("#summonOwnCard1").setVisible(false);
                    }
                });
        scene.lookup("#summonOwnCard2").addEventHandler(MouseEvent.MOUSE_EXITED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        scene.lookup("#summonOwnCard2").setVisible(false);
                    }
                });
        scene.lookup("#summonOwnCard3").addEventHandler(MouseEvent.MOUSE_EXITED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        scene.lookup("#summonOwnCard3").setVisible(false);
                    }
                });
        scene.lookup("#summonOwnCard4").addEventHandler(MouseEvent.MOUSE_EXITED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        scene.lookup("#summonOwnCard4").setVisible(false);
                    }
                });


        scene.lookup("#attackToOpponentMonster1").addEventHandler(MouseEvent.MOUSE_EXITED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        scene.lookup("#attackToOpponentMonster1").setVisible(false);
                    }
                });
        //-------------------------------------------------handle attack button


        scene.lookup("#opponentMonster1").addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        if (!pause) {
                            DuelMenuMessages result = duelMenuController.findCommand("select --monster --opponent 1");
                        }
                        if (showCardCheck(duelMenuController.getTurnPlayer().getBoard())) {
                            Image image = new Image(duelMenuController.getTurnPlayer().getBoard().getSelectedCard().getFrontImageAddress());
                            imageView.setFill(new ImagePattern(image));
                        }
                        scene.lookup("#attackToOpponentMonster1").setVisible(true);


                    }
                });
        scene.lookup("#opponentMonster1").addEventHandler(MouseEvent.MOUSE_ENTERED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        scene.lookup("#attackToOpponentMonster1").setVisible(true);
                    }
                });
        scene.lookup("#opponentMonster2").addEventHandler(MouseEvent.MOUSE_ENTERED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {

                        scene.lookup("#attackToOpponentMonster2").setVisible(true);


                    }
                });
        scene.lookup("#opponentMonster3").addEventHandler(MouseEvent.MOUSE_ENTERED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {

                        scene.lookup("#attackToOpponentMonster3").setVisible(true);


                    }
                });
        scene.lookup("#opponentMonster4").addEventHandler(MouseEvent.MOUSE_ENTERED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {

                        scene.lookup("#attackToOpponentMonster4").setVisible(true);


                    }
                });
        scene.lookup("#opponentMonster5").addEventHandler(MouseEvent.MOUSE_ENTERED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {

                        scene.lookup("#attackToOpponentMonster5").setVisible(true);


                    }
                });
        scene.lookup("#attackToOpponentMonster1").addEventHandler(MouseEvent.MOUSE_EXITED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        scene.lookup("#attackToOpponentMonster1").setVisible(false);
                    }
                });
        scene.lookup("#opponentMonster2").addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        if (!pause) {
                            DuelMenuMessages result = duelMenuController.findCommand("select --monster --opponent 2");
                        }
                        if (showCardCheck(duelMenuController.getTurnPlayer().getBoard())) {
                            Image image = new Image(duelMenuController.getTurnPlayer().getBoard().getSelectedCard().getFrontImageAddress());
                            imageView.setFill(new ImagePattern(image));
                        }
                        scene.lookup("#attackToOpponentMonster2").setVisible(true);


                    }
                });
        scene.lookup("#attackToOpponentMonster2").addEventHandler(MouseEvent.MOUSE_EXITED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        scene.lookup("#attackToOpponentMonster2").setVisible(false);
                    }
                });
        scene.lookup("#opponentMonster3").addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        if (!pause) {
                            DuelMenuMessages result = duelMenuController.findCommand("select --monster --opponent 3");
                        }
                        if (showCardCheck(duelMenuController.getTurnPlayer().getBoard())) {
                            //  imageView = new ImageView(new Image(getClass().getResource(duelMenuController.getTurnPlayer().getBoard().getSelectedCard().getFrontImageAddress()).toExternalForm()));
                        }
                        scene.lookup("#attackToOpponentMonster3").setVisible(true);


                    }
                });
        scene.lookup("#attackToOpponentMonster3").addEventHandler(MouseEvent.MOUSE_EXITED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        scene.lookup("#attackToOpponentMonster3").setVisible(false);
                    }
                });
        scene.lookup("#opponentMonster4").addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        if (!pause) {
                            DuelMenuMessages result = duelMenuController.findCommand("select --monster --opponent 4");
                        }
                        if (showCardCheck(duelMenuController.getTurnPlayer().getBoard())) {
                            ///   imageView = new ImageView(new Image(getClass().getResource(duelMenuController.getTurnPlayer().getBoard().getSelectedCard().getFrontImageAddress()).toExternalForm()));
                        }
                        scene.lookup("#attackToOpponentMonster4").setVisible(true);


                    }
                });
        scene.lookup("#attackToOpponentMonster4").addEventHandler(MouseEvent.MOUSE_EXITED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        scene.lookup("#attackToOpponentMonster4").setVisible(false);
                    }
                });
        scene.lookup("#opponentMonster5").addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        if (!pause) {
                            DuelMenuMessages result = duelMenuController.findCommand("select --monster --opponent 5");
                        }
                        if (showCardCheck(duelMenuController.getTurnPlayer().getBoard())) {
                            //  imageView = new ImageView(new Image(getClass().getResource(duelMenuController.getTurnPlayer().getBoard().getSelectedCard().getFrontImageAddress()).toExternalForm()));
                        }
                        scene.lookup("#attackToOpponentMonster5").setVisible(true);


                    }
                });
        scene.lookup("#attackToOpponentMonster5").addEventHandler(MouseEvent.MOUSE_EXITED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        scene.lookup("#attackToOpponentMonster5").setVisible(false);
                    }
                });

        scene.lookup("#attackToOpponentMonster1").addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        if (!pause) {

                            DuelMenuMessages result = duelMenuController.findCommand("attack 1");
                        }
                    }
                });
        scene.lookup("#attackToOpponentMonster2").addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        if (!pause) {
                            DuelMenuMessages result = duelMenuController.findCommand("attack 2");
                        }
                    }
                });
        scene.lookup("#attackToOpponentMonster3").addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        if (!pause) {
                            DuelMenuMessages result = duelMenuController.findCommand("attack 3");
                        }
                    }
                });
        scene.lookup("#attackToOpponentMonster4").addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        if (!pause) {
                            DuelMenuMessages result = duelMenuController.findCommand("attack 4");
                        }
                    }
                });
        scene.lookup("#attackToOpponentMonster5").addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        if (!pause) {
                            DuelMenuMessages result = duelMenuController.findCommand("attack 5");
                        }
                    }
                });


        scene.lookup("#activeMagic1").addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        if (!pause) {
                            DuelMenuMessages result = duelMenuController.findCommand("active effect");
                        }
                    }
                });
        scene.lookup("#activeMagic2").addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        if (!pause) {
                            DuelMenuMessages result = duelMenuController.findCommand("active effect");
                        }
                    }
                });
        scene.lookup("#activeMagic3").addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        if (!pause) {
                            DuelMenuMessages result = duelMenuController.findCommand("active effect");
                        }
                    }
                });
        scene.lookup("#activeMagic4").addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        if (!pause) {
                            DuelMenuMessages result = duelMenuController.findCommand("active effect");
                        }
                    }
                });
        scene.lookup("#activeMagic5").addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        if (!pause) {
                            DuelMenuMessages result = duelMenuController.findCommand("active effect");
                        }
                    }
                });


        scene.lookup("#nextPhaseButton").addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
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

    /*
        public void takeTribute() {
            MonsterCard[] monsterCards = duelMenuController.getTurnPlayer().getBoard().getMonstersZone();
            GridPane gridPane = new GridPane();
            for (int i = 1; i <= 5; i++) {
                if (monsterCards[i] != null) {
                    Rectangle rectangle = new Rectangle();
                    Image img = new Image(getClass().getResource(monsterCards[i].getFrontImageAddress()).toExternalForm());
                    rectangle.setFill(new ImagePattern(img));
                    rectangle.setHeight(50);
                    rectangle.setWidth(80);
                    int finalI = i;
                    rectangle.addEventHandler(MouseEvent.MOUSE_CLICKED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(MouseEvent e) {
                                    duelMenuController.getTurnPlayer().getBoard().getGraveyard().add(duelMenuController.getTurnPlayer().getBoard().getMonstersZone()[finalI]);
                                    duelMenuController.getTurnPlayer().getBoard().getMonstersZone()[finalI] = null;
                                    ownMonsterRectangles[finalI].setFill(null);
                                    scrollPane.setVisible(false);
                                }
                            });
                    gridPane.add(rectangle, 0, i);
                }
            }
            scrollPane.setContent(gridPane);
            scrollPane.setVisible(true);
            EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
                public void handle(ActionEvent e) {
                    scrollPane.setVisible(false);
                    scrollPane.setContent(null);
                }
            };
            scrollPane.setOnMouseClicked(this::hideGraveyard);
        }
    */
    public void winningSound() {
        String path = "src/main/resources/sounds/winningAlarm.wav";
        Media media = new Media(new File(path).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
    }


    public void changeRectangles() {
        for (int i = 1; i <= 5; i++) {
            javafx.scene.paint.Paint holdRectangle = opponentMonsterRectangles[i].getFill();
            opponentMonsterRectangles[i].setFill(ownMonsterRectangles[i].getFill());
            ownMonsterRectangles[i].setFill(holdRectangle);
        }
        for (int i = 1; i <= 5; i++) {
            javafx.scene.paint.Paint holdRectangle = opponentMagicRectangles[i].getFill();
            opponentMagicRectangles[i].setFill(ownMagicRectangles[i].getFill());
            ownMagicRectangles[i].setFill(holdRectangle);
        }
        for (int i = 0; i < 4; i++) {
            if (i + 1 > duelMenuController.getTurnPlayer().getBoard().getCardsInHand().size()) {
                ownCardsInHand[i + 1].setFill(DODGERBLUE);
            } else {
                Image img = new Image(getClass().getResource(duelMenuController.getTurnPlayer().getBoard().getCardsInHand().get(i).getFrontImageAddress()).toExternalForm());
                ownCardsInHand[i + 1].setFill(new ImagePattern(img));
            }
        }
        for (int i = 0; i < 4; i++) {
            if (i + 1 > duelMenuController.getNotTurnPlayer().getBoard().getCardsInHand().size()) {
                opponentCardsInHand[i + 1].setFill(DODGERBLUE);
            } else {
                Image img = new Image(getClass().getResource(duelMenuController.getNotTurnPlayer().getBoard().getCardsInHand().get(i).getBackImageAddress()).toExternalForm());
                opponentCardsInHand[i + 1].setFill(new ImagePattern(img));
            }
        }

        ownUsername.setText(duelMenuController.getTurnPlayer().getUsername());
        ownNickname.setText(duelMenuController.getTurnPlayer().getNickname());
        opponentName.setText(duelMenuController.getNotTurnPlayer().getUsername());
        opponentNickname.setText(duelMenuController.getNotTurnPlayer().getNickname());

        ownAvatar = (Rectangle) scene.lookup("#ownImage");
        Image image = Utils.convertToFxImage(duelMenuController.getTurnPlayer().getAvatar());
        ownAvatar.setFill(new ImagePattern(image));
        opponentAvatar = (Rectangle) scene.lookup("#opponentImage");
        image = Utils.convertToFxImage(duelMenuController.getNotTurnPlayer().getAvatar());
        opponentAvatar.setFill(new ImagePattern(image));
    }
}
