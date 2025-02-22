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

import java.io.IOException;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Random;


public class SignUpController {
    @FXML
    TextField nameField;
    @FXML
    TextField surnameField;
    @FXML
    TextField userNameField;
    @FXML
    PasswordField userPasswordField;
    @FXML
    Label errorLabel;
    @FXML
    Button signUpButton;


    public void signUp(ActionEvent e) {
        if (DataBaseConnector.doesUsernameExist(userNameField.getText())) {
            errorLabel.setText("Username already exists!");
        } else {
            if (!nameField.getText().isEmpty() && !surnameField.getText().isEmpty() &&
                !userPasswordField.getText().isEmpty() && !userPasswordField.getText().isEmpty()) {

                Random random = new Random();
                LocalDate dateOfCreation = LocalDate.now();

                DataBaseConnector.sendDataToUsersDatabase(userNameField.getText(), userPasswordField.getText(),
                        nameField.getText(), surnameField.getText(), random.nextInt(0, 1_000_000), dateOfCreation);
                errorLabel.setText("");
                try {
                    Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("login.fxml")));
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.centerOnScreen();
                    stage.show();

                    Stage signUpStage = (Stage) ((Node) e.getSource()).getScene().getWindow(); // these two lines close the current stage after the user signs up
                    signUpStage.close();
                } catch (IOException ioex) {
                    ioex.printStackTrace();
                }
            } else {
                errorLabel.setText("Enter your information!");
            }
        }
    }
}
