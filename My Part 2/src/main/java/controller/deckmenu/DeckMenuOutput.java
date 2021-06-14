package controller.deckmenu;

public class DeckMenuOutput {

    private DeckMenuOutput() {
    }

    private static DeckMenuOutput instance;

    public static DeckMenuOutput getInstance() {
        if (instance == null)
            instance = new DeckMenuOutput();
        return instance;
    }

    public void showMessage(String message) {

        System.out.print(message + "\n");

    }


}
