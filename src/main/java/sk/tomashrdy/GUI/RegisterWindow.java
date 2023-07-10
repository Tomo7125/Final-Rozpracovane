package sk.tomashrdy.GUI;

import sk.tomashrdy.entity.HashPassword;
import sk.tomashrdy.start.Start;
import sk.tomashrdy.entity.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
    //Poöle panel ako context pre okno
    public JPanel getContent(){return this.panelRegister;}

    //Konötruktor pre registraËnÈ okno
    public RegisterWindow(Frame frame, Start start) {
        this.frame = frame;
        this.start = start;
        buttonBackToMenu.addActionListener(this);
        buttonRegister.addActionListener(this);

        //NastavÌm si vöetky polia tak aby po stlaËenÌ kl·vesy enter program vykonal kliknutie na register
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

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(buttonRegister)){
            boolean passNotEmpty = false;
            char[] passwordChar = pfPassword.getPassword();
            if (passwordChar.length > 0) {
                passNotEmpty = true;}
            //Hore mam metodu ktor· zistuje Ëi je heslo pr·zdne
            //Dole skontrolujem Ëi su vöetky polia vyplnenÈ
            if (!tfFirstName.getText().isEmpty() && !tfLastName.getText().isEmpty() && !tfEmail.getText().isEmpty() && passNotEmpty) {
                //Skontrolujem Ëi m· email spr·vny form·t
                if (validateEmail(tfEmail.getText())) {
                    //Skontrolujem Ëi uû email neexistuje
                    if (!start.emailExist(tfEmail.getText())) {
                        //UloûÌm si do stringov hesl· z passwordov
                        char[] passwordArray = pfPassword.getPassword();
                        String pass1 = new String(passwordArray);
                        char[] passwordConfirmArray = pfPasswordConfirm.getPassword();
                        String pass2 = new String(passwordConfirmArray);
                        //Skontrolujem Ëi sa zhoduj˙ hesl·
                        if (pass1.equals(pass2)) {
                            //Skontrolujem Ëi hesl· obsahuj˙ potrebnÈ znaky
                            if (validatePassword(pass1)) {
                                //VytvorÌm novÈho Usera ktorÈmu nastavÌm hodnoty z fieldov
                                User newUser = new User(tfFirstName.getText(), tfLastName.getText(), tfEmail.getText().toLowerCase(), HashPassword.hashPassword(pass1));
                                //Zavol·m si metÛdu ktor· zaregistruje uûÌvatela
                                start.userRegister(newUser);
                                //NastavÌm nov˝ context
                                this.frame.setContext(new LoginWindow(frame , start).getContent());
                                // Zavol·m vyskakovacie okno ktore ozn·my ûe je uûÌvatel ˙speöne zaregistrovan˝
                                JOptionPane.showMessageDialog(null , "Registration is complete" , "Registration successful" , JOptionPane.INFORMATION_MESSAGE);
                            } else
                                // Ak je heslo v zlom form·te poölem do okna text ktor˝ povie uûÌvatelovi Ëo musÌ heslo obsahovaù
                                JOptionPane.showMessageDialog(null , "The password must contain:\n" +
                                        "at least one lowercase letter\n" +
                                        "at least one uppercase letter\n" +
                                        "at least one digit\n" +
                                        "at least one special character (!@#$%^&*()_+|`{}[]:\\\\\\\";'<>?,./)\n" +
                                        "a length of 6 to 16 characters." , "Password error" , JOptionPane.WARNING_MESSAGE);
                            //Ak sa hesla nezhoduj˙ vykon· sa okno so spr·vou a tak isto aj niûöie metÛdy else len popisuju problem pre ktor˝ program nepokraËuje
                        }else JOptionPane.showMessageDialog(null , "Password not match" , "Password error" , JOptionPane.WARNING_MESSAGE);
                    }else JOptionPane.showMessageDialog(null , "Email already exist" , "Email error" , JOptionPane.WARNING_MESSAGE);
                }else JOptionPane.showMessageDialog(null , "The email does not have the correct format." , "Email error" , JOptionPane.WARNING_MESSAGE);
            }else {
                JOptionPane.showMessageDialog(null , "All field must be filled !" , "Email error" , JOptionPane.WARNING_MESSAGE);
            }
        }
        // Po stlaËenÌ back to menu sa nastav˝ nov˝ context
        if (e.getSource().equals(buttonBackToMenu)){
            this.frame.setContext(new LoginWindow(frame , start).getContent());
        }
    }

    //Vytvorenie metody ktor· skontroluje regex a zadanÈ heslo Ëi obsahuje potrebnÈ znaky
    public boolean validatePassword(String password) {
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+|`{}\\[\\]:\";'<>?,./])[a-zA-Z\\d!@#$%^&*()_+|`{}\\[\\]:\";'<>?,./]{6,16}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }
    //Vytvorenie metody ktor· skontroluje email podla regexu Ëi je v spr·vnom form·te
    public boolean validateEmail(String email) {
        String regex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
