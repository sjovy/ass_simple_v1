package se.sjovy;

import java.util.List;

public interface PersonDAO {
    void insertPerson(Person person);
    Person selectById(int id);
    List<Person> selectByName(String name);

    List<Person> selectAllPersons();
    void deletePerson(int id);
    void updatePerson(Person person);
}