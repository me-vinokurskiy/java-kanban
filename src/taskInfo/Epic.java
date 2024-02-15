package taskInfo;

import java.util.ArrayList;

public class Epic extends Task{
    private final ArrayList<Integer> subtasks = new ArrayList<>();

    public Epic(String name, String description, Status status, TaskType type, int id) {
        super(name, description, status, type, id);
    }

    public ArrayList<Integer> getSubtasks() {
        return subtasks;
    }

    /*@Override
    public String toString() {
        return "Epic{" +
                "subtasks=" + subtasks +
                ", name='" + name + '\'' +
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
