package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.dao.DaoManager;
import model.dao.StatsDao;
import model.dao.UserDao;
import model.entity.Stats;
import model.entity.User;
import model.frontend.Login;
import model.frontend.PlayGame;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Class that shows the interface where players
 * can fill in their nicknames
 *
 * Dataflow Anomalies have been suppressed in order
 * to keep the ux logic intact.
 */
@SuppressWarnings("PMD.DataflowAnomalyAnalysis")
public class NicknameController extends Login implements Initializable {
    @FXML
    private transient AnchorPane nicknamePane;
    @FXML
    private transient TextField textNickname;
    @FXML
    private transient CheckBox optNickname;

    private transient User newUser;
    private static transient PlayGame game;

    static {
        try {
            game = new PlayGame(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Play the game!
     * @param event when the key is pressed.
     * @throws Exception this is for any Exception.
     */
    public void play(final ActionEvent event) throws Exception {
        if (!optNickname.isSelected()) {
            try {
                game.play();
            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        } else {
            textNickname.setDisable(false);
            if (textNickname.getText().length() > 0) {
                try {
                    DaoManager daoManager = new DaoManager();
                    UserDao userDao = (UserDao) daoManager.getDao(DaoManager.Table.USER);
                    newUser = new User(textNickname.getText());
                    newUser.setHashedPassword(user.getHashedPassword());
                    newUser.setUserId(userDao.getLastUserIdAdded() + 1);
                    newUser.setStats(new Stats(newUser.getUserId()));

                    StatsDao statsDao = (StatsDao) daoManager.getDao(DaoManager.Table.STATS);
                    Stats stat = new Stats(newUser.getUserId());
                    stat.setTotalPoints(0);
                    stat.setBlocksWalked(0);
                    stat.setPelletsEaten(0);
                    stat.setGamesPlayed(0);
                    stat.setGamesWon(0);
                    stat.setMaxLevel(0);
                    stat.setMaxScore(0);

                    if (userDao.save(newUser)) {
                        statsDao.save(stat);
                        daoManager.close();
                        game = new PlayGame(newUser);
                        game.play();
                    }

                    daoManager.close();

                } catch (Exception e) {
                    e.printStackTrace();
                    throw e;
                }
            } else {
                Stage stage = new Stage();

                VBox vb = new VBox(17);
                vb.setMinHeight(50);
                vb.setMinWidth(250);

                Scene scene = new Scene(vb);
                stage.setResizable(false);
                stage.setTitle("PacMan");
                stage.setScene(scene);

                Label label = new Label("Please enter a nickname!");
                vb.getChildren().add(label);
                vb.setAlignment(Pos.CENTER);
                stage.show();
            }
        }
    }

    /**
     * Return to the Start Menu page.
     *
     * @param event when user clicks on the 'Return to the Start Menu!' button
     * @throws Exception This is for any Exception
     */
    public void returnToStart(final ActionEvent event) throws Exception {
        URL url = new File("src/main/java/view/StartMenu.fxml").toURI().toURL();
        AnchorPane startPane = FXMLLoader.load(url);
        nicknamePane.getChildren().setAll(startPane);
    }

    /**
     * Initializes the controller class.
     * @param url default
     * @param rb default
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        textNickname.setDisable(true);

        EventHandler<ActionEvent> event_handler = event -> {
            if (event.getSource() instanceof CheckBox) {
                CheckBox src = (CheckBox) event.getSource();
                if (src.isSelected()) {
                    textNickname.setDisable(false);
                } else {
                    textNickname.setDisable(true);
                }
            }
        };

        optNickname.setOnAction(event_handler);
    }
}
