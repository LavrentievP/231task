package web.dao;

import web.model.Person;

import java.util.List;

public interface PersDao {

    public List<Person> upindex();

    public Person show(int id);

    public void save(Person person);

    public void update(int id, Person updatedPerson);

    public void delete(int id);

}
