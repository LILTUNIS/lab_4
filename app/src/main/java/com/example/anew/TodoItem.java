package com.example.anew;

public class TodoItem {
    private String todoText;
    private boolean isUrgent;

    public TodoItem(String todoText, boolean isUrgent) {
        this.todoText = todoText;
        this.isUrgent = isUrgent;
    }

    public String getTodoText() {
        return todoText;
    }

    public boolean isUrgent() {
        return isUrgent;
    }

}



