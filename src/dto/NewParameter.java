package dto;

import taskInfo.Status;
import taskInfo.TaskType;

public class NewParameter {
    private String name;
    private String description;
    private Status status;
    private TaskType type;
    private int epicId;


    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Status getStatus() {
        return status;
    }

    public TaskType getType() {
        return type;
    }

    public int getEpicId() {
        return epicId;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setType(TaskType type) {
        this.type = type;
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;

    }

}
