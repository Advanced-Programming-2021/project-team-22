package controller;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import java.io.File;
import java.io.IOException;

public class MenuTest {
    @BeforeAll
    static void prepareGame() {
        Database.prepareGame();
    }

    @AfterAll
    static void deletePlayersDirectory() throws IOException {
        FileUtils.deleteDirectory(new File("/Users/AMF/Desktop/Yu-Gi-Oh game/Yu-Gi-Oh game/src/database/players"));
    }
}