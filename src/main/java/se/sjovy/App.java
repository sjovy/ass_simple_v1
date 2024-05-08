package se.sjovy;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Create a person and add to the database");
            System.out.println("2. Find a person by ID");
            System.out.println("3. Exit");
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
                    System.out.println("Exiting...");
                    scanner.close();
                    System.exit(0);

                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 3.");
            }
        }
    }

    // Method to instantiate a Person object
    public static Person createPerson(String firstName, String lastName) {
        return new Person(firstName, lastName);
    }

    public static void addPersonToDatabase(Person person) {
        // Create a PersonDAOImpl object
        PersonDAO personDAO = new PersonDAOimpl();

        // Add the person into the database
        personDAO.insertPerson(person);
    }

    public static Person getPersonById(int id) {
        // Create a PersonDAOImpl object
        PersonDAO personDAO = new PersonDAOimpl();

        // Retrieve the person from the database
        return personDAO.selectById(id);
    }
}