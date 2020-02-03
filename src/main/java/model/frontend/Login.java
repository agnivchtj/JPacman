package model.frontend;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.dao.DaoManager;
import model.dao.StatsDao;
import model.dao.UserDao;
import model.entity.User;

import java.io.File;
import java.net.URL;

/**
 * Main Application.
 * This class handles user login and navigation to and from the login page.
 */
public class Login extends Application {

    private static transient Stage stage;
    private static transient DaoManager daoManager;
    protected static transient User user;

    /**
     * Constructor.
     */
    public Login() {
    }

    /**
     * Getter for logged user.
     * @return the user currently logged in
     */
    public User getUser() {
        return user;
    }

    /**
     * Starts the application.
     * @param primaryStage The Stage that should be initalised
     * @throws Exception Indicates issue with Stage
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        goToLogin(stage);
        primaryStage.show();
    }

    /**
     * Logs in the user.
     * @param username The username for the login
     * @param password The hashed password for the login
     * @return True if the username exists and the passwords match
     *          False otherwise
     */
    public static boolean userLogin(String username, String password) {
        try {
            daoManager = new DaoManager();
            UserDao userDao = (UserDao) daoManager.getDao(DaoManager.Table.USER);
            StatsDao statsDao = (StatsDao) daoManager.getDao(DaoManager.Table.STATS);
            user = userDao.getUserByUsernameAndPassword(username, password);
            user.setStats(statsDao.getStats(user.getUserId()));
            daoManager.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Logs out the user by setting the local instance to null.
     * @throws Exception Indicates issue with Stage
     */
    // this is how we are choosing to do logout for now
    @SuppressWarnings("PMD.NullAssignment")
    public void userLogout() throws Exception {
        user = null;
    }

    /**
     * Launches the application on the Login screen.
     * @throws Exception Indicates issue with Stage
     */
    private static void goToLogin(Stage stage) throws Exception {
        String targetFxml = "src/main/java/view/Login.fxml";
        replaceSceneContent(targetFxml, stage);
    }

    /**
     * Takes care of navigation to the FXML location.
     * @param fxml The address of the FXML page that should be displayed
     * @throws Exception Indicates issue with Stage
     */
    private static void replaceSceneContent(String fxml, Stage stage) throws Exception {
        URL url = new File(fxml).toURI().toURL();
        Parent root = FXMLLoader.load(url);
        Scene scene = new Scene(root, 600, 400);
        stage.setTitle("PacMan");
        stage.setScene(scene);
        stage.setResizable(false);
    }
}
