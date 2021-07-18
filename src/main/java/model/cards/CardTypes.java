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

    private final String label;

    CardTypes(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
