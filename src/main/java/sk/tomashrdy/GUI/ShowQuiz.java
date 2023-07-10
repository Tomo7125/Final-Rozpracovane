package sk.tomashrdy.GUI;

import sk.tomashrdy.entity.Quiz;
import sk.tomashrdy.entity.QuizCategory;
import sk.tomashrdy.start.Start;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ShowQuiz implements ActionListener {
    private JPanel panelQuiz;
    private JLabel quizLabel;
    private JComboBox comboBoxDificulty;
    private JComboBox comboBoxCategory;
    private JTable tableQuiz;
    private JButton buttonStartQuiz;
    private JButton buttonBack;
    Frame frame;
    Start start;
    public JPanel getContent(){return panelQuiz;}

    public ShowQuiz(Frame frame, Start start) {
        this.frame = frame;
        this.start = start;

        comboBoxDificulty.addActionListener(this);
        comboBoxCategory.addActionListener(this);
        buttonStartQuiz.addActionListener(this);
        buttonBack.addActionListener(this);

        ArrayList<Quiz> quizs = start.quizForTable();
        DefaultTableModel model = (DefaultTableModel) tableQuiz.getModel();
        model.setRowCount(0);

        model.addColumn("Index");
        model.addColumn("Quiz Name");
        model.addColumn("Category");
        model.addColumn("Difficculty");
        model.addRow(new Object[]{ "Index" , "Quiz Name" ,"Category", "Difficulty"});

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

        DefaultComboBoxModel<QuizCategory> comboBoxCategory = new DefaultComboBoxModel<>();

        comboBoxCategory.addElement(null);
        for (QuizCategory k: QuizCategory.values()) {
            comboBoxCategory.addElement(k);
        }
        this.comboBoxCategory.setModel(comboBoxCategory);


        DefaultComboBoxModel<Integer> comboBoxDifficulty = new DefaultComboBoxModel<>();

        comboBoxDifficulty.addElement(null);
        for (int i = 1; i <= 5; i++){
            comboBoxDifficulty.addElement(i);
        }
        comboBoxDificulty.setModel(comboBoxDifficulty);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(buttonBack)){
            this.frame.setContext(new DashBoard(frame , start).getContent());
        }
        if (e.getSource().equals(comboBoxCategory)){
            QuizCategory category = (QuizCategory) comboBoxCategory.getSelectedItem();
            restartTable();
            updateTableByCategory(category);
        }
        if (e.getSource().equals(comboBoxDificulty)){
            Integer difficulty = (Integer) comboBoxDificulty.getSelectedItem();
            restartTable();
            updateTableByDifficulty(difficulty);
        }
        if (e.getSource().equals(buttonStartQuiz)){
            int selectedRow = tableQuiz.getSelectedRow();
            Object quizName = tableQuiz.getValueAt(selectedRow,1);
            String quizNameValue = quizName.toString();
            Quiz newQuiz = start.createQuizByName(quizNameValue);
            this.frame.setContext(new PlayQuiz(frame , start , newQuiz).getContent());
        }
    }
    public void restartTable(){
        DefaultTableModel model = (DefaultTableModel) tableQuiz.getModel();
        model.setRowCount(0);
        model.setColumnCount(0);
    }

    public void updateTableByDifficulty(Integer difficulty){
        DefaultTableModel model = (DefaultTableModel) tableQuiz.getModel();

        model.addColumn("Index");
        model.addColumn("Quiz Name");
        model.addColumn("Category");
        model.addColumn("Difficculty");
        model.addRow(new Object[]{ "Index" , "Quiz Name" ,"Category", "Difficulty"});
        int index = 1;
        for (Quiz quiz : this.start.quizForTable()){
            if (quiz != null && (difficulty == null || quiz.getDifficulty() == difficulty)){
                model.addRow(new Object[]{index , quiz.getName() , quiz.getQuizCategory() , quiz.getDifficulty()});
                index++;
            }
        }
        tableQuiz.setModel(model);
    }

    public void updateTableByCategory(QuizCategory quizCategory){
        DefaultTableModel model = (DefaultTableModel) tableQuiz.getModel();

        model.addColumn("Index");
        model.addColumn("Quiz Name");
        model.addColumn("Category");
        model.addColumn("Difficculty");
        model.addRow(new Object[]{ "Index" , "Quiz Name" ,"Category", "Difficulty"});
        int index = 1;
        for (Quiz quiz : this.start.quizForTable()){
            if (quiz != null && (quizCategory == null || quiz.getQuizCategory() == quizCategory)){
                model.addRow(new Object[]{index , quiz.getName() , quiz.getQuizCategory() , quiz.getDifficulty()});
                index++;
            }
        }
        tableQuiz.setModel(model);
    }
}
