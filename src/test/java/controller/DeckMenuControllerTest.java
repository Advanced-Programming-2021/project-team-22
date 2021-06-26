package controller;

import controller.deckmenu.DeckMenuController;
import controller.deckmenu.DeckMenuMessages;
import controller.loginmenu.LoginMenuController;
import controller.shopmenu.ShopMenuController;
import model.Player;
import model.cards.Card;
import model.cards.magiccard.MagicCard;
import model.cards.monstercard.MonsterCard;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

class DeckMenuControllerTest extends MenuTest {

    @BeforeAll
    static void preparePlayer() {
        LoginMenuController.findCommand("user create --username Apple --P :) --nickname Great");
        Player player = Player.getPlayerByUsername("Apple");

        int numberOfCards = 0;
        ShopMenuController shopMenuController = new ShopMenuController(player);
        shopMenuController.findCommand("increase --M 1000000");
        for (String cardName : Card.getAllCards().keySet()) {
            shopMenuController.findCommand("shop buy " + cardName);
            ++numberOfCards;
            if (numberOfCards == 75) break;
        }
    }

    @Test
    void findCommandTestSingleCommands() {
        DeckMenuController deckMenuMessages = new DeckMenuController(Player.getPlayerByUsername("John"));

        DeckMenuMessages result = deckMenuMessages.findCommand(":)");
        Assertions.assertEquals(DeckMenuMessages.INVALID_COMMAND, result);
    }

    @Test
    void separateKindOfCards() {
        Player player = Player.getPlayerByUsername("Apple");
        DeckMenuController deckMenuController = new DeckMenuController(player);
        deckMenuController.findCommand("deck create :)");

//        for main deck
        deckMenuController.findCommand("deck add-card --deck :) --card Command Knight");
        deckMenuController.findCommand("deck add-card --card Battle OX --deck :)");
        deckMenuController.findCommand("deck add-card --card Monster Reborn --deck :)");
        deckMenuController.findCommand("deck add-card --card Terraforming --deck :)");
        ArrayList<MonsterCard> actualMonsterCards = new ArrayList<>();
        ArrayList<MagicCard> actualMagicCards = new ArrayList<>();

        ArrayList<MonsterCard> expectedMonsterCards = new ArrayList<>();
        expectedMonsterCards.add(new MonsterCard((MonsterCard) Card.getCardByName("Battle OX")));
        expectedMonsterCards.add(new MonsterCard((MonsterCard) Card.getCardByName("Command Knight")));

        ArrayList<MagicCard> expectedMagicCards = new ArrayList<>();
        expectedMagicCards.add(new MagicCard((MagicCard) Card.getCardByName("Monster Reborn")));
        expectedMagicCards.add(new MagicCard((MagicCard) Card.getCardByName("Terraforming")));

        DeckMenuController.separateKindOfCards(player.getDeckByName(":)"), true, actualMonsterCards, actualMagicCards);

        Assertions.assertEquals(expectedMonsterCards.size(), actualMonsterCards.size());
        for (int i = 0; i < expectedMonsterCards.size(); i++) {
            Assertions.assertEquals(expectedMonsterCards.get(i).getName(), actualMonsterCards.get(i).getName());
        }

        Assertions.assertEquals(expectedMagicCards.size(), expectedMagicCards.size());
        for (int i = 0; i < expectedMagicCards.size(); i++) {
            Assertions.assertEquals(expectedMagicCards.get(i).getName(), actualMagicCards.get(i).getName());
        }

//        for side deck
        deckMenuController.findCommand("deck add-card --card Axe Raider --deck :) --side");
        deckMenuController.findCommand("deck add-card --deck :) --card Horn Imp --side");
        deckMenuController.findCommand("deck add-card --card Pot of Greed --deck :) --side");
        deckMenuController.findCommand("deck add-card --side --card Raigeki --deck :)");
        actualMonsterCards.clear();
        actualMagicCards.clear();

        expectedMonsterCards.clear();
        expectedMonsterCards.add(new MonsterCard((MonsterCard) Card.getCardByName("Axe Raider")));
        expectedMonsterCards.add(new MonsterCard((MonsterCard) Card.getCardByName("Horn Imp")));

        expectedMagicCards.clear();
        expectedMagicCards.add(new MagicCard((MagicCard) Card.getCardByName("Pot of Greed")));
        expectedMagicCards.add(new MagicCard((MagicCard) Card.getCardByName("Raigeki")));

        DeckMenuController.separateKindOfCards(player.getDeckByName(":)"), false, actualMonsterCards, actualMagicCards);

        Assertions.assertEquals(expectedMonsterCards.size(), actualMonsterCards.size());
        for (int i = 0; i < expectedMonsterCards.size(); i++) {
            Assertions.assertEquals(expectedMonsterCards.get(i).getName(), actualMonsterCards.get(i).getName());
        }

        Assertions.assertEquals(expectedMagicCards.size(), expectedMagicCards.size());
        for (int i = 0; i < expectedMagicCards.size(); i++) {
            Assertions.assertEquals(expectedMagicCards.get(i).getName(), actualMagicCards.get(i).getName());
        }


        deckMenuController.findCommand("deck delete :)");
    }

