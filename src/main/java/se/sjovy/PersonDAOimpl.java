package se.sjovy;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonDAOimpl implements PersonDAO {
    private Connection connection;

    public PersonDAOimpl() {
        this.connection = DatabaseHelper.openConnection();
    }

    @Override
    public void insertPerson(Person person) {
        String sql = "INSERT INTO person (person_id, first_name, last_name) VALUES (NULL, ?, ?)"; // Adjusted table name and field names here

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, person.getFirstName());
            preparedStatement.setString(2, person.getLastName());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    person.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseHelper.closeConnection();
        }
    }

    @Override
    public Person selectById(int id) {
        Person person = null;
        String sql = "SELECT * FROM person WHERE person_id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");

                person = new Person(firstName, lastName);
                person.setId(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseHelper.closeConnection();
        }

        return person;
    }

    @Override
    public List<Person> selectByName(String name) {
        List<Person> persons = new ArrayList<>();
        String[] names = name.split(" ");
        String sql = "SELECT * FROM person WHERE first_name = ? AND last_name = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, names[0]);
            preparedStatement.setString(2, names[1]);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("person_id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");

                Person person = new Person(firstName, lastName);
                person.setId(id);
                persons.add(person);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseHelper.closeConnection();
        }

        return persons;
    }

    @Override
    public List<Person> selectAllPersons() {
        return null;
    }

    @Override
    public void deletePerson(int id) {
        // Return false as this method is not implemented
    }

    @Override
    public void updatePerson(Person person) {
        // Return false as this method is not implemented
    }
}