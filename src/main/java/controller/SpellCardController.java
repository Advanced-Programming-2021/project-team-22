package controller;

import model.Board;
import model.Player;
import model.cards.Card;
import model.cards.magiccard.MagicCard;
import model.cards.monstercard.MonsterCard;
import view.SpellCardView;

import java.util.ArrayList;

public class SpellCardController {
    public static boolean handleSpellCardEffect(Player turnPlayer, Player notTurnPlayer, MagicCard magicCard) {
        String name = magicCard.getName();
        switch (name) {
            case "Monster Reborn":
                return doMonsterRebornEffect(turnPlayer, notTurnPlayer);
            case "Terraforming":
                return doTerraformingEffect(turnPlayer);
            case "Pot of Greed":
                return doPotOfGreedEffect(turnPlayer);
            case "Raigeki":
                return doRaigekiEffect(notTurnPlayer);
            case "Change of Heart":
                return doChangeOfHeartEffect();
            case "Harpie's Feather Duster":
                return doHarpieFeatherDusterEffect(notTurnPlayer);
            case "Swords of Revealing Light":
                return doSwordsOfRevealingLight();
            case "Dark Hole":
                return doDarkHoleEffect(turnPlayer, notTurnPlayer);
            case "Supply Squad":
//                TODO: i should know how turn changes, then handle it --> it shouldn't handle here
                break;
            case "Spell Absorption":
                return true;
            case "Messenger of peace":
//                TODO: handle it base on Parsa code
                break;
            case "Twin Twisters":
                return doTwinTwistersEffect(turnPlayer, notTurnPlayer);
        }

        return false;
    }

    private static boolean doMonsterRebornEffect(Player turnPlayer, Player notTurnPlayer) {
        SpellCardView.showGraveyardsMonsterCards(turnPlayer, notTurnPlayer);
        int turnPlayerGraveyardMonsterCardsSize = Card.findNumberOfMonsterCards(turnPlayer.getBoard().getGraveyard());
        int notTurnPlayerGraveyardMonsterCardsSize = Card.findNumberOfMonsterCards(notTurnPlayer.getBoard().getGraveyard());
        if (turnPlayerGraveyardMonsterCardsSize + notTurnPlayerGraveyardMonsterCardsSize == 0) return false;

        Card chosenCard;
        while (true) {
            int cardNumber = getCardNumber();
            if (1 <= cardNumber && cardNumber <= turnPlayerGraveyardMonsterCardsSize) {
                chosenCard = findMonsterRebornChosenCard(turnPlayer, cardNumber);
                break;
            } else if (turnPlayerGraveyardMonsterCardsSize < cardNumber && cardNumber <= turnPlayerGraveyardMonsterCardsSize + notTurnPlayerGraveyardMonsterCardsSize + 1) {
                chosenCard = findMonsterRebornChosenCard(turnPlayer, cardNumber);
                break;
            }
            SpellCardView.invalidNumber();
        }

        MonsterCard monsterCard = (MonsterCard) chosenCard;
//        TODO: how to handle special summon
        return true;
    }

    private static int getCardNumber() {
        while (true) {
            try {
                return Integer.parseInt(SpellCardView.findCardNumber());
            } catch (Exception exception) {
                SpellCardView.invalidNumber();
            }
        }
    }

    private static Card findMonsterRebornChosenCard(Player turnPlayer, int cardNumber) {
        int count = 0;
        for (int i = 0; i <= turnPlayer.getBoard().getGraveyard().size(); i++) {
            if (Card.isMonsterCard(turnPlayer.getBoard().getGraveyard().get(i))) ++count;
            if (count == cardNumber) return turnPlayer.getBoard().getGraveyard().get(i);
        }
        return null;
    }

    private static boolean doTerraformingEffect(Player player) {
//        TODO: do Iman handle with main cards?
        ArrayList<Card> cards = player.getBoard().getDeck().getMainCards();
        ArrayList<MagicCard> fieldSpellCards = findFieldSpellCards(cards);

        SpellCardView.showFieldSpellCards(fieldSpellCards);
        if (fieldSpellCards.size() == 0) return false;

        MagicCard chosenFieldSpellCard;
        while (true) {
            int cardNumber = getCardNumber();
            if (1 <= cardNumber && cardNumber <= fieldSpellCards.size()) {
                chosenFieldSpellCard = fieldSpellCards.get(cardNumber - 1);
                player.getBoard().getCardsInHand().add(chosenFieldSpellCard);
                cards.remove(chosenFieldSpellCard);
                break;
            }
            SpellCardView.invalidNumber();
        }

        return true;
    }

    private static ArrayList<MagicCard> findFieldSpellCards(ArrayList<Card> cards) {
//        create array list of field spell cards
        ArrayList<MagicCard> fieldSpellCards = new ArrayList<>();
        for (Card card : cards) {
            if (!Card.isMonsterCard(card))  {
                MagicCard magicCard = (MagicCard) card;
                if (magicCard.getIcon().equals("Field")) fieldSpellCards.add(magicCard);
            }
        }
        return fieldSpellCards;
    }

