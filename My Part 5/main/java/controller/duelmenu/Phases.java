package controller.duelmenu;

public enum Phases {
    DRAW_PHASE,
    STANDBY_PHASE,
    MAIN_PHASE_1,
    BATTLE_PHASE,
    MAIN_PHASE_2,
    END_PHASE;
    private static Phases[] values = values();

    public Phases next() {
        return values[(this.ordinal() + 1) % values.length];
    }
}
