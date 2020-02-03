package controller;

import java.io.File;
import java.net.URL;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

/**
 * This class handles the disclaimer.
 */
public class DisclaimerController {
    @FXML
    private transient AnchorPane disclaimerPane;

    /**
     * Function to return to the previous screen.
     * @param event - event used to go back to Start Menu
     * @throws Exception - throws exception
     */
    public void returnToStart(final ActionEvent event) throws Exception {
        URL url = new File("src/main/java/view/StartMenu.fxml")
                .toURI().toURL();
        AnchorPane startPane = FXMLLoader.load(url);
        disclaimerPane.getChildren().setAll(startPane);
    }
}
