package jm.task.core.jdbc.util;

import java.sql.*;

public class Util {
    private static final String USER_NAME = "root";
    private static final String PASSWORD = "avanor";
    private static final String URL = "jdbc:mysql://localhost:3306/mydatabase";
    private static final String JDBC = "com.mysql.cj.jdbc.Driver";

    private static Connection connection;
    private static Statement statement;

    public static Connection getConnection() {
        return connection;
    }
    public static Statement getStatement() {
        return statement;
    }

    public static void setConnection() throws SQLException {
        try {
            Class.forName(JDBC);
            connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
            statement = connection.createStatement();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
    }

    public static void disconnect() {
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (connection != null) {
                connection.close();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
