package scoreboardmenu;

public class ScoreboardOutput {

    private ScoreboardOutput() {
    }

    private static scoreboardmenu.ScoreboardOutput instance;

    public static scoreboardmenu.ScoreboardOutput getInstance() {
        if (instance == null)
            instance = new scoreboardmenu.ScoreboardOutput();
        return instance;
    }

    public void showMessage(String message) {

        System.out.print(message + "\n");

    }


}
