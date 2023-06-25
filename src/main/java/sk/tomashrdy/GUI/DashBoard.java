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
    Frame frame;
    Start start;

    //Kon�truktor pre DashBoard
    public DashBoard(Frame frame, Start start) {
        this.frame = frame;
        this.start = start;

        //Nasetujem pre ka�d� jLabel potrebne udaje z mojho usera ktor� je ulozeny ako prihlaseny
        jlName.setText("Meno pou��vatela : " + start.getUser().getName() + " " + start.getUser().getLastName());
        jlEmail.setText("Email pou��vatela : " + start.getUser().getEmail());
        jlAdmin.setText(start.getUser().isAdmin() ? "Pou��vatel je admin." : "Pou��vatel nieje admin");
    }

    //Vracia panel ako content pre okno
    public JPanel getContent(){return this.panelDashBoard;}



    @Override
    public void actionPerformed(ActionEvent e) {
    // Pr�prava na nesk�r
    }
}
