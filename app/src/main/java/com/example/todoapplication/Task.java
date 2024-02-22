package  com.example.todoapplication;

import java.io.Serializable;

// Task.java
public class Task  implements Serializable {
    private String taskName;
    private String time;
    private boolean isImportant;
    private boolean isWork;


    public Task()
    {}

    public Task(String taskName, String time, boolean isImportant, boolean isWork) {
        this.taskName = taskName;
        this.time = time;
        this.isImportant = isImportant;
        this.isWork = isWork;
    }

    // Getter methods
    public String getTaskName() {
        return taskName;
    }

    public String getTime() {
        return time;
    }

    public boolean isImportant() {
        return isImportant;
    }

    public boolean isWork() {
        return isWork;
    }
}
