package controller;

import controller.duelmenu.DuelMenuController;
import controller.duelmenu.DuelMenuMessages;
import controller.duelmenu.Phases;
import model.Board;
import model.Player;
import model.cards.CardTypes;
import model.cards.monstercard.MonsterCard;
import model.cards.monstercard.MonsterCardAttributes;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AttackTest extends MenuTest {


    @Test
    public void attackOnOO() {
        Player a = new Player("a", "a", "a");
        Player b = new Player("b", "b", "b");
        a.setBoard(new Board());
        b.setBoard(new Board());
        b.getBoard().getMonstersZone()[1] = new MonsterCard("Test", (short) 4, MonsterCardAttributes.EARTH, "", CardTypes.NORMAL,
                100, 100, "", 0);
        a.getBoard().getMonstersZone()[1] = new MonsterCard("Test", (short) 4, MonsterCardAttributes.EARTH, "", CardTypes.NORMAL,
                200, 100, "", 0);
        b.getBoard().getMonstersZone()[1].setCardFaceUp(true);
        a.getBoard().setSelectedCard(a.getBoard().getMonstersZone()[1]);
        a.getBoard().setMyCardSelected(true);
        DuelMenuController duelMenuController = new DuelMenuController();
        duelMenuController.setPhase(Phases.BATTLE_PHASE);
        duelMenuController.setTurnPlayer(a);
        duelMenuController.setNotTurnPlayer(b);
        Assertions.assertEquals(DuelMenuMessages.OPPONENT_GOT_DAMAGE_IN_ATTACK, duelMenuController.attack("attack 1"));

    }

    @Test
    public void attackOnOOPart2() {
        Player a = new Player("a", "a", "a");
        Player b = new Player("b", "b", "b");
        a.setBoard(new Board());
        b.setBoard(new Board());
        b.getBoard().getMonstersZone()[1] = new MonsterCard("Test", (short) 4, MonsterCardAttributes.EARTH, "", CardTypes.NORMAL,
                100, 100, "", 0);
        a.getBoard().getMonstersZone()[1] = new MonsterCard("Test", (short) 4, MonsterCardAttributes.EARTH, "", CardTypes.NORMAL,
                100, 100, "", 0);
        b.getBoard().getMonstersZone()[1].setCardFaceUp(true);
        a.getBoard().setSelectedCard(a.getBoard().getMonstersZone()[1]);
        a.getBoard().setMyCardSelected(true);
        DuelMenuController duelMenuController = new DuelMenuController();
        duelMenuController.setPhase(Phases.BATTLE_PHASE);
        duelMenuController.setTurnPlayer(a);
        duelMenuController.setNotTurnPlayer(b);
        Assertions.assertEquals(DuelMenuMessages.BOTH_CARDS_GET_DESTROYED, duelMenuController.attack("attack 1"));

    }

    @Test
    public void attackOnOOPart3() {
        Player a = new Player("a", "a", "a");
        Player b = new Player("b", "b", "b");
        a.setBoard(new Board());
        b.setBoard(new Board());
        b.getBoard().getMonstersZone()[1] = new MonsterCard("Test", (short) 4, MonsterCardAttributes.EARTH, "", CardTypes.NORMAL,
                100, 100, "", 0);
        a.getBoard().getMonstersZone()[1] = new MonsterCard("Test", (short) 4, MonsterCardAttributes.EARTH, "", CardTypes.NORMAL,
                10, 100, "", 0);
        b.getBoard().getMonstersZone()[1].setCardFaceUp(true);
        a.getBoard().setSelectedCard(a.getBoard().getMonstersZone()[1]);
        a.getBoard().setMyCardSelected(true);
        DuelMenuController duelMenuController = new DuelMenuController();
        duelMenuController.setPhase(Phases.BATTLE_PHASE);
        duelMenuController.setTurnPlayer(a);
        duelMenuController.setNotTurnPlayer(b);
        Assertions.assertEquals(DuelMenuMessages.ATTACKING_PLAYER_CARD_DESTROYED, duelMenuController.attack("attack 1"));

    }

    @Test
    public void attackOnDOPart() {
        Player a = new Player("a", "a", "a");
        Player b = new Player("b", "b", "b");
        a.setBoard(new Board());
        b.setBoard(new Board());
        b.getBoard().getMonstersZone()[1] = new MonsterCard("Test", (short) 4, MonsterCardAttributes.EARTH, "", CardTypes.NORMAL,
                100, 100, "", 0);
        a.getBoard().getMonstersZone()[1] = new MonsterCard("Test", (short) 4, MonsterCardAttributes.EARTH, "", CardTypes.NORMAL,
                10, 100, "", 0);
        b.getBoard().getMonstersZone()[1].setCardFaceUp(true);
        a.getBoard().setSelectedCard(a.getBoard().getMonstersZone()[1]);
        a.getBoard().setMyCardSelected(true);
        DuelMenuController duelMenuController = new DuelMenuController();
        duelMenuController.setPhase(Phases.BATTLE_PHASE);
        duelMenuController.setTurnPlayer(a);
        duelMenuController.setNotTurnPlayer(b);
        Assertions.assertEquals(DuelMenuMessages.ATTACKING_PLAYER_CARD_DESTROYED, duelMenuController.attack("attack 1"));

    }

    @Test
    public void attackOnDOPart2() {
        Player a = new Player("a", "a", "a");
        Player b = new Player("b", "b", "b");
        a.setBoard(new Board());
        b.setBoard(new Board());
        b.getBoard().getMonstersZone()[1] = new MonsterCard("Test", (short) 4, MonsterCardAttributes.EARTH, "", CardTypes.NORMAL,
                100, 100, "", 0);
        a.getBoard().getMonstersZone()[1] = new MonsterCard("Test", (short) 4, MonsterCardAttributes.EARTH, "", CardTypes.NORMAL,
                100, 100, "", 0);
        b.getBoard().getMonstersZone()[1].setCardFaceUp(true);
        b.getBoard().getMonstersZone()[1].setDefensePosition(true);
        a.getBoard().setSelectedCard(a.getBoard().getMonstersZone()[1]);
        a.getBoard().setMyCardSelected(true);
        DuelMenuController duelMenuController = new DuelMenuController();
        duelMenuController.setPhase(Phases.BATTLE_PHASE);
        duelMenuController.setTurnPlayer(a);
        duelMenuController.setNotTurnPlayer(b);
        Assertions.assertEquals(DuelMenuMessages.NO_CARD_DESTROYED, duelMenuController.attack("attack 1"));

    }

    @Test
    public void attackOnDOPart3() {
        Player a = new Player("a", "a", "a");
        Player b = new Player("b", "b", "b");
        a.setBoard(new Board());
        b.setBoard(new Board());
        b.getBoard().getMonstersZone()[1] = new MonsterCard("Test", (short) 4, MonsterCardAttributes.EARTH, "", CardTypes.NORMAL,
                100, 100, "", 0);
        a.getBoard().getMonstersZone()[1] = new MonsterCard("Test", (short) 4, MonsterCardAttributes.EARTH, "", CardTypes.NORMAL,
                200, 100, "", 0);
        b.getBoard().getMonstersZone()[1].setCardFaceUp(true);
        b.getBoard().getMonstersZone()[1].setDefensePosition(true);
        a.getBoard().setSelectedCard(a.getBoard().getMonstersZone()[1]);
        a.getBoard().setMyCardSelected(true);
        DuelMenuController duelMenuController = new DuelMenuController();
        duelMenuController.setPhase(Phases.BATTLE_PHASE);
        duelMenuController.setTurnPlayer(a);
        duelMenuController.setNotTurnPlayer(b);
        Assertions.assertEquals(DuelMenuMessages.DEFENSE_POSITION_MONSTER_DESTROYED, duelMenuController.attack("attack 1"));

    }
    @Test
    public void attackOnKnight() {
        Player a = new Player("a", "a", "a");
        Player b = new Player("b", "b", "b");
        a.setBoard(new Board());
        b.setBoard(new Board());
        b.getBoard().getMonstersZone()[1] = new MonsterCard("Command knight", (short) 4, MonsterCardAttributes.EARTH, "", CardTypes.NORMAL,
                100, 100, "", 0);
        a.getBoard().getMonstersZone()[1] = new MonsterCard("Test", (short) 4, MonsterCardAttributes.EARTH, "", CardTypes.NORMAL,
                200, 100, "", 0);
        b.getBoard().getMonstersZone()[1].setCardFaceUp(true);
        a.getBoard().setSelectedCard(a.getBoard().getMonstersZone()[1]);
        a.getBoard().setMyCardSelected(true);
        DuelMenuController duelMenuController = new DuelMenuController();
        duelMenuController.setPhase(Phases.BATTLE_PHASE);
        duelMenuController.setTurnPlayer(a);
        duelMenuController.setNotTurnPlayer(b);
        Assertions.assertEquals(DuelMenuMessages.YOU_CANT_ATTACK_TO_THIS_CARD, duelMenuController.attack("attack 1"));

    }

    @Test
    public void attackOnYomi() {
        Player a = new Player("a", "a", "a");
        Player b = new Player("b", "b", "b");
        a.setBoard(new Board());
        b.setBoard(new Board());
        b.getBoard().getMonstersZone()[1] = new MonsterCard("Yomi Ship", (short) 4, MonsterCardAttributes.EARTH, "", CardTypes.NORMAL,
                100, 100, "", 0);
        a.getBoard().getMonstersZone()[1] = new MonsterCard("Test", (short) 4, MonsterCardAttributes.EARTH, "", CardTypes.NORMAL,
                200, 100, "", 0);
        b.getBoard().getMonstersZone()[1].setCardFaceUp(true);
        b.getBoard().getMonstersZone()[1].setDefensePosition(false);

        a.getBoard().setSelectedCard(a.getBoard().getMonstersZone()[1]);
        a.getBoard().setMyCardSelected(true);
        DuelMenuController duelMenuController = new DuelMenuController();
        duelMenuController.setPhase(Phases.BATTLE_PHASE);
        duelMenuController.setTurnPlayer(a);
        duelMenuController.setNotTurnPlayer(b);
        duelMenuController.attack("attack 1");
        Assertions.assertEquals("Test", a.getBoard().getGraveyard().get(0).getName());

    }
}
