package model.cards.magiccard;

import com.google.gson.annotations.SerializedName;

public enum MagicCardStatuses {
    @SerializedName("Limited")
    LIMITED("Limited"),
    @SerializedName("Unlimited")
    UNLIMITED("Unlimited");

    private final String regex;

    MagicCardStatuses(String regex) {
        this.regex = regex;
    }

    public String getRegex() {
        return regex;
    }
}
