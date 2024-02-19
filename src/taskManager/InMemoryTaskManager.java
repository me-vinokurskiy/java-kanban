package taskManager;

import dto.TaskParameters;
import taskInfo.*;

import java.util.ArrayList;
import java.util.HashMap;

public class InMemoryTaskManager implements TaskManager {
    private static int idCounter = 0;
    private final HashMap<Integer, Task> database;
    HistoryManager historyManager;

    public InMemoryTaskManager(HistoryManager historyManager) {
        database = new HashMap<>();
        this.historyManager = historyManager;
    }

    @Override
    public void add(TaskParameters parameters) {
        int id = idCounter++;
        switch (parameters.getType()) {
            case TASK:
                final Task newTask = new Task(
                        parameters.getName(),
                        parameters.getDescription(),
                        Status.NEW,
                        id
                );
                database.put(id, newTask);
                break;
            case EPIC:
                final Epic newEpic = new Epic(
                        parameters.getName(),
                        parameters.getDescription(),
                        Status.NEW,
                        id
                );
                database.put(id, newEpic);
                break;
            case SUB:
                final Subtask newSubtask = new Subtask(
                        parameters.getName(),
                        parameters.getDescription(),
                        Status.NEW,
                        id,
                        parameters.getEpicId()
                );
                database.put(id, newSubtask);
                final Integer epicId = parameters.getEpicId();
                Epic epic = (Epic) database.get(epicId);
                epic.addSubtask(id);
        }
    }

    @Override
    public void update(int id, TaskParameters parameters) {
        final Task task = database.get(id);
        if (parameters.getName() != null) task.setName(parameters.getName());
        if (parameters.getDescription() != null) task.setDescription(parameters.getDescription());
        if (parameters.getStatus() != null && task.getClass() != Epic.class) task.setStatus(parameters.getStatus());
        database.put(id, task);
        checkStatus();

    }

    @Override
    public Task get(int id) {
        historyManager.add(database.get(id));
        return database.get(id);
    }

    @Override
    public ArrayList<Task> getAllByType(Class<?> type) {
        final ArrayList<Task> tasks = new ArrayList<>();
        for (Integer id : database.keySet()) {
            if (database.get(id).getClass() == type) {
                tasks.add(database.get(id));
                historyManager.add(database.get(id));
            }
        }
        return (!tasks.isEmpty() ? tasks : null);
    }

    @Override
    public ArrayList<Subtask> getSubtasks(int epicId) {
        final ArrayList<Subtask> subtasks = new ArrayList<>();
        final Epic epic = (Epic) database.get(epicId);
        if (epic.getSubtasks().isEmpty()) return null;
        for (Integer id : epic.getSubtasks()) {
            subtasks.add((Subtask) database.get(id));
            historyManager.add(database.get(id));
        }
        return subtasks;
    }

    @Override
    public ArrayList<Task> getHistory() {
        return historyManager.getHistory();
    }
    @Override
    public void delete(Integer id) {
        final Task task = database.get(id);
        if (task.getClass() == Task.class) {
            database.remove(id);
        } else if (task.getClass() == Epic.class) {
            final Epic epic = (Epic) database.get(id);
            if (!epic.getSubtasks().isEmpty()) {
                for (Integer subtask : epic.getSubtasks()) {
                    database.remove(subtask);
                }
            }
            database.remove(id);

        } else {
            final Subtask subtask = (Subtask) database.get(id);
            final Epic epic = (Epic) database.get(subtask.getEpicId());
            epic.removeSubtask(id);
            database.replace(subtask.getEpicId(), epic);
            database.remove(id);
            checkStatus();
        }
    }
    @Override
    public void deleteAllByType(Class<?> type) {
        if (type == Subtask.class) {
            for (Integer id : database.keySet()) {
                if (database.get(id).getClass() == Subtask.class) {
                    final Subtask subtask = (Subtask) database.get(id);
                    final Epic epic = (Epic) database.get(subtask.getEpicId());
                    final ArrayList<Integer> subtasks = epic.getSubtasks();
                    subtasks.remove(id);
                    database.replace(id, null);
                }
            }
        } else if (type == Epic.class) {
            for (Integer id : database.keySet()) {
                if (database.get(id).getClass() == Epic.class || database.get(id).getClass() == Subtask.class) {
                    database.replace(id, null);
                }
            }
        } else if (type == Task.class) {
            for (Integer id : database.keySet()) {
                if (database.get(id).getClass() == Task.class) {
                    database.replace(id, null);
                }
            }
        }
        database.entrySet().removeIf(e -> e.getValue() == null);
    }
    private void checkStatus() {
        for (Integer id : database.keySet()) {
            if (database.get(id).getClass() == Epic.class) {
                Epic epic = (Epic) database.get(id);
                ArrayList<Integer> subtasks = epic.getSubtasks();
                int isDone = -1;
                int isNew = -1;
                for (Integer subtaskId : subtasks) {
                    Subtask subtask = (Subtask) database.get(subtaskId);
                    if (subtask.getStatus() != Status.DONE) {
                        isDone++;
                    }
                    if (subtask.getStatus() != Status.NEW) {
                        isNew++;
                    }
                }
                if (isDone < 0) {
                    epic.setStatus(Status.DONE);
                } else if (isNew < 0) {
                    epic.setStatus(Status.NEW);
                } else {
                    epic.setStatus(Status.IN_PROGRESS);
                }
                if (epic.getSubtasks().isEmpty()) {
                    epic.setStatus(Status.NEW);
                }
                database.replace(id, epic);
            }
        }
    }
    @Override
    public void printAllTasks() {
        for (Integer id : database.keySet()) System.out.println(database.get(id));
        if (database.isEmpty()) System.out.println("Empty");
    }
}