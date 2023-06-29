package sk.tomashrdy.GUI;

import sk.tomashrdy.entity.Start;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AllUsers implements ActionListener {
    private JPanel panelAllUsers;
    private JLabel jlUsers;
    private JTable table1;
    private JButton buttonDeleteUser;
    private JButton buttonDeleteAllUsers;
    private JButton buttonBack;
    private Frame frame;
    private Start start;

    public AllUsers(Frame frame, Start start) {
        this.frame = frame;
        this.start = start;
        buttonBack.addActionListener(this);
        buttonDeleteAllUsers.addActionListener(this);
        buttonDeleteUser.addActionListener(this);
    }

    public JPanel getContent(){return this.panelAllUsers;}

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(buttonBack)){
            frame.setContext(new AdminMenu(frame , start).getContent());
        }

    }
}
