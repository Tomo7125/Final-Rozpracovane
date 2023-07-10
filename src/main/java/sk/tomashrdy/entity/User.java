package sk.tomashrdy.entity;


import sk.tomashrdy.dbCon.DatabaseConnection;

public class User {
    private String name , lastName , email , password;
    private int score = 0;

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    private boolean admin = false;

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public User() {
    }

    //Kon�truktor pou��vany ke� �aham �daje z datab�zy a ukladam pou��vatela tam mi netreba heslo
    public User(String name, String lastName, String email , boolean isAdmin , int score) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.admin = isAdmin;
        this.score = score;
    }
    //Kon�truktor pou��vany pri vytv�rani pou��vatela tu mi netreba admina lebo je �tandardne false
    public User(String name, String lastName, String email, String password) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

}
