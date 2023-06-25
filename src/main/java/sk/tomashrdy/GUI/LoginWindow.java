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

    //Konötruktor pre Login
    public LoginWindow(Frame frame, Start start) {
        this.frame = frame;
        this.start = start;
        //Pridanie listenerov
        buttonLogin.addActionListener(this);
        buttonRegister.addActionListener(this);

        //Pre kaûd˝ textfield prid·m aby po stlaËenÌ kl·vesi enter program vykonal kliknutie na login
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

    //Vr·ti panel ako content pre okno
    public JPanel getContent(){return this.panelLogin;}

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(buttonLogin)){
            //OverÌm Ëi password nieje pr·zdny
            boolean passNotEmpty = false;
            char[] passChar = pfPassword.getPassword();
            String password = new String(passChar);
            if (passChar.length > 0) {
                passNotEmpty = true;}
            //Ak nieje login ani pasword pr·zdny tak vykon·m nasledovn˝ blok kodu
            if (!tfLogin.getText().isEmpty() && passNotEmpty){
                //zavol·m funkciu ktor· mi skontroluje Ëi sa zhoduje mail a heslo ( posielam zaheöovanÈ heslo )
                if (LoginListener.loginControl(tfLogin.getText().toLowerCase() , HashPassword.hashPassword(password))){
                    //Do onlineUser si uloûÌm d·ta uûivatela ktoremu patrÌ email z loginu
                    onlineUser = LoginListener.getUserByEmail(tfLogin.getText().toLowerCase());
                    //Odoölem onlineUsera do startu kde ho budem maù uloûenÈho po zvyöok behu programu
                    start.setUser(onlineUser);
                    //Zavol·m si okno pre uspeöne prihl·senie a potom nastav˝m nov˝ context
                    LoginListener.loginSuccessful("Welcome " +  onlineUser.getName());
                    frame.setContext(new DashBoard(frame , start).getContent());
                    //Niûöie volam okna pri ne˙speönom prihlaseny podla toho Ëo sa udialo
                }else LoginListener.loginFailed("Invalid email or password");
            }else LoginListener.loginFailed("Login or password is empty");
        }

        if (e.getSource().equals(buttonRegister)){
            //Zavol·m si okno registr·cie
            frame.setContext(new RegisterWindow(frame , start).getContent());
        }
    }
}
