package taskInfo;

public abstract class Task {
    protected String name;
    protected String description;
    protected final TaskType taskType;
    protected Status status;
    Integer id;

    public Task(String name, String description, TaskType taskType, Status status) {
        this.name = name;
        this.description = description;
        this.taskType = taskType;
        this.status = status;
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

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public TaskType getTaskType() {
        return taskType;
    }

    public Status getStatus() {
        return status;
    }
}
