package view;

import controller.Utils;
import controller.scoreboardmenu.ScoreboardMenuController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Player;

import java.util.ArrayList;
import java.util.Objects;

public class ScoreboardMenuView extends Application {
    public TableView<Player> tableView;
    public Button backButton;
    public ImageView imageView;

    public static void showScoreboard(ArrayList<Player> allPlayers) {
        int rank = 1;
        for (int i = 0; i < allPlayers.size(); i++) {
            Player player = allPlayers.get(i);
            if (i != 0 && player.getScore() != allPlayers.get(i - 1).getScore()) rank = i + 1;
            System.out.println(rank + "- " + player.getNickname() + ": " + player.getScore());
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        BorderPane root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/fxml/ScoreboardMenu.fxml")));
        Scene scene = new Scene(root, 1280, 800);
        stage.setMinHeight(scene.getHeight() + 28 /*title bar height*/);
        stage.setMinWidth(scene.getWidth());
        stage.setScene(scene);
        stage.sizeToScene();
    }

    @FXML
    public void initialize() {
        createTableView();
        handleImageView();

        Utils.handleBackButton(backButton);
    }

    private void createTableView() {
        ArrayList<Player> allPlayers = ScoreboardMenuController.sortAllPlayers();

        TableColumn<Player, String> rankColumn = createColumn("rank", false);
        TableColumn<Player, String> nicknameColumn = createColumn("nickname", true);
        TableColumn<Player, String> scoreColumn = createColumn("score", true);


        scoreColumn.setPrefWidth(100);
        double nicknameColumnPrefWidth = Utils.getStage().getWidth() - 200 - nicknameColumn.getPrefWidth() -
                scoreColumn.getPrefWidth() - 20;
        nicknameColumn.setPrefWidth(nicknameColumnPrefWidth);

        tableView.getColumns().addAll(rankColumn, nicknameColumn, scoreColumn);
        tableView.setEditable(false);

        for (int i = 0; i < allPlayers.size() && allPlayers.get(i).getRank() <= 20; i++) {
            tableView.getItems().add(allPlayers.get(i));
        }

        changeLoggedInPlayerRowColor(nicknameColumn);
    }

    private TableColumn<Player, String> createColumn(String name, boolean sortable) {
        TableColumn<Player, String> column = new TableColumn<>(name);
        column.setCellValueFactory(new PropertyValueFactory<>(name));
        column.setResizable(false);
        column.setReorderable(false);
        column.setSortable(sortable);
        return column;
    }

    private void changeLoggedInPlayerRowColor(TableColumn<Player, String> tableColumn) {
        Callback<TableColumn<Player, String>, TableCell<Player, String>> cellFactory =
                new Callback<>() {
                    @Override
                    public TableCell<Player, String> call(final TableColumn<Player, String> param) {

                        return new TableCell<>() {

                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {
                                    setText(item);
                                    TableRow<Player> row = getTableRow();

                                    row.getStyleClass().clear();
                                    row.getStyleClass().add("table-row-cell");

                                    if (row.getItem().getNickname().equals(ScoreboardMenuController.getLoggedInPlayer().getNickname())) {
                                        row.getStyleClass().clear();
                                        row.getStyleClass().add("loggedInPlayerTableRowCell");
                                    }
                                }
                            }
                        };
                    }
                };

        tableColumn.setCellFactory(cellFactory);
    }

    private void handleImageView() {
        double randomNumber = Math.random() * 3;
        if (randomNumber < 1) {
            imageView.setImage(new Image("images/scoreboardmenu/Cutin001.dds9.png"));
        } else if (randomNumber < 2) {
            imageView.setImage(new Image("images/scoreboardmenu/Cutin001.dds16.png"));
        } else {
            imageView.setImage(new Image("images/scoreboardmenu/Cutin001.dds24.png"));
        }
    }
}
