package controller;

import java.io.File;

import java.net.URL;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import model.frontend.Login;
import model.frontend.PlayGame;

public class StartMenuController {
    @FXML
    private transient AnchorPane startPane;

    /**
     * Logout button.
     *
     * @param event when user clicks on the 'logout' button
     * @throws Exception This is for any Exception
     */
    public void logout(final ActionEvent event) throws Exception {
        URL url = new File("src/main/java/view/Login.fxml").toURI().toURL();
        AnchorPane loginPane = FXMLLoader.load(url);
        startPane.getChildren().setAll(loginPane);
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
        startPane.getChildren().setAll(nicknamePane);
    }

    /**
     * Function to go to the disclaimer tab.
     * @param event when the user clicks on the 'disclaimer' button.
     * @throws Exception this is for any Exception.
     */
    public void disclaimerOnClick(final ActionEvent event) throws Exception {
        URL url = new File("src/main/java/view/Disclaimer.fxml").toURI().toURL();
        AnchorPane disclaimerPane = FXMLLoader.load(url);
        startPane.getChildren().setAll(disclaimerPane);
    }

    /**
     * Function to go to the Settings pane.
     * @param event when the user clicks on the 'Settings' button
     * @throws Exception this is for any Exception.
     */
    public void viewStats(final ActionEvent event) throws Exception {
        URL url = new File("src/main/java/view/GeneralStatistics.fxml").toURI().toURL();
        AnchorPane statisticsPane = FXMLLoader.load(url);
        startPane.getChildren().setAll(statisticsPane);
    }

    /**
     * Function to go to the Settings pane.
     * @param event when the user clicks on the 'Settings' button
     * @throws Exception this is for any Exception.
     */
    public void leaderboardMenu(final ActionEvent event) throws Exception {
        URL url = new File("src/main/java/view/GlobalLeaderboard.fxml").toURI().toURL();
        AnchorPane leaderboardPane = FXMLLoader.load(url);
        startPane.getChildren().setAll(leaderboardPane);
    }
}
