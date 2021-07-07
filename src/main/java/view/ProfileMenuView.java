package view;

import controller.profilemenu.ProfileMenuController;
import controller.profilemenu.ProfileMenuMessages;
import controller.Utils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Popup;
import javafx.stage.Stage;
import model.Player;

import java.io.IOException;
import java.net.URL;

public class ProfileMenuView {
    private final Player loggedInPlayer;
    private static Stage stage1;
    private Pane root;
    @FXML
    private Button changeP;
    public TextField currentPass;
    public PasswordField newPass;
    private ProfileMenuController profileMenuController;

    public ProfileMenuView(Player loggedInPlayer) {
        this.loggedInPlayer = loggedInPlayer;
    }

    public void profileMenuView() {
        profileMenuController = new ProfileMenuController(loggedInPlayer);
//        while (true) {
//            String command = Utils.getScanner().nextLine().trim();
//            ProfileMenuMessages result = profileMenuController.findCommand(command);
//
//            if (result.equals(ProfileMenuMessages.EXIT_PROFILE_MENU)) break;
//            else System.out.print(result.getMessage());
//        }
        try {
            menu(null);
        } catch (Exception e) {
        }
    }


    public void menu(MouseEvent mouseEvent) throws IOException {
        URL url = getClass().getResource("/view/PPP.fxml");
        root = FXMLLoader.load(url);
        Scene scene = new Scene(root, 600, 600);
        Label usernameLabel = (Label) scene.lookup("#usernameLable");
        Label nicknameLabel = (Label) scene.lookup("#nicknameLable");
        ImageView profileImage = (ImageView) scene.lookup("#profileImage");
        usernameLabel.setText(loggedInPlayer.getUsername());
        nicknameLabel.setText(loggedInPlayer.getNickname());
        //TODO add image!  profileImage.setImage();
        stage1.setScene(scene);
        // changeP = (Button)findViewById(R.id.yourbuttonid);
        stage1.show();
        //  changeP.setVisible(false);

    }

    public void changePasswordMenu(MouseEvent mouseEvent) throws IOException {
        System.out.println("asdasdasd");
        URL url = getClass().getResource("/view/ChangePassword.fxml");
        root = FXMLLoader.load(url);
        Scene scene = new Scene(root, 600, 600);
        stage1.setScene(scene);
        stage1.show();
    }


    public void changeNickname(MouseEvent mouseEvent) throws IOException {
        System.out.println("asdasdasd");
        URL url = getClass().getResource("/view/ChangeNickname.fxml");
        root = FXMLLoader.load(url);
        Scene scene = new Scene(root, 600, 600);
        stage1.setScene(scene);
        stage1.show();
    }

    public void changeNicknameFunction(MouseEvent mouseEvent) {
        System.out.println("]]]]");
    }

    public void backToMainMenu(MouseEvent mouseEvent) throws IOException {
        System.out.println("backToMainMenu");
        menu(mouseEvent);
    }

    public void callChangePasswordFunction() {
        System.out.println("callChangePasswordFunction");
        //TODO call change password function in controller
        Popup popup = new Popup();
        // popup.getContent().add();
        EventHandler<ActionEvent> event =
                new EventHandler<ActionEvent>() {

                    public void handle(ActionEvent e) {
                        if (!popup.isShowing())
                            popup.show(stage1);
                        else
                            popup.hide();
                    }
                };
    }

    public void exitProfile(MouseEvent mouseEvent) {
        //TODO back to maain menu!
    }
}
