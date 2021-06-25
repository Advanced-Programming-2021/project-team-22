package controller.duelmenu;

import controller.Utils;
import model.Board;
import model.Deck;
import model.Player;
import model.cards.Card;
import model.cards.CardTypes;
import model.cards.magiccard.MagicCard;
import model.cards.monstercard.MonsterCard;
import view.DuelMenuView;

import java.util.Collections;
import java.util.regex.Matcher;

public class DuelMenuController {
    private Player turnPlayer;
    private Player notTurnPlayer;
    private Phases phase;
    private boolean isAITurn;

    public void setTurnPlayer(Player turnPlayer) {
        this.turnPlayer = turnPlayer;
    }

    public void setNotTurnPlayer(Player notTurnPlayer) {
        this.notTurnPlayer = notTurnPlayer;
    }

    public Player getTurnPlayer() {
        return turnPlayer;
    }

    public Player getNotTurnPlayer() {
        return notTurnPlayer;
    }

    public Phases getPhase() {
        return phase;
    }

    public void setPhase(Phases phase) {
        this.phase = phase;
    }

    public static String specifyTurnPlayer(Player firstPlayer, Player secondPlayer) {
        String firstPlayerChoiceInString = DuelMenuView.findChooseOfPlayerInMiniGame(firstPlayer);
        if (!isMiniGameChoiceValid(firstPlayerChoiceInString)) return "invalid choice";
        MiniGameChoices firstPlayerChoice = MiniGameChoices.valueOf(firstPlayerChoiceInString.toUpperCase());

        String secondPlayerChoiceInString = DuelMenuView.findChooseOfPlayerInMiniGame(secondPlayer);
        if (!isMiniGameChoiceValid(secondPlayerChoiceInString)) return "invalid choice";
        MiniGameChoices secondPlayerChoice = MiniGameChoices.valueOf(secondPlayerChoiceInString.toUpperCase());

        if (firstPlayerChoice.equals(secondPlayerChoice)) return "draw";

        return findMiniGameWinner(firstPlayer, secondPlayer, firstPlayerChoice, secondPlayerChoice);
    }

