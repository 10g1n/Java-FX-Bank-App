package md.project.bankapp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataBaseConnector {
    private static Connection connection;
    private static final String url = "jdbc:postgresql://localhost:5432/BankApp";
    private static final String user = "marcel";
    private static final String password = "Marcel_Tekwill";

    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(url, user, password);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return connection;
    }


    public static void sendDataToUsersDatabase(String userName, String userPassword, String name, String surname, int accountNum, LocalDate dateCreated) {

        String query = "INSERT INTO users (name, surname, user_name, password, account_number, date_created) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = DataBaseConnector.getConnection().prepareStatement(query)) {

            stmt.setString(1, name);
            stmt.setString(2, surname);
            stmt.setString(3, userName);
            stmt.setString(4, BCrypt.hashpw(userPassword, BCrypt.gensalt()));
            stmt.setInt(5, accountNum);
            stmt.setDate(6, Date.valueOf(dateCreated));

            stmt.executeUpdate();

//            System.out.println("Successfully updated");
        } catch (SQLException e) {
            Logger logger = Logger.getLogger(DataBaseConnector.class.getName());
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public static void sendDataToTransactionsDatabase(LocalDate date, double amount, String sender, String receiver) {
        String query = "INSERT INTO transactions (date_time, amount, sender, receiver) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = DataBaseConnector.getConnection().prepareStatement(query)) {

            stmt.setDate(1, Date.valueOf(date));
            stmt.setDouble(2, amount);
            stmt.setString(3, sender);
            stmt.setString(4, receiver);

            stmt.executeUpdate();

//            System.out.println("Insertion of transactions successfully completed!");
        } catch (SQLException e) {
            Logger logger = Logger.getLogger(DataBaseConnector.class.getName());
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public static boolean doesUsernameExist(String userName) {
        String query = "SELECT 1 FROM users WHERE user_name = ? LIMIT 1";

        try (PreparedStatement stmt = DataBaseConnector.getConnection().prepareStatement(query)) {
            stmt.setString(1, userName);

            ResultSet set = stmt.executeQuery();

            return set.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String matchesPassword(String userName) {
        String query = "SELECT password FROM users WHERE user_name = ?";

        try (PreparedStatement stmt = DataBaseConnector.getConnection().prepareStatement(query)) {

            stmt.setString(1, userName);
            ResultSet set = stmt.executeQuery();

            if (set.next()) {
                return set.getString("password");
            }

        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        return null;
    }

    public static String getNameByUserName(String userName) {
        String query = "SELECT name FROM users WHERE user_name = ?";

        try (PreparedStatement stmt = DataBaseConnector.getConnection().prepareStatement(query)){

            stmt.setString(1, userName);

            ResultSet set = stmt.executeQuery();

            if (set.next()) {
                return set.getString("name");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static double getBalance(String userName) {
        String query = "SELECT account_balance FROM users WHERE user_name = ?";
        try (PreparedStatement stmt = DataBaseConnector.getConnection().prepareStatement(query)) {

            stmt.setString(1, userName);

            ResultSet set = stmt.executeQuery();

            if (set.next()) {
                return set.getDouble("account_balance");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    public static void transferMoney(double amount, String sender, String receiver) {
        String query = "UPDATE users SET account_balance = account_balance + ? WHERE user_name = ?";
        String query2 = "UPDATE users SET account_balance = account_balance - ? WHERE user_name = ?";


            try (PreparedStatement statement = DataBaseConnector.getConnection().prepareStatement(query)) {
                statement.setDouble(1, amount);
                statement.setString(2, receiver);

                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try (PreparedStatement statement2 = DataBaseConnector.getConnection().prepareStatement(query2)) {
                statement2.setDouble(1, amount);
                statement2.setString(2, sender);

                statement2.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }

    public static ObservableList<Transaction> getTransactionInfo(String userName, int limit) {
        String query = "SELECT date_time, amount, sender, receiver FROM transactions WHERE sender = ? OR receiver = ? ORDER BY date_time DESC LIMIT ?";
        ObservableList<Transaction> transactions = FXCollections.observableArrayList();

        try (PreparedStatement stmt = DataBaseConnector.getConnection().prepareStatement(query)) {

            stmt.setString(1, userName);
            stmt.setString(2, userName);
            stmt.setInt(3, limit);

            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                Date date = resultSet.getDate("date_time");
                double amount = resultSet.getDouble("amount");
                String sender = resultSet.getString("sender");
                String receiver = resultSet.getString("receiver");
                transactions.add(new Transaction(date, sender, receiver, amount));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }

    public static void transferFunds(String userName, double amount) {
        String query = "UPDATE users SET account_balance = account_balance + ? WHERE user_name = ?";

        try (PreparedStatement stmt = DataBaseConnector.getConnection().prepareStatement(query)) {

            stmt.setDouble(1, amount);
            stmt.setString(2, userName);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int getAccountNumByUsername(String userName) {
        String query = "SELECT account_number FROM users WHERE user_name = ?";

        try (PreparedStatement stmt = DataBaseConnector.getConnection().prepareStatement(query)) {
            stmt.setString(1, userName);

            ResultSet set = stmt.executeQuery();

            if (set.next()) {
                return set.getInt("account_number");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static Date getDateByUsername(String userName) {
        String query = "SELECT date_created FROM users WHERE user_name = ?";

        try (PreparedStatement stmt = DataBaseConnector.getConnection().prepareStatement(query)) {
            stmt.setString(1, userName);

            ResultSet set = stmt.executeQuery();

            if (set.next()) {
                return set.getDate("date_created");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
