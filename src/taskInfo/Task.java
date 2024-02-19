package taskInfo;

import java.util.Objects;

public class Task {
    protected String name;
    protected String description;
    protected Status status;
    protected final int id;

    public Task(String name, String description, Status status, int id) {
        this.name = name;
        this.description = description;
        this.status = status;
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
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id &&
                Objects.equals(name, task.name) &&
                Objects.equals(description, task.description) &&
                status == task.status;
    }
    @Override
    public int hashCode() {
        return Objects.hash(name, description, status, id);
    }
    @Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
                ", desc='" + description + '\'' +
                ", status=" + status +
                ", id=" + id +
                '}';
    }
}
