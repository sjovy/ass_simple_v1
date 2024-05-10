package se.sjovy;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TodoItemDAOImpl implements TodoItemFetchDAO, TodoItemStoreDAO {
    private Connection connection;

    public TodoItemDAOImpl() {
        this.connection = DatabaseHelper.openConnection();
    }

    @Override
    public void insertTodoItem(TodoItem todoItem) {
        String sql = "INSERT INTO todo_item (todo_id, title, description, deadline, done, assignee_id) VALUES (NULL, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, todoItem.getTitle());
            preparedStatement.setString(2, todoItem.getDescription());
            java.sql.Date sqlDate = java.sql.Date.valueOf(todoItem.getDeadline());
            preparedStatement.setDate(3, sqlDate);
            preparedStatement.setBoolean(4, todoItem.isDone());
            if (todoItem.getAssigneeId() != null) {
                preparedStatement.setInt(5, todoItem.getAssigneeId());
            } else {
                preparedStatement.setNull(5, java.sql.Types.INTEGER);
            }

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating todo item failed, no rows affected.");
            }

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    todoItem.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating todo item failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseHelper.closeConnection();
        }
    }

    @Override
    public List<TodoItem> selectAllTodoItems() {
        List<TodoItem> todoItems = new ArrayList<>();
        String sql = "SELECT * FROM todo_item";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("todo_id");
                String title = resultSet.getString("title");
                String description = resultSet.getString("description");
                LocalDate deadline = resultSet.getDate("deadline").toLocalDate();
                boolean done = resultSet.getBoolean("done");
                Integer assigneeId = resultSet.getObject("assignee_id", Integer.class);

                TodoItem todoItem = new TodoItem(title, description, deadline.toString(), done, assigneeId);
                todoItem.setId(id);
                todoItems.add(todoItem);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseHelper.closeConnection();
        }

        return todoItems;
    }

    @Override
    public TodoItem selectById(int id) {
        TodoItem todoItem = null;
        String sql = "SELECT * FROM todo_item WHERE todo_id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String title = resultSet.getString("title");
                String description = resultSet.getString("description");
                LocalDate deadline = resultSet.getDate("deadline").toLocalDate();
                boolean done = resultSet.getBoolean("done");
                Integer assigneeId = resultSet.getObject("assignee_id", Integer.class);

                todoItem = new TodoItem(title, description, deadline.toString(), done, assigneeId);
                todoItem.setId(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseHelper.closeConnection();
        }

        return todoItem;
    }

    @Override
    public List<TodoItem> findByDoneStatus(boolean done) {
        List<TodoItem> todoItems = new ArrayList<>();
        String sql = "SELECT * FROM todo_item WHERE done = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setBoolean(1, done);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("todo_id");
                String title = resultSet.getString("title");
                String description = resultSet.getString("description");
                LocalDate deadline = resultSet.getDate("deadline").toLocalDate();
                Integer assigneeId = resultSet.getObject("assignee_id", Integer.class);

                TodoItem todoItem = new TodoItem(title, description, deadline.toString(), done, assigneeId);
                todoItem.setId(id);
                todoItems.add(todoItem);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseHelper.closeConnection();
        }

        return todoItems;
    }

    @Override
    public List<TodoItem> findByAssigneeId(int assigneeId) {
        List<TodoItem> todoItems = new ArrayList<>();
        String sql = "SELECT * FROM todo_item WHERE assignee_id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, assigneeId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("todo_id");
                String title = resultSet.getString("title");
                String description = resultSet.getString("description");
                LocalDate deadline = resultSet.getDate("deadline").toLocalDate();
                boolean done = resultSet.getBoolean("done");
                Integer assigneeIdResult = resultSet.getObject("assignee_id", Integer.class);

                TodoItem todoItem = new TodoItem(title, description, deadline.toString(), done, assigneeIdResult);
                todoItem.setId(id);
                todoItems.add(todoItem);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseHelper.closeConnection();
        }

        return todoItems;
    }

    @Override
    public List<TodoItem> findByAssigneeName(String assigneeName) {
        List<TodoItem> todoItems = new ArrayList<>();
        String sql = "SELECT todo_item.* " +
                        "FROM todo_item " +
                        "JOIN person ON todo_item.assignee_id = person_id " +
                        "WHERE person.first_name = ? OR person.last_name = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, assigneeName);
            preparedStatement.setString(2, assigneeName);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("todo_id");
                String title = resultSet.getString("title");
                String description = resultSet.getString("description");
                LocalDate deadline = resultSet.getDate("deadline").toLocalDate();
                boolean done = resultSet.getBoolean("done");
                Integer assigneeId = resultSet.getObject("assignee_id", Integer.class);

                TodoItem todoItem = new TodoItem(title, description, deadline.toString(), done, assigneeId);
                todoItem.setId(id);
                todoItems.add(todoItem);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseHelper.closeConnection();
        }

        return todoItems;
    }

    @Override
    public List<TodoItem> findByUnassignedTodoItem() {
        List<TodoItem> todoItems = new ArrayList<>();
        String sql = "SELECT * FROM todo_item WHERE assignee_id IS NULL";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("todo_id");
                String title = resultSet.getString("title");
                String description = resultSet.getString("description");
                LocalDate deadline = resultSet.getDate("deadline").toLocalDate();
                boolean done = resultSet.getBoolean("done");
                Integer assigneeId = resultSet.getObject("assignee_id", Integer.class);

                TodoItem todoItem = new TodoItem(title, description, deadline.toString(), done, assigneeId);
                todoItem.setId(id);
                todoItems.add(todoItem);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseHelper.closeConnection();
        }

        return todoItems;
    }

    @Override
    public void deleteTodoItem(int id) {
        String sql = "DELETE FROM todo_item WHERE todo_id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                System.out.println("No TodoItem found with ID: " + id);
            } else {
                System.out.println("TodoItem with ID " + id + " was deleted.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseHelper.closeConnection();
        }
    }

    @Override
    public void updateTodoItem(TodoItem todoItem) {
        String sql = "UPDATE todo_item SET title = ?, description = ?, deadline = ?, done = ?, assignee_id = ? WHERE todo_id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, todoItem.getTitle());
            preparedStatement.setString(2, todoItem.getDescription());
            java.sql.Date sqlDate = java.sql.Date.valueOf(todoItem.getDeadline());
            preparedStatement.setDate(3, sqlDate);
            preparedStatement.setBoolean(4, todoItem.isDone());
            if (todoItem.getAssigneeId() != null) {
                preparedStatement.setInt(5, todoItem.getAssigneeId());
            } else {
                preparedStatement.setNull(5, java.sql.Types.INTEGER);
            }
            preparedStatement.setInt(6, todoItem.getId());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                System.out.println("No TodoItem found with ID: " + todoItem.getId());
            } else {
                System.out.println("TodoItem with ID " + todoItem.getId() + " was updated.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseHelper.closeConnection();
        }
    }
}