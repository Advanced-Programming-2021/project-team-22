package controller.deckmenu;

import controller.loginmenu.LoginMenuController;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class DeckMenuControllerTest {

    @BeforeAll
    static void createPlayer() {
        LoginMenuController.findCommand("user create --username Apple --P :) --nickname Great");
    }

    @Test
    void separateKindOfCards() {
    }

    @Test
    void findCommand() {
    }
}