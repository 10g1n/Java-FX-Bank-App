package md.project.bankapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;

public class LoginController {
    @FXML
    TextField userNameField;
    @FXML
    PasswordField passwordField;
    @FXML
    Button loginButton;
    @FXML
    Button signUpButton;
    @FXML
    Label userNameErrorLabel;
    @FXML
    Label passwordErrorLabel;

    private Parent root;
    private Stage stage;
    private Scene scene;

    public static String userName = "";

    public void login(ActionEvent e) throws IOException {

        String hashedPassword = DataBaseConnector.matchesPassword(userNameField.getText());

        if (!DataBaseConnector.doesUsernameExist(userNameField.getText())) {
            userNameErrorLabel.setText("Username not found!");
        } else {
            if (BCrypt.checkpw(passwordField.getText(), hashedPassword)) {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("main-page.fxml"));
                root = loader.load();

                userName = userNameField.getText();

                MainPageController mainController = loader.getController();

                mainController.setUserData(DataBaseConnector.getNameByUserName(userName));

                mainController.setTransactionInfo(userName);

                stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.centerOnScreen();
                stage.show();
            } else {
                passwordErrorLabel.setText("Wrong password!");
            }
        }
    }

    public void signUp(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("signup-page.fxml"));
        root = loader.load();

        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.centerOnScreen();
        stage.setScene(scene);
        stage.show();
    }
}
