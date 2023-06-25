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

    //Vytv�ranie okien pre vykonan� alebo nevykonan� registr�ciu
    static void registrationSuccessful(String username){
        MiniFrame successfulFrame = new MiniFrame("Sucessful" , "Hello " + username + " , you account has ben created" , Color.GREEN);
    }
    static void registrationFailed(String message){
        MiniFrame failedFrame = new MiniFrame("Failed" , message , Color.RED);
    }

    //Metoda pre ooverenie �i existuje email ( apr�klad pri registr�cii over�m �i email existuje ak ano neprid�m ho znova )
    static boolean emailExist(String email) {
        boolean mailExist = false;
        DatabaseConnection databaseConnection = new DatabaseConnection();
        //Vytvor�m dotaz aby mi spo�ital po�et v�skytov kde email je rovnak� ako email ktor� som zadal na vstupe
        String query = "SELECT COUNT(*) FROM users WHERE email = ?";
        //Prid�m do statementu email zo vstupu ( v�ade sa sna��m o�etri� mail metodou .toLoweCase() aby som sa vyhol probl�mom pri porovn�vani )
        try (PreparedStatement statement = databaseConnection.prepareStatement(query)) {
            statement.setString(1, email.toLowerCase());
            // Ak je po�et v�skitov ve�� ako 0 tak mi ulo�� do mailExist true
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
    //Vytvorenie metody ktor� skontroluje regex a zadan� heslo �i obsahuje potrebn� znaky
    static boolean validatePassword(String password) {
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+|`{}\\[\\]:\";'<>?,./])[a-zA-Z\\d!@#$%^&*()_+|`{}\\[\\]:\";'<>?,./]{6,16}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }
    //Vytvorenie metody ktor� skontroluje email podla regexu �i je v spr�vnom form�te
    static boolean validateEmail(String email) {
        String regex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

}
