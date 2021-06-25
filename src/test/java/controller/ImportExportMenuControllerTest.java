package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import controller.importexportmenu.ImportExportMenuController;
import controller.importexportmenu.ImportExportMenuMessages;
import model.cards.Card;
import org.apache.commons.io.FileUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

class ImportExportMenuControllerTest extends MenuTest {

    @Test
    void findCommandEnterAMenuMethod() {
        ImportExportMenuMessages result = ImportExportMenuController.findCommand("menu enter Login Menu ");
        Assertions.assertEquals(ImportExportMenuMessages.INVALID_COMMAND, result);

        result = ImportExportMenuController.findCommand("menu enter LOGIN Menu");
        Assertions.assertEquals(ImportExportMenuMessages.INVALID_NAVIGATION, result);

        result = ImportExportMenuController.findCommand("menu enter main Menu");
        Assertions.assertEquals(ImportExportMenuMessages.INVALID_NAVIGATION, result);

        result = ImportExportMenuController.findCommand("menu enter DuEl MEnu");
        Assertions.assertEquals(ImportExportMenuMessages.INVALID_NAVIGATION, result);

        result = ImportExportMenuController.findCommand("menu enter DECk MeNu");
        Assertions.assertEquals(ImportExportMenuMessages.INVALID_NAVIGATION, result);

        result = ImportExportMenuController.findCommand("menu enter Scoreboard MENU");
        Assertions.assertEquals(ImportExportMenuMessages.INVALID_NAVIGATION, result);

        result = ImportExportMenuController.findCommand("menu enter profilE MeNU");
        Assertions.assertEquals(ImportExportMenuMessages.INVALID_NAVIGATION, result);

        result = ImportExportMenuController.findCommand("menu enter ShoP Menu");
        Assertions.assertEquals(ImportExportMenuMessages.INVALID_NAVIGATION, result);

        result = ImportExportMenuController.findCommand("menu enter ImportExport Menu");
        Assertions.assertEquals(ImportExportMenuMessages.INVALID_NAVIGATION, result);
    }

    @Test
    void findCommandTestSingleCommands() {
        ImportExportMenuMessages result = ImportExportMenuController.findCommand("menu exit");
        Assertions.assertEquals(ImportExportMenuMessages.EXIT_IMPORT_EXPORT_MENU, result);

        result = ImportExportMenuController.findCommand("menu show-current");
        Assertions.assertEquals(ImportExportMenuMessages.SHOW_MENU, result);

        result = ImportExportMenuController.findCommand("menu");
        Assertions.assertEquals(ImportExportMenuMessages.INVALID_COMMAND, result);
    }

    @Test
    void findCommandImportCardMethod() {
        ImportExportMenuMessages result = ImportExportMenuController.findCommand("import cardBattle OX ");
        Assertions.assertEquals(ImportExportMenuMessages.INVALID_COMMAND, result);

        result = ImportExportMenuController.findCommand("import card Battle OX");
        Assertions.assertEquals(ImportExportMenuMessages.UNAVAILABLE_FILE, result);

        createCardJsonFile("Battle OX");
        result = ImportExportMenuController.findCommand("import card Battle OX");
        Assertions.assertEquals(ImportExportMenuMessages.AVAILABLE_CARD, result);

        Card.getAllCards().remove("Battle OX");
        removeNameValueFromJsonCardFile("Battle OX");
        result = ImportExportMenuController.findCommand("import card Battle OX");
        Assertions.assertEquals(ImportExportMenuMessages.INVALID_FILE, result);

        Database.prepareGame();
        createCardJsonFile("Battle OX");
        Card.getAllCards().remove("Battle OX");
        result = ImportExportMenuController.findCommand("import card Battle OX");
        Assertions.assertEquals(ImportExportMenuMessages.EMPTY, result);

        FileUtils.deleteQuietly(new File("/Users/AMF/Desktop/Yu-Gi-Oh game/finished work/phase 1/phase 1 code/src/main/resources/cards/Battle OX.json"));

        createCardJsonFile("Trap Hole");
        Card.getAllCards().remove("Trap Hole");
        removeNameValueFromJsonCardFile("Trap Hole");
        result = ImportExportMenuController.findCommand("import card Trap Hole");
        Assertions.assertEquals(ImportExportMenuMessages.INVALID_FILE, result);

        Database.prepareGame();
        createCardJsonFile("Trap Hole");
        Card.getAllCards().remove("Trap Hole");
        result = ImportExportMenuController.findCommand("import card Trap Hole");
        Assertions.assertEquals(ImportExportMenuMessages.EMPTY, result);

        FileUtils.deleteQuietly(new File("/Users/AMF/Desktop/Yu-Gi-Oh game/finished work/phase 1/phase 1 code/src/main/resources/cards/Trap Hole.json"));
    }

    void createCardJsonFile(String cardName) {
        try {
            FileWriter fileWriter = new FileWriter("src/main/resources/cards/" + cardName + ".json");
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            fileWriter.write(gson.toJson(Card.getCardByName(cardName)));
            fileWriter.close();
        } catch (IOException ignored) {
        }
    }

    void removeNameValueFromJsonCardFile(String cardName) {
        JSONParser parser = new JSONParser();
        try {
            Object object = parser.parse(new FileReader("src/main/resources/cards/" + cardName + ".json"));
            JSONObject jsonObject = (JSONObject) object;
            jsonObject.remove("name");
            FileWriter fileWriter = new FileWriter("src/main/resources/cards/" + cardName + ".json");
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            fileWriter.write(gson.toJson(jsonObject));
            fileWriter.close();
        } catch (Exception ignored) {
        }
    }

    @Test
    void findCommandExportCardMethod() {
        ImportExportMenuMessages result = ImportExportMenuController.findCommand("export cardBattle OX ");
        Assertions.assertEquals(ImportExportMenuMessages.INVALID_COMMAND, result);

        result = ImportExportMenuController.findCommand("export card A");
        Assertions.assertEquals(ImportExportMenuMessages.UNAVAILABLE_CARD, result);

        result = ImportExportMenuController.findCommand("export card Battle OX");
        Assertions.assertEquals(ImportExportMenuMessages.EMPTY, result);

        FileUtils.deleteQuietly(new File("/Users/AMF/Desktop/Yu-Gi-Oh game/finished work/phase 1/phase 1 code/src/main/resources/cards/Battle OX.json"));
    }
}