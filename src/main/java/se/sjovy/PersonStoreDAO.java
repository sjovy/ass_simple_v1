package se.sjovy;

public interface PersonStoreDAO {
    void insertPerson(Person person);
    void deletePerson(int id);
    void updatePerson(Person person);
}