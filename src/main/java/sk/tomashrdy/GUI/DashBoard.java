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

    //Konštruktor pre DashBoard
    public DashBoard(Frame frame, Start start) {
        this.frame = frame;
        this.start = start;

        //Nasetujem pre každý jLabel potrebne udaje z mojho usera ktorý je ulozeny ako prihlaseny
        jlName.setText("Meno používatela : " + start.getUser().getName() + " " + start.getUser().getLastName());
        jlEmail.setText("Email používatela : " + start.getUser().getEmail());
        jlAdmin.setText(start.getUser().isAdmin() ? "Používatel je admin." : "Používatel nieje admin");
    }

    //Vracia panel ako content pre okno
    public JPanel getContent(){return this.panelDashBoard;}



    @Override
    public void actionPerformed(ActionEvent e) {
    // Príprava na neskôr
    }
}
