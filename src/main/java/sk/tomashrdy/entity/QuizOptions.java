package sk.tomashrdy.entity;

public class QuizOptions {
    //Text odpovede
    private String textOptions;
    //boolean èi je odpoveï správna
    private boolean isCorrect;

    public QuizOptions(String textOptions, boolean isCorrect) {
        this.textOptions = textOptions;
        this.isCorrect = isCorrect;
    }

    public String getTextOptions() {
        return textOptions;
    }

    public void setTextOptions(String textOptions) {
        this.textOptions = textOptions;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(boolean isCorrect) {
        this.isCorrect = isCorrect;
    }
}