    private static boolean isMiniGameChoiceValid(String choice) {
        try {
            MiniGameChoices.valueOf(choice.toUpperCase());
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    private static String findMiniGameWinner(Player firstPlayer, Player secondPlayer,
                                             MiniGameChoices firstPlayerChoice, MiniGameChoices secondPlayerChoice) {
        switch (firstPlayerChoice) {
            case STONE:
                if (secondPlayerChoice.equals(MiniGameChoices.PAPER)) {
                    DuelMenuMessages.setShowTurnPlayer(secondPlayer);
                    return secondPlayer.getUsername();
                } else {
                    DuelMenuMessages.setShowTurnPlayer(firstPlayer);
                    return firstPlayer.getUsername();
                }
            case PAPER:
                if (secondPlayerChoice.equals(MiniGameChoices.SCISSOR)) {
                    DuelMenuMessages.setShowTurnPlayer(secondPlayer);
                    return secondPlayer.getUsername();
                } else {
                    DuelMenuMessages.setShowTurnPlayer(firstPlayer);
                    return firstPlayer.getUsername();
                }
            case SCISSOR:
                if (secondPlayerChoice.equals(MiniGameChoices.STONE)) {
                    DuelMenuMessages.setShowTurnPlayer(secondPlayer);
                    return secondPlayer.getUsername();
                } else {
                    DuelMenuMessages.setShowTurnPlayer(firstPlayer);
                    return firstPlayer.getUsername();
                }
        }

//        this is never happen
        return null;
    }

    public DuelMenuMessages initialGame(Player firstPlayer, Player secondPlayer) {
//        TODO: handle it for ai
        String result = specifyTurnPlayer(firstPlayer, secondPlayer);
        if (result.equals("invalid choice")) return DuelMenuMessages.MINI_GAME_INVALID_CHOICE;
        else if (result.equals("draw")) return DuelMenuMessages.DRAW;
        else turnPlayer = Player.getPlayerByUsername(result);

        if (turnPlayer.equals(firstPlayer)) notTurnPlayer = secondPlayer;
        else notTurnPlayer = firstPlayer;

        turnPlayer.createBoard();
        notTurnPlayer.createBoard();

        turnPlayer.getBoard().setDeck(new Deck(turnPlayer.getActivatedDeck()));
        notTurnPlayer.getBoard().setDeck(new Deck(notTurnPlayer.getActivatedDeck()));

        Collections.shuffle(turnPlayer.getActivatedDeck().getMainCards());
        Collections.shuffle(notTurnPlayer.getActivatedDeck().getMainCards());

        return DuelMenuMessages.SHOW_TURN_PLAYER;
    }

    public DuelMenuMessages findCommand(String command) {
//        TODO: handle menu commands --> menu exit and ...
        if (command.startsWith("decrease ")) return cheatCodeDecreaseOpponentLifePont(command, notTurnPlayer);
        else if (command.startsWith("increase ")) return cheatCodeIncreaseLifePoint(command, turnPlayer);
        else if (command.startsWith("duel set-winner ")) return cheatCodeSetWinner(command);
        else if (command.startsWith("select hand force ")) return cheatCodeAddCardToHand(command, turnPlayer);
        else if (command.equals("select -d")) return deselectCard();
        else if (command.startsWith("select ")) return checkSelectCard(command);
        else if (command.equals("summon")) return summonMonster();
        else if (command.equals("set")) return checkSetACard();
        else if (command.startsWith("set --position"));// return checkChangePosition(command);
        else if (command.equals("flip-summon")) ;//return checkFlipSummon();
        else if (command.equals("attack direct")) return directAttack();
        else if (command.startsWith("attack")) return attack(command);
        else if (command.equals("activate effect")) return checkActiveASpellCard();
        else if (command.equals("activate effect trap")) return checkActiveATrapCard();
        else if (command.equals("show graveyard")) {
            DuelMenuView.showGraveyard(turnPlayer.getBoard());
            return DuelMenuMessages.EMPTY;

        } else if (command.equals("back")) ;//checkBack();
        else if (command.equals("card show --selected")) return showSelectedCard();
        else if (command.startsWith("card show ")) {
            Utils.showCard(command.substring(10));
            return DuelMenuMessages.EMPTY;

        } else if (command.equals("cancel")) ;//cancelCommand();
        else if (command.equals("surrender")) /*TODO*/;

//        TODO: handle cheat/debug commands

        return DuelMenuMessages.INVALID_COMMAND;
    }

    private DuelMenuMessages cheatCodeDecreaseOpponentLifePont(String command, Player player) {
        Matcher matcher = Utils.getMatcher(DuelMenuRegexes.CHEAT_DECREASE_OPPONENT_LIFE_POINT.getRegex(), command);
        if (matcher.find()) {
            player.decreaseLifePoint(Integer.parseInt(matcher.group(1)));
            return DuelMenuMessages.EMPTY;
        } else return DuelMenuMessages.INVALID_COMMAND_CHEAT_CODE;

    }

    private DuelMenuMessages cheatCodeIncreaseLifePoint(String command, Player player) {
        Matcher matcher = Utils.getMatcher(DuelMenuRegexes.CHEAT_INCREASE_LIFE_POINT.getRegex(), command);
        if (matcher.find()) {
            player.increaseLifePoint(Integer.parseInt(matcher.group(1)));
            return DuelMenuMessages.EMPTY;
        } else return DuelMenuMessages.INVALID_COMMAND_CHEAT_CODE;
    }

    private DuelMenuMessages cheatCodeSetWinner(String command) {
        Matcher matcher = Utils.getMatcher(DuelMenuRegexes.CHEAT_SET_WINNER.getRegex(), command);
        if (matcher.find()) {
            String nickname = matcher.group(1);
            if (turnPlayer.getNickname().equals(nickname)){
                notTurnPlayer.setLifePoint(0);
            }
            else if (notTurnPlayer.getNickname().equals(nickname)){
                turnPlayer.setLifePoint(0);
            }
            else return DuelMenuMessages.WRONG_NICKNAME_CHEAT_CODE;
        } else return DuelMenuMessages.INVALID_COMMAND_CHEAT_CODE;

        return DuelMenuMessages.EMPTY;
    }

    private DuelMenuMessages cheatCodeAddCardToHand(String command, Player player) {
        Matcher matcher = Utils.getMatcher(DuelMenuRegexes.CHEAT_ADD_CARD_TO_HAND.getRegex(), command);
        if (matcher.find()) {
            String cardName = matcher.group(1);
            if (Card.getCardByName(cardName) == null) return DuelMenuMessages.UNAVAILABLE_CARD_CHEAT_CODE;
            else {

                Card card = Card.getCardByName(cardName);
                if (Card.isMonsterCard(card)) {
                    player.getBoard().getCardsInHand().add(new MonsterCard((MonsterCard) card));
                } else {
                    player.getBoard().getCardsInHand().add(new MagicCard((MagicCard) card));
                }
                return DuelMenuMessages.EMPTY;

            }
        } else return DuelMenuMessages.INVALID_COMMAND_CHEAT_CODE;
    }

    private DuelMenuMessages checkSelectCard(String command) {
//        TODO: handle --> if there isn't any card in main deck, he/she loses
//        TODO: maybe clean it more
        Matcher matcher;
        if ( (matcher = Utils.getMatcher(DuelMenuRegexes.SELECT_MONSTER_ZONE.getRegex(), command)).find() ) {
            if (!isSelectionValid(matcher)) return DuelMenuMessages.INVALID_SELECTION;
            else if (!isCardAvailableInMonstersZone(matcher, turnPlayer)) return DuelMenuMessages.CARD_NOT_FOUND;
            selectCardFromMonstersZone(matcher, true);

        } else if ( (matcher = Utils.getMatcher(DuelMenuRegexes.SELECT_MAGIC_ZONE.getRegex(), command)).find() ) {
            if (!isSelectionValid(matcher)) return DuelMenuMessages.INVALID_SELECTION;
            else if (!isCardAvailableInMagicsZone(matcher, turnPlayer)) return DuelMenuMessages.CARD_NOT_FOUND;
            selectCardFromMagicsZone(matcher, true);

        } else if ( (matcher = Utils.getMatcher(DuelMenuRegexes.SELECT_OPPONENT_MONSTER_ZONE_MONSTER_PATTERN.getRegex(), command)).find() ) {
            if (!isSelectionValid(matcher)) return DuelMenuMessages.INVALID_SELECTION;
            else if (!isCardAvailableInMonstersZone(matcher, notTurnPlayer)) return DuelMenuMessages.CARD_NOT_FOUND;
            selectCardFromMonstersZone(matcher, false);

        } else if ( (matcher = Utils.getMatcher(DuelMenuRegexes.SELECT_OPPONENT_MONSTER_ZONE_OPPONENT_PATTERN.getRegex(), command)).find() ) {
            if (!isSelectionValid(matcher)) return DuelMenuMessages.INVALID_SELECTION;
            else if (!isCardAvailableInMonstersZone(matcher, notTurnPlayer)) return DuelMenuMessages.CARD_NOT_FOUND;
            selectCardFromMonstersZone(matcher, false);

        } else if ( (matcher = Utils.getMatcher(DuelMenuRegexes.SELECT_OPPONENT_MAGIC_ZONE_SPELL_PATTERN.getRegex(), command)).find() ) {
            if (!isSelectionValid(matcher)) return DuelMenuMessages.INVALID_SELECTION;
            else if (!isCardAvailableInMagicsZone(matcher, turnPlayer)) return DuelMenuMessages.CARD_NOT_FOUND;
            selectCardFromMagicsZone(matcher, false);

        } else if ( (matcher = Utils.getMatcher(DuelMenuRegexes.SELECT_OPPONENT_MAGIC_ZONE_OPPONENT_PATTERN.getRegex(), command)).find() ) {
            if (!isSelectionValid(matcher)) return DuelMenuMessages.INVALID_SELECTION;
            else if (!isCardAvailableInMagicsZone(matcher, turnPlayer)) return DuelMenuMessages.CARD_NOT_FOUND;
            selectCardFromMagicsZone(matcher, false);

        } else if (Utils.getMatcher(DuelMenuRegexes.SELECT_FIELD_ZONE.getRegex(), command).find()) {
            if (turnPlayer.getBoard().getFieldZone() == null) {
                return DuelMenuMessages.CARD_NOT_FOUND;
            }
            selectCardFromFieldZone(true);

        } else if (Utils.getMatcher(DuelMenuRegexes.SELECT_OPPONENT_FIELD_ZONE_FIELD_PATTERN.getRegex(), command).find()) {
            if (notTurnPlayer.getBoard().getFieldZone() == null) {
                return DuelMenuMessages.CARD_NOT_FOUND;
            }
            selectCardFromFieldZone(false);

        } else if (Utils.getMatcher(DuelMenuRegexes.SELECT_OPPONENT_FIELD_ZONE_OPPONENT_PATTERN.getRegex(), command).find()) {
            if (notTurnPlayer.getBoard().getFieldZone() == null) {
                return DuelMenuMessages.CARD_NOT_FOUND;
            }
            selectCardFromFieldZone(false);

        } else if ( (matcher = Utils.getMatcher(DuelMenuRegexes.SELECT_CARDS_IN_HAND.getRegex(), command)).find() ) {
            int number = Integer.parseInt(matcher.group(1));
            if (number > turnPlayer.getBoard().getCardsInHand().size()) {
                return DuelMenuMessages.INVALID_SELECTION;
            }
            turnPlayer.getBoard().setSelectedCard(turnPlayer.getBoard().getCardsInHand().get(number - 1));
            turnPlayer.getBoard().setMyCardSelected(true);
            turnPlayer.getBoard().setACardInHandSelected(true);

        } else  {
            turnPlayer.getBoard().setSelectedCard(null);
            turnPlayer.getBoard().setMyCardSelected(false);
            return DuelMenuMessages.INVALID_SELECTION;
        }

        return DuelMenuMessages.CARD_SELECTED;
    }

    private boolean isSelectionValid(Matcher matcher) {
        int number = Integer.parseInt(matcher.group(1));
        return number <= 5 && number >= 1;
    }

    private boolean isCardAvailableInMonstersZone(Matcher matcher, Player player) {
        int number = Integer.parseInt(matcher.group(1));
        return player.getBoard().getMonstersZone()[number] != null;
    }

    private boolean isCardAvailableInMagicsZone(Matcher matcher, Player player) {
        int number = Integer.parseInt(matcher.group(1));
        return player.getBoard().getMagicsZone()[number] != null;
    }

    private void selectCardFromMonstersZone(Matcher matcher, boolean isMyCardSelected) {
        int number = Integer.parseInt(matcher.group(1));
        if (isMyCardSelected) {
            turnPlayer.getBoard().setSelectedCard(turnPlayer.getBoard().getMonstersZone()[number]);
            turnPlayer.getBoard().setMyCardSelected(true);
        } else {
            turnPlayer.getBoard().setSelectedCard(notTurnPlayer.getBoard().getMonstersZone()[number]);
        }
    }

    private void selectCardFromMagicsZone(Matcher matcher, boolean isMyCardSelected) {
        int number = Integer.parseInt(matcher.group(1));
        if (isMyCardSelected) {
            turnPlayer.getBoard().setSelectedCard(turnPlayer.getBoard().getMagicsZone()[number]);
            turnPlayer.getBoard().setMyCardSelected(true);
        } else {
            turnPlayer.getBoard().setSelectedCard(notTurnPlayer.getBoard().getMagicsZone()[number]);
        }
    }

    private void selectCardFromFieldZone(boolean isMyCardSelected) {
        if (isMyCardSelected) {
            turnPlayer.getBoard().setSelectedCard(turnPlayer.getBoard().getFieldZone());
            turnPlayer.getBoard().setMyCardSelected(true);
        } else {
            turnPlayer.getBoard().setSelectedCard(notTurnPlayer.getBoard().getSelectedCard());
        }
    }

    private DuelMenuMessages deselectCard() {
        Board board = turnPlayer.getBoard();
        DuelMenuMessages result = checkDeselectCard();
        if (result == null) {
            board.setSelectedCard(null);
            return DuelMenuMessages.DESELECTED;
        } else return result;
    }

    private DuelMenuMessages checkDeselectCard() {
        Board board = turnPlayer.getBoard();
        if (board.getSelectedCard() == null)
            return DuelMenuMessages.NOT_SELECTED_CARD;
        return null;
    }

    private DuelMenuMessages summonMonster() {
        Board turnPlayerBoard = turnPlayer.getBoard();
        DuelMenuMessages result = null;
        result = checkSummonMonster();
        if (result != null)
            return result;
        turnPlayer.setHasSummonedInTurn(true);
        MonsterCard selectedMonster = (MonsterCard) turnPlayerBoard.getSelectedCard();
        if (selectedMonster.getLevel() <= 4) {
            turnPlayerBoard.getSelectedCard().setCardFaceUp(false);
            turnPlayerBoard.setSummonCardOnMonsterZone();
            turnPlayer.getBoard().setSelectedCard(null);
        } else if (selectedMonster.getLevel() == 5 || selectedMonster.getLevel() == 6) {
            return summonWithOneTribute();
        } else if (selectedMonster.getLevel() == 7 || selectedMonster.getLevel() == 8) {
            return summonWithTwoTribute();
        }
        return DuelMenuMessages.SUMMONED_SUCCESSFULLY;
    }

    private DuelMenuMessages checkSummonMonster() {
        Board turnPlayerBoard = turnPlayer.getBoard();
        Card selectedCard = turnPlayerBoard.getSelectedCard();
        if (!turnPlayerBoard.isMyCardSelected()) {
            return DuelMenuMessages.UNAVAILABLE_SELECTED_CARD;
        }
        if (turnPlayerBoard.isACardInHandSelected() || !model.cards.Card.isMonsterCard(selectedCard) || selectedCard.getCardType() == CardTypes.RITUAL) {
            return DuelMenuMessages.SUMMON_NOT_POSSIBLE;
        } else if (phase != Phases.MAIN_PHASE_1 && phase != Phases.MAIN_PHASE_2) {
            return DuelMenuMessages.NOT_TRUE_PHASE;
        } else if (turnPlayerBoard.isMonsterZoneFull()) {
            return DuelMenuMessages.FULL_MONSTERS_ZONE;
        } else if (turnPlayer.getHasSummonedInTurn() == true) {
            return DuelMenuMessages.ALREADY_SUMMONED_OR_SET;
        }
        return null;
    }

    private DuelMenuMessages summonWithOneTribute() {
        Board turnPlayerBoard = turnPlayer.getBoard();
        if (!turnPlayerBoard.isThereOneMonsterForTribute()) {
            return DuelMenuMessages.NOT_ENOUGH_CARD_FOR_TRIBUTE;
        }
        String addressString = Utils.getScanner().nextLine().trim();
        int address = Integer.parseInt(addressString);
        if (!turnPlayerBoard.isThereMonsterCardInAddress(address)) {
            return DuelMenuMessages.NO_MONSTER_ON_THIS_ADDRESS;
        }
        turnPlayerBoard.removeTribute(address);
        turnPlayerBoard.setSummonCardOnMonsterZone();
        return DuelMenuMessages.SUMMONED_SUCCESSFULLY;
    }

    private DuelMenuMessages summonWithTwoTribute() {
        Board turnPlayerBoard = turnPlayer.getBoard();
        if (!turnPlayerBoard.isThereTwoMonsterForTribute()) {
            return DuelMenuMessages.NOT_ENOUGH_CARD_FOR_TRIBUTE;
        }
        String addressString = Utils.getScanner().nextLine().trim();
        int address = Integer.parseInt(addressString);
        addressString = Utils.getScanner().nextLine().trim();
        int address2 = Integer.parseInt(addressString);
        if (!turnPlayerBoard.isThereMonsterCardInAddress(address)) {
            return DuelMenuMessages.NO_MONSTER_ON_THIS_ADDRESS;
        }
        if (!turnPlayerBoard.isThereMonsterCardInAddress(address2)) {
            return DuelMenuMessages.NO_MONSTER_ON_THIS_ADDRESS;
        }
        turnPlayerBoard.removeTribute(address);
        turnPlayerBoard.removeTribute(address2);
        turnPlayerBoard.setSummonCardOnMonsterZone();
        return DuelMenuMessages.SUMMONED_SUCCESSFULLY;
    }

    private DuelMenuMessages checkSetACard() {
        Board board = turnPlayer.getBoard();
        Card selectedCard = board.getSelectedCard();
        if (board.getSelectedCard() == null) return DuelMenuMessages.UNAVAILABLE_SELECTED_CARD;
        else if (!board.isACardInHandSelected()) return DuelMenuMessages.CANT_SET;
        else if (!phase.equals(Phases.MAIN_PHASE_1) && !phase.equals(Phases.MAIN_PHASE_2)) return DuelMenuMessages.NOT_TRUE_PHASE;
        else if (Card.isMonsterCard(selectedCard)) return checkSetAMonsterCard(selectedCard);

        MagicCard magicCard = (MagicCard) selectedCard;
        return setAMagicCard(magicCard);
    }

    private DuelMenuMessages setAMagicCard(MagicCard magicCard) {
        Board board = turnPlayer.getBoard();
        if (magicCard.getIcon().equals("Field")) board.addSpellCardToFieldZone(magicCard);
        else if (board.isMagicsZoneFull()) return DuelMenuMessages.FULL_MAGICS_ZONE;
        else board.addMagicCardToMagicsZone(magicCard);
        magicCard.setPowerUsed(false);
        magicCard.setCardFaceUp(false);
        magicCard.setIsSetInThisTurn(true);

        return DuelMenuMessages.SET_SUCCESSFULLY;
    }

    private DuelMenuMessages checkSetAMonsterCard(Card card) {
        return DuelMenuMessages.EMPTY;
    }

//    private DuelMenuMessages setAMonster() {
//
//    }
//
//    private DuelMenuMessages checkChangePosition(String command) {
//
//    }
//
//    private DuelMenuMessages changePosition(String command) {
//
//    }
//
//    private void updateGraveyard() {
////        TODO: handle it!
//    }
//
//    private DuelMenuMessages checkFlipSummon() {
//
//    }
//
//    private DuelMenuMessages flipSummon() {
//
//    }

    private DuelMenuMessages attack(String command) {
        Board attackingPlayerBoard = turnPlayer.getBoard();
        Board opponentPlayerBoard = notTurnPlayer.getBoard();

        Matcher matcher = Utils.getMatcher(DuelMenuRegexes.ATTACK.getRegex(), command);
        if (matcher.find()) {
            int numberOfChosenCard = Integer.parseInt(matcher.group(1));

            DuelMenuMessages result = checkAttack(numberOfChosenCard);
            if (result == null) {
                if (isConditionPreparedToActivateMagicCardInOpponentTurn(notTurnPlayer))
                    checkActiveMagicCardInOpponentTurn(turnPlayer, notTurnPlayer);

                MonsterCard attackingMonster = (MonsterCard) attackingPlayerBoard.getSelectedCard();
                MonsterCard opponentMonster = opponentPlayerBoard.getMonstersZone()[numberOfChosenCard];
                TrapResult trapResult = MagicCardController.doTrapEffectsInTurnPlayerAttack(turnPlayer, notTurnPlayer,
                        attackingMonster, opponentMonster);
                switch (trapResult) {
                    case STOP:
                        return DuelMenuMessages.ACTION_CANCELED_BY_TRAP_CARD;
                    case NEGATE_ATTACK:
//                    TODO: ending battle phase
                        return DuelMenuMessages.ACTION_CANCELED_BY_TRAP_CARD;
                }

                MagicCardController.handleFieldSpellCardsEffect(turnPlayer, notTurnPlayer, attackingMonster, true);
                MagicCardController.handleFieldSpellCardsEffect(turnPlayer, notTurnPlayer, opponentMonster, true);
                MagicCardController.handleEquipSpellCardsEffect(turnPlayer, notTurnPlayer, attackingMonster, true);
                MagicCardController.handleEquipSpellCardsEffect(turnPlayer, notTurnPlayer, opponentMonster, true);

                DuelMenuMessages tempResult = attackingMonster.attack(turnPlayer, notTurnPlayer, numberOfChosenCard);

                MagicCardController.handleFieldSpellCardsEffect(turnPlayer, notTurnPlayer, attackingMonster, false);
                MagicCardController.handleFieldSpellCardsEffect(turnPlayer, notTurnPlayer, opponentMonster, false);
                MagicCardController.handleEquipSpellCardsEffect(turnPlayer, notTurnPlayer, attackingMonster, false);
                MagicCardController.handleEquipSpellCardsEffect(turnPlayer, notTurnPlayer, opponentMonster, false);

                return tempResult;
            }
            return result;

        } else return DuelMenuMessages.INVALID_CARD_SELECT;
    }

    private DuelMenuMessages checkAttack(int numberOfChosenCard) {
        Board attackingPlayerBoard = turnPlayer.getBoard();
        Board opponentPlayerBoard = notTurnPlayer.getBoard();

        if (attackingPlayerBoard.getSelectedCard() == null || !attackingPlayerBoard.isMyCardSelected())
            return DuelMenuMessages.NOT_SELECTED_CARD;
        if (attackingPlayerBoard.getSelectedCard() instanceof MonsterCard)
            return DuelMenuMessages.CANT_ATTACK_WITH_CARD;
        //TODO check battle phase
        MonsterCard card = (MonsterCard) attackingPlayerBoard.getSelectedCard();
        if (card.isAttacked())
            return DuelMenuMessages.ATTACKED_BEFORE;
        if (opponentPlayerBoard.getMonstersZone()[numberOfChosenCard] == null)
            return DuelMenuMessages.NO_CARD_FOUND_IN_THE_POSITION;
        return null;
    }

    private DuelMenuMessages directAttack() {
        Board board = turnPlayer.getBoard();
        DuelMenuMessages messages = checkDirectAttack();
        if (messages != null)
            return messages;
        else {
            if (isConditionPreparedToActivateMagicCardInOpponentTurn(notTurnPlayer))
                checkActiveMagicCardInOpponentTurn(turnPlayer, notTurnPlayer);

            MonsterCard card = (MonsterCard) board.getSelectedCard();
            TrapResult trapResult = MagicCardController.doTrapEffectsInTurnPlayerAttack(turnPlayer, notTurnPlayer,
                    card, null);
            switch (trapResult) {
                case STOP:
                    return DuelMenuMessages.ACTION_CANCELED_BY_TRAP_CARD;
                case NEGATE_ATTACK:
//                    TODO: ending battle phase
                    return DuelMenuMessages.ACTION_CANCELED_BY_TRAP_CARD;
            }

            MagicCardController.handleFieldSpellCardsEffect(turnPlayer, notTurnPlayer, card, true);
            MagicCardController.handleEquipSpellCardsEffect(turnPlayer, notTurnPlayer, card, true);

            notTurnPlayer.decreaseLifePoint(card.getAttackPoints());

            MagicCardController.handleFieldSpellCardsEffect(turnPlayer, notTurnPlayer, card, false);
            MagicCardController.handleEquipSpellCardsEffect(turnPlayer, notTurnPlayer, card, false);

            DuelMenuMessages.setDamageAmount(card.getAttackPoints());
            return DuelMenuMessages.DIRECT_ATTACK_DONE;
        }
    }

    private DuelMenuMessages checkDirectAttack() {
        Board board = turnPlayer.getBoard();

        if (board.getSelectedCard() == null || !board.isMyCardSelected())
            return DuelMenuMessages.NOT_SELECTED_CARD;
        if (!phase.equals(Phases.BATTLE_PHASE))
            return DuelMenuMessages.NOT_SUITABLE_PHASE;
        if (board.getSelectedCard() instanceof MonsterCard) {
            MonsterCard card = (MonsterCard) board.getSelectedCard();
            if (card.isAttacked()) return DuelMenuMessages.ATTACKED_BEFORE;
        } else return DuelMenuMessages.CANT_ATTACK_WITH_CARD;

        return null;
    }

    private DuelMenuMessages checkActiveASpellCard() {
        Board board = turnPlayer.getBoard();
        Card selectedCard = board.getSelectedCard();
        if (selectedCard == null) return DuelMenuMessages.UNAVAILABLE_SELECTED_CARD;
        else if (!selectedCard.getCardType().equals(CardTypes.SPELL)) return DuelMenuMessages.NOT_SPELL_CARD;
        else if (!phase.equals(Phases.MAIN_PHASE_1) && !phase.equals(Phases.MAIN_PHASE_2)) return DuelMenuMessages.CANT_ACTIVATE_SPELL_EFFECT;
        else if (selectedCard.isPowerUsed()) return DuelMenuMessages.CARD_ACTIVATED_BEFORE;
        else if (!board.isMyCardSelected()) return DuelMenuMessages.NOT_OWNER;

        return activeASpellCard(selectedCard, turnPlayer, notTurnPlayer);
    }

    private DuelMenuMessages activeASpellCard(Card selectedCard, Player turnPlayer, Player notTurnPlayer) {
        if (isConditionPreparedToActivateMagicCardInOpponentTurn(notTurnPlayer))
            checkActiveMagicCardInOpponentTurn(turnPlayer, notTurnPlayer);

        Board board = turnPlayer.getBoard();
        MagicCard spellCard = (MagicCard) selectedCard;
        TrapResult trapResult = MagicCardController.doTrapEffectsInTurnPlayerActivateSpellCard(turnPlayer, notTurnPlayer,
                spellCard);
        if (trapResult == TrapResult.STOP) return DuelMenuMessages.ACTION_CANCELED_BY_TRAP_CARD;


        if (spellCard.getIcon().equals("Field")) {
            turnPlayer.getBoard().addSpellCardToFieldZone(spellCard);
            spellCard.setPowerUsed(true);
            spellCard.setCardFaceUp(true);
            MagicCardController.doSpellAbsorptionEffect(turnPlayer);
            MagicCardController.doSpellAbsorptionEffect(notTurnPlayer);
            return DuelMenuMessages.SPELL_ACTIVATED;
        } else if (board.isMagicsZoneFull() && board.isACardInHandSelected()) return DuelMenuMessages.FULL_MAGICS_ZONE;
        if (spellCard.getIcon().equals("Quick-play") && spellCard.isSetInThisTurn()) return DuelMenuMessages.SET_IN_THIS_TURN;

        if (!MagicCardController.doSpellCardEffect(turnPlayer, notTurnPlayer, spellCard)) return DuelMenuMessages.UNDONE_PREPARATIONS;
        if (board.isACardInHandSelected()) board.addMagicCardToMagicsZone(spellCard);
        spellCard.setPowerUsed(true);
        spellCard.setCardFaceUp(true);
        MagicCardController.doSpellAbsorptionEffect(turnPlayer);
        MagicCardController.doSpellAbsorptionEffect(notTurnPlayer);
        if (spellCard.getIcon().equals("Normal") || spellCard.getIcon().equals("Ritual")) board.moveMagicCardToGraveyard(spellCard);
        return DuelMenuMessages.SPELL_ACTIVATED;
    }

    private DuelMenuMessages checkActiveATrapCard() {
        Board board = turnPlayer.getBoard();
        Card selectedCard = board.getSelectedCard();
        if (selectedCard == null) return DuelMenuMessages.UNAVAILABLE_SELECTED_CARD;
        else if (!selectedCard.getCardType().equals(CardTypes.TRAP)) return DuelMenuMessages.NOT_TRAP_CARD;
        else if (selectedCard.isPowerUsed()) return DuelMenuMessages.CARD_ACTIVATED_BEFORE;
        else if (!turnPlayer.getBoard().isMyCardSelected()) return DuelMenuMessages.NOT_OWNER;
        else if (board.isMagicsZoneFull() && board.isACardInHandSelected()) return DuelMenuMessages.FULL_MAGICS_ZONE;

        return activeATrapCard(selectedCard, turnPlayer, notTurnPlayer);
    }

    private DuelMenuMessages activeATrapCard(Card selectedCard, Player turnPlayer, Player notTurnPlayer) {
        MagicCard spellCard = (MagicCard) selectedCard;
        if (spellCard.isSetInThisTurn()) return DuelMenuMessages.SET_IN_THIS_TURN;
        spellCard.setPowerUsed(true);
        spellCard.setCardFaceUp(true);

        if (spellCard.getName().equals("Mind Crush")) MagicCardController.handleMindCrushEffect(turnPlayer, notTurnPlayer);
        else if (spellCard.getName().equals("Call of The Haunted")) MagicCardController.handleCallOfTheHauntedEffect(turnPlayer);
        return DuelMenuMessages.TRAP_ACTIVATED;
    }

    private void checkActiveMagicCardInOpponentTurn(Player turnPlayer, Player notTurnPlayer) {
        while (true) {
            if (DuelMenuView.activeMagicCardInOpponentTurn(notTurnPlayer, turnPlayer))
                handleYesAnswerInChangeTurn(notTurnPlayer, turnPlayer);
            else {
                DuelMenuView.showTurnAndBoard(turnPlayer, notTurnPlayer);
                break;
            }
        }
    }

    private void handleYesAnswerInChangeTurn(Player turnPlayer, Player notTurnPlayer) {
        String command = Utils.getScanner().nextLine();
        Matcher matcher = Utils.getMatcher(DuelMenuRegexes.ACTIVE_MAGIC_CARD_IN_OPPONENT_TURN.getRegex(), command);
        if (matcher.find()) {

            MagicCard magicCard = turnPlayer.getBoard().getFaceDownMagicCardFromMagicsZoneByName(matcher.group(1));
            if (magicCard == null) DuelMenuView.showUnavailableCard();
            else if (magicCard.getCardType().equals(CardTypes.SPELL) && !magicCard.getIcon().equals("Quick-play"))
                DuelMenuView.showCantActivateCard();
            else {
//                so we can activate magic card
                if (activeMagicCardInOpponentTurn(turnPlayer, notTurnPlayer, magicCard)) DuelMenuView.showActiveSuccessfullyInOpponentTurn();
                else DuelMenuView.showActiveUnsuccessfullyInOpponentTurn();
            }

        } else DuelMenuView.showNotTurnToDoMoves();
    }

    private boolean activeMagicCardInOpponentTurn(Player turnPlayer, Player notTurnPlayer, MagicCard magicCard) {
        if (magicCard.getCardType().equals(CardTypes.SPELL)) {
            DuelMenuMessages message = activeASpellCard(magicCard, turnPlayer, notTurnPlayer);
            return !message.equals(DuelMenuMessages.UNDONE_PREPARATIONS) &&
                    !message.equals(DuelMenuMessages.ACTION_CANCELED_BY_TRAP_CARD);

        } else if (magicCard.getCardType().equals(CardTypes.TRAP)) activeATrapCard(magicCard, turnPlayer, notTurnPlayer);

        return true;
    }

    private boolean isConditionPreparedToActivateMagicCardInOpponentTurn(Player notTurnPlayer) {
        MagicCard[] magicsZone = notTurnPlayer.getBoard().getMagicsZone();
        for (int i = 1; i < magicsZone.length; i++) {
            MagicCard magicCard = magicsZone[i];
            if (magicCard != null && !magicCard.getCardFaceUp() && (magicCard.getCardType().equals(CardTypes.TRAP) ||
                    magicCard.getCardType().equals(CardTypes.SPELL) && magicCard.getIcon().equals("Quick-play")))
                return true;
        }

        return false;
    }

    public DuelMenuMessages showSelectedCard() {
        Board board = turnPlayer.getBoard();
        Card selectedCard = board.getSelectedCard();
        if (selectedCard == null) return DuelMenuMessages.NOT_SELECTED_CARD;
        else if (!board.isMyCardSelected() && !selectedCard.getCardFaceUp()) return DuelMenuMessages.INVISIBLE_CARD;

        Utils.showCard(selectedCard.getName());
        return DuelMenuMessages.EMPTY;
    }

//    private DuelMenuMessages checkBack() {
//
//    }
//
//    private void cancelCommand() {
//
//    }
//
//    TODO: handle activeASpellInOpponentTurn
//    TODO: -----------------------------------------------
//    TODO: we are not sure about under functions
//
//    private DuelMenuMessages ritualSummon(String command) {
//
//    }
//
//    private DuelMenuMessages specialSummon(String command) {
//
//    }
//
//    private Player/*or Enum*/ checkWinner() {
//
//    }
//
//    TODO: -----------------------------------------------

}
