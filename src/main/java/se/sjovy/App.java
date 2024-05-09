package se.sjovy;

import java.util.*;

public class App {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Person operations");
            System.out.println("2. TodoItem operations");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    PersonHandler.handlePersonOperations(scanner);
                    break;

                case 2:
                    TodoItemHandler.handleTodoItemOperations(scanner);
                    break;

                case 0:
                    System.out.println("Exiting...");
                    scanner.close();
                    System.exit(0);

                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 2.");
            }
        }
    }
}