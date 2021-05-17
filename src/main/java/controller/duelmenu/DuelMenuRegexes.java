package controller.duelmenu;

public enum DuelMenuRegexes {
    SELECT_MONSTER_ZONE("^select --(?:monster|M) ((?:-|)\\d+)$"),
    SELECT_MAGIC_ZONE("^select --(?:spell|S) ((?:-|)\\d+)$"),
    SELECT_OPPONENT_MONSTER_ZONE_MONSTER_PATTERN("^select --(?:monster|M) --(?:opponent|O) ((?:-|)\\d+)$"),
    SELECT_OPPONENT_MONSTER_ZONE_OPPONENT_PATTERN("^select --(?:opponent|O) --(?:monster|M) ((?:-|)\\d+)$"),
    SELECT_OPPONENT_MAGIC_ZONE_SPELL_PATTERN("^select --(?:spell|S) --(?:opponent|O) ((?:-|)\\d+)$"),
    SELECT_OPPONENT_MAGIC_ZONE_OPPONENT_PATTERN("^select --(?:opponent|O) --(?:spell|S) ((?:-|)\\d+)$"),
    SELECT_FIELD_ZONE("^select --(?:field|F)$"),
    SELECT_OPPONENT_FIELD_ZONE_FIELD_PATTERN("^select --(?:field|F) --(?:opponent|O)$"),
    SELECT_OPPONENT_FIELD_ZONE_OPPONENT_PATTERN("^select --(?:opponent|O) --(?:field|F)$"),
    SELECT_CARDS_IN_HAND("^select --(?:hand|H) ((?:-|)\\d+)$");


    private final String regex;

    DuelMenuRegexes(String message) {
        this.regex = message;
    }

    public String getRegex() {
        return regex;
    }
}
