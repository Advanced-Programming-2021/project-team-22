package controller;

import model.Board;
import model.Player;
import model.cards.Card;
import model.cards.magiccard.MagicCard;
import model.cards.monstercard.MonsterCard;
import view.SpellCardView;

import java.util.ArrayList;
import java.util.Collections;

public class SpellCardController {
//    this method doesn't handle field spell cards
    public static boolean doSpellCardEffect(Player turnPlayer, Player notTurnPlayer, MagicCard spellCard) {
//        handle every kind of spell cards except field and equip
        String cardName = spellCard.getName();
        switch (cardName) {
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
            case "Mystical space typhoon":
                return doMysticalSpaceTyphoonEffect(turnPlayer, notTurnPlayer);
            case "Ring of defense":
//                TODO: handle it base trap and monster code
                break;
//                it's the only ritual spell card
            case "Advanced Ritual Art":
                return doAdvancedRitualArtEffect();
        }

//        handle equip spell cards
        return chooseMonsterToEquip(turnPlayer, notTurnPlayer, spellCard);
//        any other kind of card doesn't enter to this method
    }


//    handle every kind of spell cards except field and equip
    private static boolean doMonsterRebornEffect(Player turnPlayer, Player notTurnPlayer) {
        SpellCardView.showGraveyardsMonsterCards(turnPlayer, notTurnPlayer);

        int turnPlayerGraveyardMonsterCardsSize = Card.findNumberOfMonsterCards(turnPlayer.getBoard().getGraveyard());
        int notTurnPlayerGraveyardMonsterCardsSize = Card.findNumberOfMonsterCards(notTurnPlayer.getBoard().getGraveyard());
        if (turnPlayerGraveyardMonsterCardsSize + notTurnPlayerGraveyardMonsterCardsSize == 0) return false;

        MonsterCard chosenCard;
        while (true) {
            int cardNumber = getCardNumber();
            chosenCard = findMonsterRebornChosenCard(turnPlayer, notTurnPlayer, cardNumber);
            if (chosenCard != null) break;
            SpellCardView.invalidNumber();
        }

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

    private static MonsterCard findMonsterRebornChosenCard(Player turnPlayer, Player notTurnPlayer, int cardNumber) {
        int count = 1;
        ArrayList<Card> turnPlayerGraveyard = turnPlayer.getBoard().getGraveyard();
        ArrayList<Card> notTurnPlayerGraveyard = notTurnPlayer.getBoard().getGraveyard();

        for (Card card : turnPlayerGraveyard) {
            if (Card.isMonsterCard(card)) {
                if (count == cardNumber) return (MonsterCard) card;
                ++count;
            }
        }
        for (Card card : notTurnPlayerGraveyard) {
            if (Card.isMonsterCard(card)) {
                if (count == cardNumber) return (MonsterCard) card;
                ++count;
            }
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

        Collections.shuffle(cards);
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

        int turnPlayerNumberOfMagicCards = turnPlayer.getBoard().getNumberOfFullPartsOfMagicsZone();
        int notTurnPlayerNumberOfMagicCards = notTurnPlayer.getBoard().getNumberOfFullPartsOfMagicsZone();
        if (turnPlayerNumberOfMagicCards + notTurnPlayerNumberOfMagicCards == 0) return true;

        int numberOfChosenCards;
        while (true) {
            numberOfChosenCards = getNumberOfCardsToChoose();
            if (0 <= numberOfChosenCards && numberOfChosenCards <= 2 &&
                    numberOfChosenCards <= turnPlayerNumberOfMagicCards + notTurnPlayerNumberOfMagicCards) {
                break;
            }
            SpellCardView.invalidNumber();
        }

        for (int i = 0; i < numberOfChosenCards; i++) {
            doMysticalSpaceTyphoonEffect(turnPlayer, notTurnPlayer);
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

    private static boolean doMysticalSpaceTyphoonEffect(Player turnPlayer, Player notTurnPlayer) {
        SpellCardView.showMagicsZonesCards(turnPlayer, notTurnPlayer);
        int turnPlayerNumberOfMagicCards = turnPlayer.getBoard().getNumberOfFullPartsOfMagicsZone();
        int notTurnPlayerNumberOfMagicCards = notTurnPlayer.getBoard().getNumberOfFullPartsOfMagicsZone();
        if (turnPlayerNumberOfMagicCards + notTurnPlayerNumberOfMagicCards == 0) return false;

        while (true) {
            int cardNumber = getCardNumber();
            if (destroyAMagicCardFromMagicZone(turnPlayer, cardNumber, 1) ||
                    destroyAMagicCardFromMagicZone(notTurnPlayer, cardNumber, turnPlayerNumberOfMagicCards + 1))
                break;
            SpellCardView.invalidNumber();
        }

        return true;
    }

//    it's the only ritual spell card
    private static boolean doAdvancedRitualArtEffect() {
//        TODO: handle it based on Iman's code
        return false;
    }


//    handle field spell cards
    public static void handleFieldSpellCardsEffect(Player firstPlayer, Player secondPlayer, MonsterCard monsterCard, boolean doEffect) {
//        doEffect is true --> do the card effect
//        doEffect is false --> undo the card effect
        MagicCard firstPlayerFieldZoneCard = firstPlayer.getBoard().getFieldZone();
        MagicCard secondPlayerFieldZoneCard = secondPlayer.getBoard().getFieldZone();
        Player monsterCardOwner = findMonsterCardOwner(firstPlayer, secondPlayer, monsterCard);
        handleEachFieldSpellCardEffect(monsterCardOwner, monsterCard, firstPlayerFieldZoneCard, doEffect);
        handleEachFieldSpellCardEffect(monsterCardOwner, monsterCard, secondPlayerFieldZoneCard, doEffect);
    }

    private static Player findMonsterCardOwner(Player firstPlayer, Player secondPlayer, MonsterCard monsterCard) {
        if (firstPlayer.getBoard().isCardAvailableInMonstersZone(monsterCard)) return firstPlayer;
        if (secondPlayer.getBoard().isCardAvailableInMonstersZone(monsterCard)) return secondPlayer;

//        this will never happen, just for assurance
        return null;
    }

    private static void handleEachFieldSpellCardEffect(Player monsterCardOwner, MonsterCard monsterCard, MagicCard fieldZoneCard,
                                                       boolean doEffect) {
//        doEffect is true --> do the card effect
//        doEffect is false --> undo the card effect
        if (fieldZoneCard == null || !fieldZoneCard.getCardFaceUp()) return;

        switch (fieldZoneCard.getName()) {
            case "Yami":
                handleYamiEffect(monsterCard, doEffect);
                break;
            case "Forest":
                handleForestEffect(monsterCard, doEffect);
                break;
            case "Closed Forest":
                handleClosedForestEffect(monsterCardOwner, monsterCard, doEffect);
                break;
            case "Umiiruka":
                handleUmiirukaEffect(monsterCard, doEffect);
                break;
        }
    }

    private static void handleYamiEffect(MonsterCard monsterCard, boolean doEffect) {
//        doEffect is true --> do the card effect
//        doEffect is false --> undo the card effect

        String monsterCardType = monsterCard.getMonsterType();
        if (monsterCardType.equals("Fiend") || monsterCardType.equals("Spellcaster")) {
            if (doEffect) {
                monsterCard.increaseAttackPoints(200);
                monsterCard.increaseDefencePoints(200);
            } else {
                monsterCard.decreaseAttackPoints(200);
                monsterCard.decreaseDefencePoints(200);
            }
        } else if (monsterCard.getMonsterType().equals("Fairy")) {
            if (doEffect) {
                monsterCard.decreaseAttackPoints(200);
                monsterCard.decreaseDefencePoints(200);
            } else {
                monsterCard.increaseAttackPoints(200);
                monsterCard.increaseDefencePoints(200);
            }
        }
    }

    private static void handleForestEffect(MonsterCard monsterCard, boolean doEffect) {
//        doEffect is true --> do the card effect
//        doEffect is false --> undo the card effect

        String monsterCardType = monsterCard.getMonsterType();
        if (monsterCardType.equals("Insect") || monsterCardType.equals("Beast") ||
                monsterCardType.equals("Beast-Warrior")) {
            if (doEffect) {
                monsterCard.increaseAttackPoints(200);
                monsterCard.increaseDefencePoints(200);
            } else {
                monsterCard.decreaseAttackPoints(200);
                monsterCard.decreaseDefencePoints(200);
            }
        }
    }

    private static void handleClosedForestEffect(Player monsterCardOwner, MonsterCard monsterCard, boolean doEffect) {
//        doEffect is true --> do the card effect
//        doEffect is false --> undo the card effect

        int graveyardMonsterCardsSize = Card.findNumberOfMonsterCards(monsterCardOwner.getBoard().getGraveyard());
        MonsterCard[] monstersZone = monsterCardOwner.getBoard().getMonstersZone();

        for (int i = 1; i < monstersZone.length; i++) {
            if (monstersZone[i] != null && monstersZone[i].getMonsterType().equals("Beast-Type")) {
                if (doEffect) monsterCard.increaseAttackPoints(100 * graveyardMonsterCardsSize);
                else monsterCard.decreaseAttackPoints(100 * graveyardMonsterCardsSize);
            }
        }

    }

    private static void handleUmiirukaEffect(MonsterCard monsterCard, boolean doEffect) {
//        doEffect is true --> do the card effect
//        doEffect is false --> undo the card effect

        if (monsterCard.getMonsterType().equals("Aqua")) {
            if (doEffect) {
                monsterCard.increaseAttackPoints(500);
                monsterCard.decreaseDefencePoints(400);
            } else {
                monsterCard.decreaseAttackPoints(500);
                monsterCard.increaseDefencePoints(400);
            }
        }
    }


//    handle equip spell cards
    private static boolean chooseMonsterToEquip(Player turnPlayer, Player notTurnPlayer, MagicCard spellCard) {
        SpellCardView.showFaceUpMonsterCards(turnPlayer, notTurnPlayer);

        int turnPlayerFaceUpMonsterCardsNumber = turnPlayer.getBoard().getNumberOfFaceUpMonsterCards();
        int notTurnPlayerFaceUpMonsterCardsNumber = notTurnPlayer.getBoard().getNumberOfFaceUpMonsterCards();
        Board turnPlayerBoard = turnPlayer.getBoard();
        if (turnPlayerFaceUpMonsterCardsNumber + notTurnPlayerFaceUpMonsterCardsNumber == 0 ||
        !turnPlayerBoard.isMagicsZoneFull()) return false;
        if (spellCard.getName().equals("Magnum Shield") && turnPlayerBoard.getNumberOfWarriorMonsterCards() == 0) return false;

        MonsterCard chosenCard;
        while (true) {
            int cardNumber = getCardNumber();
            chosenCard = findFaceUpMonsterCard(turnPlayer, notTurnPlayer, cardNumber);
//            second and third condition assure me that Magnum Shield just equipped Warrior Monster Cards
            if (chosenCard != null &&
                    (!spellCard.getName().equals("Magnum Shield") || chosenCard.getMonsterType().equals("Warrior"))) break;
            SpellCardView.invalidNumber();
        }

        chosenCard.addToEquippedBy(spellCard);
        turnPlayerBoard.addMagicCardToMagicsZone(spellCard);
        return true;
    }

    private static MonsterCard findFaceUpMonsterCard(Player turnPlayer, Player notTurnPlayer, int cardNumber) {
        int count = 1;
        MonsterCard[] turnPlayerFaceUpMonsterCards = turnPlayer.getBoard().getMonstersZone();
        MonsterCard[] notTurnPlayerFaceUpMonsterCards = notTurnPlayer.getBoard().getMonstersZone();

        for (int i = 1; i < turnPlayerFaceUpMonsterCards.length; i++) {
            MonsterCard monsterCard = turnPlayerFaceUpMonsterCards[i];
            if (monsterCard.getCardFaceUp()) {
                if (count == cardNumber) return monsterCard;
                ++count;
            }
        }
        for (int i = 1; i < notTurnPlayerFaceUpMonsterCards.length; i++) {
            MonsterCard monsterCard = notTurnPlayerFaceUpMonsterCards[i];
            if (monsterCard.getCardFaceUp()) {
                if (count == cardNumber) return monsterCard;
                ++count;
            }
        }

        return null;
    }

    public static void handleEquipSpellCardsEffect(Player firstPlayer, Player secondPlayer, MonsterCard monsterCard, boolean doEffect) {
//        doEffect is true --> do the card effect
//        doEffect is false --> undo the card effect

        Player monsterCardOwner = findMonsterCardOwner(firstPlayer, secondPlayer, monsterCard);
        for (MagicCard spellCard : monsterCard.getEquippedBy()) {
            switch (spellCard.getName()) {
                case "Sword of dark destruction":
                    handleSwordOfDarkDestructionEffect(monsterCard, doEffect);
                    break;
                case "Black Pendant":
                    handleBlackPendantEffect(monsterCard, doEffect);
                    break;
                case "United We Stand":
                    handleUnitedWeStandEffect(monsterCardOwner, monsterCard, doEffect);
                    break;
                case "Magnum Shield":
                    handleMagnumShieldEffect(monsterCard, doEffect);
                    break;
            }
        }
    }

    private static void handleSwordOfDarkDestructionEffect(MonsterCard monsterCard, boolean doEffect) {
//        doEffect is true --> do the card effect
//        doEffect is false --> undo the card effect

        String monsterCardType = monsterCard.getMonsterType();
        if (monsterCardType.equals("Fiend") || monsterCardType.equals("Spellcaster")) {
            if (doEffect) {
                monsterCard.increaseAttackPoints(400);
                monsterCard.decreaseDefencePoints(200);
            } else {
                monsterCard.decreaseAttackPoints(400);
                monsterCard.increaseDefencePoints(200);
            }
        }

    }

    private static void handleBlackPendantEffect(MonsterCard monsterCard, boolean doEffect) {
//        doEffect is true --> do the card effect
//        doEffect is false --> undo the card effect

        if (doEffect) monsterCard.increaseAttackPoints(500);
        else monsterCard.decreaseAttackPoints(500);
    }

    private static void handleUnitedWeStandEffect(Player monsterCardOwner, MonsterCard monsterCard, boolean doEffect) {
//        doEffect is true --> do the card effect
//        doEffect is false --> undo the card effect

        int numberOfFaceUpMonsterCards = monsterCardOwner.getBoard().getNumberOfFaceUpMonsterCards();
        if (doEffect) {
            monsterCard.increaseAttackPoints(800 * numberOfFaceUpMonsterCards);
            monsterCard.increaseDefencePoints(800 * numberOfFaceUpMonsterCards);
        } else {
            monsterCard.decreaseAttackPoints(800 * numberOfFaceUpMonsterCards);
            monsterCard.decreaseDefencePoints(800 * numberOfFaceUpMonsterCards);
        }
    }

    private static void handleMagnumShieldEffect(MonsterCard monsterCard, boolean doEffect) {
//        doEffect is true --> do the card effect
//        doEffect is false --> undo the card effect

//        I know that Magnum Shield just equipped Warrior monster cards
        if (monsterCard.isDefensePosition()) {
            if (doEffect) monsterCard.increaseDefencePoints(monsterCard.getAttackPoints());
            else monsterCard.decreaseDefencePoints(monsterCard.getAttackPoints());
        } else {
            if (doEffect) monsterCard.increaseAttackPoints(monsterCard.getDefensePoints());
            else monsterCard.decreaseAttackPoints(monsterCard.getDefensePoints());
        }
    }
}
