package taskInfo;

import java.util.ArrayList;
import java.util.Objects;

public final class Epic extends Task{
    private final ArrayList<Integer> subtasks = new ArrayList<>();

    public Epic(String name, String description, Status status, int id) {
        super(name, description, status, id);
    }

    public void addSubtask(int id) {
        subtasks.add(id);
    }
    public ArrayList<Integer> getSubtasks() {
        return subtasks;
    }
    public void removeSubtask(Integer id) {
        subtasks.remove(id);
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Epic epic = (Epic) o;
        return Objects.equals(name, epic.name) &&
                Objects.equals(description, epic.description) &&
                Objects.equals(status, epic.status) &&
                Objects.equals(subtasks, epic.subtasks) &&
                this.id == epic.id;
    }
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), subtasks);
    }
    @Override
    public String toString() {
        return "Epic{" +
                "subtasks=" + subtasks +
                ", name='" + name + '\'' +
                ", desc='" + description + '\'' +
                ", status=" + status +
                ", id=" + id +
                '}';
    }
}
