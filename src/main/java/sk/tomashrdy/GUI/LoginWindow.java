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

    //Konštruktor pre Login
    public LoginWindow(Frame frame, Start start) {
        this.frame = frame;
        this.start = start;
        //Pridanie listenerov
        buttonLogin.addActionListener(this);
        buttonRegister.addActionListener(this);

        //Pre každý textfield pridám aby po stlaèení klávesi enter program vykonal kliknutie na login
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

    //Vráti panel ako content pre okno
    public JPanel getContent(){return this.panelLogin;}


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(buttonLogin)){
            //Overím èi password nieje prázdny
            boolean passNotEmpty = false;
            char[] passChar = pfPassword.getPassword();
            String password = new String(passChar);
            if (passChar.length > 0) {
                passNotEmpty = true;}
            //Ak nieje login ani pasword prázdny tak vykonám nasledovný blok kodu
            if (!tfLogin.getText().isEmpty() && passNotEmpty){
                //zavolám funkciu ktorá mi skontroluje èi sa zhoduje mail a heslo ( posielam zahešované heslo )
                if (loginControl(tfLogin.getText().toLowerCase() , HashPassword.hashPassword(password))){
                    //Do onlineUser si uložím dáta uživatela ktoremu patrí email z loginu
                    onlineUser = getUserByEmail(tfLogin.getText().toLowerCase());
                    //Odošlem onlineUsera do startu kde ho budem ma uloženého po zvyšok behu programu
                    start.setUser(onlineUser);
                    //Pošlem nový content a zavolam vyskakovacie okno
                    frame.setContext(new DashBoard(frame , start).getContent());
                    JOptionPane.showMessageDialog(null , "Welcome" , "Login successful" , JOptionPane.INFORMATION_MESSAGE);
                    //Nižšie volam okna pri neúspešnom prihlaseny podla toho èo sa udialo
                }else JOptionPane.showMessageDialog(null , "Invalid email or password" , "Login failed" , JOptionPane.WARNING_MESSAGE);
            }else JOptionPane.showMessageDialog(null , "Login or password is empty" , "Login fialed" , JOptionPane.WARNING_MESSAGE);
        }

        if (e.getSource().equals(buttonRegister)){
            //Zavolám si okno registrácie
            frame.setContext(new RegisterWindow(frame , start).getContent());
        }
    }
    //Metoda ktora mi bude vracia celu zložku uživatela podla emailu
    public User getUserByEmail(String email) {
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
    //Metoda na kontrolu emailu a hesla
    public boolean loginControl(String email, String password) {
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
}
