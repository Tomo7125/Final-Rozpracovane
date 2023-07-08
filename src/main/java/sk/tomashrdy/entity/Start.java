package sk.tomashrdy.entity;

import sk.tomashrdy.GUI.Frame;
import sk.tomashrdy.dbCon.DatabaseConnection;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Start {
    //Použijem na uloženie prihláseného používatela
    User user;

    //Vráti udaje používatela ktorého tu budem mať uloženeho ako prihlaseneho
    public User getUser() {
        return user;
    }
    // Nastavím si použivátela po prihlásení
    public void setUser(User user) {
        this.user = user;
    }
    //Konštruktor
    public Start() {
    }
    //Metóda pre spustenie programu
    public void spusti(){Frame frame = new Frame(this);}

    public void deleteUserByEmail(String email){
        DatabaseConnection databaseConnection = new DatabaseConnection();
        databaseConnection.executeUpdate("DELETE FROM users WHERE email = ?" ,
                email);
        databaseConnection.disconnect();
    }

    public ArrayList<Quiz> quizForTable(){
        String path = "D:\\Kurz\\Macrosoft\\Final_Projekt_02\\Kvizy";
        ArrayList<Quiz> quiz = new ArrayList<>();
        File directory = new File(path);
        if (directory.exists() && directory.isDirectory()){
            File[] files = directory.listFiles();
            if (files != null){
                for (File file : files){
                    Quiz newQuiz = new Quiz();
                    if (file.isFile() && file.getName().endsWith(".txt")){
                        ArrayList<String> lines = new ArrayList<>();
                        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                            String line;
                            while ((line = reader.readLine()) != null) {
                                lines.add(line);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if (!lines.isEmpty()) {
                            String firstLine = lines.get(0);
                            String[] splitFirstLine = firstLine.split(";");
                            newQuiz.setName(splitFirstLine[0]);
                            newQuiz.setQuizCategory(QuizCategory.valueOf(splitFirstLine[1]));
                            newQuiz.setDifficulty(Integer.parseInt(splitFirstLine[2]));
                            quiz.add(newQuiz);
                        }
                    }
                }
            }
        }
        return quiz;
    }

    public ArrayList<Quiz> createQuizFromTXT() {
        String path = "D:\\Kurz\\Macrosoft\\Final_Projekt_02\\Kvizy";
        ArrayList<Quiz> quiz = new ArrayList<>();
        File directory = new File(path);
        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    Quiz newQuiz = new Quiz();
                    if (file.isFile() && file.getName().endsWith(".txt")) {
                        ArrayList<String> lines = new ArrayList<>();
                        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                            String line;
                            while ((line = reader.readLine()) != null) {
                                lines.add(line);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if (!lines.isEmpty()) {
                            String firstLine = lines.get(0);
                            String[] splitFirstLine = firstLine.split(";");
                            newQuiz.setName(splitFirstLine[0]);
                            newQuiz.setQuizCategory(QuizCategory.valueOf(splitFirstLine[1]));
                            newQuiz.setDifficulty(Integer.parseInt(splitFirstLine[2]));
                            for (int i = 1; i < lines.size(); i++) {
                                String line = lines.get(i);
                                String[] splitLine = line.split(";");
                                String option = "";
                                boolean correct = false;
                                ArrayList<QuizOptions> options= new ArrayList<>();
                                QuizQuestion quizQuestion = new QuizQuestion(splitLine[0], options);
                                newQuiz.addQuestion(quizQuestion);
                                for (int x = 1 ; x < splitLine.length ; x+=2){
                                    int y = x+1;
                                        option = splitLine[x];
                                        if (splitLine[y].equals("1")){
                                            correct = true;
                                        }else { correct = false; }
                                        QuizOptions quizOption = new QuizOptions(null , false);
                                        quizOption.setTextOptions(option);
                                        quizOption.setIsCorrect(correct);
                                        quizQuestion.addOptions(quizOption);
                                }
                            }
                        }
                    }
                    quiz.add(newQuiz);
                }
            }
        }
        return quiz;
    }
}
