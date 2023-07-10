package sk.tomashrdy.GUI;

import sk.tomashrdy.entity.Quiz;
import sk.tomashrdy.entity.QuizCategory;
import sk.tomashrdy.start.Start;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreateQuiz implements ActionListener {

    private JPanel panelAddQuiz;
    private JLabel jlAddQuiz;
    private JTextField tfName;
    private JTextField tfDescription;
    private JComboBox comboBoxLanguage;
    private JSlider sliderDifficulty;
    private JButton buttonNext;
    private JButton buttonBack;
    private Frame frame;
    private Start star;
    public JPanel getContent(){return this.panelAddQuiz;}

    public CreateQuiz(Frame frame, Start star) {
        this.frame = frame;
        this.star = star;
        buttonBack.addActionListener(this);
        buttonNext.addActionListener(this);

        DefaultComboBoxModel<QuizCategory> comboBoxCategory = new DefaultComboBoxModel<>();

        for (QuizCategory k: QuizCategory.values()) {
            comboBoxCategory.addElement(k);
        }
        comboBoxLanguage.setModel(comboBoxCategory);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(buttonBack)){
            this.frame.setContext(new AdminMenu(frame , star).getContent());
        }
        if (e.getSource().equals(buttonNext)){
            if (!tfName.getText().isEmpty() && !tfDescription.getText().isEmpty()){
                Quiz quiz = new Quiz();
                quiz.setNameForFile(tfName.getText());
                quiz.setName(tfDescription.getText());
                quiz.setQuizCategory((QuizCategory) comboBoxLanguage.getSelectedItem());
                quiz.setDifficulty(sliderDifficulty.getValue());
                this.frame.setContext( new CreateQuestion(frame , star , quiz).getContent());

            }else {
                JOptionPane.showMessageDialog(null,"Name and descrtiption must by filled" , "warning" , JOptionPane.WARNING_MESSAGE);
            }
        }
    }
}
