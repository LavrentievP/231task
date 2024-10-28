package web.servise;

import org.springframework.transaction.annotation.Transactional;
import web.dao.PersDao;
import web.dao.PersonDao;
import web.model.Person;

import java.util.List;

public class PersonServise implements PersServ {

private final PersDao persDao = new PersonDao();
    @Override
    @Transactional
    public List<Person> upindex() {

        return persDao.upindex();
    }

    @Override
    @Transactional
    public Person show(int id) {

        return persDao.show(id);
    }

    @Override
    @Transactional
    public void save(Person person) {

        persDao.save(person);
    }

    @Override
    @Transactional
    public void update(int id, Person updatedPerson) {
persDao.update(id,updatedPerson);
    }

    @Override
    @Transactional
    public void delete(int id) {
persDao.delete(id);
    }
}
