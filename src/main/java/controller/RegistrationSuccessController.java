package controller;

import java.io.File;
import java.net.URL;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

/**
 * This class controls the interactivity on the successful Registration page.
 */
public class RegistrationSuccessController {
    @FXML
    private transient AnchorPane successPane;

    /**
     * Method to go back to the login page.
     * @param event when the user clicks on the 'Go back to Login' button.
     * @throws Exception This is for any Exception.
     */
    public void toLogin(final ActionEvent event) throws Exception {
        URL url = new File("src/main/java/view/Login.fxml").toURI().toURL();
        AnchorPane loginPane = FXMLLoader.load(url);
        successPane.getChildren().setAll(loginPane);
    }
}
