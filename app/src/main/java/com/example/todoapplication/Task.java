package  com.example.todoapplication;

import java.io.Serializable;

// Task.java
public class Task  implements Serializable {
    private String taskName;
    private String taskId;
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
    public void setTaskId(String taskId)
    {
        this.taskId = taskId;

    }
    public String getTaskId() {
        return taskId;
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
