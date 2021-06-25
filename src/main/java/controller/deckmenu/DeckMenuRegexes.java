package controller.deckmenu;

public enum DeckMenuRegexes {
    CREATE_DECK("^deck create ([^\n]+)$"),
    DELETE_DECK("^deck delete ([^\n]+)$"),
    ACTIVATE_DECK("^deck set-activate ([^\n]+)$"),
    HANDLE_CARD_SIDE_FIRST_PATTERN("^deck (?:add|rm)-card --(?:card|C) (?<cardName>.+) --(?:deck|D) (?<deckName>.+) --(?:side|S)$"),
    HANDLE_CARD_SIDE_SECOND_PATTERN("^deck (?:add|rm)-card --(?:card|C) (?<cardName>.+) --(?:side|S) --(?:deck|D) (?<deckName>.+)$"),
    HANDLE_CARD_SIDE_THIRD_PATTERN("^deck (?:add|rm)-card --(?:deck|D) (?<deckName>.+) --(?:card|C) (?<cardName>.+) --(?:side|S)$"),
    HANDLE_CARD_SIDE_FOURTH_PATTERN("^deck (?:add|rm)-card --(?:deck|D) (?<deckName>.+) --(?:side|S) --(?:card|C) (?<cardName>.+)$"),
    HANDLE_CARD_SIDE_FIFTH_PATTERN("^deck (?:add|rm)-card --(?:side|S) --(?:card|C) (.+) --(?:deck|D) (?<deckName>.+)$"),
    HANDLE_CARD_SIDE_SIXTH_PATTERN("^deck (?:add|rm)-card --(?:side|S) --(?:deck|D) (?<deckName>.+) --(?:card|C) (?<cardName>.+)$"),
    HANDLE_CARD_MAIN_FIRST_PATTERN("^deck (?:add|rm)-card --(?:card|C) (?<cardName>.+) --(?:deck|D) (?<deckName>.+)$"),
    HANDLE_CARD_MAIN_SECOND_PATTERN("^deck (?:add|rm)-card --(?:deck|D) (?<deckName>.+) --(?:card|C) (?<cardName>.+)$"),
    SHOW_MAIN_DECK("^deck show --(?:deck-name|D) (.+)$"),
    SHOW_SIDE_DECK_FIRST_PATTERN("^deck show --(?:deck-name|D) (.+) --(?:side|S)$"),
    SHOW_SIDE_DECK_SECOND_PATTERN("^deck show --(?:side|S) --(?:deck-name|D) (.+)");

    private final String regex;

    DeckMenuRegexes(String regex) {
        this.regex = regex;
    }

    public String getRegex() {
        return regex;
    }
}
