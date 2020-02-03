package controller;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import model.dao.DaoManager;
import model.dao.LeaderboardDao;
import model.entity.Leaderboard;


public class GlobalLeaderboardController implements Initializable {

    @FXML
    private transient AnchorPane leaderboardPane;
    @FXML
    private transient Text first;
    @FXML
    private transient Text second;
    @FXML
    private transient Text third;
    @FXML
    private transient Text forth;
    @FXML
    private transient Text fifth;
    @FXML
    private transient Text one;
    @FXML
    private transient Text two;
    @FXML
    private transient Text three;
    @FXML
    private transient Text four;
    @FXML
    private transient Text five;

    private transient DaoManager daoManager;


    /**
     * Method to go back to the Login menu.
     * @param event when the user clicks on the button
     * @throws Exception this is for any Exception.
     */
    public void back(final ActionEvent event) throws Exception {
        URL url = new File("src/main/java/view/StartMenu.fxml").toURI().toURL();
        AnchorPane startPane = FXMLLoader.load(url);
        leaderboardPane.getChildren().setAll(startPane);
    }

    /**
     * Initializes the controller class.
     *
     * @param url default
     * @param rb default
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        daoManager = new DaoManager();
        try {
            List<Leaderboard> leaderboard = ((LeaderboardDao)
                    daoManager.getDao(DaoManager.Table.LEADERBOARD)).getLeaderboard();
            first.setText(leaderboard.get(0).getUsername());
            second.setText(leaderboard.get(1).getUsername());
            third.setText(leaderboard.get(2).getUsername());
            forth.setText(leaderboard.get(3).getUsername());
            fifth.setText(leaderboard.get(4).getUsername());

            one.setText(Integer.toString(leaderboard.get(0).getPoints()));
            two.setText(Integer.toString(leaderboard.get(1).getPoints()));
            three.setText(Integer.toString(leaderboard.get(2).getPoints()));
            four.setText(Integer.toString(leaderboard.get(3).getPoints()));
            five.setText(Integer.toString(leaderboard.get(4).getPoints()));
            daoManager.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
