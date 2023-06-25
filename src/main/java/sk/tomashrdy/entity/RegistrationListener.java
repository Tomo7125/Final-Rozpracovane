package sk.tomashrdy.entity;

import sk.tomashrdy.GUI.MiniFrame;
import sk.tomashrdy.dbCon.DatabaseConnection;

import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface RegistrationListener {

    static void registrationSuccessful(String username){
        MiniFrame successfulFrame = new MiniFrame("Sucessful" , "Hello " + username + " , you account has ben created" , Color.GREEN);
    }
    static void registrationFailed(String message){
        MiniFrame failedFrame = new MiniFrame("Failed" , message , Color.RED);
    }


    static boolean emailExist(String email) {
        boolean mailExist = false;
        DatabaseConnection databaseConnection = new DatabaseConnection();
        String query = "SELECT COUNT(*) FROM users WHERE email = ?";

        try (PreparedStatement statement = databaseConnection.prepareStatement(query)) {
            statement.setString(1, email.toLowerCase());

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    mailExist = (count > 0);
                }
            }
            databaseConnection.disconnect();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return mailExist;
    }

    static boolean validatePassword(String password) {
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+|`{}\\[\\]:\";'<>?,./])[a-zA-Z\\d!@#$%^&*()_+|`{}\\[\\]:\";'<>?,./]{6,16}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }
    static boolean validateEmail(String email) {
        String regex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

}
