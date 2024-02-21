package dto;

import taskInfo.Status;
import taskInfo.TaskType;

public class TaskParameters {
    private String name;
    private String description;
    private Status status;
    private TaskType type;
    private Integer epicId;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Status getStatus() {
        return status;
    }
    public void setStatus(Status status) {
        this.status = status;
    }
    public TaskType getType() {
        return type;
    }
    public void setType(TaskType type) {
        this.type = type;
    }
    public Integer getEpicId() {
        return epicId;
    }
    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }
}
