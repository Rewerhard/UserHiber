package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private static Connection connection = Util.setConnection();

    public UserDaoJDBCImpl() {

    }

    @Override
    public void createUsersTable() {
        String sqlCreateTable = "CREATE TABLE IF NOT EXISTS users(" +
                "user_id BIGINT PRIMARY KEY AUTO_INCREMENT NOT NULL, " +
                "user_name  VARCHAR(30)," +
                "user_lastname VARCHAR(30)," +
                "user_age TINYINT" +
                ");";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sqlCreateTable);
        } catch (SQLException e) {
            printMessage(e);
        }
    }

    @Override
    public void dropUsersTable() {
        String sqlDropTable = "DROP TABLE users";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sqlDropTable);
        } catch (SQLException e) {
            printMessage(e);
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        String sqlInsert = "INSERT INTO users (user_name, user_lastname, user_age) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlInsert)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            System.out.println("User с именем " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            printMessage(e);
        }
    }

    @Override
    public void removeUserById(long id) {
        String sqlRemove = "DELETE FROM users WHERE user_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlRemove)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            printMessage(e);
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        String sqlSelect = "SELECT * FROM users";
        try (ResultSet resultSet = connection.createStatement().executeQuery(sqlSelect)) {
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong(1));
                user.setName(resultSet.getString(2));
                user.setLastName(resultSet.getString(3));
                user.setAge(resultSet.getByte(4));
                list.add(user);
                System.out.println(user);
            }

        } catch (SQLException e) {
            printMessage(e);
        }
        return list;
    }

    @Override
    public void cleanUsersTable() {
        String sqlClean = "TRUNCATE TABLE users";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sqlClean);
        } catch (SQLException e) {
            printMessage(e);
        }
    }

    public static void printMessage(SQLException e) {
        System.out.println("SQLException: " + e.getMessage());
        System.out.println("SQLState: " + e.getSQLState());
        System.out.println("VendorError: " + e.getErrorCode());
    }

    public static void disconnect() {
        try {
            connection.close();
        } catch (SQLException e) {
            printMessage(e);
        }
    }
}