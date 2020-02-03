package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import model.dao.DaoManager;
import model.dao.StatsDao;
import model.entity.Stats;
import model.frontend.Login;
import model.frontend.PlayGame;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class GeneralStatisticsController extends Login implements Initializable {
    @FXML
    private transient AnchorPane statisticsPane;
    private static transient PlayGame game;
    @FXML
    private transient Text blocksWalked;
    @FXML
    private transient Text totalPoints;
    @FXML
    private transient Text pelletsEaten;
    @FXML
    private transient Text gamesPlayed;
    @FXML
    private transient Text gamesWon;
    @FXML
    private transient Text maxLevel;
    @FXML
    private transient Text maxScore;

    private transient DaoManager daoManager;


    static {
        try {
            game = new PlayGame(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to go back to the Login menu.
     * @param event when the user clicks on the button
     * @throws Exception this is for any Exception.
     */
    public void back(final ActionEvent event) throws Exception {
        URL url = new File("src/main/java/view/StartMenu.fxml").toURI().toURL();
        AnchorPane startPane = FXMLLoader.load(url);
        statisticsPane.getChildren().setAll(startPane);
    }

    /**
     * Button to play the game.
     *
     * @param event when user clicks on the 'Play Game!' button
     * @throws Exception This is for any Exception
     */
    public void enterNickname(final ActionEvent event) throws Exception {
        URL url = new File("src/main/java/view/Nickname.fxml").toURI().toURL();
        AnchorPane nicknamePane = FXMLLoader.load(url);
        statisticsPane.getChildren().setAll(nicknamePane);
    }

    /**
     * Initializes the controller class.
     * @param url default
     * @param rb default
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        daoManager = new DaoManager();

        try {
            StatsDao statsDao = (StatsDao) daoManager.getDao(DaoManager.Table.STATS);
            Stats stats = statsDao.getStats(user.getUserId());

            totalPoints.setText(Integer.toString(stats.getTotalPoints()));
            blocksWalked.setText(Integer.toString(stats.getBlocksWalked()));
            pelletsEaten.setText(Integer.toString(stats.getPelletsEaten()));
            gamesPlayed.setText((Integer.toString(stats.getGamesPlayed())));
            gamesWon.setText(Integer.toString(stats.getGamesWon()));
            maxLevel.setText(Integer.toString(stats.getMaxLevel()));
            maxScore.setText(Integer.toString(stats.getMaxScore()));
            daoManager.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
