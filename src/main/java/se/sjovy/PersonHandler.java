package se.sjovy;

import java.util.*;

public class PersonHandler {
    public static void handlePersonOperations(Scanner scanner) {

        while (true) {
            System.out.println("1. Create a person and add to the database");
            System.out.println("2. Find a person by ID");
            System.out.println("3. Find persons by name");
            System.out.println("4. Fetch all persons");
            System.out.println("5. Delete a person");
            System.out.println("6. Update a person");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter first name: ");
                    String firstName = scanner.next();

                    System.out.print("Enter last name: ");
                    String lastName = scanner.next();

                    Person person = createPerson(firstName, lastName);
                    addPersonToDatabase(person);
                    System.out.println("Person created and added to database: " + person);
                    break;

                case 2:
                    System.out.print("Enter person ID: ");
                    int id = scanner.nextInt();

                    Person personFound = getPersonById(id);
                    System.out.println("Person found: " + personFound);
                    break;

                case 3:
                    System.out.print("Enter person name: ");
                    scanner.nextLine(); // Consume newline left-over
                    String name = scanner.nextLine();

                    List<Person> personsFound = getPersonsByName(name);
                    System.out.println("Persons found: " + personsFound);
                    break;

                case 4:
                    System.out.println("Fetching all persons from the database...");
                    List<Person> allPersons = getAllPersons();
                    System.out.println("All persons: " + allPersons);
                    break;

                case 5:
                    System.out.print("Enter person ID to delete: ");
                    int idToDelete = scanner.nextInt();

                    deletePerson(idToDelete);
                    System.out.println("Person with ID " + idToDelete + " has been deleted.");
                    break;

                case 6:
                    System.out.print("Enter person ID to update: ");
                    int idToUpdate = scanner.nextInt();
                    scanner.nextLine(); // Consume newline left-over

                    System.out.print("Enter new first name: ");
                    String newFirstName = scanner.nextLine();

                    System.out.print("Enter new last name: ");
                    String newLastName = scanner.nextLine();

                    Person personToUpdate = new Person(newFirstName, newLastName);
                    personToUpdate.setId(idToUpdate);

                    updatePerson(personToUpdate);
                    System.out.println("Person with ID " + idToUpdate + " has been updated.");
                    break;

                case 0:
                    System.out.println("Exiting...");
                    scanner.close();
                    System.exit(0);

                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 6.");
            }
        }
    }

    // Method to instantiate a Person object
    public static Person createPerson(String firstName, String lastName) {
        return new Person(firstName, lastName);
    }

    public static void addPersonToDatabase(Person person) {
        // Create a PersonStoreDAO object
        PersonStoreDAO storeDAO = new PersonDAOimpl();

        // Add the person into the database
        storeDAO.insertPerson(person);
    }

    public static Person getPersonById(int id) {
        // Create a PersonFetchDAO object
        PersonFetchDAO fetchDAO = new PersonDAOimpl();

        // Retrieve the person from the database
        return fetchDAO.selectById(id);
    }

    public static List<Person> getPersonsByName(String name) {
        // Create a PersonFetchDAO object
        PersonFetchDAO fetchDAO = new PersonDAOimpl();

        // Retrieve the persons from the database
        return fetchDAO.selectByName(name);
    }

    public static List<Person> getAllPersons() {
        // Create a PersonFetchDAO object
        PersonFetchDAO fetchDAO = new PersonDAOimpl();

        // Retrieve all persons from the database
        return fetchDAO.selectAllPersons();
    }

    public static void deletePerson(int id) {
        // Create a PersonStoreDAO object
        PersonStoreDAO storeDAO = new PersonDAOimpl();

        // Delete the person from the database
        storeDAO.deletePerson(id);
    }

    public static void updatePerson(Person person) {
        // Create a PersonStoreDAO object
        PersonStoreDAO storeDAO = new PersonDAOimpl();

        // Update the person in the database
        storeDAO.updatePerson(person);
    }
}