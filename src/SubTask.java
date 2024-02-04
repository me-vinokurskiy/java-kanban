public class SubTask extends Task {

    public SubTask(String name, String description, TaskType taskType, Status status, int id) {
        super(name, description, taskType, status);
        this.id = id;
    }

    @Override
    public String toString() {
        return "SubTask{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", taskType=" + taskType +
                ", status=" + status +
                ", id=" + id +
                '}';
    }
}
