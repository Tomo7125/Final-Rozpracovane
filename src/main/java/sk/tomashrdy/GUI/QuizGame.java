package sk.tomashrdy.GUI;

import sk.tomashrdy.entity.Quiz;
import sk.tomashrdy.entity.Start;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class QuizGame implements ActionListener {
    private JPanel panelQuizGame;
    private JLabel tfQuestion;
    private JButton buttonOption1;
    private JButton buttonOption2;
    private JButton buttonOption3;
    private JButton buttonOption4;
    private Frame frame;
    private Start start;
    private Quiz quiz;

    public QuizGame(Frame frame, Start start, Quiz quiz) {
        this.frame = frame;
        this.start = start;
        this.quiz = quiz;
    }


    public JPanel getContent(){return this.panelQuizGame;}

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
