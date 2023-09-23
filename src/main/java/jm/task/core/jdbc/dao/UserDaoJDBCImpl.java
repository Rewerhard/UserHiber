package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {

        try {
            Util.setConnection();
            Util.getStatement().executeUpdate("CREATE TABLE IF NOT EXISTS users(" +
                    "user_id BIGINT PRIMARY KEY AUTO_INCREMENT NOT NULL, " +
                    "user_name  VARCHAR(30)," +
                    "user_lastname VARCHAR(30)," +
                    "user_age TINYINT" +
                    ");"
            );
        } catch (SQLException e) {
            printMessage(e);
        } finally {
            Util.disconnect();
        }
    }

    public void dropUsersTable() {

        try {
            Util.setConnection();
            Util.getStatement().executeUpdate("DROP TABLE users");
        } catch (SQLException e) {
            printMessage(e);
        } finally {
            Util.disconnect();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        PreparedStatement preparedStatement = null;
        String sql = "INSERT INTO users (user_name, user_lastname, user_age) VALUES (?, ?, ?)";
        try {
            Util.setConnection();
            preparedStatement = Util.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            System.out.println("User с именем " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            printMessage(e);
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    printMessage(e);
                }
            }
            Util.disconnect();
        }
    }

    public void removeUserById(long id) {
        PreparedStatement statement = null;
        try {
            Util.setConnection();
            statement = Util.getConnection().prepareStatement("DELETE FROM users WHERE id = ?");
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            printMessage(e);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    printMessage(e);
                }
            }
            Util.disconnect();
        }
    }

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        try {
            Util.setConnection();
            try (ResultSet resultSet = Util.getStatement().executeQuery("SELECT * FROM users")) {
                while (resultSet.next()) {
                    User user = new User();
                    user.setId(resultSet.getLong(1));
                    user.setName(resultSet.getString(2));
                    user.setLastName(resultSet.getString(3));
                    user.setAge(resultSet.getByte(4));
                    list.add(user);
                    System.out.println(user);
                }
            }
        } catch (SQLException e) {
            printMessage(e);
        } finally {
            Util.disconnect();
        }
        return list;
    }

    public void cleanUsersTable() {
        try {
            Util.setConnection();
            Util.getStatement().executeUpdate("TRUNCATE TABLE users");
        } catch (SQLException e) {
            printMessage(e);
        }
    }

    public void printMessage(SQLException e) {
        System.out.println("SQLException: " + e.getMessage());
        System.out.println("SQLState: " + e.getSQLState());
        System.out.println("VendorError: " + e.getErrorCode());
    }

}
