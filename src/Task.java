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
}
