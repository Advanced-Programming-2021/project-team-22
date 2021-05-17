package controller.duelmenu;

import model.Player;

public enum DuelMenuMessages {
    MINI_GAME_INVALID_CHOICE("please enter a valid option\n"),
    DRAW("draw\nplease try again:\n"),
    SHOW_TURN_PLAYER("<username> should start first\n"),
    SHOW_GRAVEYARD(""),
    INVALID_SELECTION("invalid selection\n"),
    CARD_SELECTED("card selected\n"),
    CARD_NOT_FOUND("no card found in the given position\n"),
    UNAVAILABLE_SELECTED_CARD("no card is selected yet\n"),
    NOT_SPELL_CARD("activate effect is only for spell cards.\n"),
    CANT_ACTIVATE_SPELL_EFFECT("you can’t activate an effect on this turn\n"),
    CARD_ACTIVATED_BEFORE("you have already activated this card\n"),
    FULL_MAGICS_ZONE("spell card zone is full\n"),
    UNDONE_PREPARATIONS("preparations of this spell are not done yet\n"),
    SPELL_ACTIVATED("spell activated\n"),
    NOT_OWNER("you aren't owner of selected card\n"),
    CANT_SET("you can’t set this card\n"),
    NOT_TRUE_PHASE("you can’t do this action in this phase\n"),
    SET_SUCCESSFULLY("set successfully"),

    EMPTY(""),
    INVALID_COMMAND("invalid command\n");

    private String message;

    DuelMenuMessages(String message) {
        this.message = message;
    }

    public static void setShowTurnPlayer(Player player) {
        SHOW_TURN_PLAYER.message = player.getUsername() + " should start first";
    }

    public String getMessage() {
        return message;
    }
}
