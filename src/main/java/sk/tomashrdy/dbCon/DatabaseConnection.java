package sk.tomashrdy.dbCon;

import java.sql.*;


public class DatabaseConnection {
    private static DatabaseConnection databaseConnection;
    private Connection connection;
    private final String password = System.getenv("DB_PASSWORD");

    private DatabaseConnection() {
        try {

            Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres","postgres",password);
            this.connection = con;

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static DatabaseConnection getConection(){
        if (databaseConnection == null){
            databaseConnection = new DatabaseConnection();
        }return databaseConnection;
    }

    //Metoda pre odpojenie DB
    public void disconnect() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            // Spracovanie chyby pri uzatv·ranÌ spojenia
        }
    }

    // Metoda ktora odosiela query do DB a vracia mi ResultSet
    public ResultSet executeQuery(String query) {
        ResultSet resultSet = null;
        try {
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            disconnect();

        } catch (SQLException e) {
            // Spracovanie chyby pri vykon·vanÌ dotazu
        }
        return resultSet;
    }
    public ResultSet executeQuery(String query, String parameter) {
        ResultSet resultSet = null;
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, parameter);
            resultSet = statement.executeQuery();
            disconnect();
        } catch (SQLException e) {
            // Spracovanie chyby pri vykon·vanÌ dotazu
        }
        return resultSet;
    }

    //Metoda ktor· mi updatuje data. NaprÌklad ked chcem pridaù do datab·zy z·znam a vracia mi int s poËtom z·znamov ktorÈ boli zmenenÈ / pridanÈ
    public int executeUpdate(String query, Object... parameters) {
        int rowsAffected = 0;
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            setParameters(statement, parameters);
            rowsAffected = statement.executeUpdate();
            disconnect();
        } catch (SQLException e) {
            // Spracovanie chyby pri vykon·vanÌ aktualiz·cie
        }
        return rowsAffected;
    }

    // Prid·vanie parametrov do query tak aby som mohol maù lubovoln˝ poËet parametrov
    private void setParameters(PreparedStatement statement, Object... parameters) throws SQLException {
        for (int i = 0; i < parameters.length; i++) {
            statement.setObject(i + 1, parameters[i]);
        }
    }

    //Metoda vytvorÌ prepojenie na datab·zu a vrati prepareStatement ktor˝ obsahuje pripraven˙ query na odoslanie do DB
    public PreparedStatement prepareStatement(String query) throws SQLException {
        return connection.prepareStatement(query);
    }
    // Metoda upraven· a presunut· do triedy User
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

