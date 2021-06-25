package controller.deckmenu;

import controller.Database;
import controller.MenuRegexes;
import controller.Utils;
import model.Deck;
import model.Player;
import model.cards.Card;
import model.cards.SortCardByName;
import model.cards.magiccard.MagicCard;
import model.cards.monstercard.MonsterCard;
import view.DeckMenuView;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class DeckMenuController {
    private final Player loggedInPlayer;

    public DeckMenuController(Player loggedInPlayer) {
        this.loggedInPlayer = loggedInPlayer;
    }

    public static void separateKindOfCards(Deck deck, boolean isMain, ArrayList<MonsterCard> monsterCards,
                                           ArrayList<MagicCard> magicCards) {
        if (isMain) {
            for (Card card : deck.getMainCards()) {
                if (card instanceof MonsterCard) monsterCards.add((MonsterCard) card);
                else magicCards.add((MagicCard) card);
            }

        } else {
            for (Card card : deck.getSideCards()) {
                if (card instanceof MonsterCard) monsterCards.add((MonsterCard) card);
                else magicCards.add((MagicCard) card);
            }

        }

        monsterCards.sort(new SortCardByName());
        magicCards.sort(new SortCardByName());
    }

    public DeckMenuMessages findCommand(String command) {

        if (command.startsWith("menu enter")) return enterAMenu(command);
        else if (command.equals("menu exit")) return DeckMenuMessages.EXIT_DECK_MENU;
        else if (command.equals("menu show-current")) return DeckMenuMessages.SHOW_MENU;
        else if (command.startsWith("card show ")) {
            Utils.showCard(command.substring(10));
            return DeckMenuMessages.EMPTY;
        } else if (command.startsWith("deck create ")) return createDeck(command);
        else if (command.startsWith("deck delete ")) return deleteDeck(command);
        else if (command.startsWith("deck set-activate ")) return activateADeck(command);
        else if (command.startsWith("deck add-card ")) return addCard(command);
        else if (command.startsWith("deck rm-card ")) return removeCard(command);
        else if (command.equals("deck show --all")) return DeckMenuMessages.SHOW_ALL_DECKS;
        else if (command.equals("deck show --cards")) return DeckMenuMessages.SHOW_ALL_CARDS;
        else if (command.startsWith("deck show ")) return showADeck(command);

        return DeckMenuMessages.INVALID_COMMAND;
    }

    private DeckMenuMessages enterAMenu(String command) {
        Matcher matcher = Utils.getMatcher(MenuRegexes.ENTER_A_MENU.getRegex(), command);
        if (!matcher.find()) return DeckMenuMessages.INVALID_COMMAND;

        return DeckMenuMessages.INVALID_NAVIGATION;
    }

    private DeckMenuMessages createDeck(String command) {
        Matcher matcher = Utils.getMatcher(DeckMenuRegexes.CREATE_DECK.getRegex(), command);
        if (!matcher.find()) return DeckMenuMessages.INVALID_COMMAND;

        String deckName = matcher.group(1);
        if (loggedInPlayer.getDeckByName(deckName) != null) {
            DeckMenuMessages.setAvailableDeck(deckName);
            return DeckMenuMessages.AVAILABLE_DECK;
        }

        loggedInPlayer.addDeckToAllDecks(new Deck(deckName));
        return DeckMenuMessages.DECK_CREATED;
    }

    private DeckMenuMessages deleteDeck(String command) {
        Matcher matcher = Utils.getMatcher(DeckMenuRegexes.DELETE_DECK.getRegex(), command);
        if (!matcher.find()) return DeckMenuMessages.INVALID_COMMAND;

        if (!handleDeckAvailability(matcher)) return DeckMenuMessages.UNAVAILABLE_DECK;
        Deck deck = loggedInPlayer.getDeckByName(matcher.group(1));

        loggedInPlayer.getBoughtCards().addAll(deck.getMainCards());
        loggedInPlayer.getBoughtCards().addAll(deck.getSideCards());
        loggedInPlayer.removeDeckFromAllDecks(deck);
        if (loggedInPlayer.getActivatedDeck().equals(deck)) loggedInPlayer.setActivatedDeck(null);
        return DeckMenuMessages.DECK_DELETED;
    }

    private boolean handleDeckAvailability(Matcher matcher) {
        String deckName = matcher.group(1);
        Deck deck = loggedInPlayer.getDeckByName(deckName);
        if (deck == null) {
            DeckMenuMessages.setUnavailableDeck(deckName);
            return false;
        }

        return true;
    }

    private DeckMenuMessages activateADeck(String command) {
        Matcher matcher = Utils.getMatcher(DeckMenuRegexes.ACTIVATE_DECK.getRegex(), command);
        if (!matcher.find()) return DeckMenuMessages.INVALID_COMMAND;

        if (!handleDeckAvailability(matcher)) return DeckMenuMessages.UNAVAILABLE_DECK;
        Deck deck = loggedInPlayer.getDeckByName(matcher.group(1));

        loggedInPlayer.setActivatedDeck(deck);
        return DeckMenuMessages.DECK_ACTIVATED;
    }

    private DeckMenuMessages addCard(String command) {
        Matcher matcher = findHandleCardMatcher(command);
        if (matcher == null) return DeckMenuMessages.INVALID_COMMAND;

        String cardName, deckName;
        boolean isMain = isMainHandleCardPattern(command);
        cardName = matcher.group("cardName");
        deckName = matcher.group("deckName");

        return checkAddCard(cardName, deckName, isMain);
    }

    private Matcher findHandleCardMatcher(String command) {
        Matcher matcher;
        if ( (matcher = Utils.getMatcher(DeckMenuRegexes.HANDLE_CARD_SIDE_FIRST_PATTERN.getRegex(), command)).find())
            return matcher;
        if ( (matcher = Utils.getMatcher(DeckMenuRegexes.HANDLE_CARD_SIDE_SECOND_PATTERN.getRegex(), command)).find())
            return matcher;
        if ( (matcher = Utils.getMatcher(DeckMenuRegexes.HANDLE_CARD_SIDE_THIRD_PATTERN.getRegex(), command)).find())
            return matcher;
        if ( (matcher = Utils.getMatcher(DeckMenuRegexes.HANDLE_CARD_SIDE_FOURTH_PATTERN.getRegex(), command)).find())
            return matcher;
        if ( (matcher = Utils.getMatcher(DeckMenuRegexes.HANDLE_CARD_SIDE_FIFTH_PATTERN.getRegex(), command)).find())
            return matcher;
        if ( (matcher = Utils.getMatcher(DeckMenuRegexes.HANDLE_CARD_SIDE_SIXTH_PATTERN.getRegex(), command)).find())
            return matcher;
        if ( (matcher = Utils.getMatcher(DeckMenuRegexes.HANDLE_CARD_MAIN_FIRST_PATTERN.getRegex(), command)).find())
            return matcher;
        if ( (matcher = Utils.getMatcher(DeckMenuRegexes.HANDLE_CARD_MAIN_SECOND_PATTERN.getRegex(), command)).find())
            return matcher;

        return null;
    }

    private boolean isMainHandleCardPattern(String command) {
        return !Utils.getMatcher(DeckMenuRegexes.HANDLE_CARD_SIDE_FIRST_PATTERN.getRegex(), command).find() &&
                !Utils.getMatcher(DeckMenuRegexes.HANDLE_CARD_SIDE_SECOND_PATTERN.getRegex(), command).find() &&
                !Utils.getMatcher(DeckMenuRegexes.HANDLE_CARD_SIDE_THIRD_PATTERN.getRegex(), command).find() &&
                !Utils.getMatcher(DeckMenuRegexes.HANDLE_CARD_SIDE_FOURTH_PATTERN.getRegex(), command).find() &&
                !Utils.getMatcher(DeckMenuRegexes.HANDLE_CARD_SIDE_FIFTH_PATTERN.getRegex(), command).find() &&
                !Utils.getMatcher(DeckMenuRegexes.HANDLE_CARD_SIDE_SIXTH_PATTERN.getRegex(), command).find();
    }

    private DeckMenuMessages checkAddCard(String cardName, String deckName, boolean isMain) {
        Card card = loggedInPlayer.getCardByNameFromBoughtCards(cardName);
        if (card == null) {
            DeckMenuMessages.setUnavailableCard(cardName);
            return DeckMenuMessages.UNAVAILABLE_CARD;
        }

        Deck deck = loggedInPlayer.getDeckByName(deckName);
        if (deck == null) {
            DeckMenuMessages.setUnavailableDeck(deckName);
            return DeckMenuMessages.UNAVAILABLE_DECK;
        }

        if (isMain && deck.isMainDeckFull()) return DeckMenuMessages.FULL_MAIN_DECK;
        if (!isMain && deck.isSideDeckFull()) return DeckMenuMessages.FULL_SIDE_DECK;

        if (deck.isThreeCardsAvailable(cardName)) {
            DeckMenuMessages.setThreeCardsAvailable(cardName, deckName);
            return DeckMenuMessages.THREE_CARDS_AVAILABLE;
        }


        if (isMain) deck.addCardToMainDeck(card);
        else deck.addCardToSideDeck(card);
        loggedInPlayer.removeCardFromBoughtCards(card);
        Database.updatePlayerInformationInDatabase(loggedInPlayer);
        return DeckMenuMessages.CARD_ADDED;
    }

    private DeckMenuMessages removeCard(String command) {
        Matcher matcher = findHandleCardMatcher(command);
        if (matcher == null) return DeckMenuMessages.INVALID_COMMAND;

        String cardName, deckName;
        boolean isMain = isMainHandleCardPattern(command);
        cardName = matcher.group("cardName");
        deckName = matcher.group("deckName");

        return checkRemoveCard(cardName, deckName, isMain);
    }

    private DeckMenuMessages checkRemoveCard(String cardName, String deckName, boolean isMain) {
        Deck deck = loggedInPlayer.getDeckByName(deckName);
        if (deck == null) {
            DeckMenuMessages.setUnavailableDeck(deckName);
            return DeckMenuMessages.UNAVAILABLE_DECK;
        }

        Card removedCard;
        if (isMain) removedCard = deck.removeCardFromMainDeck(cardName);
        else removedCard = deck.removeCardFromSideDeck(cardName);

        if (removedCard == null) {
            if (isMain) {
                DeckMenuMessages.setUnavailableCardInMainDeck(cardName);
                return DeckMenuMessages.UNAVAILABLE_CARD_IN_MAIN_DECK;
            } else {
                DeckMenuMessages.setUnavailableCardInSideDeck(cardName);
                return DeckMenuMessages.UNAVAILABLE_CARD_IN_SIDE_DECK;
            }
        }


        loggedInPlayer.getBoughtCards().add(removedCard);
        Database.updatePlayerInformationInDatabase(loggedInPlayer);
        return DeckMenuMessages.CARD_REMOVED;
    }

    private DeckMenuMessages showADeck(String command) {
        Matcher matcher = findShowCardMatcher(command);
        if (matcher == null) return DeckMenuMessages.INVALID_COMMAND;

        String deckName = matcher.group(1);
        boolean isMain = isMainShowCardPattern(command);

        Deck deck = loggedInPlayer.getDeckByName(deckName);
        if (deck == null) {
            DeckMenuMessages.setUnavailableDeck(deckName);
            return DeckMenuMessages.UNAVAILABLE_DECK;
        }

        DeckMenuView.showADeck(deck, isMain);
        return DeckMenuMessages.EMPTY;
    }

    private Matcher findShowCardMatcher(String command) {
        Matcher matcher;
        if ( (matcher = Utils.getMatcher(DeckMenuRegexes.SHOW_SIDE_DECK_FIRST_PATTERN.getRegex(), command)).find())
            return matcher;
        if ( (matcher = Utils.getMatcher(DeckMenuRegexes.SHOW_SIDE_DECK_SECOND_PATTERN.getRegex(), command)).find())
            return matcher;
        if ( (matcher = Utils.getMatcher(DeckMenuRegexes.SHOW_MAIN_DECK.getRegex(), command)).find())
            return matcher;

        return null;
    }

    private boolean isMainShowCardPattern(String command) {
        return !Utils.getMatcher(DeckMenuRegexes.SHOW_SIDE_DECK_FIRST_PATTERN.getRegex(), command).find() &&
                !Utils.getMatcher(DeckMenuRegexes.SHOW_SIDE_DECK_SECOND_PATTERN.getRegex(), command).find();
    }
}
