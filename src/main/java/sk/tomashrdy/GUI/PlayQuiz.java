package sk.tomashrdy.GUI;

import sk.tomashrdy.entity.Quiz;
import sk.tomashrdy.start.Start;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PlayQuiz implements ActionListener {
    private JPanel panelPlayQuiz;
    private JLabel jlQuestion;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JButton button4;
    private JLabel jlScoreInQuiz;
    private JLabel jlQuizTitle;
    private Frame frame;
    private Start start;
    private Quiz quiz;
    private int scoreInQuiz = 0;
    private int questionNumber = 0;

    public JPanel getContent(){return this.panelPlayQuiz;}

    public PlayQuiz(Frame frame, Start start, Quiz quiz) {
        this.frame = frame;
        this.start = start;
        this.quiz = quiz;
        button1.addActionListener(this);
        button2.addActionListener(this);
        button3.addActionListener(this);
        button4.addActionListener(this);

      jlScoreInQuiz.setText("Your score is : " + scoreInQuiz);
      jlQuizTitle.setText("Quiz : " + quiz.getName());
      jlQuestion.setText(quiz.getQuestions().get(questionNumber).getTextQuestion());
      button1.setText(quiz.getQuestions().get(questionNumber).getTextOptions().get(0).getTextOptions());
      button2.setText(quiz.getQuestions().get(questionNumber).getTextOptions().get(1).getTextOptions());
      button3.setText(quiz.getQuestions().get(questionNumber).getTextOptions().get(2).getTextOptions());
      button4.setText(quiz.getQuestions().get(questionNumber).getTextOptions().get(3).getTextOptions());


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(button1)){
            if (quiz.getQuestions().get(questionNumber).getTextOptions().get(0).isCorrect()){
                scoreInQuiz++;
                JOptionPane.showMessageDialog(null,"Perfect" , "Correctly" , JOptionPane.INFORMATION_MESSAGE);
            }else {JOptionPane.showMessageDialog(null,"False, correct is :" + correctOption(), "False" , JOptionPane.WARNING_MESSAGE);}
        }if (e.getSource().equals(button2)){
            if (quiz.getQuestions().get(questionNumber).getTextOptions().get(1).isCorrect()){
                scoreInQuiz++;
                JOptionPane.showMessageDialog(null,"Perfect" , "Correctly" , JOptionPane.INFORMATION_MESSAGE);
            }else {JOptionPane.showMessageDialog(null,"False, correct is :" + correctOption(), "False" , JOptionPane.WARNING_MESSAGE);}
        }if (e.getSource().equals(button3)){
            if (quiz.getQuestions().get(questionNumber).getTextOptions().get(2).isCorrect()){
                scoreInQuiz++;
                JOptionPane.showMessageDialog(null,"Perfect" , "Correctly" , JOptionPane.INFORMATION_MESSAGE);
            }else {JOptionPane.showMessageDialog(null,"False, correct is :" + correctOption(), "False" , JOptionPane.WARNING_MESSAGE);}
        }if (e.getSource().equals(button4)){
            if (quiz.getQuestions().get(questionNumber).getTextOptions().get(3).isCorrect()){
                scoreInQuiz++;
                JOptionPane.showMessageDialog(null,"Perfect" , "Correctly" , JOptionPane.INFORMATION_MESSAGE);
            }else {JOptionPane.showMessageDialog(null,"False, correct is :" + correctOption(), "False" , JOptionPane.WARNING_MESSAGE);}
        }
        questionNumber++;

        if (questionNumber >= quiz.getQuestions().size()){
            JOptionPane.showMessageDialog(null,"Great, you score is :" + scoreInQuiz , "Quiz Complete" , JOptionPane.INFORMATION_MESSAGE);
            start.updateScore(start.getUser().getEmail() , scoreInQuiz);
            this.frame.setContext(new DashBoard(frame , start).getContent());
        }else {
            jlQuestion.setText(quiz.getQuestions().get(questionNumber).getTextQuestion());
            button1.setText(quiz.getQuestions().get(questionNumber).getTextOptions().get(0).getTextOptions());
            button2.setText(quiz.getQuestions().get(questionNumber).getTextOptions().get(1).getTextOptions());
            button3.setText(quiz.getQuestions().get(questionNumber).getTextOptions().get(2).getTextOptions());
            button4.setText(quiz.getQuestions().get(questionNumber).getTextOptions().get(3).getTextOptions());
            jlScoreInQuiz.setText("Your score is : " + scoreInQuiz);
        }
    }
    public String correctOption(){
        if (quiz.getQuestions().get(questionNumber).getTextOptions().get(0).isCorrect()){
            return quiz.getQuestions().get(questionNumber).getTextOptions().get(0).getTextOptions();
        }else if (quiz.getQuestions().get(questionNumber).getTextOptions().get(1).isCorrect()){
            return quiz.getQuestions().get(questionNumber).getTextOptions().get(1).getTextOptions();
        }else if (quiz.getQuestions().get(questionNumber).getTextOptions().get(2).isCorrect()){
            return quiz.getQuestions().get(questionNumber).getTextOptions().get(2).getTextOptions();
        }else if (quiz.getQuestions().get(questionNumber).getTextOptions().get(3).isCorrect()){
            return quiz.getQuestions().get(questionNumber).getTextOptions().get(3).getTextOptions();
        } else return null;
    }
}
