package sk.tomashrdy.GUI;

import sk.tomashrdy.entity.Start;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SelectQuiz implements ActionListener {
    private JPanel panelQuiz;
    private JLabel quizLabel;
    private JComboBox comboBoxDificulity;
    private JComboBox comboBoxCategory;
    private JTable table1;
    Frame frame;
    Start start;

    public SelectQuiz(Frame frame, Start start) {
        this.frame = frame;
        this.start = start;

        comboBoxDificulity.addActionListener(this);
        comboBoxCategory.addActionListener(this);

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

    }
}
