package sk.tomashrdy.GUI;

import sk.tomashrdy.start.Start;

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
    private JButton buttonAddQuiz;
    private Frame frame;
    private Start start;
    public JPanel getContent(){return this.panelAdminMenu;}

    public AdminMenu(Frame frame, Start start) {
        this.frame = frame;
        this.start = start;
        butonBack.addActionListener(this);
        buttonAllQuiz.addActionListener(this);
        buttonAllUsers.addActionListener(this);
        buttonAddQuiz.addActionListener(this);

        jlName.setText("Login : " + start.getUser().getName() + " " + start.getUser().getLastName());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(butonBack)){
            this.frame.setContext(new DashBoard(frame , start).getContent());
        }
        if (e.getSource().equals(buttonAllUsers)){
            this.frame.setContext(new AllUsers(frame , start).getContent());
        }
        if (e.getSource().equals(buttonAllQuiz)){
            this.frame.setContext(new AdminQuizMenu(frame , start).getContent());
        }
        if (e.getSource().equals(buttonAddQuiz)){
            this.frame.setContext(new CreateQuiz(frame , start).getContent());
        }
    }
}
