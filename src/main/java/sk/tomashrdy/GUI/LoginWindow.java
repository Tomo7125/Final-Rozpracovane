package sk.tomashrdy.GUI;

import sk.tomashrdy.entity.HashPassword;
import sk.tomashrdy.entity.LoginListener;
import sk.tomashrdy.entity.Start;
import sk.tomashrdy.entity.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class LoginWindow implements ActionListener {

    Frame frame;
    Start start;
    private JPanel panelLogin;
    private JTextField tfLogin;
    private JPasswordField pfPassword;
    private JButton buttonLogin;
    private JButton buttonRegister;
    private User onlineUser;

    public LoginWindow(Frame frame, Start start) {
        this.frame = frame;
        this.start = start;
        buttonLogin.addActionListener(this);
        buttonRegister.addActionListener(this);

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


    public JPanel getContent(){return this.panelLogin;}

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(buttonLogin)){
            boolean passNotEmpty = false;
            char[] passChar = pfPassword.getPassword();
            String password = new String(passChar);
            if (passChar.length > 0) {
                passNotEmpty = true;}
            if (!tfLogin.getText().isEmpty() && passNotEmpty){
                if (LoginListener.loginControl(tfLogin.getText().toLowerCase() , HashPassword.hashPassword(password))){
                    onlineUser = LoginListener.getUserByEmail(tfLogin.getText().toLowerCase());
                    start.setUser(onlineUser);
                    LoginListener.loginSuccessful("Welcome " +  onlineUser.getName());
                    frame.setContext(new DashBoard(frame , start).getContent());
                }else LoginListener.loginFailed("Invalid email or password");
            }else LoginListener.loginFailed("Login or password is empty");
        }

        if (e.getSource().equals(buttonRegister)){
            frame.setContext(new RegisterWindow(frame , start).getContent());
        }
    }
}
