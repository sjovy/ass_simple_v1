package se.sjovy;

import java.util.List;

public interface PersonFetchDAO {
    Person selectById(int id);
    List<Person> selectByName(String name);
    List<Person> selectAllPersons();
}