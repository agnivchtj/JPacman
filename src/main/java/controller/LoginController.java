package controller;

import java.io.File;

import java.net.URL;

import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import model.frontend.Login;

/**
 * This is where the LoginController code should go.
 */
public class LoginController implements Initializable {
    @FXML
    private transient TextField textUsername;
    @FXML
    private transient PasswordField textPassword;
    @FXML
    private transient AnchorPane loginPane;

    /**
     * Method to login the user.
     * @param event when user clicks on the Login button.
     * @throws Exception this is for any exception.
     */
    public void login(final ActionEvent event) throws Exception {
        String username = textUsername.getText();
        String password = textPassword.getText();
        if (Login.userLogin(username, password)) {
            URL url = new File("src/main/java/view/StartMenu.fxml").toURI().toURL();
            AnchorPane startPane = FXMLLoader.load(url);
            loginPane.getChildren().setAll(startPane);
        } else {
            Stage stage = new Stage();

            VBox vb = new VBox(17);
            vb.setMinHeight(50);
            vb.setMinWidth(250);

            Scene scene = new Scene(vb);
            stage.setResizable(false);
            stage.setTitle("PacMan");
            stage.setScene(scene);

            Label label = new Label("Invalid username/password");
            vb.getChildren().add(label);
            vb.setAlignment(Pos.CENTER);
            stage.show();
        }
    }

    /**
     * Method to go to the registration screen.
     * @param event when user clicks on the 'here' link
     * @throws Exception this is for any Exception.
     */
    public void registration(final ActionEvent event) throws Exception {
        URL url = new File("src/main/java/view/Registration.fxml").toURI().toURL();
        AnchorPane registerPane = FXMLLoader.load(url);
        loginPane.getChildren().setAll(registerPane);
    }

    /**
     * 'Dummy' code for now.
     * @param url The URL that should be passed
     * @param rb The ResourceBundle that should be passed
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //
    }
}
