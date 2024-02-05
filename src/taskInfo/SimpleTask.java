package taskInfo;

public class SimpleTask extends Task{

    public SimpleTask(String name, String description, TaskType taskType, Status status,int id) {
        super(name, description, taskType, status);
        this.id = id;
    }

    @Override
    public String toString() {
        return "taskInfo.SimpleTask{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", taskType=" + taskType +
                ", status=" + status +
                ", id=" + id +
                '}';
    }
}
