package sk.tomashrdy.entity;


import sk.tomashrdy.dbCon.DatabaseConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


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

    //Kon�truktor pou��vany ke� �aham udaje z datab�zy a ukladam pou��vatela tam mi netreba heslo
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

    //Metoda ktor� mi prid� noveho u��vatela do datab�zy pomocou mojej met�dy executeUpdate
    public void userRegister(User user){
        DatabaseConnection databaseConnection = new DatabaseConnection();
        databaseConnection.executeUpdate("INSERT INTO users (first_name, last_name, email, password, isadmin, score) VALUES (?, ?, ?, ?, ?, 0)" ,
                user.getName() , user.getLastName() , user.getEmail() , user.getPassword() , user.admin);
        databaseConnection.disconnect();
    }
    public ArrayList<User> getAllUsers() {
        ArrayList<User> allUsers = new ArrayList<>();
        DatabaseConnection dbConnect = new DatabaseConnection();
        ResultSet resultSet;

        resultSet = dbConnect.executeQuery("SELECT * FROM users");

        try {
            while (resultSet.next()) {
                String name = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String email = resultSet.getString("email");
                int score = resultSet.getInt("score");
                Boolean isAdmin = resultSet.getBoolean("isAdmin");

                User user = new User(name, lastName, email , isAdmin , score);
                allUsers.add(user);
            }
            dbConnect.disconnect();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return allUsers;
    }
}
