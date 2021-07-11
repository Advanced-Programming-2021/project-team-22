package view;

import controller.profilemenu.ProfileMenuController;
import controller.profilemenu.ProfileMenuMessages;
import controller.Utils;
import controller.shopmenu.ShopMenuController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Player;
import model.cards.Card;

import java.util.Objects;

public class ProfileMenuView extends Application {
    public BorderPane borderPane;
    public Button backButton;
//    public void profileMenuView() {
//        ProfileMenuController profileMenuController = new ProfileMenuController(loggedInPlayer);
//        while (true) {
//            String command = Utils.getScanner().nextLine().trim();
//            ProfileMenuMessages result = profileMenuController.findCommand(command);
//
//            if (result.equals(ProfileMenuMessages.EXIT_PROFILE_MENU)) break;
//            else System.out.print(result.getMessage());
//        }
//    }

    @Override
    public void start(Stage stage) throws Exception {
        BorderPane root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/fxml/ProfileMenu.fxml")));
        Scene scene = new Scene(root, 1280, 800);
        stage.setMinHeight(scene.getHeight() + 28 /*title bar height*/);
        stage.setMinWidth(scene.getWidth());
        stage.setScene(scene);
    }

    @FXML
    public void initialize() {

        Utils.handleBackButton(backButton);

    }
}
