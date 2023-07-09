package sk.tomashrdy.GUI;

import sk.tomashrdy.entity.Quiz;
import sk.tomashrdy.entity.QuizOptions;
import sk.tomashrdy.entity.QuizQuestion;
import sk.tomashrdy.entity.Start;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class CreateQuestion implements ActionListener {
    private JPanel panelQuestion;
    private JLabel jlQuestion;
    private JTextField tfQuestion;
    private JTextField tfOption1;
    private JTextField tfOption2;
    private JTextField tfOption3;
    private JTextField tfOption4;
    private JRadioButton radioButtonOption1;
    private JRadioButton radioButtonOption2;
    private JRadioButton radioButtonOption3;
    private JRadioButton radioButtonOption4;
    private JLabel jlAnswer;
    private JButton buttonaddQuestion;
    private JButton buttonSendQuiz;
    private JButton buttonBack;
    private JLabel jlCount;
    private Frame frame;
    private Start start;
    private Quiz quiz;
    private int questionCount = 0;

    public CreateQuestion(Frame frame, Start start, Quiz quiz) {
        this.frame = frame;
        this.start = start;
        this.quiz = quiz;
        buttonBack.addActionListener(this);
        buttonSendQuiz.addActionListener(this);
        buttonaddQuestion.addActionListener(this);

        buttonSendQuiz.setEnabled(false);
        if (questionCount >= 5){
            buttonSendQuiz.setEnabled(true);
        }
    }

    public JPanel getContent(){return this.panelQuestion;}

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(buttonBack)){
            this.frame.setContext(new AdminMenu(frame , start).getContent());
        }
        if (e.getSource().equals(buttonaddQuestion)){
            ArrayList<QuizOptions> quizOptions = new ArrayList<>();
            QuizOptions quizOptions1 = new QuizOptions(tfOption1.getText() , radioButtonOption1.isSelected());
            QuizOptions quizOptions2 = new QuizOptions(tfOption2.getText() , radioButtonOption2.isSelected());
            QuizOptions quizOptions3 = new QuizOptions(tfOption3.getText() , radioButtonOption3.isSelected());
            QuizOptions quizOptions4 = new QuizOptions(tfOption4.getText() , radioButtonOption4.isSelected());
            quizOptions.add(quizOptions1);
            quizOptions.add(quizOptions2);
            quizOptions.add(quizOptions3);
            quizOptions.add(quizOptions4);
            QuizQuestion quizQuestion = new QuizQuestion(tfQuestion.getText() , quizOptions);
            quiz.addQuestion(quizQuestion);
            questionCount++;
            tfQuestion.setText("");
            tfOption1.setText("");
            tfOption2.setText("");
            tfOption3.setText("");
            tfOption4.setText("");
            ButtonGroup buttonGroup = new ButtonGroup();
            buttonGroup.add(radioButtonOption1);
            buttonGroup.add(radioButtonOption2);
            buttonGroup.add(radioButtonOption3);
            buttonGroup.add(radioButtonOption4);
            buttonGroup.clearSelection();
            jlCount.setText("Question count : " + questionCount);
            if (questionCount >= 2){  // upravit speù na 5
                buttonSendQuiz.setEnabled(true);
            }
        }
        if (e.getSource().equals(buttonSendQuiz)){
            start.createNewQuiz(quiz);
            this.frame.setContext(new AdminMenu(frame , start).getContent());
        }
    }
}
