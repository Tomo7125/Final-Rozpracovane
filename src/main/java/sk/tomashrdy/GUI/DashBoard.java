package sk.tomashrdy.GUI;

import sk.tomashrdy.entity.Start;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DashBoard implements ActionListener {
    private JPanel panelDashBoard;
    private JLabel jlName;
    private JLabel jlEmail;
    private JLabel jlAdmin;
    private JLabel jlMenu;
    private JButton buttonAdminMenu;
    private JButton buttonLogout;
    private JButton quizButton;
    Frame frame;
    Start start;

    //Kon�truktor pre DashBoard
    public DashBoard(Frame frame, Start start) {
        this.frame = frame;
        this.start = start;
        buttonAdminMenu.addActionListener(this);
        buttonLogout.addActionListener(this);

        //Nasetujem pre ka�d� jLabel potrebne udaje z mojho usera ktor� je ulozeny ako prihlaseny
        jlName.setText("Login : " + start.getUser().getName() + " " + start.getUser().getLastName());
   //     jlEmail.setText("Email pou��vatela : " + start.getUser().getEmail());
    //    jlAdmin.setText(start.getUser().isAdmin() ? "Pou��vatel je admin." : "Pou��vatel nieje admin");
        if (!start.getUser().isAdmin()){buttonAdminMenu.setVisible(false);}
    }

    //Vracia panel ako content pre okno
    public JPanel getContent(){return this.panelDashBoard;}
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(buttonAdminMenu)){
            frame.setContext(new AdminMenu(frame , start).getContent());
        }
        if (e.getSource().equals(buttonLogout)){
            frame.setContext(new LoginWindow(frame , start).getContent());
        }
    // Pr�prava na nesk�r
    }
}
