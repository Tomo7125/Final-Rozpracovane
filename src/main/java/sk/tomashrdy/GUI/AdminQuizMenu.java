package sk.tomashrdy.GUI;

import sk.tomashrdy.entity.Quiz;
import sk.tomashrdy.entity.QuizCategory;
import sk.tomashrdy.entity.Start;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class AdminQuizMenu implements ActionListener {
    private JPanel panelAdminQuizMenu;
    private JLabel jlQuiz;
    private JTable tableQuiz;
    private JComboBox comboBoxCategory;
    private JComboBox comboBoxDifficulity;
    private JButton buttonBack;
    private JButton buttonDelete;
    private Frame frame;
    private Start start;

    public AdminQuizMenu(Frame frame, Start start) {
        this.frame = frame;
        this.start = start;
        buttonDelete.addActionListener(this);
        buttonBack.addActionListener(this);
        comboBoxCategory.addActionListener(this);
        comboBoxDifficulity.addActionListener(this);

        ArrayList<Quiz> quizs = start.quizForTable();
        DefaultTableModel model = (DefaultTableModel) tableQuiz.getModel();
        model.setRowCount(0);

        model.addColumn("Index");
        model.addColumn("Quiz Name");
        model.addColumn("Category");
        model.addColumn("Difficulity");
        model.addRow(new Object[]{"Index", "Quiz Name", "Category", "Difficulty"});

        int index = 1;
        for (Quiz quiz : quizs) {
            if (quiz != null) {
                String name = quiz.getName();
                String category = quiz.getQuizCategory().name();
                int difficulty = quiz.getDifficulty();

                model.addRow(new Object[]{index, name, category, difficulty});
                index++;
            }
        }

        DefaultComboBoxModel<QuizCategory> comboboxModelKategoria = new DefaultComboBoxModel<>();

        comboboxModelKategoria.addElement(null);
        for (QuizCategory k : QuizCategory.values()) {
            comboboxModelKategoria.addElement(k);
        }
        comboBoxCategory.setModel(comboboxModelKategoria);


        DefaultComboBoxModel<Integer> comboboxModelObtiaznost = new DefaultComboBoxModel<>();

        comboboxModelObtiaznost.addElement(null);
        for (int i = 1; i <= 5; i++) {
            comboboxModelObtiaznost.addElement(i);
        }
        comboBoxDifficulity.setModel(comboboxModelObtiaznost);
    }

    public void restartTable () {
        DefaultTableModel model = (DefaultTableModel) tableQuiz.getModel();
        model.setRowCount(0);
        model.setColumnCount(0);
    }

    public void updateTableByDifficulty (Integer difficulty){
        DefaultTableModel model = (DefaultTableModel) tableQuiz.getModel();

        model.addColumn("Index");
        model.addColumn("Quiz Name");
        model.addColumn("Category");
        model.addColumn("Difficculty");
        model.addRow(new Object[]{"Index", "Quiz Name", "Category", "Difficulty"});
        int index = 1;
        for (Quiz quiz : this.start.quizForTable()) {
            if (quiz != null && (difficulty == null || quiz.getDifficulty() == difficulty)) {
                model.addRow(new Object[]{index, quiz.getName(), quiz.getQuizCategory(), quiz.getDifficulty()});
                index++;
            }
        }
        tableQuiz.setModel(model);
    }

    public void updateTableByCategory (QuizCategory quizCategory){
        DefaultTableModel model = (DefaultTableModel) tableQuiz.getModel();

        model.addColumn("Index");
        model.addColumn("Quiz Name");
        model.addColumn("Category");
        model.addColumn("Difficculty");
        model.addRow(new Object[]{"Index", "Quiz Name", "Category", "Difficulty"});
        int index = 1;
        for (Quiz quiz : this.start.quizForTable()) {
            if (quiz != null && (quizCategory == null || quiz.getQuizCategory() == quizCategory)) {
                model.addRow(new Object[]{index, quiz.getName(), quiz.getQuizCategory(), quiz.getDifficulty()});
                index++;
            }
        }
        tableQuiz.setModel(model);
    }

    public JPanel getContent(){return this.panelAdminQuizMenu;}

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(buttonBack)){
            frame.setContext(new AdminMenu(frame,start).getContent());
        }
        if (e.getSource().equals(comboBoxDifficulity)){
            Integer difficulty = (Integer) comboBoxDifficulity.getSelectedItem();
            restartTable();
            updateTableByDifficulty(difficulty);
        }
        if (e.getSource().equals(comboBoxCategory)){
            QuizCategory category = (QuizCategory) comboBoxCategory.getSelectedItem();
            restartTable();
            updateTableByCategory(category);
        }
    }
}
