package md.project.bankapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class ProfilePageController {

    @FXML
    Button dashboardButton;
    @FXML
    Button transactionsButton;
    @FXML
    Button logoutButton;
    @FXML
    Button transferFundsButton;
    @FXML
    TextField amountField;
    @FXML
    Label errorLabel;
    @FXML
    Label accountNumber;
    @FXML
    Label creationDate;
    @FXML
    Label accountBalance;

    private Parent root;
    private Stage stage;
    private Scene scene;

    public void goToDashBoard(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("main-page.fxml"));
        root = loader.load();

        MainPageController mainPageController = loader.getController();

        mainPageController.setUserData(DataBaseConnector.getNameByUserName(LoginController.userName));

        mainPageController.setTransactionInfo(LoginController.userName);

        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.centerOnScreen();
        stage.setScene(scene);
        stage.show();
    }

    public void goToTransactions(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("transactions-page.fxml"));
        root = loader.load();

        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.centerOnScreen();
        stage.setScene(scene);
        stage.show();
    }

    public void logout(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
        root = loader.load();

        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    public void transfer() {

        try {
            errorLabel.setText("");
            double amount = Double.parseDouble(amountField.getText());

            if (amount >= 0.0) {
//                System.out.println("Class Profile Page Controller username: " + LoginController.userName);
                DataBaseConnector.transferFunds(LoginController.userName, Double.parseDouble(amountField.getText()));
                errorLabel.setText("Funds moved successfully!");
            } else {
                errorLabel.setText("Enter positive numbers!");
            }
        } catch (NumberFormatException nfe) {
            errorLabel.setText("Enter only numbers!");
        }

    }

    public void loadDataToProfile() {
        accountNumber.setText(String.valueOf(DataBaseConnector.getAccountNumByUsername(LoginController.userName)));
        accountBalance.setText(String.valueOf(DataBaseConnector.getBalance(LoginController.userName)));
        creationDate.setText(String.valueOf(DataBaseConnector.getDateByUsername(LoginController.userName)));
    }
}
