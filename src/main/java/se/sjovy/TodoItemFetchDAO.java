package se.sjovy;

import java.util.List;

public interface TodoItemFetchDAO {
    TodoItem selectById(int id);
    List<TodoItem> selectAllTodoItems();
    List<TodoItem> findByDoneStatus(boolean done);
    List<TodoItem> findByAssigneeId(int assigneeId);
    List<TodoItem> findByAssigneeName(String assigneeName);
    List<TodoItem> findByUnassignedTodoItem();
}