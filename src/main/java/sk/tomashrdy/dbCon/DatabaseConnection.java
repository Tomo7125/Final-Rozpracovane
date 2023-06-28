package sk.tomashrdy.dbCon;

import java.sql.*;

public class DatabaseConnection {
    private Connection connection;
    //Hodnoty pre pripojenie na moju DB
    private final String url = "jdbc:postgresql://localhost:5432/postgres";
    private final String username = "postgres";
    private final String password = System.getenv("DB_PASSWORD");
    //Metoda pre pripojenie na DB
    public String connect(){
        try {
            connection = DriverManager.getConnection(url , username , password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
    //Metoda pre odpojenie DB
    public void disconnect(){
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            // Spracovanie chyby pri uzatv·ranÌ spojenia
        }
    }
    // Metoda ktora odosiela query do DB a vracia mi ResultSet
    public ResultSet executeQuery(String query){
        ResultSet resultSet = null;
        try {
            connect();
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            disconnect();

        } catch (SQLException e) {
            // Spracovanie chyby pri vykon·vanÌ dotazu
        }
        return resultSet;
    }
    //Metoda ktora mi updatuje data Napriklad ked chcem pridaù do datab·zy z·znam a vracia mi int s poËtom zaznamov ktorÈ boli zmenenÈ / pridanÈ
    public int executeUpdate(String query, Object... parameters) {
        int rowsAffected = 0;
        try {
            connect();
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
    //Metoda vytvorÌ prepojenie na databazu a vrati prepareStatement ktor˝ obsahuje pripraven˙ query na odoslanie do DB
    public PreparedStatement prepareStatement(String query) throws SQLException {
        connect();
        return connection.prepareStatement(query);
    }
}

