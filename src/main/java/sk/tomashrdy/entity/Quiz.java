package sk.tomashrdy.entity;

import java.util.ArrayList;


public class Quiz {
    private String nameForFile;
    //nazov
    private String name;
    //kategoria
    private QuizCategory quizCategory;
    //obtiaznost
    private int difficulty; //1..5
    //kolekcia otazok
    private ArrayList<QuizQuestion> questions;
    public String getName() {
        return name;
    }

    public QuizCategory getQuizCategory() {
        return quizCategory;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public ArrayList<QuizQuestion> getQuestions() {
        return questions;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setQuizCategory(QuizCategory quizCategory) {
        this.quizCategory = quizCategory;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public void setQuestions(ArrayList<QuizQuestion> questions) {
        this.questions = questions;
    }
    public String getNameForFile() {
        return nameForFile;
    }

    public void setNameForFile(String nameForFile) {
        this.nameForFile = nameForFile;
    }

    public void addQuestion(QuizQuestion question){
        if (questions == null){
            questions = new ArrayList<>();
        }
        this.questions.add(question);
    }
}