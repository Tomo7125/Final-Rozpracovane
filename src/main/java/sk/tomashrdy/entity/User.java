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

    //Konötruktor pouûÌvany keÔ ùaham udaje z datab·zy a ukladam pouûÌvatela tam mi netreba heslo
    public User(String name, String lastName, String email , boolean isAdmin , int score) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.admin = isAdmin;
        this.score = score;
    }
    //Konötruktor pouûÌvany pri vytv·rani pouûÌvatela tu mi netreba admina lebo je ötandardne false
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

    //Metoda ktor· mi prid· noveho uûÌvatela do datab·zy pomocou mojej metÛdy executeUpdate
    public void userRegister(User user){
        DatabaseConnection databaseConnection = new DatabaseConnection();
        databaseConnection.executeUpdate("INSERT INTO users (first_name, last_name, email, password, isadmin) VALUES (?, ?, ?, ?, ?, 0)" ,
                user.getName() , user.getLastName() , user.getEmail() , user.getPassword() , user.admin);
        databaseConnection.disconnect();
    }
}
