package se.sjovy;

import java.util.*;

public class App {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Person operations");
            System.out.println("2. TodoItem operations");
            System.out.println("9. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    handlePersonOperations(scanner);
                    break;

                case 2:
                    handleTodoItemOperations(scanner);
                    break;

                case 9:
                    System.out.println("Exiting...");
                    scanner.close();
                    System.exit(0);

                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 2.");
            }
        }
    }

    public static void handlePersonOperations(Scanner scanner) {

        while (true) {
            System.out.println("1. Create a person and add to the database");
            System.out.println("2. Find a person by ID");
            System.out.println("3. Find persons by name");
            System.out.println("4. Fetch all persons");
            System.out.println("5. Delete a person");
            System.out.println("6. Update a person");
            System.out.println("9. Exit");
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
                    System.out.print("Enter person name (first name and last name separated by a space): ");
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

                case 9:
                    System.out.println("Exiting...");
                    scanner.close();
                    System.exit(0);

                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 6.");
            }
        }
    }

    public static void handleTodoItemOperations(Scanner scanner) {
    while (true) {
        System.out.println("1. Create a TodoItem and add to the database");
        System.out.println("9. Exit");
        System.out.print("Enter your choice: ");

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline left-over

        switch (choice) {
            case 1:
                System.out.print("Enter title: ");
                String title = scanner.nextLine();

                System.out.print("Enter description: ");
                String description = scanner.nextLine();

                System.out.print("Enter deadline (yyyy-MM-dd): ");
                String deadline = scanner.nextLine();

                System.out.print("Is the task done? (true/false): ");
                boolean done = scanner.nextBoolean();
                scanner.nextLine(); // Consume newline left-over

                System.out.print("Enter assignee ID: ");
                int assigneeId = scanner.nextInt();
                scanner.nextLine(); // Consume newline left-over

                TodoItem todoItem = createTodoItem(title, description, deadline, done, assigneeId);
                addTodoItemToDatabase(todoItem);
                System.out.println("TodoItem created and added to database: " + todoItem);
                break;

            case 9:
                System.out.println("Exiting...");
                return;

            default:
                System.out.println("Invalid choice. Please enter a number between 1 and 9.");
        }
    }
}

    // Method to instantiate a TodoItem object
    public static TodoItem createTodoItem(String title, String description, String deadline, boolean done, int assigneeId) {
        return new TodoItem(title, description, deadline, done, assigneeId);
    }

    public static void addTodoItemToDatabase(TodoItem todoItem) {
        // Create a TodoItemStoreDAO object
        TodoItemStoreDAO storeDAO = new TodoItemDAOImpl();

        // Add the TodoItem into the database
        storeDAO.insertTodoItem(todoItem);
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