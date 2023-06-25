package sk.tomashrdy.GUI;

import sk.tomashrdy.dbCon.DatabaseConnection;
import sk.tomashrdy.entity.HashPassword;
import sk.tomashrdy.entity.RegistrationListener;
import sk.tomashrdy.entity.Start;
import sk.tomashrdy.entity.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class RegisterWindow implements ActionListener, HashPassword , RegistrationListener {
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
                if (RegistrationListener.validateEmail(tfEmail.getText())) {
                    //Skontrolujem �i u� email neexistuje
                    if (!RegistrationListener.emailExist(tfEmail.getText())) {
                        //Ulo��m si do stringov hesl� z passwordov
                        char[] passwordArray = pfPassword.getPassword();
                        String pass1 = new String(passwordArray);
                        char[] passwordConfirmArray = pfPasswordConfirm.getPassword();
                        String pass2 = new String(passwordConfirmArray);
                        //Skontrolujem �i sa zhoduj� hesl�
                        if (pass1.equals(pass2)) {
                            //Skontrolujem �i hesl� obsahuj� potrebn� znaky
                            if (RegistrationListener.validatePassword(pass1)) {
                                //Vytvor�m nov�ho Usera ktor�mu nastav�m hodnoty z fieldov
                                User newUser = new User(tfFirstName.getText(), tfLastName.getText(), tfEmail.getText().toLowerCase(), HashPassword.hashPassword(pass1));
                                //Zavol�m si met�du ktor� zaregistruje u��vatela
                                newUser.userRegister(newUser);
                                // Zavol�m vyskakovacie okno ktore ozn�my �e je u��vatel �spe�ne zaregistrovan�
                                RegistrationListener.registrationSuccessful(newUser.getName());
                                //Nastav�m nov� context
                                frame.setContext(new LoginWindow(frame , start).getContent());
                            } else
                                // Ak je heslo v zlom form�te po�lem do okna text ktor� povie u��vatelovi �o mus� heslo obsahova�
                                RegistrationListener.registrationFailed("<html>The password must contain:<br>\" +\n" +
                                        "                        \"at least one lowercase letter<br>\" +\n" +
                                        "                        \"at least one uppercase letter<br>\" +\n" +
                                        "                        \"at least one digit<br>\" +\n" +
                                        "                        \"at least one special character (!@#$%^&*()_+|`{}[]:\\\";'<>?,./)<br>\" +\n" +
                                        "                        \"a length of 6 to 16 characters.</html>");
                            //Ak sa hesla nezhoduj� vykon� sa okno so spr�vou a tak isto aj ni��ie met�dy else len popisuju problem pre ktor� program nepokra�uje
                        }else RegistrationListener.registrationFailed("Password not match");
                    }else RegistrationListener.registrationFailed("Email already exist");
                }else RegistrationListener.registrationFailed("The email does not have the correct format.");
            }else {
                RegistrationListener.registrationFailed("All field must be filled !");
            }
        }
        // Po stla�en� back to menu sa nastav� nov� context
        if (e.getSource().equals(buttonBackToMenu)){
            frame.setContext(new LoginWindow(frame , start).getContent());
        }
    }
}
