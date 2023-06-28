package sk.tomashrdy.GUI;

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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterWindow implements ActionListener, HashPassword {
    Frame frame;
    Start start;
    private JPanel panelRegister;
    private JTextField tfFirstName;
    private JTextField tfLastName;
    private JTextField tfEmail;
    private JPasswordField pfPassword;
    private JPasswordField pfPasswordConfirm;
    private JButton buttonRegister;
    private JButton buttonBackToMenu;

    //Kon�truktor pre registra�n� okno
    public RegisterWindow(Frame frame, Start start) {
        this.frame = frame;
        this.start = start;
        buttonBackToMenu.addActionListener(this);
        buttonRegister.addActionListener(this);

        //Nastav�m si v�etky polia tak aby po stla�en� kl�vesy enter program vykonal kliknutie na register
        tfFirstName.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    buttonRegister.doClick(); // Vyvolanie kliknutia na buttonLogin
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        tfLastName.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    buttonRegister.doClick(); // Vyvolanie kliknutia na buttonLogin
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        tfEmail.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    buttonRegister.doClick(); // Vyvolanie kliknutia na buttonLogin
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
                    buttonRegister.doClick(); // Vyvolanie kliknutia na buttonLogin
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        pfPasswordConfirm.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    buttonRegister.doClick(); // Vyvolanie kliknutia na buttonLogin
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
    }


    //Po�le panel ako context pre okno
    public JPanel getContent(){return this.panelRegister;}

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(buttonRegister)){
            boolean passNotEmpty = false;
            char[] passwordChar = pfPassword.getPassword();
            if (passwordChar.length > 0) {
                passNotEmpty = true;}
            //Hore mam metodu ktor� zistuje �i je heslo pr�zdne
            //Dole skontrolujem �i su v�etky polia vyplnen�
            if (!tfFirstName.getText().isEmpty() && !tfLastName.getText().isEmpty() && !tfEmail.getText().isEmpty() && passNotEmpty) {
                //Skontrolujem �i m� email spr�vny form�t
                if (validateEmail(tfEmail.getText())) {
                    //Skontrolujem �i u� email neexistuje
                    if (!emailExist(tfEmail.getText())) {
                        //Ulo��m si do stringov hesl� z passwordov
                        char[] passwordArray = pfPassword.getPassword();
                        String pass1 = new String(passwordArray);
                        char[] passwordConfirmArray = pfPasswordConfirm.getPassword();
                        String pass2 = new String(passwordConfirmArray);
                        //Skontrolujem �i sa zhoduj� hesl�
                        if (pass1.equals(pass2)) {
                            //Skontrolujem �i hesl� obsahuj� potrebn� znaky
                            if (validatePassword(pass1)) {
                                //Vytvor�m nov�ho Usera ktor�mu nastav�m hodnoty z fieldov
                                User newUser = new User(tfFirstName.getText(), tfLastName.getText(), tfEmail.getText().toLowerCase(), HashPassword.hashPassword(pass1));
                                //Zavol�m si met�du ktor� zaregistruje u��vatela
                                newUser.userRegister(newUser);
                                // Zavol�m vyskakovacie okno ktore ozn�my �e je u��vatel �spe�ne zaregistrovan�
                                registrationSuccessful(newUser.getName());
                                //Nastav�m nov� context
                                frame.setContext(new LoginWindow(frame , start).getContent());
                            } else
                                // Ak je heslo v zlom form�te po�lem do okna text ktor� povie u��vatelovi �o mus� heslo obsahova�
                                registrationFailed("<html>The password must contain:<br>\" +\n" +
                                        "                        \"at least one lowercase letter<br>\" +\n" +
                                        "                        \"at least one uppercase letter<br>\" +\n" +
                                        "                        \"at least one digit<br>\" +\n" +
                                        "                        \"at least one special character (!@#$%^&*()_+|`{}[]:\\\";'<>?,./)<br>\" +\n" +
                                        "                        \"a length of 6 to 16 characters.</html>");
                            //Ak sa hesla nezhoduj� vykon� sa okno so spr�vou a tak isto aj ni��ie met�dy else len popisuju problem pre ktor� program nepokra�uje
                        }else registrationFailed("Password not match");
                    }else registrationFailed("Email already exist");
                }else registrationFailed("The email does not have the correct format.");
            }else {
                registrationFailed("All field must be filled !");
            }
        }
        // Po stla�en� back to menu sa nastav� nov� context
        if (e.getSource().equals(buttonBackToMenu)){
            frame.setContext(new LoginWindow(frame , start).getContent());
        }
    }
    //Vytv�ranie okien pre vykonan� alebo nevykonan� registr�ciu
    public void registrationSuccessful(String username){
        MiniFrame successfulFrame = new MiniFrame("Sucessful" , "Hello " + username + " , you account has ben created" , Color.GREEN);
    }
    public void registrationFailed(String message){
        MiniFrame failedFrame = new MiniFrame("Failed" , message , Color.RED);
    }

    //Metoda pre ooverenie �i existuje email ( apr�klad pri registr�cii over�m �i email existuje ak ano neprid�m ho znova )
    public boolean emailExist(String email) {
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
    public boolean validatePassword(String password) {
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+|`{}\\[\\]:\";'<>?,./])[a-zA-Z\\d!@#$%^&*()_+|`{}\\[\\]:\";'<>?,./]{6,16}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }
    //Vytvorenie metody ktor� skontroluje email podla regexu �i je v spr�vnom form�te
    public boolean validateEmail(String email) {
        String regex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
