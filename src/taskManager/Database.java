package taskManager;

import taskInfo.EpicTask;
import taskInfo.Status;
import taskInfo.SubTask;

import java.util.HashMap;

public class Database {
    private final HashMap<Integer, Object> taskDatabase = new HashMap<>();

    public void saveToDatabase(int id, Object newTask) {
        checkStatus();
        taskDatabase.put(id, newTask);
    }

    public HashMap<Integer, Object> getTaskDatabase() {
        return taskDatabase;
    }

    public void clear() {
        taskDatabase.clear();

    }

    public void remove(int id) {
        for (Integer i : taskDatabase.keySet()) {
            Object task = taskDatabase.get(i);
            if (task.getClass() == EpicTask.class) {
                ((EpicTask) task).getSubTaskHashMap().remove(id);
            }
        }
        checkStatus();
        taskDatabase.remove(id);
    }

    private void checkStatus() {
        for (Integer i : taskDatabase.keySet()) {
            Object task = taskDatabase.get(i);
            if (task.getClass() == EpicTask.class) {
                int isDone = -1;
                for (Integer i1 : ((EpicTask) task).getSubTaskHashMap().keySet()) {
                    SubTask subTask = ((EpicTask) task).getSubTaskHashMap().get(i1);
                    if (subTask.getStatus() != Status.DONE) isDone++;
                }
                if (isDone < 0) {
                    ((EpicTask) task).setStatus(Status.DONE);
                } else {
                    ((EpicTask) task).setStatus(Status.IN_PROGRESS);
                }
                if (((EpicTask) task).getSubTaskHashMap().isEmpty()) ((EpicTask) task).setStatus(Status.NEW);
                taskDatabase.put(i, task);
            }

        }
    }

    @Override
    public String toString() {
        return "\n" + "taskManager.Database{" +
                "database=" + taskDatabase +
                '}';
    }
}
