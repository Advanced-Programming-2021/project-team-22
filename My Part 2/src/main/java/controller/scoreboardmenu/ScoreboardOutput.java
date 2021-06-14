package controller.scoreboardmenu;

public class ScoreboardOutput {

    private ScoreboardOutput() {
    }

    private static ScoreboardOutput instance;

    public static ScoreboardOutput getInstance() {
        if (instance == null)
            instance = new ScoreboardOutput();
        return instance;
    }

    public void showMessage(String message) {

        System.out.print(message + "\n");

    }


}