    private static boolean doPotOfGreedEffect(Player player) {
        Board board = player.getBoard();
        ArrayList<Card> deckCards = board.getDeck().getMainCards();
        if (deckCards.size() < 2) return false;

        Card firstCard = deckCards.get(deckCards.size() - 1);
        deckCards.remove(deckCards.size() - 1);
        Card secondCard = deckCards.get(deckCards.size() - 1);
        deckCards.remove(deckCards.size() - 1);
        board.getCardsInHand().add(firstCard);
        board.getCardsInHand().add(secondCard);

        return true;
    }

    private static boolean doRaigekiEffect(Player player) {
        MonsterCard[] monstersZone = player.getBoard().getMonstersZone();
        if (isCardArrayNull(monstersZone)) return false;
        emptyAZone(player, monstersZone);
        return true;
    }

    private static boolean isCardArrayNull(Card[] cards) {
        for (Card card : cards) {
            if (card != null) return false;
        }
        return true;
    }

    private static void emptyAZone(Player player, Card[] cards) {
        for (int i = 0; i < cards.length; ++i) {
            if (cards[i] != null) {
                player.getBoard().getGraveyard().add(cards[i]);
                cards[i] = null;
            }
        }
    }

    private static boolean doChangeOfHeartEffect() {
//        TODO: handle it!
        return true;
    }

    private static boolean doHarpieFeatherDusterEffect(Player player) {
        MagicCard[] magicsZone = player.getBoard().getMagicsZone();
        if (isCardArrayNull(magicsZone)) return false;
        emptyAZone(player, magicsZone);
        return true;
    }

    private static boolean doSwordsOfRevealingLight() {
//        TODO: handle it!
        return true;
    }

    private static boolean doDarkHoleEffect(Player turnPlayer, Player notTurnPlayer) {
        return doRaigekiEffect(turnPlayer) || doRaigekiEffect(notTurnPlayer);
    }

    public static void doSpellAbsorptionEffect(Player player) {
        if (player.getBoard().isCardFaceUp("Spell Absorption")) player.increaseLifePoint(500);
    }

    private static boolean doTwinTwistersEffect(Player turnPlayer, Player notTurnPlayer) {
        SpellCardView.showCardsInHand(turnPlayer);
        ArrayList<Card> cardsInHand = turnPlayer.getBoard().getCardsInHand();
        if (cardsInHand.size() == 0) return false;

        while (true) {
            int cardNumber = getCardNumber();
            if (1 <= cardNumber && cardNumber <= cardsInHand.size()) {
                turnPlayer.getBoard().getGraveyard().add(cardsInHand.get(cardNumber - 1));
                cardsInHand.remove(cardNumber - 1);
                break;
            }
            SpellCardView.invalidNumber();
        }

        SpellCardView.showMagicsZonesCards(turnPlayer, notTurnPlayer);

        int turnPlayerNumberOfMagicCards = turnPlayer.getBoard().findNumberOfFullPartsOfMagicsZone();
        int notTurnPlayerNumberOfMagicCards = notTurnPlayer.getBoard().findNumberOfFullPartsOfMagicsZone();
        int numberOfChosenCards;
        while (true) {
            numberOfChosenCards = getNumberOfCardsToChoose();
            if (0 <= numberOfChosenCards && numberOfChosenCards <= turnPlayerNumberOfMagicCards + notTurnPlayerNumberOfMagicCards) {
                break;
            }
            SpellCardView.invalidNumber();
        }

        for (int i = 0; i < numberOfChosenCards; i++) {

            while (true) {
                int cardNumber = getCardNumber();
                if (destroyAMagicCardFromMagicZone(turnPlayer, cardNumber, 1) ||
                        destroyAMagicCardFromMagicZone(notTurnPlayer, cardNumber, turnPlayerNumberOfMagicCards + 1))
                    break;
                SpellCardView.invalidNumber();
            }

        }

        return true;
    }

    private static int getNumberOfCardsToChoose() {
        while (true) {
            try {
                return Integer.parseInt(SpellCardView.findNumberOfCardsToChoose());
            } catch (Exception exception) {
                SpellCardView.invalidNumber();
            }
        }
    }

    private static boolean destroyAMagicCardFromMagicZone(Player player, int cardNumber, int startNumber) {
        MagicCard[] playerMagicsZone = player.getBoard().getMagicsZone();
        for (int i = 1; i < playerMagicsZone.length; i++) {
            if (playerMagicsZone[i] != null) {
                if (startNumber == cardNumber) {
                    player.getBoard().getGraveyard().add(playerMagicsZone[i]);
                    playerMagicsZone[i] = null;
                    return true;
                }
                ++startNumber;
            }
        }

        return false;
    }


}