    @Test
    void findCommandEnterAMenuMethod() {
        DeckMenuController deckMenuController = new DeckMenuController(Player.getPlayerByUsername("John"));

        DeckMenuMessages result = deckMenuController.findCommand("menu enter Login Menu ");
        Assertions.assertEquals(DeckMenuMessages.INVALID_COMMAND, result);

        result = deckMenuController.findCommand("menu enter LOGIN Menu");
        Assertions.assertEquals(DeckMenuMessages.INVALID_NAVIGATION, result);
    }

    @Test
    void findCommandShowCardMethod() {
        DeckMenuController deckMenuController = new DeckMenuController(Player.getPlayerByUsername("Apple"));

        ByteArrayOutputStream outContent = Utils.setByteArrayOutputStream();
        DeckMenuMessages result = deckMenuController.findCommand("card show Battle OX");
        Assertions.assertEquals(DeckMenuMessages.EMPTY, result);

        String expectedResult = "Name: Battle OX\n" +
                "Level: 4\n" +
                "Type: Beast-Warrior\n" +
                "ATK: 1700\n" +
                "DEF: 1000\n" +
                "Description: A monster with tremendous power, it destroys enemies with a swing of its axe.\n";

        Assertions.assertEquals(expectedResult, outContent.toString());
    }

    @Test
    void findCommandCreateDeckMethod() {
        DeckMenuController deckMenuController = new DeckMenuController(Player.getPlayerByUsername("Apple"));

        DeckMenuMessages result = deckMenuController.findCommand("deck create 1");
        Assertions.assertEquals(DeckMenuMessages.DECK_CREATED, result);

        result = deckMenuController.findCommand("deck create 1");
        Assertions.assertEquals(DeckMenuMessages.AVAILABLE_DECK, result);

        deckMenuController.findCommand("deck delete 1");
    }

    @Test
    void findCommandDeleteDeckMethod() {
        DeckMenuController deckMenuController = new DeckMenuController(Player.getPlayerByUsername("Apple"));

        DeckMenuMessages result = deckMenuController.findCommand("deck delete 1");
        Assertions.assertEquals(DeckMenuMessages.UNAVAILABLE_DECK, result);

        deckMenuController.findCommand("deck create 1");
        result = deckMenuController.findCommand("deck delete 1");
        Assertions.assertEquals(DeckMenuMessages.DECK_DELETED, result);
    }

    @Test
    void findCommandActivateADeckMethod() {
        DeckMenuController deckMenuController = new DeckMenuController(Player.getPlayerByUsername("Apple"));

        DeckMenuMessages result = deckMenuController.findCommand("deck set-activate 1");
        Assertions.assertEquals(DeckMenuMessages.UNAVAILABLE_DECK, result);

        deckMenuController.findCommand("deck create 1");
        result = deckMenuController.findCommand("deck set-activate 1");
        Assertions.assertEquals(DeckMenuMessages.DECK_ACTIVATED, result);

        deckMenuController.findCommand("deck delete 1");
    }

