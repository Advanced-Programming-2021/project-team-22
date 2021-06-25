package controller.deckmenu;

public enum DeckMenuMessages {
    INVALID_NAVIGATION("menu navigation is not possible\n"),
    EXIT_DECK_MENU(""),
    SHOW_MENU("Deck Menu\n"),
    AVAILABLE_DECK("deck with name <deck name> already exists\n"),
    DECK_CREATED("deck created successfully!\n"),
    UNAVAILABLE_DECK("deck with name <deck name> does not exist\n"),
    DECK_DELETED("deck deleted successfully\n"),
    DECK_ACTIVATED("deck activated successfully\n"),
    UNAVAILABLE_CARD("card with name <card name> does not exist\n"),
    FULL_MAIN_DECK("main deck is full\n"),
    FULL_SIDE_DECK("side deck is full\n"),
    THREE_CARDS_AVAILABLE("there are already three cards with name <card name> in deck <deck name>\n"),
    CARD_ADDED("card added to deck successfully\n"),
    UNAVAILABLE_CARD_IN_MAIN_DECK("card with name <card name> does not exist in main deck\n"),
    UNAVAILABLE_CARD_IN_SIDE_DECK("card with name <card name> does not exist in side deck\n"),
    CARD_REMOVED("card removed form deck successfully\n"),
    SHOW_ALL_DECKS(""),
    SHOW_ALL_CARDS(""),
    EMPTY(""),
    INVALID_COMMAND("invalid command\n");

    private String message;

    DeckMenuMessages(String message) {
        this.message = message;
    }

    public static void setAvailableDeck(String deckName) {
        AVAILABLE_DECK.message = "deck with name " + deckName + " already exists\n";
    }

    public static void setUnavailableDeck(String deckName) {
        UNAVAILABLE_DECK.message = "deck with name " + deckName + " does not exist\n";
    }

    public static void setUnavailableCard(String cardName) {
        UNAVAILABLE_CARD.message = "card with name " + cardName + " does not exist\n";
    }

    public static void setThreeCardsAvailable(String cardName, String deckName) {
        THREE_CARDS_AVAILABLE.message = "there are already three cards with name " + cardName + " in deck " + deckName + "\n";
    }

    public static void setUnavailableCardInMainDeck(String cardName) {
        UNAVAILABLE_CARD_IN_MAIN_DECK.message = "card with name " + cardName + " does not exist in main deck\n";
    }

    public static void setUnavailableCardInSideDeck(String cardName) {
        UNAVAILABLE_CARD_IN_SIDE_DECK.message = "card with name " + cardName + " does not exist in side deck\n";
    }

    public String getMessage() {
        return message;
    }
}
