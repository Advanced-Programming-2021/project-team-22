package controller.shopmenu;

public enum ShopMenuRegexes {
    BUY_CARD("^shop buy ([^\n]+)$"),
    CHEAT_INCREASE_MONEY("^increase --(?:money|M) ([0-9]+)$");

    private final String regex;

    ShopMenuRegexes(String regex) {
        this.regex = regex;
    }

    public String getRegex() {
        return regex;
    }
}
