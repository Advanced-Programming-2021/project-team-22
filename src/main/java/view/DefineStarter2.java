package view;

import controller.duelmenu.DuelMenuController;
import controller.duelmenu.DuelMenuMessages;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.net.URL;

public class DefineStarter2 extends Application {
    Pane root;

    @Override
    public void start(Stage primaryStage) throws Exception {
        URL url = getClass().getResource("/view/fxml/duelmenu/defineStarter2.fxml");
        try {
            root = FXMLLoader.load(url);
        } catch (Exception e) {
            System.out.println("cant load board!");
        }

        Scene scene = new Scene(root, 600, 600);
        Rectangle rectangle1 = (Rectangle) scene.lookup("#rectangle1");
        Rectangle rectangle2 = (Rectangle) scene.lookup("#rectangle2");
        Rectangle rectangle3 = (Rectangle) scene.lookup("#rectangle3");
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
                        DuelMenuController.setSecondPlayerChoiceInString("paper");
                        DuelMenuController duelMenuController = new DuelMenuController();
                        DuelMenuView.setDuelMenuController(duelMenuController);

                        DuelMenuMessages resultOfInitialGame = null;
                        resultOfInitialGame = duelMenuController.initialGame(DefineStarter1.duelMenuView.getFirstPlayer(), DefineStarter1.duelMenuView.getSecondPlayer());
                        if (resultOfInitialGame == null || !resultOfInitialGame.equals(DuelMenuMessages.SHOW_TURN_PLAYER)) {
                            DefineStarter1 defineStarter1 = new DefineStarter1(DefineStarter1.duelMenuView.getFirstPlayer(), DefineStarter1.duelMenuView.getSecondPlayer(), DefineStarter1.duelMenuView.getNumberOfRounds());
                            try {
                                defineStarter1.start(primaryStage);
                            } catch (Exception exception) {
                                exception.printStackTrace();
                            }
                        } else {
                            try {
                                DefineStarter1.duelMenuView.start(primaryStage);
                            } catch (Exception exception) {
                                exception.printStackTrace();
                            }
                        }
                    }
                });
        scene.lookup("#rectangle2").addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        DuelMenuController.setSecondPlayerChoiceInString("stone");
                        DuelMenuController duelMenuController = new DuelMenuController();
                        DuelMenuView.setDuelMenuController(duelMenuController);

                        DuelMenuMessages resultOfInitialGame = null;
                        resultOfInitialGame = duelMenuController.initialGame(DefineStarter1.duelMenuView.getFirstPlayer(), DefineStarter1.duelMenuView.getSecondPlayer());
                        if (resultOfInitialGame == null || !resultOfInitialGame.equals(DuelMenuMessages.SHOW_TURN_PLAYER)) {
                            DefineStarter1 defineStarter1 = new DefineStarter1(DefineStarter1.duelMenuView.getFirstPlayer(), DefineStarter1.duelMenuView.getSecondPlayer(), DefineStarter1.duelMenuView.getNumberOfRounds());
                            try {
                                defineStarter1.start(primaryStage);
                            } catch (Exception exception) {
                                exception.printStackTrace();
                            }
                        } else {
                            try {
                                DefineStarter1.duelMenuView.start(primaryStage);
                            } catch (Exception exception) {
                                exception.printStackTrace();
                            }
                        }
                    }
                });
        scene.lookup("#rectangle3").addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        DuelMenuController.setSecondPlayerChoiceInString("scissor");
                        DuelMenuController duelMenuController = new DuelMenuController();
                        DuelMenuView.setDuelMenuController(duelMenuController);

                        DuelMenuMessages resultOfInitialGame = null;
                        resultOfInitialGame = duelMenuController.initialGame(DefineStarter1.duelMenuView.getFirstPlayer(), DefineStarter1.duelMenuView.getSecondPlayer());
                        if (resultOfInitialGame == null || !resultOfInitialGame.equals(DuelMenuMessages.SHOW_TURN_PLAYER)) {
                            DefineStarter1 defineStarter1 = new DefineStarter1(DefineStarter1.duelMenuView.getFirstPlayer(), DefineStarter1.duelMenuView.getSecondPlayer(), DefineStarter1.duelMenuView.getNumberOfRounds());
                            try {
                                defineStarter1.start(primaryStage);
                            } catch (Exception exception) {
                                exception.printStackTrace();
                            }
                        } else {
                            try {
                                DefineStarter1.duelMenuView.start(primaryStage);
                            } catch (Exception exception) {
                                exception.printStackTrace();
                            }
                        }
                    }
                });
    }
}
