package sk.tomashrdy.GUI;

import sk.tomashrdy.entity.HashPassword;
import sk.tomashrdy.start.Start;
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
    public JPanel getContent(){return this.panelLogin;}

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
                if (start.loginControl(tfLogin.getText().toLowerCase() , HashPassword.hashPassword(password))){
                    //Do onlineUser si ulo��m d�ta u�ivatela ktoremu patr� email z loginu
                    onlineUser = start.getUserByEmail(tfLogin.getText().toLowerCase());
                    //Odo�lem onlineUsera do startu kde ho budem ma� ulo�en�ho po zvy�ok behu programu
                    start.setUser(onlineUser);
                    //Po�lem nov� content a zavolam vyskakovacie okno
                    this.frame.setContext(new DashBoard(frame , start).getContent());
                    JOptionPane.showMessageDialog(null , "Welcome" , "Login successful" , JOptionPane.INFORMATION_MESSAGE);
                    //Ni��ie volam okna pri ne�spe�nom prihlaseny podla toho �o sa udialo
                }else JOptionPane.showMessageDialog(null , "Invalid email or password" , "Login failed" , JOptionPane.WARNING_MESSAGE);
            }else JOptionPane.showMessageDialog(null , "Login or password is empty" , "Login fialed" , JOptionPane.WARNING_MESSAGE);
        }

        if (e.getSource().equals(buttonRegister)){
            //Zavol�m si okno registr�cie
            this.frame.setContext(new RegisterWindow(frame , start).getContent());
        }
    }
}
