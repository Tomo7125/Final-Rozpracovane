package sk.tomashrdy.entity;

import sk.tomashrdy.GUI.MiniFrame;
import sk.tomashrdy.dbCon.DatabaseConnection;

import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface LoginListener {

    //Vytváranie okien ktore za zobrazia podla toho èi sa podarilo prihlásenie alebo nie
    static void loginSuccessful(String username){
        MiniFrame logSuccessful = new MiniFrame("Login" , username , Color.GREEN);
    }
    static void loginFailed(String message){
        MiniFrame logFailed = new MiniFrame("Login fialed" , message , Color.RED);
    }
    //Metóda ktorá mi overí èi boli zadané správne údaje do emailu a hesla , vstupný parameter je email a heslo z Login framu
    static boolean loginControl(String email, String password) {
        // V query si vytvorím dotaz na DB kolko riadkov obsahuje daný email a heslo ( je potrebné heslo posiela už zašifrované )
        String query = "SELECT COUNT(*) FROM users WHERE email = ? AND password = ?";
        //Štandardne je loginSuccessful false zmením ho neskôr ak sa bude zhodova nejaky mail a heslo so zadanými
        boolean loginSuccessful = false;

        DatabaseConnection databaseConnection = new DatabaseConnection();
        //Vytvorenie prepojenia a zadanie query , výsledok sa uloží do resulSetu
        try (PreparedStatement statement = databaseConnection.prepareStatement(query)) {
            statement.setString(1, email);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                //Do count sa mi uložilo èíslo z prveho columu resultsetu , následne nižšie skontrolujem èi je count veèší ako 0
                // ak je veèší tak v databaze je email a heslo ktore som zadal do loginu -> loginsuccessful sa zmení na true
                loginSuccessful = (count > 0);
            }
            databaseConnection.disconnect();
        } catch (SQLException e) {
            // Spracovanie chyby pri vykonávaní dotazu
        }

        return loginSuccessful;
    }
    //Metoda vracia objekt User , vstupný parameter je email rátam s tým že už mam ošetrené èi email existuje
    static User getUserByEmail(String email) {
        User user = null;
        //Dotaz na DB aby mi vitiahla first_name , last_name , email a to èi je admin užívatel podla emailu ( vstupný parameter email )
        String query = "SELECT first_name, last_name, email , isAdmin FROM users WHERE email = ?";

        DatabaseConnection databaseConnection = new DatabaseConnection();
        try (PreparedStatement statement = databaseConnection.prepareStatement(query)) {
            //Pridám si do query mail zo vstupu a hodím ho na prvý otáznik
            statement.setString(1, email);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                //Poukladám si do premenných údaje ktore som si vyžiadal podla názvov stlpcov
                String name = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String userEmail = resultSet.getString("email");
                boolean isAdmin = resultSet.getBoolean("isAdmin");
                //Vytvorím si Usera a nasetujem mu premenne
                user = new User(name, lastName, userEmail , isAdmin);
            }
        } catch (SQLException e) {
            // Spracovanie chyby pri vykonávaní dotazu
        }

        return user;
    }


}
