package se.sjovy;

import java.util.*;

public class TodoItemHandler {
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
}