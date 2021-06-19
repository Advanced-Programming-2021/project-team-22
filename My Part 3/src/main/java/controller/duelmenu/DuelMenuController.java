package controller.duelmenu;

import controller.SpellCardController;
import controller.Utils;
import model.Board;
import model.Deck;
import model.Player;
import model.cards.Card;
import model.cards.CardTypes;
import model.cards.magiccard.MagicCard;
import model.cards.monstercard.MonsterCard;
import view.DuelMenuView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Matcher;

public class DuelMenuController {
    private Player turnPlayer;
    private Player notTurnPlayer;
    private Player helpTurnPlayer;
    private Phases phase;
    private boolean isAITurn;
    private int isSummoned = 0; //0 : is not summoned before, 1 : is summoned before

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

        turnPlayer.getBoard().setDeck(turnPlayer.getActivatedDeck());
        notTurnPlayer.getBoard().setDeck(notTurnPlayer.getActivatedDeck());

        Collections.shuffle(turnPlayer.getActivatedDeck().getMainCards());
        Collections.shuffle(notTurnPlayer.getActivatedDeck().getMainCards());

        return DuelMenuMessages.SHOW_TURN_PLAYER;
    }

    public DuelMenuMessages findCommand(String command) {
//        TODO: handle menu commands --> menu exit and ...
        if (command.startsWith("decrease ")) return cheatCodeDecreaseOpponentLifePont(command);
        else if (command.startsWith("increase ")) return cheatCodeIncreaseLifePoint(command);
        else if (command.startsWith("duel set-winner ")) return cheatCodeSetWinner(command);
        else if (command.startsWith("increase --money ")) return cheatCodeIncreaseMoney(command);
        else if (command.startsWith("select ")) return checkSelectCard(command);
        else if (command.equals("select -d")) return deselectCard();
        else if (command.equals("summon")) ;//return checkSummonMonster();
        else if (command.equals("set")) return checkSetACard();
        else if (command.startsWith("set --position"));// return checkChangePosition(command);
        else if (command.equals("flip-summon")) ;//return checkFlipSummon();
        else if (command.equals("attack direct")) return directAttack();
        else if (command.startsWith("attack")) return attack(command);
        else if (command.equals("activate effect")) return checkActiveASpellCard();
        else if (command.equals("show graveyard")) {
            DuelMenuView.showGraveyard(turnPlayer.getBoard());
            return DuelMenuMessages.EMPTY;
        } else if (command.equals("back")) ;//checkBack();
        else if (command.equals("card show --selected")) {
            DuelMenuView.printCard(1, turnPlayer.getBoard().getSelectedCard());
            return DuelMenuMessages.EMPTY;
        } else if (command.equals("cancel")) ;//cancelCommand();
        else if (command.equals("surrender")) /*TODO*/;

//        TODO: handle cheat/debug commands

        return DuelMenuMessages.INVALID_COMMAND;
    }

    private DuelMenuMessages cheatCodeDecreaseOpponentLifePont(String command) {
        Matcher matcher = Utils.getMatcher(DuelMenuRegexes.CHEAT_DECREASE_OPPONENT_LIFE_POINT.getRegex(), command);
        if (matcher.find()) {
            notTurnPlayer.decreaseLifePoint(Integer.parseInt(matcher.group(1)));
            return DuelMenuMessages.EMPTY;
        } else return DuelMenuMessages.INVALID_COMMAND_CHEAT_CODE;

    }

    private DuelMenuMessages cheatCodeIncreaseLifePoint(String command) {
        Matcher matcher = Utils.getMatcher(DuelMenuRegexes.CHEAT_INCREASE_LIFE_POINT.getRegex(), command);
        if (matcher.find()) {
            turnPlayer.increaseLifePoint(Integer.parseInt(matcher.group(1)));
            return DuelMenuMessages.EMPTY;
        } else return DuelMenuMessages.INVALID_COMMAND_CHEAT_CODE;
    }

    private DuelMenuMessages cheatCodeSetWinner(String command) {
        Matcher matcher = Utils.getMatcher(DuelMenuRegexes.CHEAT_SET_WINNER.getRegex(), command);
        if (matcher.find()) {
            String nickname = matcher.group(1);
            if (turnPlayer.getNickname().equals(nickname)) /*TODO: handle win the player*/;
            else if (notTurnPlayer.getNickname().equals(nickname)) /*TODO: handle win the player*/;
            else DuelMenuMessages.WRONG_NICKNAME_CHEAT_CODE;
        } else return DuelMenuMessages.INVALID_COMMAND_CHEAT_CODE;
    }

    private DuelMenuMessages cheatCodeIncreaseMoney(String command) {
        Matcher matcher = Utils.getMatcher(DuelMenuRegexes.CHEAT_INCREASE_MONEY.getRegex(), command);
        if (matcher.find()) {
            turnPlayer.increaseMoney(Integer.parseInt(matcher.group(1)));
            return null;
        } else return DuelMenuMessages.INVALID_COMMAND_CHEAT_CODE;

    }

    //Iman's Code
    private void changePhase() {
        phase = phase.next();
        DuelMenuMessages.changephase(phase);

    }

    private void changeGameTurn(Player firstPlayer, Player secondPlayer) {
        if (turnPlayer == firstPlayer) {
            turnPlayer = secondPlayer;
            notTurnPlayer = firstPlayer;
        }
        if (turnPlayer == secondPlayer) {
            turnPlayer = firstPlayer;
            notTurnPlayer = secondPlayer;
        }
        DuelMenuMessages.playerTurn(turnPlayer);

    }

    private void changeGameTurn() {
        helpTurnPlayer = turnPlayer;
        turnPlayer = notTurnPlayer;
        notTurnPlayer = helpTurnPlayer;
        DuelMenuMessages.playerTurn(turnPlayer);

    }

    private void drawPhase() {
        changeGameTurn();
        DuelMenuMessages.playerTurn(turnPlayer);
        cardDraw();
    }

    public void cardDraw() {
        Deck deck = turnPlayer.getBoard().getDeck();
        if (deck.getNumberOfCardsInMainDeck() == 0) {
            turnPlayer.setLifePoint(0);
            return;
        }
        turnPlayer.getBoard().drawCard();
    }

    private DuelMenuMessages summonMonster() {
        int position = 0; // TODO fix this
        Card selectedCard = turnPlayer.getBoard().getSelectedCard();
        MonsterCard selectedMonster = (MonsterCard) selectedCard;
        if (selectedMonster.getLevel() <= 4) {
            selectedCard.getCardFaceUp();
            selectedMonster.settoOO(selectedMonster);
            isSummoned = 1;
            turnPlayer.getBoard().setSelectedCard(null);
            return DuelMenuMessages.SUMMONED_SUCCESSFULLY;
        }
        else if (selectedMonster.getLevel() == 5 || selectedMonster.getLevel() == 6) {
            summonWithOneTribute(position);
        }
        else if (selectedMonster.getLevel() == 7 || selectedMonster.getLevel() == 8) {
            summonWithTwoTribute(position);
        }
    }

    private DuelMenuMessages checkSummonMonster() {
        Card selectedCard = turnPlayer.getBoard().getSelectedCard();
        if (!turnPlayer.getBoard().isMyCardSelected()) {
            DuelMenuMessages.noCardSelected();
        }
        MonsterCard[] monstersZone = turnPlayer.getBoard().getMonstersZone();
        if (turnPlayer.getBoard().isACardInHandSelected() || !model.cards.Card.isMonsterCard(selectedCard) || selectedCard.getCardType() == CardTypes.RITUAL) {
            return DuelMenuMessages.SUMMON_NOT_POSSIBLE;
        }
        else if (phase != Phases.MAIN_PHASE_1 && phase != Phases.MAIN_PHASE_2) {
            return DuelMenuMessages.NOT_TRUE_PHASE;
        }
        else if (turnPlayer.getBoard().isMonsterZoneFull()) {
            return DuelMenuMessages.MONSTER_ZONE_IS_FULL;
        }
        else if (isSummoned == 1){
            return DuelMenuMessages.ALREADY_SUMMONED_OR_SET;
        }

    }

    private DuelMenuMessages summonWithOneTribute(int position) {
        if (!turnPlayer.getBoard().isThereOneMonsterForTribute(turnPlayer.getBoard().getMonstersZone())) {
            return DuelMenuMessages.NOT_ENOUGH_CARD_FOR_TRIBUTE;
        }
        String addressString = Utils.getScanner().nextLine().trim();
        if (addressString.equals("")) return null; // TODO fix this
        int address = setCardAddressInMyBoard(Integer.parseInt(addressString));
        if (!turnPlayer.getBoard().isThereCardInAddress(turnPlayer.getBoard().getMonstersZone(), address)) {
            return DuelMenuMessages.NO_MONSTER_ON_THIS_ADDRESS;
        }
        //turnPlayer.getBoard().removeFromMonsterZone(monsterZone.get(address1));
        //turnPlayer.getBoard().removeFromHand(selectedCard);

    }

    private void summonWithTwoTribute(int position) {
        if (!turnPlayer.getBoard().isThereTwoMonsterForTribute(turnPlayer.getBoard().getMonstersZone())) {
            DuelMenuMessages.NotEnoughCardForTribute();
        }
        String addressString1 = Utils.getScanner().nextLine().trim();
        if (addressString1.equals("surrender")) return;
        String addressString2 = Utils.getScanner().nextLine().trim();
        if (addressString2.equals("surrender")) return;
        int address1 = setCardAddressInMyBoard(Integer.parseInt(addressString1));
        int address2 = setCardAddressInMyBoard(Integer.parseInt(addressString2));
        if (!turnPlayer.getBoard().isThereCardInAddress(turnPlayer.getBoard().getMonstersZone(), address1)) return;
        if (!turnPlayer.getBoard().isThereCardInAddress(turnPlayer.getBoard().getMonstersZone(), address2)) return;
        // turnPlayer.getBoard().deleteMonsterFromZone(monsterZone.get(address1), monsterZone);
        // turnPlayer.getBoard().deleteMonsterFromZone(monsterZone.get(address2));

    }

    private int setCardAddressInMyBoard(int address) {
        if (address == 5) return 0;
        if (address == 3) return 1;
        if (address == 1) return 2;
        if (address == 2) return 3;
        if (address == 4) return 4;
        return -1;
    }

    private DuelMenuMessages set() {
        Card selectedCard = turnPlayer.getBoard().getSelectedCard();
        // int position = selectedCardIndex;
        if (!turnPlayer.getBoard().isMyCardSelected()) {
            DuelMenuMessages.noCardSelected();
        }
        if (turnPlayer.getBoard().isACardInHandSelected()) {
            return DuelMenuMessages.SET_NOT_POSSIBLE;
        }
        if (phase != Phases.MAIN_PHASE_1 && phase != Phases.MAIN_PHASE_2) {
            return DuelMenuMessages.NOT_TRUE_PHASE;
        }
        if (turnPlayer.getBoard().isMonsterZoneFull()) {
            return DuelMenuMessages.MONSTER_ZONE_IS_FULL;
        }
        if (isSummoned == 1){
            return DuelMenuMessages.ALREADY_SUMMONED_OR_SET;
        }
        return DuelMenuMessages.SET_SUCCESSFULLY;
        if (model.cards.Card.isMonsterCard(selectedCard)) {
            setMonster();
        }
        if (model.cards.Card.isMonsterCard(selectedCard)) {
            setSpell();
        }


    }

    private DuelMenuMessages setMonster() {

    }

    private DuelMenuMessages setSpell() {

    }

    private DuelMenuMessages checkFlipSummon() {
        Card selectedCard = turnPlayer.getBoard().getSelectedCard();
        MonsterCard[] monstersZone = turnPlayer.getBoard().getMonstersZone();
        MonsterCard selectedMonster = (MonsterCard) selectedCard;
        if (!turnPlayer.getBoard().isMyCardSelected()) {
            DuelMenuMessages.noCardSelected();
        }
        if (turnPlayer.getBoard().isACardInHandSelected() || !model.cards.Card.isMonsterCard(selectedCard)) { // check type of monsters
            return DuelMenuMessages.SUMMON_NOT_POSSIBLE;
        }
        if (phase != Phases.MAIN_PHASE_1 && phase != Phases.MAIN_PHASE_2) {
            return DuelMenuMessages.NOT_TRUE_PHASE;
        }
        if (turnPlayer.getBoard().isCardAvailableInMonstersZone(selectedMonster)) {
            return DuelMenuMessages.CHANG_POSITION_NOT_POSSIBLE;
        }
        if (selectedMonster.toString().equals("DH") || isSummoned != 0) {
            return DuelMenuMessages.FLIP_SUMMON_NOT_POSSIBLE;
        }
        selectedMonster.settoDO(selectedMonster);
        isSummoned = 1;
        turnPlayer.getBoard().setSelectedCard(null);
        return DuelMenuMessages.SUMMONED_SUCCESSFULLY;

    }

    private DuelMenuMessages ritualSummon() {


    }

    private DuelMenuMessages specialSummon() {


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
//
//
//    private void victimize() {
////        TODO: handle it!
//    }
//
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

    private DuelMenuMessages attack(String command) {
        Board attackingPlayerBoard = turnPlayer.getBoard();
        Board opponentPlayerBoard = notTurnPlayer.getBoard();

        Matcher matcher = Utils.getMatcher(DuelMenuRegexes.ATTACK.getRegex(), command);
        if (matcher.find()) {
            int numberOfChosenCard = Integer.parseInt(matcher.group(1));

            DuelMenuMessages result = checkAttack(numberOfChosenCard);
            if (result == null) {
                MonsterCard attackingMonster = (MonsterCard) attackingPlayerBoard.getSelectedCard();
                MonsterCard opponentMonster = opponentPlayerBoard.getMonstersZone()[numberOfChosenCard];

                SpellCardController.handleFieldSpellCardsEffect(turnPlayer, notTurnPlayer, attackingMonster, true);
                SpellCardController.handleFieldSpellCardsEffect(turnPlayer, notTurnPlayer, opponentMonster, true);
                SpellCardController.handleEquipSpellCardsEffect(turnPlayer, notTurnPlayer, attackingMonster, true);
                SpellCardController.handleEquipSpellCardsEffect(turnPlayer, notTurnPlayer, opponentMonster, true);
                DuelMenuMessages tempResult = attackingMonster.attack(turnPlayer, notTurnPlayer, numberOfChosenCard);
                SpellCardController.handleFieldSpellCardsEffect(turnPlayer, notTurnPlayer, attackingMonster, false);
                SpellCardController.handleFieldSpellCardsEffect(turnPlayer, notTurnPlayer, opponentMonster, false);
                SpellCardController.handleEquipSpellCardsEffect(turnPlayer, notTurnPlayer, attackingMonster, false);
                SpellCardController.handleEquipSpellCardsEffect(turnPlayer, notTurnPlayer, opponentMonster, false);

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
            MonsterCard card = (MonsterCard) board.getSelectedCard();
            SpellCardController.handleFieldSpellCardsEffect(turnPlayer, notTurnPlayer, card, true);
            SpellCardController.handleEquipSpellCardsEffect(turnPlayer, notTurnPlayer, card, true);
            notTurnPlayer.decreaseLifePoint(card.getAttackPoints());
            SpellCardController.handleFieldSpellCardsEffect(turnPlayer, notTurnPlayer, card, false);
            SpellCardController.handleEquipSpellCardsEffect(turnPlayer, notTurnPlayer, card, false);

            DuelMenuMessages.setDamageAmount(card.getAttackPoints());
            return DuelMenuMessages.DIRECT_ATTACK_DONE;
        }
    }

    private DuelMenuMessages checkDirectAttack() {
        Board board = turnPlayer.getBoard();

        if (board.getSelectedCard() == null || !board.isMyCardSelected())
            return DuelMenuMessages.NOT_SELECTED_CARD;
        if (phase.equals(phase.))
            return DuelMenuMessages.NOT_SUITABLE_PHASE;
        if (board.getSelectedCard() instanceof MonsterCard) {
            MonsterCard card = (MonsterCard) board.getSelectedCard();//TODO: handle cast exception!!
            if (card.isAttacked()) return DuelMenuMessages.ATTACKED_BEFORE;
        } else return DuelMenuMessages.CANT_ATTACK_WITH_CARD;

        return null;
    }

    private DuelMenuMessages checkActiveASpellCard() {
//        TODO: clean it!
        Board board = turnPlayer.getBoard();
        Card selectedCard = board.getSelectedCard();
        if (selectedCard == null) return DuelMenuMessages.UNAVAILABLE_SELECTED_CARD;
        else if (!selectedCard.getCardType().equals(CardTypes.SPELL)) return DuelMenuMessages.NOT_SPELL_CARD;
        else if (!phase.equals(Phases.MAIN_PHASE_1) && !phase.equals(Phases.MAIN_PHASE_2)) return DuelMenuMessages.CANT_ACTIVATE_SPELL_EFFECT;
        else if (selectedCard.isPowerUsed()) return DuelMenuMessages.CARD_ACTIVATED_BEFORE;
        else if (!turnPlayer.getBoard().isMyCardSelected()) return DuelMenuMessages.NOT_OWNER;

        MagicCard spellCard = (MagicCard) selectedCard;
        if (spellCard.getIcon().equals("Field")) {
            turnPlayer.getBoard().addSpellCardToFieldZone(spellCard);
            spellCard.setPowerUsed(true);
            spellCard.setCardFaceUp(true);
            SpellCardController.doSpellAbsorptionEffect(turnPlayer);
            SpellCardController.doSpellAbsorptionEffect(notTurnPlayer);
            return DuelMenuMessages.SPELL_ACTIVATED;
        } else if (board.isMagicsZoneFull() && board.isACardInHandSelected()) return DuelMenuMessages.FULL_MAGICS_ZONE;

        if (!SpellCardController.doSpellCardEffect(turnPlayer, notTurnPlayer, spellCard)) return DuelMenuMessages.UNDONE_PREPARATIONS;
        if (board.isACardInHandSelected()) board.addMagicCardToMagicsZone(spellCard);
        selectedCard.setPowerUsed(true);
        selectedCard.setCardFaceUp(true);
        SpellCardController.doSpellAbsorptionEffect(turnPlayer);
        SpellCardController.doSpellAbsorptionEffect(notTurnPlayer);
        return DuelMenuMessages.SPELL_ACTIVATED;
    }

    private DuelMenuMessages setAMagicCard(MagicCard magicCard) {
        Board board = turnPlayer.getBoard();
        if (magicCard.getIcon().equals("Field")) board.addSpellCardToFieldZone(magicCard);
        else if (board.isMagicsZoneFull()) return DuelMenuMessages.FULL_MAGICS_ZONE;
        else board.addMagicCardToMagicsZone(magicCard);
        magicCard.setPowerUsed(false);
        magicCard.setCardFaceUp(false);

        return DuelMenuMessages.SET_SUCCESSFULLY;
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
