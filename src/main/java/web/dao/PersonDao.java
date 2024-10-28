package web.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import web.config.SessionConfig;
import web.model.Person;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PersonDao implements PersDao {
/*

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Person> upindex() {
        return entityManager.createQuery("FROM Person", Person.class).getResultList();
    }

    @Override
    public void save(Person person) {
        entityManager.persist(person);
    }

    @Override
    public Person show(int id) {
        Person person = entityManager.find(Person.class,id);
        entityManager.detach(person);
        return person;
    }

    @Override
    public void update(int id, Person person) {
        entityManager.merge(person);
    }

    @Override
    public void delete(int id) {
        Person person = entityManager.find(Person.class, id);
        if (person != null) {
            entityManager.remove(person);
        }
    }
*/
/*    @Override
    public List<Person> upindex() {
        EntityManager entityManager = null;
        List<Person> people = null;
        try {
            entityManager = SessionConfig.getEntityManager(); // Получаем экземпляр EntityManager
            people = entityManager.createQuery("SELECT  p FROM Person p ", Person.class).getResultList(); // Используем JPQL
        } finally {
            if (entityManager != null) {
                entityManager.close(); // Закрываем EntityManager
            }
        }
        return people;
    }

    @Override
    public Person show(int id) {
        EntityManager entityManager = null;
        Person person = null;
        try {
            entityManager = SessionConfig.getEntityManager(); // Предполагаем, что у вас есть метод для получения EntityManager
            person = entityManager.find(Person.class, id);
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
        return person;
    }

    @Override
    public void save(Person person) {
        EntityManager entityManager = null;
        try {
            entityManager = SessionConfig.getEntityManager(); // Получаем экземпляр EntityManager
            entityManager.getTransaction().begin(); // Начинаем транзакцию
            entityManager.persist(new Person(person.getId(), person.getName(), person.getSex())); // Сохраняем объект
            entityManager.getTransaction().commit(); // Подтверждаем транзакцию
        } catch (Exception e) {
            if (entityManager != null && entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback(); // Откатываем транзакцию в случае ошибки
            }
            throw new RuntimeException(e);
        } finally {
            if (entityManager != null) {
                entityManager.close(); // Закрываем EntityManager
            }
        }
    }
    @Override
    public void update(int id, Person updatedPerson) {
        EntityManager entityManager = null;
        try {
            entityManager = SessionConfig.getEntityManager(); // Получаем экземпляр EntityManager
            entityManager.getTransaction().begin(); // Начинаем транзакцию

            Person person = entityManager.find(Person.class, id); // Находим объект по ID
            if (person != null) { // Проверяем, найден ли объект
                person.setName(updatedPerson.getName()); // Обновляем имя
                person.setSex(updatedPerson.getSex()); // Обновляем пол
                entityManager.merge(person); // Объединяем изменения
            }

            entityManager.getTransaction().commit(); // Подтверждаем транзакцию
        } catch (Exception e) {
            if (entityManager != null && entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback(); // Откатываем транзакцию в случае ошибки
            }
            throw new RuntimeException(e);
        } finally {
            if (entityManager != null) {
                entityManager.close(); // Закрываем EntityManager
            }
        }
    }

    @Override
    public void delete(int id) {
        EntityManager entityManager = null;
        try {
            entityManager = SessionConfig.getEntityManager(); // Получаем экземпляр EntityManager
            entityManager.getTransaction().begin(); // Начинаем транзакцию

            Person person = entityManager.find(Person.class, id); // Находим объект по ID
            if (person != null) { // Проверяем, найден ли объект
                entityManager.remove(person); // Удаляем объект
            }

            entityManager.getTransaction().commit(); // Подтверждаем транзакцию
        } catch (Exception e) {
            if (entityManager != null && entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback(); // Откатываем транзакцию в случае ошибки
            }
            throw new RuntimeException(e);
        } finally {
            if (entityManager != null) {
                entityManager.close(); // Закрываем EntityManager
            }
        }
    }
}
*/


    private static int PEOPLE_COUNT = 0;

    private static final String URL = "jdbc:postgresql://localhost:5432/mybaze";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "postgres";
    private static final Connection connection;

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public List<Person> upindex() {

//        SessionFactory sessionFactory = SessionConfig.getSessionFactory();
//        Session session = sessionFactory.openSession();
//        List<Person> people = session.createQuery("from Person").list();
//        session.close();
//        return people;

        List<Person> people = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM person";
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                Person person = new Person();
                person.setId(resultSet.getInt("id"));
                person.setName(resultSet.getString("name"));
                person.setSex(resultSet.getString("sex"));

                people.add(person);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return people;
    }

    @Override
    public Person show(int id) {

//        SessionFactory sessionFactory = SessionConfig.getSessionFactory();
//        Session session = sessionFactory.openSession();
//        Person person = session.get(Person.class, id);
//        session.close();
//        return person;


        Person person = null;

        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("SELECT * FROM person WHERE id = ?");
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            resultSet.next();
            person = new Person();

            person.setId(resultSet.getInt("id"));
            person.setName(resultSet.getString("name"));
            person.setSex(resultSet.getString("sex"));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return person;

    }

    @Override
    public void save(Person person) {
//       try( Session session = SessionConfig.getSessionFactory().openSession()) {
//           Transaction transaction = session.beginTransaction();
//           session.save(new Person(person.getId(), person.getName(), person.getSex()));
//           transaction.commit();
//       } catch(Exception e) {
//           throw new RuntimeException(e);
//       }


        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("INSERT INTO person VALUES (1, ?, ?)");

            preparedStatement.setString(1, person.getName());
            preparedStatement.setString(2, person.getSex());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(int id, Person updatedPerson) {

//SessionFactory sessionFactory = SessionConfig.getSessionFactory();
//        Session session = sessionFactory.openSession();
//        session.beginTransaction();
//        Person person = session.get(Person.class, id);
//        person.setName(updatedPerson.getName());
//        person.setSex(updatedPerson.getSex());
//        session.update(person);
//        session.getTransaction().commit();
//        session.close();


        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("UPDATE person SET name = ?, sex = ? WHERE id = ?");
            preparedStatement.setString(1, updatedPerson.getName());
            preparedStatement.setString(2, updatedPerson.getSex());
            preparedStatement.setInt(3, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void delete(int id) {

//        SessionFactory sessionFactory = SessionConfig.getSessionFactory();
//        Session session = sessionFactory.openSession();
//        session.beginTransaction();
//        Person person = session.get(Person.class, id);
//        session.delete(person);
//        session.getTransaction().commit();
//        session.close();

        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("DELETE FROM person WHERE id=?");
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
