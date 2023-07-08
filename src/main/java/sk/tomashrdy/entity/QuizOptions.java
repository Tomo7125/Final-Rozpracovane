package sk.tomashrdy.entity;

public class QuizOptions {
    private String textOptions;
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

// vyrie�i� pre�o sa quiz zobraz� 2x , zmeni� start na delete , dorie�i� separovanie