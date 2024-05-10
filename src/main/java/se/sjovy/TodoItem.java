package se.sjovy;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TodoItem {
    private int id;
    private String title;
    private String description;
    private LocalDate deadline;
    private boolean done;
    private Integer assigneeId;

    // constructor
    public TodoItem(String title, String description, String deadline, boolean done, Integer assigneeId) {
        this.title = title;
        this.description = description;
        this.deadline = LocalDate.parse(deadline, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.done = done;
        this.assigneeId = assigneeId;
    }

    // setters
    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public void setAssignedTo(Integer assignedTo) {
        this.assigneeId = assigneeId;
    }

    // getters
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public boolean isDone() {
        return done;
    }

    public Integer getAssigneeId() {
        return assigneeId;
    }

    // toString method
    @Override
    public String toString() {
        return "TodoItem{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", deadline=" + deadline +
                ", done=" + done +
                ", assignedTo=" + assigneeId +
                '}';
    }
}
