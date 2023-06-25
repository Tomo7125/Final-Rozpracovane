package sk.tomashrdy.entity;

import sk.tomashrdy.GUI.MiniFrame;
import sk.tomashrdy.dbCon.DatabaseConnection;

import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface LoginListener {

    static void loginSuccessful(String username){
        MiniFrame logSuccessful = new MiniFrame("Login" , username , Color.GREEN);
    }
    static void loginFailed(String message){
        MiniFrame logFailed = new MiniFrame("Login fialed" , message , Color.RED);
    }
    static boolean loginControl(String email, String password) {
        String query = "SELECT COUNT(*) FROM users WHERE email = ? AND password = ?";
        boolean loginSuccessful = false;

        DatabaseConnection databaseConnection = new DatabaseConnection();

        try (PreparedStatement statement = databaseConnection.prepareStatement(query)) {
            statement.setString(1, email);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                loginSuccessful = (count > 0);
            }
            databaseConnection.disconnect();
        } catch (SQLException e) {
            // Spracovanie chyby pri vykonávaní dotazu
        }

        return loginSuccessful;
    }
    static User getUserByEmail(String email) {
        User user = null;
        String query = "SELECT first_name, last_name, email , isAdmin FROM users WHERE email = ?";

        DatabaseConnection databaseConnection = new DatabaseConnection();
        try (PreparedStatement statement = databaseConnection.prepareStatement(query)) {
            statement.setString(1, email);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String name = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String userEmail = resultSet.getString("email");
                boolean isAdmin = resultSet.getBoolean("isAdmin");

                user = new User(name, lastName, userEmail , isAdmin);
            }
        } catch (SQLException e) {
            // Spracovanie chyby pri vykonávaní dotazu
        }

        return user;
    }


}