    @Test
    void findCommandAddCardMethod() {
        Player player = Player.getPlayerByUsername("Apple");
        DeckMenuController deckMenuController = new DeckMenuController(player);

        DeckMenuMessages result = deckMenuController.findCommand("deck add-card A");
        Assertions.assertEquals(DeckMenuMessages.INVALID_COMMAND, result);

        result = deckMenuController.findCommand("deck add-card --card A --deck 1");
        Assertions.assertEquals(DeckMenuMessages.UNAVAILABLE_CARD, result);

        result = deckMenuController.findCommand("deck add-card --card Battle OX --deck 1");
        Assertions.assertEquals(DeckMenuMessages.UNAVAILABLE_DECK, result);

        ShopMenuController shopMenuController = new ShopMenuController(player);
        shopMenuController.findCommand("shop buy Battle OX");
        shopMenuController.findCommand("shop buy Battle OX");
        shopMenuController.findCommand("shop buy Battle OX");

        deckMenuController.findCommand("deck create 1");
        deckMenuController.findCommand("deck add-card --side --deck 1 --card Battle OX");
        deckMenuController.findCommand("deck add-card --card Battle OX --deck 1");
        deckMenuController.findCommand("deck add-card --card Battle OX --side --deck 1");
        result = deckMenuController.findCommand("deck add-card --deck 1 --side --card Battle OX");
        Assertions.assertEquals(DeckMenuMessages.THREE_CARDS_AVAILABLE, result);

        shopMenuController.findCommand("shop buy Monster Reborn");
        deckMenuController.findCommand("deck add-card --side --deck 1 --card Monster Reborn");
        result = deckMenuController.findCommand("deck add-card --side --deck 1 --card Monster Reborn");
        Assertions.assertEquals(DeckMenuMessages.ONE_CARD_AVAILABLE, result);

        deckMenuController.findCommand("deck delete 1");
    }

    @Test
    void findCommandRemoveCardMethod() {
        Player player = Player.getPlayerByUsername("Apple");
        DeckMenuController deckMenuController = new DeckMenuController(player);

        DeckMenuMessages result = deckMenuController.findCommand("deck rm-card --card Battle OX --deck 1");
        Assertions.assertEquals(DeckMenuMessages.UNAVAILABLE_DECK, result);

        deckMenuController.findCommand("deck create 1");
        result = deckMenuController.findCommand("deck rm-card --card A --deck 1");
        Assertions.assertEquals(DeckMenuMessages.UNAVAILABLE_CARD_IN_MAIN_DECK, result);

        result = deckMenuController.findCommand("deck rm-card --card A --deck 1 --side");
        Assertions.assertEquals(DeckMenuMessages.UNAVAILABLE_CARD_IN_SIDE_DECK, result);

        deckMenuController.findCommand("deck add-card --card Battle OX --side --deck 1");
        result = deckMenuController.findCommand("deck rm-card --card Battle OX --deck 1 --side");
        Assertions.assertEquals(DeckMenuMessages.CARD_REMOVED, result);

        deckMenuController.findCommand("deck delete 1");
    }

    @Test
    void findCommandShowADeckMethod() {
        DeckMenuController deckMenuController = new DeckMenuController(Player.getPlayerByUsername("Apple"));

        DeckMenuMessages result = deckMenuController.findCommand("deck show --deck-name 1");
        Assertions.assertEquals(DeckMenuMessages.UNAVAILABLE_DECK, result);

        ByteArrayOutputStream outContent = Utils.setByteArrayOutputStream();
        deckMenuController.findCommand("deck create 1");
        result = deckMenuController.findCommand("deck show --deck-name 1");
        Assertions.assertEquals(DeckMenuMessages.EMPTY, result);

        String expectedResult = "Deck: 1\n" +
                "Main deck\n" +
                "Monsters:\n" +
                "Spell and Traps:\n";
        Assertions.assertEquals(expectedResult, outContent.toString());

        result = deckMenuController.findCommand("deck show --deck-name 1 --side");
        Assertions.assertEquals(DeckMenuMessages.EMPTY, result);

        result = deckMenuController.findCommand("deck show --side --deck-name 1");
        Assertions.assertEquals(DeckMenuMessages.EMPTY, result);

        result = deckMenuController.findCommand("deck show --side");
        Assertions.assertEquals(DeckMenuMessages.INVALID_COMMAND, result);

        deckMenuController.findCommand("deck delete 1");
    }
}