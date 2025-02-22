package md.project.bankapp;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class MainPageController {
    @FXML
    Button transactionsButton;
    @FXML
    Button profileButton;
    @FXML
    Button logoutButton;
    @FXML
    Button sendMoneyButton;
    @FXML
    TextField payeeUserNameTextField;
    @FXML
    TextField amountTextField;
    @FXML
    Label dateNow;
    @FXML
    Label userName;
    @FXML
    Label balanceLabel;
    @FXML
    Label noUserFoundErrorLabel;
    @FXML
    Label invalidAmountErrorLabel;
    @FXML
    Label transactionDate1;
    @FXML
    Label transactionDate2;
    @FXML
    Label transactionDate3;
    @FXML
    Label transactionSender1;
    @FXML
    Label transactionSender2;
    @FXML
    Label transactionSender3;
    @FXML
    Label transactionReceiver1;
    @FXML
    Label transactionReceiver2;
    @FXML
    Label transactionReceiver3;
    @FXML
    Label transactionAmount1;
    @FXML
    Label transactionAmount2;
    @FXML
    Label transactionAmount3;
    @FXML
    Label accountNumberLabel;
    @FXML
    Rectangle transaction1;
    @FXML
    Rectangle transaction2;
    @FXML
    Rectangle transaction3;


    private Parent root;
    private Stage stage;
    private Scene scene;

    public void goToTransactions(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("transactions-page.fxml"));
        root = loader.load();

        TransactionsPageController transactionsController = loader.getController();
        transactionsController.loadTransactions(LoginController.userName);

        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.centerOnScreen();
        stage.setScene(scene);
        stage.show();
    }

    public void goToProfileButton(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("profile-page.fxml"));
        root = loader.load();

        ProfilePageController profileController = loader.getController();
        profileController.loadDataToProfile();

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

    public void sendMoney() {
        if (DataBaseConnector.doesUsernameExist(payeeUserNameTextField.getText())) {

            noUserFoundErrorLabel.setText("");
            try {

                double amount = Double.parseDouble(amountTextField.getText());
                if (amount < 0.0) {

                    invalidAmountErrorLabel.setText("Enter positive amounts!");

                } else {

                    double remainingMoney = Double.parseDouble(balanceLabel.getText()) - amount;

                    if (amount > remainingMoney) {
                        invalidAmountErrorLabel.setText("Not enough money!");
                    } else {

                        DataBaseConnector.sendDataToTransactionsDatabase(LocalDate.now(), amount, LoginController.userName, payeeUserNameTextField.getText());
                        DataBaseConnector.transferMoney(amount, LoginController.userName, payeeUserNameTextField.getText());

                        balanceLabel.setText(String.valueOf(remainingMoney));
                        invalidAmountErrorLabel.setText("Money sent successfully!");
                    }
                }

            } catch (NumberFormatException nfe) {
                invalidAmountErrorLabel.setText("Enter only numbers!");
            }
        } else {
            noUserFoundErrorLabel.setText("No user found!");
        }
    }

    public void setUserData(String name) {
        LocalDate localDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMM uuuu");
        String text = localDate.format(formatter);

        balanceLabel.setText(String.valueOf(DataBaseConnector.getBalance(LoginController.userName)));

        accountNumberLabel.setText(String.valueOf(DataBaseConnector.getAccountNumByUsername(LoginController.userName)));
//        System.out.println("Class Main Page Controller username: "+LoginController.userName);

        userName.setText(name);
        dateNow.setText(text);
    }

    public void setTransactionInfo(String userName) {
        ObservableList<Transaction> transactions = DataBaseConnector.getTransactionInfo(userName, 3);

        hideAllTransactions();

        for (int i = 0; i < transactions.size(); i++) {
            setTransactionDetails(i+1, transactions.get(i));
        }
    }

    private void hideAllTransactions() {
        transaction1.setVisible(false);
        transaction2.setVisible(false);
        transaction3.setVisible(false);

        transactionDate1.setVisible(false);
        transactionDate2.setVisible(false);
        transactionDate3.setVisible(false);

        transactionSender1.setVisible(false);
        transactionSender2.setVisible(false);
        transactionSender3.setVisible(false);

        transactionReceiver1.setVisible(false);
        transactionReceiver2.setVisible(false);
        transactionReceiver3.setVisible(false);

        transactionAmount1.setVisible(false);
        transactionAmount2.setVisible(false);
        transactionAmount3.setVisible(false);
    }


    private void setTransactionDetails(int transactionNumber, Transaction transaction) {
        switch (transactionNumber) {
            case 1:
                transactionDate1.setText(String.valueOf(transaction.getDate()));
                transactionSender1.setText(transaction.getSender());
                transactionReceiver1.setText(transaction.getReceiver());
                transactionAmount1.setText(String.valueOf(transaction.getAmount()));

                transaction1.setVisible(true);
                transactionDate1.setVisible(true);
                transactionSender1.setVisible(true);
                transactionReceiver1.setVisible(true);
                transactionAmount1.setVisible(true);
                break;
            case 2:
                transactionDate2.setText(String.valueOf(transaction.getDate()));
                transactionSender2.setText(transaction.getSender());
                transactionReceiver2.setText(transaction.getReceiver());
                transactionAmount2.setText(String.valueOf(transaction.getAmount()));

                transaction2.setVisible(true);
                transactionDate2.setVisible(true);
                transactionSender2.setVisible(true);
                transactionReceiver2.setVisible(true);
                transactionAmount2.setVisible(true);
                break;
            case 3:
                transactionDate3.setText(String.valueOf(transaction.getDate()));
                transactionSender3.setText(transaction.getSender());
                transactionReceiver3.setText(transaction.getReceiver());
                transactionAmount3.setText(String.valueOf(transaction.getAmount()));

                transaction3.setVisible(true);
                transactionDate3.setVisible(true);
                transactionSender3.setVisible(true);
                transactionReceiver3.setVisible(true);
                transactionAmount3.setVisible(true);
                break;
        }
    }
}

