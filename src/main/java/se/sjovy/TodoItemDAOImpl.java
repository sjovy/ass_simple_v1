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
    String sql = "INSERT INTO todoitem (todo_id, title, description, deadline, done, assignee_id) VALUES (NULL, ?, ?, ?, ?, ?)";

    try {
        PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, todoItem.getTitle());
        preparedStatement.setString(2, todoItem.getDescription());
        java.sql.Date sqlDate = java.sql.Date.valueOf(todoItem.getDeadline());
        preparedStatement.setDate(3, sqlDate);
        preparedStatement.setBoolean(4, todoItem.isDone());
        preparedStatement.setInt(5, todoItem.getAssigneeId());

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
        return null;
    }

    @Override
    public TodoItem selectById(int id) {
        return null;
    }

    @Override
    public List<TodoItem> findByDoneStatus(boolean done) {
        return null;
    }

    @Override
    public List<TodoItem> findByAssignee(int assigneeId) {
        return null;
    }

    @Override
    public List<TodoItem> findByUnassignedTodoItem() {
        return null;
    }

    @Override
    public void deleteTodoItem(int id) {
        // To be implemented
    }

    @Override
    public void updateTodoItem(TodoItem todoItem) {
        // To be implemented
    }
}