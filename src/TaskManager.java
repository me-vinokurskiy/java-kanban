import java.util.HashMap;

public class TaskManager {
    private final Database database = new Database();
    private int taskCounter = 0;

    public void addNewTaskToDatabase (String name, String description, TaskType type, Status status) {
        final int id = taskCounter++;
        if (type == TaskType.SIMPLE) {
            SimpleTask newSimpleTask = new SimpleTask(name, description, type, status, id);
            database.saveToDatabase(id, newSimpleTask);
        }
        if (type == TaskType.EPIC) {
            EpicTask newEpicTask = new EpicTask(name, description, type, id);
            database.saveToDatabase(id, newEpicTask);
        }
    }

    public void addNewSubTask (String name, String description, TaskType type, Status status, int epicId) {
        final int id = taskCounter++;
        SubTask newSubTask = new SubTask(name, description, type, status, id);
        database.saveToDatabase(id, newSubTask);

        HashMap<Integer, Object> taskDatabase = database.getTaskDatabase();

        EpicTask currentEpicTask = (EpicTask) taskDatabase.get(epicId);
        currentEpicTask.saveSubTask(id, newSubTask);
        database.saveToDatabase(epicId, currentEpicTask);
    }

    public void update (int id, NewParameters parameters) {
        HashMap<Integer, Object> taskDatabase = database.getTaskDatabase();
        Object task = taskDatabase.get(id);
        Task updTask;

        if (task.getClass() == SimpleTask.class) {
            updTask = (SimpleTask) task;
        } else if (task.getClass() == EpicTask.class) {
            updTask = (EpicTask) task;
        } else {
            updTask = (SubTask) task;
        }

        if (parameters.getName() != null) updTask.name = parameters.getName();
        if (parameters.getDescription() != null) updTask.description = parameters.getDescription();
        if (parameters.getStatus() != null && updTask.getClass() != EpicTask.class) updTask.status = parameters.getStatus();

        database.saveToDatabase(id, updTask);
    }

    public Database getAllTasks() {
        return database;
    }
    public void removeAllTasks() {
        database.clear();
    }

    public Object getTaskFromDatabaseById(int id) {
        HashMap<Integer, Object> taskDatabase = database.getTaskDatabase();
        return taskDatabase.get(id);
    }

    public void removeTaskFromDatabaseById (int id) {
        database.remove(id);
    }

}
