package sk.tomashrdy.GUI;

import sk.tomashrdy.entity.Quiz;
import sk.tomashrdy.entity.Start;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ShowQuiz implements ActionListener {
    private JPanel panelQuiz;
    private JLabel quizLabel;
    private JComboBox comboBoxDificulity;
    private JComboBox comboBoxCategory;
    private JTable table1;
    private JButton buttonStartQuiz;
    private JButton buttonBack;
    Frame frame;
    Start start;

    public ShowQuiz(Frame frame, Start start) {
        this.frame = frame;
        this.start = start;

        comboBoxDificulity.addActionListener(this);
        comboBoxCategory.addActionListener(this);
        buttonStartQuiz.addActionListener(this);
        buttonBack.addActionListener(this);

        ArrayList<Quiz> quizs = start.quizForTable();
        DefaultTableModel model = (DefaultTableModel) table1.getModel();
        model.setRowCount(0);

        model.addColumn("Index");
        model.addColumn("Quiz Name");
        model.addColumn("Category");
        model.addColumn("Difficculty");

        int index = 1;
        for (Quiz quiz : quizs){
            if (quiz != null){
                String name = quiz.getName();
                String category = quiz.getQuizCategory().name();
                int difficulty = quiz.getDifficulty();

                model.addRow(new Object[]{index , name , category , difficulty});
                index++;
            }
        }

        DefaultComboBoxModel<Integer> defaultComboBoxModel = new DefaultComboBoxModel<>();
        comboBoxDificulity.setModel(defaultComboBoxModel);
        defaultComboBoxModel.addElement(null);
        for (int i = 1 ; i <= 5 ; i++){
            defaultComboBoxModel.addElement(i);
        }
    }



    public JPanel getContent(){return panelQuiz;}

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(buttonBack)){
            frame.setContext(new DashBoard(frame , start).getContent());
        }
    }
}
