package model.cards;

import com.google.gson.annotations.SerializedName;

public enum CardTypes {
    @SerializedName("Normal")
    NORMAL("Normal"),
    @SerializedName("Effect")
    EFFECT("Effect"),
    @SerializedName("Ritual")
    RITUAL("Ritual"),
    @SerializedName("Spell")
    SPELL("Spell"),
    @SerializedName("Trap")
    TRAP("Trap");

    private final String regex;

    CardTypes(String regex) {
        this.regex = regex;
    }

    public String getRegex() {
        return regex;
    }
}
