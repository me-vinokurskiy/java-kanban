import java.util.HashMap;

public class EpicTask extends Task{
    HashMap<Integer, SubTask> subTaskHashMap = new HashMap<>();

    public EpicTask(String name, String description, TaskType taskType, int id) {
        super(name, description, taskType, Status.NEW);
        this.id = id;
    }

    public void saveSubTask (int id, SubTask subTask) {
        subTaskHashMap.put(id, subTask);
    }



    @Override
    public String toString() {
        return "EpicTask{" +
                "subTaskHashMap=" + subTaskHashMap +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", taskType=" + taskType +
                ", status=" + status +
                ", id=" + id +
                '}';
    }
}
