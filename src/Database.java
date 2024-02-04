import java.util.HashMap;

public class Database {
    private final HashMap<Integer, Object> taskDatabase = new HashMap<>();

    public void saveToDatabase(int id, Object newTask) {
        if (newTask.getClass() == SubTask.class) {
            for (Integer i : taskDatabase.keySet()) {
                Object task = taskDatabase.get(i);
                if (task.getClass() == EpicTask.class) {
                    EpicTask epicTask = (EpicTask) task;
                    if (epicTask.subTaskHashMap.containsValue(newTask)) {
                        int isDone = -1;
                        for (Integer i1 : epicTask.subTaskHashMap.keySet()) {
                            SubTask subTask = epicTask.subTaskHashMap.get(i1);
                            if (subTask.status != Status.DONE) isDone++;
                        }
                        if (isDone < 0) {
                            epicTask.status = Status.DONE;
                        } else {
                            epicTask.status = Status.IN_PROGRESS;
                        }
                    }
                    saveToDatabase(i, epicTask);
                }
            }
        }
        taskDatabase.put(id, newTask);
    }

    public HashMap<Integer, Object> getTaskDatabase() {
        return taskDatabase;
    }

    @Override
    public String toString() {
        return "\n" + "Database{" +
                "database=" + taskDatabase +
                '}';
    }

    public void clear() {
        taskDatabase.clear();
    }

    public void remove(int id) {
        taskDatabase.remove(id);
    }
}
