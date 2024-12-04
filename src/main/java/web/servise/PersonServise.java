package web.servise;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.model.Person;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
public class PersonServise implements PersServ {

    @PersistenceContext
    private EntityManager entityManager;
    ;


    @Override
    @Transactional
    public List<Person> upindex() {
        return entityManager.createQuery("FROM Person", Person.class).getResultList();
    }

    @Override
    @Transactional
    public Person show(int id) {
        return entityManager.find(Person.class, id);
    }

    @Override
    @Transactional
    public void save(Person person) {
        entityManager.persist(person);
    }

    @Override
    @Transactional
    public void update(int id, Person updatedPerson) {
        entityManager.merge(updatedPerson);
    }

    @Override
    @Transactional
    public void delete(int id) {
        Person person = entityManager.find(Person.class, id);
        if (person != null) {
            entityManager.remove(person);

        }
    }
}