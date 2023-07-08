package sk.tomashrdy.entity;

import java.util.ArrayList;

public class QuizQuestion {
    private String textQuestion;
    private ArrayList<QuizOptions> textOptions;

    public QuizQuestion(String textQuestion, ArrayList<QuizOptions> textOptions) {
        this.textQuestion = textQuestion;
        this.textOptions = textOptions;
    }


    public String getTextQuestion() {
        return textQuestion;
    }

    public ArrayList<QuizOptions> getTextOptions() {return textOptions;}

    public void addOptions(QuizOptions option){
        if (textOptions == null){
            textOptions = new ArrayList<>();
        }
        this.textOptions.add(option);
    }
}