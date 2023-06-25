package sk.tomashrdy.dbCon;

import java.sql.*;

public class DatabaseConnection {
    private Connection connection;

    private final String url = "jdbc:postgresql://localhost:5432/postgres";
    private final String username = "postgres";
    private final String password = "postgres";

    public String connect(){
        try {
            connection = DriverManager.getConnection(url , username , password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
    public void disconnect(){
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            // Spracovanie chyby pri uzatváraní spojenia
        }
    }
    public ResultSet executeQuery(String query){
        ResultSet resultSet = null;
        try {
            connect();
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            disconnect();

        } catch (SQLException e) {
            // Spracovanie chyby pri vykonávaní dotazu
        }
        return resultSet;
    }
    public int executeUpdate(String query, Object... parameters) {
        int rowsAffected = 0;
        try {
            connect();
            PreparedStatement statement = connection.prepareStatement(query);
            setParameters(statement, parameters);
            rowsAffected = statement.executeUpdate();
            disconnect();
        } catch (SQLException e) {
            // Spracovanie chyby pri vykonávaní aktualizácie
        }
        return rowsAffected;
    }

    private void setParameters(PreparedStatement statement, Object... parameters) throws SQLException {
        for (int i = 0; i < parameters.length; i++) {
            statement.setObject(i + 1, parameters[i]);
        }
    }

    public PreparedStatement prepareStatement(String query) throws SQLException {
        connect();
        return connection.prepareStatement(query);
    }
}

