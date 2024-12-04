package web.dao;


import org.springframework.stereotype.Repository;
import web.model.Person;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class PersonDao implements PersDao {


    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Person> upindex() {
        TypedQuery<Person> query = entityManager.createQuery("FROM Person ORDER BY id ASC", Person.class);
        return query.getResultList();


    }

    @Override
    public void save(Person person) {
        entityManager.persist(person);
    }

    @Override
    public Person show(int id) {

        return entityManager.find(Person.class, id);
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
}

