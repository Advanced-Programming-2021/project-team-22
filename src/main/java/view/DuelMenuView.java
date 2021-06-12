package view;

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

    public DuelMenuView(Player firstPlayer, Player secondPlayer) {
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
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
//        monsters[5].print();
//        System.out.print(monsters[5].print() + "    ");
//        System.out.print(monsters[3].print() + "    ");
//        System.out.print(monsters[1].print() + "    ");
//        System.out.print(monsters[2].print() + "    ");
//        System.out.print(monsters[4].print() + "    ");
//        System.out.println();
    }

    private static void showMagicsZone(Board board) {
        MagicCard[] magicsZone = board.getMagicsZone();
        magicsZone[5].print();
        magicsZone[3].print();
        magicsZone[1].print();
        magicsZone[2].print();
        magicsZone[4].print();
    }

    private static void showOpponentMonstersZone(Board board) {
        MonsterCard[] monsters = board.getMonstersZone();
//        System.out.print(monsters[4].print() + "    ");
//        System.out.print(monsters[2].print() + "    ");
//        System.out.print(monsters[1].print() + "    ");
//        System.out.print(monsters[3].print() + "    ");
//        System.out.print(monsters[5].print() + "    ");
    }

    private static void showOpponentMagicsZone(Board board) {
        MagicCard[] magicsZone = board.getMagicsZone();
        magicsZone[4].print();
        magicsZone[2].print();
        magicsZone[1].print();
        magicsZone[3].print();
        magicsZone[5].print();
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
            System.out.println("you cant see this card!");
            return false;
        }
        return true;
    }

    public void duelMenuView() {
        DuelMenuController duelMenuController = new DuelMenuController();

        DuelMenuMessages resultOfInitialGame = null;
        while (resultOfInitialGame == null || !resultOfInitialGame.equals(DuelMenuMessages.SHOW_TURN_PLAYER)) {
            resultOfInitialGame = duelMenuController.initialGame(firstPlayer, secondPlayer);
            System.out.print(resultOfInitialGame.getMessage());
        }

        while (true) {
            String command = Utils.getScanner().nextLine().trim();
            DuelMenuMessages result = duelMenuController.findCommand(command);

            System.out.print(result.getMessage());
        }
    }

}
