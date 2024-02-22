package taskManager;

import dto.TaskParameters;
import taskInfo.*;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class InMemoryTaskManager implements TaskManager {
    private static int idCounter;
    private final Map<Integer, Task> database;
    HistoryManager historyManager;

    public InMemoryTaskManager(HistoryManager historyManager) {
        idCounter = 0;
        database = new HashMap<>();
        this.historyManager = historyManager;
    }

    @Override
    public int add(TaskParameters parameters) {
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
                if (database.containsKey(parameters.getEpicId())) {
                    final Subtask newSubtask = new Subtask(
                            parameters.getName(),
                            parameters.getDescription(),
                            Status.NEW,
                            id,
                            parameters.getEpicId()
                    );
                    final int epicId = parameters.getEpicId();
                    final Epic epic = (Epic) database.get(epicId);
                    epic.addSubtask(id);
                    database.put(id, newSubtask);
                    break;
                }
        }
        return id;
    }

    @Override
    public void update(Integer id, TaskParameters parameters) {
        if (database.get(id) != null) {
            final Task task = database.get(id);
            if (parameters.getName() != null) task.setName(parameters.getName());
            if (parameters.getDescription() != null) task.setDescription(parameters.getDescription());
            if (parameters.getStatus() != null && task.getClass() != Epic.class) task.setStatus(parameters.getStatus());
            database.put(id, task);
            checkStatus();
        }

    }

    @Override
    public Task get(Integer id) {
        historyManager.add(database.get(id));
        return database.get(id);
    }

    @Override
    public List<Task> getAllByType(Class<?> type) {
        final List<Task> tasks = new ArrayList<>();
        for (Integer id : database.keySet()) {
            if (database.get(id).getClass() == type) {
                tasks.add(database.get(id));
                historyManager.add(database.get(id));
            }
        }
        return (!tasks.isEmpty() ? tasks : null);
    }

    @Override
    public List<Subtask> getSubtasks(int epicId) {
        final List<Subtask> subtasks = new ArrayList<>();
        final Epic epic = (Epic) database.get(epicId);
        if (epic.getSubtasks().isEmpty()) return null;
        for (Integer id : epic.getSubtasks()) {
            subtasks.add((Subtask) database.get(id));
            historyManager.add(database.get(id));
        }
        return subtasks;
    }

    @Override
    public List<Task> getHistory() {
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
                    final List<Integer> subtasks = epic.getSubtasks();
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
                List<Integer> subtasks = epic.getSubtasks();
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