package taskManager;

import taskInfo.*;

import java.util.ArrayList;
import java.util.HashMap;

public class Database {
    HashMap<Integer, Object> taskDatabase = new HashMap<>();

    public void saveInDatabase(Integer id, Object task) {
        checkStatus();
        taskDatabase.put(id, task);
    }

    public HashMap<Integer, Object> getTaskDatabase() {
        checkStatus();
        return taskDatabase;
    }

    public void deleteById(Integer id) {
        if (taskDatabase.get(id).getClass() == Subtask.class) {
            Subtask subtask = (Subtask) taskDatabase.get(id);
            int epicId = subtask.getEpicID();
            Epic epicTask = (Epic) taskDatabase.get(epicId);
            ArrayList<Integer> subtasks = epicTask.getSubtasks();
            subtasks.remove(id);
            taskDatabase.put(epicId, epicTask);
        }
        checkStatus();
        taskDatabase.remove(id);
    }

    private void checkStatus() {
        for (Integer i : taskDatabase.keySet()) {
            Object task = taskDatabase.get(i);
            if (task.getClass() == Epic.class) {
                int isDone = -1;
                for (int i1 = 0; i1 < ((Epic) task).getSubtasks().size(); i1++) {
                    Subtask subtask = (Subtask) taskDatabase.get(((Epic) task).getSubtasks().get(i1));
                    if (subtask != null && subtask.getStatus() != Status.DONE) isDone++;
                }
                if (isDone < 0) {
                    ((Epic) task).setStatus(Status.DONE);
                } else {
                    ((Epic) task).setStatus(Status.IN_PROGRESS);
                }
                if (((Epic) task).getSubtasks().isEmpty()) ((Epic) task).setStatus(Status.NEW);
                taskDatabase.put(i, task);
            }
        }
    }

    @Override
    public String toString() {
        return "Database{" +
                "taskDatabase=" + taskDatabase +
                '}';
    }

}
