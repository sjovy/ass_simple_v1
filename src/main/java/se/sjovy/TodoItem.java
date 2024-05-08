package se.sjovy;

public class TodoItem {
    private int id;
    private String task;
    // other fields...

    // constructor
    public TodoItem(String task) {
        this.task = task;
    }

    // setters
    public void setId(int id) {
        this.id = id;
    }

    public void setTask(String task) {
        this.task = task;
    }

    // getters
    public int getId() {
        return id;
    }

    public String getTask() {
        return task;
    }

    // toString method
    @Override
    public String toString() {
        return "TodoItem{" +
                "id=" + id +
                ", task='" + task + '\'' +
                '}';
    }
}
