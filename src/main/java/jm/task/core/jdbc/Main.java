package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        UserService userService = new UserServiceImpl();

        userService.createUsersTable();

        userService.saveUser("Ivan", "Ivanov", (byte) 25);
        userService.saveUser("Maksim", "Maksimov", (byte) 18);
        userService.saveUser("Oleg", "Olegov", (byte) 30);
        userService.saveUser("Evgen", "Evgenov", (byte) 85);

        userService.getAllUsers();

        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
