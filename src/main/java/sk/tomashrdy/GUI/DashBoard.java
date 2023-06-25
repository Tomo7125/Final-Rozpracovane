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

    public DashBoard(Frame frame, Start start) {
        this.frame = frame;
        this.start = start;

        jlName.setText("Meno používatela : " + start.getUser().getName() + " " + start.getUser().getLastName());
        jlEmail.setText("Email používatela : " + start.getUser().getEmail());
        jlAdmin.setText(start.getUser().isAdmin() ? "Používatel je admin." : "Používatel nieje admin");
    }

    public JPanel getContent(){return this.panelDashBoard;}



    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
