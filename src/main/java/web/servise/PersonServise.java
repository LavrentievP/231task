package web.servise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dao.PersDao;

import web.model.Person;

import java.util.List;
@Service
public class PersonServise implements PersServ {

private PersDao dao ;

    @Autowired
    public PersonServise( PersDao dao) {
        this.dao = dao;
    }

    @Override
    @Transactional
    public List<Person> upindex() {
        return dao.upindex();
    }

    @Override
    @Transactional
    public Person show(int id) {
        return dao.show(id);
    }

    @Override
    @Transactional
    public void save(Person person) {
        dao.save(person);
    }

    @Override
    @Transactional
    public void update(int id, Person updatedPerson) {
        dao.update(id,updatedPerson);
    }

    @Override
    @Transactional
    public void delete(int id) {
        dao.delete(id);
    }
}
