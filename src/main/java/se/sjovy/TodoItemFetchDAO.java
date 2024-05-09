package se.sjovy;

import java.util.List;

public interface TodoItemFetchDAO {
    TodoItem selectById(int id);
    List<TodoItem> selectAllTodoItems();
    List<TodoItem> findByDoneStatus(boolean done);
    List<TodoItem> findByAssignee(int assigneeId);
    List<TodoItem> findByUnassignedTodoItem();
}