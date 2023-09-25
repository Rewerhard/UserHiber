package jm.task.core.jdbc.util;


import jm.task.core.jdbc.dao.UserDaoJDBCImpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static final String USER_NAME = "root";
    private static final String PASSWORD = "avanor";
    private static final String URL = "jdbc:mysql://localhost:3306/mydatabase";
    private static final String JDBC = "com.mysql.cj.jdbc.Driver";

    public static Connection setConnection() {
        Connection connectin = null;
        try {
            Class.forName(JDBC);
            connectin = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
        return connectin;
    }

    public static void close() {
        UserDaoJDBCImpl.disconnect();
    }

}
