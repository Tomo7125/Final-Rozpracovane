package sk.tomashrdy.GUI;

import sk.tomashrdy.start.Start;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DashBoard implements ActionListener {
    private JPanel panelDashBoard;
    private JLabel jlScore;
    private JLabel jlEmail;
    private JLabel jlAdmin;
    private JLabel jlMenu;
    private JButton buttonAdminMenu;
    private JButton buttonLogout;
    private JButton buttonQuiz;
    private JLabel jlName;
    private JButton buttonHighScore;
    Frame frame;
    Start start;
    public JPanel getContent(){return this.panelDashBoard;}

    //Konštruktor pre DashBoard
    public DashBoard(Frame frame, Start start) {
        this.frame = frame;
        this.start = start;
        buttonAdminMenu.addActionListener(this);
        buttonLogout.addActionListener(this);
        buttonQuiz.addActionListener(this);
        buttonHighScore.addActionListener(this);

        //Nasetujem pre každý jLabel potrebne udaje z mojho usera ktorý je ulozeny ako prihlaseny
        jlName.setText("Login : " + start.getUser().getName() + " " + start.getUser().getLastName());
        jlScore.setText("Score : " + start.getScore(start.getUser().getEmail()));
        if (!start.getUser().isAdmin()){buttonAdminMenu.setVisible(false);}
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(buttonAdminMenu)){
            this.frame.setContext(new AdminMenu(frame , start).getContent());
        }
        if (e.getSource().equals(buttonLogout)){
            this.frame.setContext(new LoginWindow(frame , start).getContent());
        }
        if (e.getSource().equals(buttonQuiz)){
            this.frame.setContext(new ShowQuiz(frame , start).getContent());
        }
        if (e.getSource().equals(buttonHighScore)){
            this.frame.setContext(new HighScore(frame , start).getContent());
        }
    }
}
