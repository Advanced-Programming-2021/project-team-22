package controller.duelmenu;

import model.Player;

public enum DuelMenuMessages {
    INVALID_NAVIGATION("menu navigation is not possible\n"),
    SHOW_MENU("Duel Menu\n"),
    MINI_GAME_INVALID_CHOICE("please enter a valid option\n"),
    DRAW("draw\nplease try again:\n"),
    SHOW_TURN_PLAYER("<username> should start first\n"),
    INVALID_SELECTION("invalid selection\n"),
    CARD_SELECTED("card selected\n"),
    CARD_NOT_FOUND("no card found in the given position\n"),
    UNAVAILABLE_SELECTED_CARD("no card is selected yet\n"),
    NOT_SPELL_CARD("activate effect is only for spell cards.\n"),
    NOT_TRAP_CARD("activate effect is only for trap cards.\n"),
    CANT_ACTIVATE_SPELL_EFFECT("you can’t activate an effect on this turn\n"),
    CARD_ACTIVATED_BEFORE("you have already activated this card\n"),
    FULL_MAGICS_ZONE("spell card zone is full\n"),
    UNDONE_PREPARATIONS("preparations of this spell are not done yet\n"),
    SET_IN_THIS_TURN("you set this card in this turn so you can't activate it\n"),
    SPELL_ACTIVATED("spell activated\n"),
    TRAP_ACTIVATED("trap activated\n"),
    NOT_OWNER("you aren't owner of selected card\n"),
    CANT_SET("you can’t set this card\n"),
    NOT_TRUE_PHASE("you can’t do this action in this phase\n"),
    SET_SUCCESSFULLY("set successfully\n"),
    NOT_SELECTED_CARD("no card is selected yet\n"),
    ATTACKED_BEFORE("this card already attacked\n"),
    NO_CARD_FOUND_IN_THE_POSITION("no card found in the given position\n"),
    CANT_ATTACK_WITH_CARD("you can’t attack with this card\n"),
    YOU_CANT_ATTACK_TO_THIS_CARD("you cant attack to this card\n"),
    ATTACK_CANCELED("attack to this card was canceled\n"),
    DIRECT_ATTACK_DONE("you opponent receives <damage> battle damage\n"),
    DH_EQUAL_DAMAGE("opponent’s monster card was <monster card name> and no card is destroyed\n"),
    DESELECTED("card deselected\n"),
    OPPONENT_GOT_DAMAGE_IN_ATTACK("your opponent’s monster is destroyed and your opponent receives <damage> battle damage\n"),
    ATTACKING_PLAYER_CARD_DESTROYED("Your monster card is destroyed and you received <damage> battle damage\n"),
    DEFENSE_POSITION_MONSTER_DESTROYED("the defense position monster is destroyed\n"),
    NO_CARD_DESTROYED("no card is destroyed\n"),
    RECEIVE_DAMAGE_BY_ATTACKING_TO_DEFENSE_CARD("no card is destroyed and you received <damage> battle damage\n"),
    BOTH_CARDS_GET_DESTROYED("both you and your opponent monster cards are destroyed and no one receives damage\n"),
    WRONG_NICKNAME_CHEAT_CODE("your entered nickname is wrong\n"),
    UNAVAILABLE_CARD_CHEAT_CODE("your entered card name is unavailable\n"),
    INVALID_COMMAND_CHEAT_CODE("invalid command\n"),
    ACTION_CANCELED_BY_TRAP_CARD("action canceled by a trap card\n"),
    INVISIBLE_CARD("card is not visible\n"),
    SUMMONED_SUCCESSFULLY("summoned successfully\n"),
    SUMMON_NOT_POSSIBLE("action not allowed in this phase\n"),
    FULL_MONSTERS_ZONE("monster card zone is full\n"),
    ALREADY_SUMMONED_OR_SET("you already summoned/set on this turn\n"),
    NOT_ENOUGH_CARD_FOR_TRIBUTE("there are not enough cards for tribute\n"),
    NO_MONSTER_ON_THIS_ADDRESS("there no monsters one this address\n"),
    CANT_CHANGE_POSITION("you can’t change this card position\n"),
    ALREADY_IN_WANTED_POSITION("this card is already in the wanted position\n"),
    POSITION_CHANGED_BEFORE("you already changed this card position in this turn\n"),
    POSITION_CHANGED("monster card position changed successfully\n"),
    ENTER_MAIN_MENU(""),
    PLAY_ANOTHER_TURN(""),
    EMPTY(""),
    INVALID_COMMAND("invalid command\n");

    private String message;

    DuelMenuMessages(String message) {
        this.message = message;
    }

    public static void setShowTurnPlayer(Player player) {
        SHOW_TURN_PLAYER.message = player.getUsername() + " should start first\n";
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

    public static void setDHEqualDamage(String cardName) {
        DuelMenuMessages.DH_EQUAL_DAMAGE.message = "opponent’s monster card was " + cardName + " and no card is destroyed\n";
    }

    public String getMessage() {
        return message;
    }
}
