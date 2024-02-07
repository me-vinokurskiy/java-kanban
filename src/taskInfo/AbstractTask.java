package taskInfo;

public abstract class AbstractTask {
    protected String name;
    protected String description;
    protected Status status;
    protected final TaskType type;
    int id;


    public AbstractTask(String name, String description, Status status, TaskType type) {
        this.name = name;
        this.description = description;
        this.status = status;
        this.type = type;
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

    public Status getStatus() {
        return status;
    }

    public int getId() {
        return id;
    }

    public TaskType getType() {
        return type;
    }
}
