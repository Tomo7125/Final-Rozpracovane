package sk.tomashrdy.entity;

import sk.tomashrdy.GUI.MiniFrame;
import sk.tomashrdy.dbCon.DatabaseConnection;

import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface LoginListener {

    //Vytv�ranie okien ktore za zobrazia podla toho �i sa podarilo prihl�senie alebo nie
    static void loginSuccessful(String username){
        MiniFrame logSuccessful = new MiniFrame("Login" , username , Color.GREEN);
    }
    static void loginFailed(String message){
        MiniFrame logFailed = new MiniFrame("Login fialed" , message , Color.RED);
    }
    //Met�da ktor� mi over� �i boli zadan� spr�vne �daje do emailu a hesla , vstupn� parameter je email a heslo z Login framu
    static boolean loginControl(String email, String password) {
        // V query si vytvor�m dotaz na DB kolko riadkov obsahuje dan� email a heslo ( je potrebn� heslo posiela� u� za�ifrovan� )
        String query = "SELECT COUNT(*) FROM users WHERE email = ? AND password = ?";
        //�tandardne je loginSuccessful false zmen�m ho nesk�r ak sa bude zhodova� nejaky mail a heslo so zadan�mi
        boolean loginSuccessful = false;

        DatabaseConnection databaseConnection = new DatabaseConnection();
        //Vytvorenie prepojenia a zadanie query , v�sledok sa ulo�� do resulSetu
        try (PreparedStatement statement = databaseConnection.prepareStatement(query)) {
            statement.setString(1, email);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                //Do count sa mi ulo�ilo ��slo z prveho columu resultsetu , n�sledne ni��ie skontrolujem �i je count ve�� ako 0
                // ak je ve�� tak v databaze je email a heslo ktore som zadal do loginu -> loginsuccessful sa zmen� na true
                loginSuccessful = (count > 0);
            }
            databaseConnection.disconnect();
        } catch (SQLException e) {
            // Spracovanie chyby pri vykon�van� dotazu
        }

        return loginSuccessful;
    }
    //Metoda vracia objekt User , vstupn� parameter je email r�tam s t�m �e u� mam o�etren� �i email existuje
    static User getUserByEmail(String email) {
        User user = null;
        //Dotaz na DB aby mi vitiahla first_name , last_name , email a to �i je admin u��vatel podla emailu ( vstupn� parameter email )
        String query = "SELECT first_name, last_name, email , isAdmin FROM users WHERE email = ?";

        DatabaseConnection databaseConnection = new DatabaseConnection();
        try (PreparedStatement statement = databaseConnection.prepareStatement(query)) {
            //Prid�m si do query mail zo vstupu a hod�m ho na prv� ot�znik
            statement.setString(1, email);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                //Pouklad�m si do premenn�ch �daje ktore som si vy�iadal podla n�zvov stlpcov
                String name = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String userEmail = resultSet.getString("email");
                boolean isAdmin = resultSet.getBoolean("isAdmin");
                //Vytvor�m si Usera a nasetujem mu premenne
                user = new User(name, lastName, userEmail , isAdmin);
            }
        } catch (SQLException e) {
            // Spracovanie chyby pri vykon�van� dotazu
        }

        return user;
    }


}
