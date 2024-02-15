package taskInfo;

public class Task {
    protected String name;
    protected String description;
    protected Status status;
    protected final TaskType type;
    int id;


    public Task(String name, String description, Status status, TaskType type, int id) {
        this.name = name;
        this.description = description;
        this.status = status;
        this.type = type;
        this.id = id;
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

    /*@Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", type=" + type +
                ", id=" + id +
                '}';
    }*/

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
