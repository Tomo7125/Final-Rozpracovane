package sk.tomashrdy.GUI;

import sk.tomashrdy.start.Start;
import sk.tomashrdy.entity.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class HighScore implements ActionListener {
    private JPanel panelHighScore;
    private JTable tableScore;
    private JButton buttonBack;
    private Frame frame;
    private Start start;

    public JPanel getContent(){return this.panelHighScore;}

    public HighScore(Frame frame, Start start) {
        this.frame = frame;
        this.start = start;
        buttonBack.addActionListener(this);

        ArrayList<User> users = start.getAllUsers();
        DefaultTableModel model = (DefaultTableModel) tableScore.getModel();
        model.setRowCount(0);

        Collections.sort(users, Comparator.comparingInt(User::getScore).reversed());

        model.addColumn("Index");
        model.addColumn("Name");
        model.addColumn("Last name");
        model.addColumn("Score");
        model.addRow(new Object[]{ "Index" , "Name" ,"Last name", "Score"});

        int index = 1;
        for (User user : users){
            if (user != null){
                String name = user.getName();
                String lastName = user.getLastName();
                int score = start.getScore(user.getEmail());


                model.addRow(new Object[]{index , name , lastName , score});
                index++;
            }
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(buttonBack)){
            this.frame.setContext(new DashBoard(frame , start).getContent());
        }
    }
}
