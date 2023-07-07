package sk.tomashrdy.entity;

import java.util.ArrayList;

public class QuizQuestion {
    private String text_otazky;
    //kolekcia moznosti
    private ArrayList<QuizOptions> moznosti;

    public QuizQuestion(String text_otazky, ArrayList<QuizOptions> moznosti) {
        this.text_otazky = text_otazky;
        this.moznosti = moznosti;
    }


    public String getText_otazky() {
        return text_otazky;
    }

    public ArrayList<QuizOptions> getMoznosti() {
        return moznosti;
    }
}