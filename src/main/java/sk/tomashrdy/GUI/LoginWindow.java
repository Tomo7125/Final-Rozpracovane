package sk.tomashrdy.GUI;

import com.sun.xml.internal.bind.v2.TODO;
import sk.tomashrdy.dbCon.DatabaseConnection;
import sk.tomashrdy.entity.HashPassword;
import sk.tomashrdy.entity.Start;
import sk.tomashrdy.entity.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginWindow implements ActionListener {

    Frame frame;
    Start start;
    private JPanel panelLogin;
    private JTextField tfLogin;
    private JPasswordField pfPassword;
    private JButton buttonLogin;
    private JButton buttonRegister;
    private User onlineUser;

    //Kon�truktor pre Login
    public LoginWindow(Frame frame, Start start) {
        this.frame = frame;
        this.start = start;
        //Pridanie listenerov
        buttonLogin.addActionListener(this);
        buttonRegister.addActionListener(this);

        //Pre ka�d� textfield prid�m aby po stla�en� kl�vesi enter program vykonal kliknutie na login
        tfLogin.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    buttonLogin.doClick(); // Vyvolanie kliknutia na buttonLogin
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        pfPassword.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    buttonLogin.doClick(); // Vyvolanie kliknutia na buttonLogin
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

    }

    //Vr�ti panel ako content pre okno
    public JPanel getContent(){return this.panelLogin;}


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(buttonLogin)){
            //Over�m �i password nieje pr�zdny
            boolean passNotEmpty = false;
            char[] passChar = pfPassword.getPassword();
            String password = new String(passChar);
            if (passChar.length > 0) {
                passNotEmpty = true;}
            //Ak nieje login ani pasword pr�zdny tak vykon�m nasledovn� blok kodu
            if (!tfLogin.getText().isEmpty() && passNotEmpty){
                //zavol�m funkciu ktor� mi skontroluje �i sa zhoduje mail a heslo ( posielam zahe�ovan� heslo )
                if (loginControl(tfLogin.getText().toLowerCase() , HashPassword.hashPassword(password))){
                    //Do onlineUser si ulo��m d�ta u�ivatela ktoremu patr� email z loginu
                    onlineUser = getUserByEmail(tfLogin.getText().toLowerCase());
                    //Odo�lem onlineUsera do startu kde ho budem ma� ulo�en�ho po zvy�ok behu programu
                    start.setUser(onlineUser);
                    //Po�lem nov� content a zavolam vyskakovacie okno
                    frame.setContext(new DashBoard(frame , start).getContent());
                    JOptionPane.showMessageDialog(null , "Welcome" , "Login successful" , JOptionPane.INFORMATION_MESSAGE);
                    //Ni��ie volam okna pri ne�spe�nom prihlaseny podla toho �o sa udialo
                }else JOptionPane.showMessageDialog(null , "Invalid email or password" , "Login failed" , JOptionPane.WARNING_MESSAGE);
            }else JOptionPane.showMessageDialog(null , "Login or password is empty" , "Login fialed" , JOptionPane.WARNING_MESSAGE);
        }

        if (e.getSource().equals(buttonRegister)){
            //Zavol�m si okno registr�cie
            frame.setContext(new RegisterWindow(frame , start).getContent());
        }
    }
    //Metoda ktora mi bude vracia� celu zlo�ku u�ivatela podla emailu
    public User getUserByEmail(String email) {
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
    //Metoda na kontrolu emailu a hesla
    public boolean loginControl(String email, String password) {
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
}
