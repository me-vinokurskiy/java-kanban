package taskInfo;

public class Subtask extends AbstractTask{
    private final int epicID;


    public Subtask(String name, String description, Status status, TaskType type, int id, int epicID) {
        super(name, description, status, type);
        this.id = id;
        this.epicID = epicID;
    }

    public int getEpicID() {
        return epicID;
    }

    @Override
    public String toString() {
        return "Subtask{" +
                "epicID=" + epicID +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", type=" + type +
                ", id=" + id +
                '}';
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
