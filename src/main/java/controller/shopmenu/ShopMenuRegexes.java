package controller.shopmenu;

public enum ShopMenuRegexes {
    ENTER_A_MENU("^menu enter (?i)(Login|Main|Duel|Deck|Scoreboard|Profile|Shop|ImportExport) Menu$"),
    BUY_CARD("^shop buy ([^\n]+)$"),
    CHEAT_INCREASE_MONEY("^increase --money ([0-9]+)$");

    private final String regex;

    ShopMenuRegexes(String regex) {
        this.regex = regex;
    }

    public String getRegex() {
        return regex;
    }
}
