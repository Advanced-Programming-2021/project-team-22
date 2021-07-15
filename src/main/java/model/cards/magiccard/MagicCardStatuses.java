package model.cards.magiccard;

import com.google.gson.annotations.SerializedName;

public enum MagicCardStatuses {
    @SerializedName("Limited")
    LIMITED("Limited"),
    @SerializedName("Unlimited")
    UNLIMITED("Unlimited");

    private final String label;

    MagicCardStatuses(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
