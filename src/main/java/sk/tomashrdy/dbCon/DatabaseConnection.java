package sk.tomashrdy.dbCon;

import java.sql.*;


public class DatabaseConnection {
    private Connection connection;
    //Hodnoty pre pripojenie na moju DB
    private final String url = "jdbc:postgresql://localhost:5432/postgres";
    private final String username = "postgres";
    //Heslo mam ulo�en� ako premennu v mojom PC
    private final String password = System.getenv("DB_PASSWORD");

    //Metoda pre pripojenie na DB
    public String connect() {
        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    //Metoda pre odpojenie DB
    public void disconnect() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            // Spracovanie chyby pri uzatv�ran� spojenia
        }
    }

    // Metoda ktora odosiela query do DB a vracia mi ResultSet
    public ResultSet executeQuery(String query) {
        ResultSet resultSet = null;
        try {
            connect();
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            disconnect();

        } catch (SQLException e) {
            // Spracovanie chyby pri vykon�van� dotazu
        }
        return resultSet;
    }
    public ResultSet executeQuery(String query, String parameter) {
        ResultSet resultSet = null;
        try {
            connect();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, parameter);
            resultSet = statement.executeQuery();
            disconnect();
        } catch (SQLException e) {
            // Spracovanie chyby pri vykon�van� dotazu
        }
        return resultSet;
    }

    //Metoda ktor� mi updatuje data. Napr�klad ked chcem prida� do datab�zy z�znam a vracia mi int s po�tom z�znamov ktor� boli zmenen� / pridan�
    public int executeUpdate(String query, Object... parameters) {
        int rowsAffected = 0;
        try {
            connect();
            PreparedStatement statement = connection.prepareStatement(query);
            setParameters(statement, parameters);
            rowsAffected = statement.executeUpdate();
            disconnect();
        } catch (SQLException e) {
            // Spracovanie chyby pri vykon�van� aktualiz�cie
        }
        return rowsAffected;
    }

    // Prid�vanie parametrov do query tak aby som mohol ma� lubovoln� po�et parametrov
    private void setParameters(PreparedStatement statement, Object... parameters) throws SQLException {
        for (int i = 0; i < parameters.length; i++) {
            statement.setObject(i + 1, parameters[i]);
        }
    }

    //Metoda vytvor� prepojenie na datab�zu a vrati prepareStatement ktor� obsahuje pripraven� query na odoslanie do DB
    public PreparedStatement prepareStatement(String query) throws SQLException {
        connect();
        return connection.prepareStatement(query);
    }
    // Metoda upraven� a presunut� do triedy User
//    public ArrayList<User> getAllUser() {
//        ArrayList<User> allUser = new ArrayList<>();
//        ResultSet users = executeQuery("SELECT * FROM users");
//
//        try {
//            while (users.next()) {
//                User user = new User(users.getString("first_name"), users.getString("last_name"),
//                        users.getString("email"), users.getBoolean("isAdmin") , users.getInt("score"));
//                allUser.add(user);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return allUser;
//    }
}

