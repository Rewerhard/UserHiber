package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;


import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private static SessionFactory sessionFactory = Util.getSessionFactory();

    public UserDaoHibernateImpl() {
    }


    @Override
    public void createUsersTable() {
        String sqlCreateTable = "CREATE TABLE IF NOT EXISTS users(" +
                "user_id BIGINT PRIMARY KEY AUTO_INCREMENT NOT NULL, " +
                "user_name  VARCHAR(30)," +
                "user_lastname VARCHAR(30)," +
                "user_age TINYINT" +
                ");";
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            session.createSQLQuery(sqlCreateTable).addEntity(User.class).executeUpdate();
            session.getTransaction().commit();
            System.out.println("Таблица users создана");
        }
    }

    @Override
    public void dropUsersTable() {
        String sqlDropTable = "DROP TABLE IF EXISTS users";
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            session.createSQLQuery(sqlDropTable).addEntity(User.class).executeUpdate();
            session.getTransaction().commit();
            System.out.println("Таблица users удалена");
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = sessionFactory.getCurrentSession()) {
            User user = new User(name, lastName, age);
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
            System.out.println("User с именем " + name + " добавлен в базу данных");
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);
            session.getTransaction().commit();
            System.out.println("User с  " + id + " удален из базы данных");
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users;
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            users = session.createQuery("from User", User.class).getResultList();
            session.getTransaction().commit();
            users.forEach(System.out::println);
        }
        return users;
    }


    @Override
    public void cleanUsersTable() {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            session.createQuery("delete User").executeUpdate();
            session.getTransaction().commit();
            System.out.println("Таблица users очищена");
        }
    }

    public static void disconnect() {
        try {
            sessionFactory.close();
        } catch (HibernateException e) {
            System.out.println("HibernateException: " + e.getMessage());
        }
    }
}
