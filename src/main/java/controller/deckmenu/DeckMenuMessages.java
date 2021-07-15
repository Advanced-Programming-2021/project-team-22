package controller.deckmenu;

public enum DeckMenuMessages {
    AVAILABLE_DECK("deck with name <deck name> already exists"),
    INVALID_DECK_NAME("your entered deck name is invalid"),
    DECK_CREATED("deck created successfully!"),
    UNAVAILABLE_SELECTION("you didn't select any deck yet"),
    DECK_DELETED("deck deleted successfully"),
    DECK_ACTIVATED("deck activated successfully"),
    FULL_MAIN_DECK("main deck is full"),
    FULL_SIDE_DECK("side deck is full"),
    THREE_CARDS_AVAILABLE("there are already three cards with name <card name> in deck <deck name>"),
    ONE_CARD_AVAILABLE("there are already one card with name <card name> in deck <deck name>"),
    CARD_ADDED("card added to deck successfully"),
    CARD_REMOVED("card removed form deck successfully");

    private String message;

    DeckMenuMessages(String message) {
        this.message = message;
    }

    public static void setAvailableDeck(String deckName) {
        AVAILABLE_DECK.message = "deck with name " + deckName + " already exists\n";
    }

    public static void setThreeCardsAvailable(String cardName, String deckName) {
        THREE_CARDS_AVAILABLE.message = "there are already three cards with name " + cardName + " in deck " + deckName + "\n";
    }

    public static void setOneCardAvailable(String cardName, String deckName) {
        ONE_CARD_AVAILABLE.message = "there are already one card with name " + cardName + " in deck " + deckName + "\n";
    }

    public String getMessage() {
        return message;
    }
}
