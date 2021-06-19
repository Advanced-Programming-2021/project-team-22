package controller.duelmenu;

import model.Player;

public enum DuelMenuMessages {
    MINI_GAME_INVALID_CHOICE("please enter a valid option\n"),
    DRAW("draw\nplease try again:\n"),
    SHOW_TURN_PLAYER("<username> should start first\n"),
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
    SET_SUCCESSFULLY("set successfully\n"),
    NOT_SELECTED_CARD("no card is selected yet\n"),
    ATTACKED_BEFORE("this card already attacked\n"),
    NOT_SUITABLE_PHASE("you can’t do this action in this phase\n"),
    INVALID_CARD_SELECT("invalid selection\n"),
    NO_CARD_FOUND_IN_THE_POSITION("no card found in the given position\n"),
    CANT_ATTACK_WITH_CARD("you can’t attack with this card\n"),
    YOU_CANT_ATTACK_TO_THIS_CARD("you cant attack to this card\n"),
    ATTACK_CANCELED("attack to this card was canceled\n"),
    DIRECT_ATTACK_DONE("you opponent receives <damage> battle damage\n"),
    DESELECTED("card deselected\n"),
    INVALID_COMMAND_CHEAT_CODE("invalid command\n"),
    OPPONENT_GOT_DAMAGE_IN_ATTACK("your opponent’s monster is destroyed and your opponent receives <damage> battle damage\n"),
    ATTACKING_PLAYER_CARD_DESTROYED("Your monster card is destroyed and you received <damage> battle damage\n"),
    DEFENSE_POSITION_MONSTER_DESTROYED("the defense position monster is destroyed\n"),
    NO_CARD_DESTROYED("no card is destroyed\n"),
    RECEIVE_DAMAGE_BY_ATTACKING_TO_DEFENSE_CARD("no card is destroyed and you received <damage> battle damage\n"),
    BOTH_CARDS_GET_DESTROYED("both you and your opponent monster cards are destroyed and no one receives damage\n"),
    EMPTY(""),
    NO_CARD_SELECTED("no card is selected yet\n"),
    SUMMON_NOT_POSSIBLE("you can’t summon this card\n"),
    FLIP_SUMMON_NOT_POSSIBLE("you can’t flip summon this card\n"),
    SET_NOT_POSSIBLE("you can’t set this card\n"),
    PLAYER_TURN("it's <turnPlayer.getNickname()> 's turn"),
    PHASE_IS_CHANGED("phase: <phase>\n"),
    ALREADY_SUMMONED_OR_SET("you already summoned/set on this turn\n"),
    NOT_ENOUGH_CARD_FOR_TRIBUTE("there are not enough card for tribute\n"),
    NO_MONSTER_ON_THIS_ADDRESS("there are no monster one this address\n"),
    MONSTER_ZONE_IS_FULL("monster card zone is full\n"),
    SUMMONED_SUCCESSFULLY("summoned successfully\n"),
    CHANG_POSITION_NOT_POSSIBLE("you can’t change this card position\n"),
    INVALID_COMMAND("invalid command\n");
    private String message;

    DuelMenuMessages(String message) {
        this.message = message;
    }

    public static void setShowTurnPlayer(Player player) {
        DuelMenuMessages.SHOW_TURN_PLAYER.message = player.getUsername() + " should start first\n";
    }

    public static void NotEnoughCardForTribute(){
        DuelMenuMessages.NOT_ENOUGH_CARD_FOR_TRIBUTE.message = "there are not enough card for tribute\n";
        System.out.print("there are not enough card for tribute\n");
    }

    public static void noCardSelected() {
        DuelMenuMessages.NO_CARD_SELECTED.message = "no card is selected yet";
    }

    public static void playerTurn(Player player) {
        DuelMenuMessages.PLAYER_TURN.message = "it's + player.getUsername() + 's turn";
    }

    public static void changephase(Phases phase) {
        DuelMenuMessages.PHASE_IS_CHANGED.message = "phase: " + phase;
    }

    public static void setDamageAmount(int damageAmount) {
        DuelMenuMessages.DIRECT_ATTACK_DONE.message = "you opponent receives " + damageAmount + " battle damage\n";
    }

    public static void setOpponentGotDamageInAttack(int damage) {
        DuelMenuMessages.OPPONENT_GOT_DAMAGE_IN_ATTACK.message =
                "your opponent’s monster is destroyed and your opponent receives" + damage + " battle damage\n";
    }

    public static void setAttackingPlayerCardDestroyed(int damage) {
        DuelMenuMessages.ATTACKING_PLAYER_CARD_DESTROYED.message =
                "Your monster card is destroyed and you received " + damage + " battle damage\n";
    }

    public static void setReceiveDamageByAttackingToDefenseCard(int damage) {
        DuelMenuMessages.RECEIVE_DAMAGE_BY_ATTACKING_TO_DEFENSE_CARD.message =
                "no card is destroyed and you received " + damage + " battle damage\n";
    }

    public String getMessage() {
        return message;
    }
}
