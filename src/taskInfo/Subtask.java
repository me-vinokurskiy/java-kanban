package taskInfo;

import java.util.Objects;

public final class Subtask extends Task{
    final int epicId;
    public Subtask(String name, String description, Status status, int id, int epicId) {
        super(name, description, status, id);
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Subtask subtask = (Subtask) o;
        return Objects.equals(name, subtask.name) &&
                Objects.equals(description, subtask.description) &&
                Objects.equals(status, subtask.status) &&
                this.id == subtask.id &&
                epicId == subtask.epicId;
    }
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), epicId);
    }
    @Override
    public String toString() {
        return "Subtask{" +
                "epicId=" + epicId +
                ", name='" + name + '\'' +
                ", desc='" + description + '\'' +
                ", status=" + status +
                ", id=" + id +
                '}';
    }
}
