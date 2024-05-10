package se.sjovy;

import java.util.*;

public class TodoItemHandler {
    public static void handleTodoItemOperations(Scanner scanner) {
        while (true) {
            System.out.println("1. Create a TodoItem and add to the database");
            System.out.println("2. Fetch all TodoItems");
            System.out.println("3. Fetch a TodoItem by ID");
            System.out.println("4. Fetch TodoItems by done status");
            System.out.println("5. Fetch TodoItems by assignee ID");
            System.out.println("6. Fetch TodoItems by assignee Name");
            System.out.println("7. Fetch unassigned TodoItems");
            System.out.println("8. Delete TodoItem by ID");
            System.out.println("9. Update TodoItem by ID");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

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
                    scanner.nextLine();

                    System.out.print("Enter assignee ID: ");
                    Integer assigneeId = null;
                    String assigneeIdInput = scanner.nextLine();
                    try {
                        assigneeId = Integer.parseInt(assigneeIdInput);
                    } catch (NumberFormatException e) {
                        // assigneeId remains null
                    }

                    TodoItem todoItem = createTodoItem(title, description, deadline, done, assigneeId);
                    addTodoItemToDatabase(todoItem);
                    System.out.println("TodoItem created and added to database: " + todoItem);
                    break;

                case 2:
                    System.out.println("Fetching all TodoItems...");
                    List<TodoItem> todoItems = fetchAllTodoItems();
                    todoItems.forEach(System.out::println);
                    break;

                case 3:
                    System.out.print("Enter the ID of the TodoItem to fetch: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Fetching TodoItem with ID " + id + "...");
                    TodoItem fetchedTodoItem = fetchTodoItemById(id);
                    System.out.println(fetchedTodoItem);
                    break;

                case 4:
                    System.out.print("Enter the done status to fetch by (true/false): ");
                    boolean doneStatus = scanner.nextBoolean();
                    scanner.nextLine();
                    System.out.println("Fetching TodoItems with done status " + doneStatus + "...");
                    List<TodoItem> todoItemsByDoneStatus = fetchTodoItemsByDoneStatus(doneStatus);
                    todoItemsByDoneStatus.forEach(System.out::println);
                    break;

                case 5:
                    System.out.print("Enter the assignee ID to fetch by: ");
                    int fetchedAssigneeId = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Fetching TodoItems with assignee ID " + fetchedAssigneeId + "...");
                    List<TodoItem> todoItemsByAssignee = fetchTodoItemsByAssigneeId(fetchedAssigneeId);
                    todoItemsByAssignee.forEach(System.out::println);
                    break;

                case 6:
                    System.out.print("Enter the assignee name to fetch by: ");
                    String fetchedAssigneeName = scanner.nextLine();
                    System.out.println("Fetching TodoItems with assignee name " + fetchedAssigneeName + "...");
                    List<TodoItem> todoItemsByAssigneeName = fetchTodoItemsByAssigneeName(fetchedAssigneeName);
                    todoItemsByAssigneeName.forEach(System.out::println);
                    break;

                case 7:
                    System.out.println("Fetching unassigned TodoItems...");
                    TodoItemFetchDAO fetchDAO = new TodoItemDAOImpl();
                    List<TodoItem> unassignedTodoItems = fetchDAO.findByUnassignedTodoItem();
                    unassignedTodoItems.forEach(System.out::println);
                    break;

                case 8:
                    System.out.print("Enter the ID of the TodoItem to delete: ");
                    int deleteId = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Deleting TodoItem with ID " + deleteId + "...");
                    deleteTodoItem(deleteId);
                    break;

                case 9:
                    System.out.print("Enter the ID of the TodoItem to update: ");
                    int updateId = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Updating TodoItem with ID " + updateId + "...");
                    System.out.print("Enter the new title: ");
                    String newTitle = scanner.nextLine();
                    System.out.print("Enter the new description: ");
                    String newDescription = scanner.nextLine();
                    System.out.print("Enter the new deadline (yyyy-mm-dd): ");
                    String newDeadline = scanner.nextLine();
                    System.out.print("Is the task done? (true/false): ");
                    boolean newDone = scanner.nextBoolean();
                    scanner.nextLine();
                    System.out.print("Enter the new assignee ID: ");
                    int newAssigneeId = scanner.nextInt();
                    scanner.nextLine();
                    TodoItem updatedTodoItem = new TodoItem(newTitle, newDescription, newDeadline, newDone, newAssigneeId);
                    updatedTodoItem.setId(updateId);
                    updateTodoItem(updatedTodoItem);
                    break;

                case 0:
                    System.out.println("Exiting...");
                    scanner.close();
                    System.exit(0);

                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 8.");
            }
        }
    }

    // Method to instantiate a TodoItem object
    public static TodoItem createTodoItem(String title, String description, String deadline, boolean done, Integer assigneeId) {
        return new TodoItem(title, description, deadline, done, assigneeId);
    }

    public static void addTodoItemToDatabase(TodoItem todoItem) {
        // Create a TodoItemStoreDAO object
        TodoItemStoreDAO storeDAO = new TodoItemDAOImpl();

        // Add the TodoItem into the database
        storeDAO.insertTodoItem(todoItem);
    }

    public static List<TodoItem> fetchAllTodoItems() {
        // Create a TodoItemFetchDAO object
        TodoItemFetchDAO fetchDAO = new TodoItemDAOImpl();

        // Fetch all TodoItems from the database
        return fetchDAO.selectAllTodoItems();
    }

    public static TodoItem fetchTodoItemById(int id) {
        // Create a TodoItemFetchDAO object
        TodoItemFetchDAO fetchDAO = new TodoItemDAOImpl();

        // Fetch the TodoItem from the database by its ID
        return fetchDAO.selectById(id);
    }

    public static List<TodoItem> fetchTodoItemsByDoneStatus(boolean done) {
        // Create a TodoItemFetchDAO object
        TodoItemFetchDAO fetchDAO = new TodoItemDAOImpl();

        // Fetch the TodoItems from the database by done status
        return fetchDAO.findByDoneStatus(done);
    }

    public static List<TodoItem> fetchTodoItemsByAssigneeId(int assigneeId) {
        // Create a TodoItemFetchDAO object
        TodoItemFetchDAO fetchDAO = new TodoItemDAOImpl();

        // Fetch the TodoItems from the database by assignee ID
        return fetchDAO.findByAssigneeId(assigneeId);
    }

    public static List<TodoItem> fetchTodoItemsByAssigneeName(String assigneeName) {
        // Create a TodoItemFetchDAO object
        TodoItemFetchDAO fetchDAO = new TodoItemDAOImpl();

        // Fetch the TodoItems from the database by assignee name
        return fetchDAO.findByAssigneeName(assigneeName);
    }

    public static void deleteTodoItem(int id) {
        // Create a TodoItemStoreDAO object
        TodoItemStoreDAO storeDAO = new TodoItemDAOImpl();

        // Delete the TodoItem from the database by its ID
        storeDAO.deleteTodoItem(id);
    }

    public static void updateTodoItem(TodoItem todoItem) {
        // Create a TodoItemStoreDAO object
        TodoItemStoreDAO storeDAO = new TodoItemDAOImpl();

        // Update the TodoItem in the database
        storeDAO.updateTodoItem(todoItem);
    }
}