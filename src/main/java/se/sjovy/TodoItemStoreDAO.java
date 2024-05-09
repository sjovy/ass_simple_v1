package se.sjovy;

public interface TodoItemStoreDAO {
    void insertTodoItem(TodoItem todoItem);
    void deleteTodoItem(int id);
    void updateTodoItem(TodoItem todoItem);
}
