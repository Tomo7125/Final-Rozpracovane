package sk.tomashrdy.GUI;

import sk.tomashrdy.entity.Start;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminMenu implements ActionListener {
    private JPanel panelAdminMenu;
    private JLabel jlAdminMenu;
    private JButton buttonAllQuiz;
    private JButton buttonAllUsers;
    private JButton butonBack;
    private JLabel jlName;
    private Frame frame;
    private Start start;

    public AdminMenu(Frame frame, Start start) {
        this.frame = frame;
        this.start = start;
        butonBack.addActionListener(this);
        buttonAllQuiz.addActionListener(this);
        buttonAllUsers.addActionListener(this);

        jlName.setText("Login : " + start.getUser().getName() + " " + start.getUser().getLastName());
    }

    public JPanel getContent(){return this.panelAdminMenu;}


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(butonBack)){
            frame.setContext(new DashBoard(frame , start).getContent());
        }
        if (e.getSource().equals(buttonAllUsers)){
            frame.setContext(new AllUsers(frame , start).getContent());
        }
        if (e.getSource().equals(buttonAllQuiz)){
            frame.setContext(new AdminQuizMenu(frame , start).getContent());
        }
    }
}
