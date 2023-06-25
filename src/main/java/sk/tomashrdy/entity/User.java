package sk.tomashrdy.entity;


import sk.tomashrdy.dbCon.DatabaseConnection;


public class User {
    String name , lastName , email , password;
    boolean isAdmin = false;

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public User(String name, String lastName, String email , boolean isAdmin) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.isAdmin = isAdmin;
    }

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
    public void userRegister(User user){
        DatabaseConnection databaseConnection = new DatabaseConnection();
        databaseConnection.executeUpdate("INSERT INTO users (first_name, last_name, email, password, isadmin) VALUES (?, ?, ?, ?, ?)" ,
                user.getName() , user.getLastName() , user.getEmail() , user.getPassword() , user.isAdmin);

    }
}
