package view;

import controller.duelmenu.DuelMenuController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import model.Player;

import java.awt.*;
import java.net.URL;

public class DefineStarter1 extends Application {
    static Pane root;
    public static DuelMenuView duelMenuView;
    private Stage primaryStage;
    static Player firstPlayer;

    public DefineStarter1() {
    }

    public DefineStarter1(Player firstPlayer, Player secondPlayer, int numberOfRounds) {
        DefineStarter1.firstPlayer = firstPlayer;
        System.out.println(firstPlayer.getUsername());
    //    duelMenuView = new DuelMenuView(firstPlayer, secondPlayer, numberOfRounds);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        set();
    }

    public void set() {
        URL url = getClass().getResource("/view/fxml/duelmenu/defineStarter.fxml");
        try {
            root = FXMLLoader.load(url);
        } catch (Exception e) {
            System.out.println("cant load board!");
        }

        Scene scene = new Scene(root, 600, 600);
        primaryStage.setScene(scene);
        primaryStage.setMinHeight(0);
        primaryStage.setMinWidth(0);
        primaryStage.sizeToScene();
        primaryStage.show();
        Rectangle rectangle1 = (Rectangle) scene.lookup("#rectangle1");
        Rectangle rectangle2 = (Rectangle) scene.lookup("#rectangle2");
        Rectangle rectangle3 = (Rectangle) scene.lookup("#rectangle3");
        Label label = (Label) scene.lookup("#lableName");
      //  System.out.println(firstPlayer.getNickname());
        label.setText(duelMenuView.getFirstPlayer().getNickname());
        Image img = new Image(getClass().getResource("/images/duelmenu/paper.bmp").toExternalForm());
        rectangle1.setFill(new ImagePattern(img));
        img = new Image(getClass().getResource("/images/duelmenu/sang.bmp").toExternalForm());
        rectangle2.setFill(new ImagePattern(img));
        img = new Image(getClass().getResource("/images/duelmenu/sward.bmp").toExternalForm());
        rectangle3.setFill(new ImagePattern(img));
        scene.lookup("#rectangle1").addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        System.out.println("AAAAAAAAA");
                        DuelMenuController.setFirstPlayerChoiceInString("paper");
                        DefineStarter2 defineStarter2 = new DefineStarter2();
                        try {
                            defineStarter2.start(primaryStage);
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }
                    }
                });
        scene.lookup("#rectangle2").addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        System.out.println("AAAAAAAAA");

                        DuelMenuController.setFirstPlayerChoiceInString("stone");
                        DefineStarter2 defineStarter2 = new DefineStarter2();
                        try {
                            defineStarter2.start(primaryStage);
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }
                    }
                });
        scene.lookup("#rectangle3").addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        System.out.println("AAAAAAAAA");

                        DuelMenuController.setFirstPlayerChoiceInString("scissor");
                        DefineStarter2 defineStarter2 = new DefineStarter2();
                        try {
                            defineStarter2.start(primaryStage);
                            System.out.println("BBBBB");
                        } catch (Exception exception) {
                            System.out.println("CCCC");
                            exception.printStackTrace();
                        }
                    }
                });
    }
}
