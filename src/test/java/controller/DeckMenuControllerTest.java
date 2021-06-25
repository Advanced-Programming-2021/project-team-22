package controller;

import controller.deckmenu.DeckMenuController;
import controller.loginmenu.LoginMenuController;
import controller.shopmenu.ShopMenuController;
import model.Player;
import model.cards.Card;
import model.cards.magiccard.MagicCard;
import model.cards.monstercard.MonsterCard;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

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
    void separateKindOfCards() {
        Player player = Player.getPlayerByUsername("Apple");
        DeckMenuController deckMenuController = new DeckMenuController(player);
        deckMenuController.findCommand("deck create :)");

        deckMenuController.findCommand("deck add-card --card Command Knight --deck :)");
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
    }

    @Test
    void findCommand() {
    }
}