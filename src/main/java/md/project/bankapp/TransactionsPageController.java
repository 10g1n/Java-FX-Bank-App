package md.project.bankapp;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;

public class TransactionsPageController {
    @FXML
    Button dashboardButton;
    @FXML
    Button profileButton;
    @FXML
    Button logoutButton;

    @FXML
    Rectangle transaction1;
    @FXML
    Rectangle transaction2;
    @FXML
    Rectangle transaction3;
    @FXML
    Rectangle transaction4;
    @FXML
    Rectangle transaction5;
    @FXML
    Rectangle transaction6;

    @FXML
    Label transactionDate1;
    @FXML
    Label transactionDate2;
    @FXML
    Label transactionDate3;
    @FXML
    Label transactionDate4;
    @FXML
    Label transactionDate5;
    @FXML
    Label transactionDate6;

    @FXML
    Label transactionSender1;
    @FXML
    Label transactionSender2;
    @FXML
    Label transactionSender3;
    @FXML
    Label transactionSender4;
    @FXML
    Label transactionSender5;
    @FXML
    Label transactionSender6;

    @FXML
    Label transactionReceiver1;
    @FXML
    Label transactionReceiver2;
    @FXML
    Label transactionReceiver3;
    @FXML
    Label transactionReceiver4;
    @FXML
    Label transactionReceiver5;
    @FXML
    Label transactionReceiver6;

    @FXML
    Label transactionAmount1;
    @FXML
    Label transactionAmount2;
    @FXML
    Label transactionAmount3;
    @FXML
    Label transactionAmount4;
    @FXML
    Label transactionAmount5;
    @FXML
    Label transactionAmount6;

    private Parent root;
    private Stage stage;
    private Scene scene;

    public void logout(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
        root = loader.load();

        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    public void goToDashBoard(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("main-page.fxml"));
        root = loader.load();

        MainPageController mainController = loader.getController();

        mainController.setUserData(DataBaseConnector.getNameByUserName(LoginController.userName));

        mainController.setTransactionInfo(LoginController.userName);

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

    public void loadTransactions(String userName) {
        ObservableList<Transaction> transactions = DataBaseConnector.getTransactionInfo(userName, 6);

        hideAllTransactions();

        for (int i = 0; i < transactions.size(); i++) {
            setTransactionDetails(i+1, transactions.get(i));
        }
    }

    private void hideAllTransactions() {
        transaction1.setVisible(false);
        transaction2.setVisible(false);
        transaction3.setVisible(false);
        transaction4.setVisible(false);
        transaction5.setVisible(false);
        transaction6.setVisible(false);

        transactionDate1.setVisible(false);
        transactionDate2.setVisible(false);
        transactionDate3.setVisible(false);
        transactionDate4.setVisible(false);
        transactionDate5.setVisible(false);
        transactionDate6.setVisible(false);

        transactionSender1.setVisible(false);
        transactionSender2.setVisible(false);
        transactionSender3.setVisible(false);
        transactionSender4.setVisible(false);
        transactionSender5.setVisible(false);
        transactionSender6.setVisible(false);

        transactionReceiver1.setVisible(false);
        transactionReceiver2.setVisible(false);
        transactionReceiver3.setVisible(false);
        transactionReceiver4.setVisible(false);
        transactionReceiver5.setVisible(false);
        transactionReceiver6.setVisible(false);

        transactionAmount1.setVisible(false);
        transactionAmount2.setVisible(false);
        transactionAmount3.setVisible(false);
        transactionAmount4.setVisible(false);
        transactionAmount5.setVisible(false);
        transactionAmount6.setVisible(false);
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
            case 4:
                transactionDate4.setText(String.valueOf(transaction.getDate()));
                transactionSender4.setText(transaction.getSender());
                transactionReceiver4.setText(transaction.getReceiver());
                transactionAmount4.setText(String.valueOf(transaction.getAmount()));

                transaction4.setVisible(true);
                transactionDate4.setVisible(true);
                transactionSender4.setVisible(true);
                transactionReceiver4.setVisible(true);
                transactionAmount4.setVisible(true);
                break;
            case 5:
                transactionDate5.setText(String.valueOf(transaction.getDate()));
                transactionSender5.setText(transaction.getSender());
                transactionReceiver5.setText(transaction.getReceiver());
                transactionAmount5.setText(String.valueOf(transaction.getAmount()));

                transaction5.setVisible(true);
                transactionDate5.setVisible(true);
                transactionSender5.setVisible(true);
                transactionReceiver5.setVisible(true);
                transactionAmount5.setVisible(true);
                break;
            case 6:
                transactionDate6.setText(String.valueOf(transaction.getDate()));
                transactionSender6.setText(transaction.getSender());
                transactionReceiver6.setText(transaction.getReceiver());
                transactionAmount6.setText(String.valueOf(transaction.getAmount()));

                transaction6.setVisible(true);
                transactionDate6.setVisible(true);
                transactionSender6.setVisible(true);
                transactionReceiver6.setVisible(true);
                transactionAmount6.setVisible(true);
                break;
        }
    }
}
