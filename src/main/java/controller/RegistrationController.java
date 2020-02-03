package controller;

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
import model.frontend.Registration;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Class controls the functionality on the registration screen.
 */
public class RegistrationController implements Initializable {
    @FXML
    private transient TextField textUsername;
    @FXML
    private transient PasswordField textPassword;
    @FXML
    private transient TextField textConfirm;
    @FXML
    private transient AnchorPane registerPane;

    /**
     * Method to register the user.
     * @param event when the user clicks on the 'Register' button.
     * @throws Exception this is for any exception.
     */
    public void registration(final ActionEvent event) throws Exception {
        String username = textUsername.getText();
        String password = textPassword.getText();
        String pass_confirm = textConfirm.getText();

        boolean flag = (pass_confirm != null && password.compareTo(pass_confirm) == 0);
        if (flag && Registration.userRegistration(username, password)) {
            URL url = new File("src/main/java/view/"
                    + "RegistrationSuccess.fxml").toURI().toURL();
            AnchorPane successPane = FXMLLoader.load(url);
            registerPane.getChildren().setAll(successPane);
        } else {
            Stage stg = new Stage();

            VBox vbox = new VBox(17);
            vbox.setMinHeight(50);
            vbox.setMinWidth(250);

            Scene scene = new Scene(vbox);
            stg.setTitle("PacMan");
            stg.setResizable(false);
            stg.setScene(scene);

            Label label = new Label("Registration failed! Please try again.");
            vbox.getChildren().add(label);
            vbox.setAlignment(Pos.CENTER);
            stg.show();
        }
    }

    /**
     * Method to redirect user to login screen.
     * @param event when the user clicks on the 'here' link.
     * @throws Exception this is for any exception.
     */
    public void login(final ActionEvent event) throws Exception {
        URL url = new File("src/main/java/view/Login.fxml").toURI().toURL();
        AnchorPane loginPane = FXMLLoader.load(url);
        registerPane.getChildren().setAll(loginPane);
    }

    /**
     * 'Dummy' code for now.
     * @param url The URL that should be passed
     * @param rb The ResourceBundle that should be passed
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        textUsername.setPromptText("get from user");
        textPassword.setPromptText("get from user and hash");
    }
}
