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

    public RegisterWindow(Frame frame, Start start) {
        this.frame = frame;
        this.start = start;
        buttonBackToMenu.addActionListener(this);
        buttonRegister.addActionListener(this);

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


    public JPanel getContent(){return this.panelRegister;}

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(buttonRegister)){
            boolean passNotEmpty = false;
            char[] passwordChar = pfPassword.getPassword();
            if (passwordChar.length > 0) {
                passNotEmpty = true;}
            if (!tfFirstName.getText().isEmpty() && !tfLastName.getText().isEmpty() && !tfEmail.getText().isEmpty() && passNotEmpty) {
                if (RegistrationListener.validateEmail(tfEmail.getText())) {
                    if (!RegistrationListener.emailExist(tfEmail.getText())) {
                        char[] passwordArray = pfPassword.getPassword();
                        String pass1 = new String(passwordArray);
                        char[] passwordConfirmArray = pfPasswordConfirm.getPassword();
                        String pass2 = new String(passwordConfirmArray);
                        if (pass1.equals(pass2)) {
                            if (RegistrationListener.validatePassword(pass1)) {
                                User newUser = new User(tfFirstName.getText(), tfLastName.getText(), tfEmail.getText().toLowerCase(), HashPassword.hashPassword(pass1));
                                newUser.userRegister(newUser);
                                RegistrationListener.registrationSuccessful(newUser.getName());
                                frame.setContext(new LoginWindow(frame , start).getContent());
                            } else
                                RegistrationListener.registrationFailed("<html>The password must contain:<br>\" +\n" +
                                        "                        \"at least one lowercase letter<br>\" +\n" +
                                        "                        \"at least one uppercase letter<br>\" +\n" +
                                        "                        \"at least one digit<br>\" +\n" +
                                        "                        \"at least one special character (!@#$%^&*()_+|`{}[]:\\\";'<>?,./)<br>\" +\n" +
                                        "                        \"a length of 6 to 16 characters.</html>");
                        }else RegistrationListener.registrationFailed("Password not match");
                    }else RegistrationListener.registrationFailed("Email already exist");
                }else RegistrationListener.registrationFailed("The email does not have the correct format.");
            }else {
                RegistrationListener.registrationFailed("All field must be filled !");
            }
        }
        if (e.getSource().equals(buttonBackToMenu)){
            frame.setContext(new LoginWindow(frame , start).getContent());
        }
    }
}
