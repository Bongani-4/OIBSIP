package  com.example.todoapplication;

import java.io.Serializable;

// Task.java
public class Task  implements Serializable {
    private String taskName;
    private String dateTime;
    private boolean urgent;
    private String taskType;


    public Task()
    {}

    public Task(String taskName, String dateTime, boolean urgent, String taskType) {
        this.taskName = taskName;
        this.dateTime = dateTime;
        this.urgent = urgent;
        this.taskType = taskType;
    }

    // Getter methods
    public String getTaskName() {
        return taskName;
    }

    public String getDateTime() {
        return dateTime;
    }

    public boolean isUrgent() {
        return urgent;
    }

    public String isTaskType() {
        return taskType;
    }
}
