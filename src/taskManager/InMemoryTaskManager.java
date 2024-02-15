package taskManager;


import taskInfo.*;
import dto.NewParameter;

import java.util.ArrayList;
import java.util.HashMap;

public class InMemoryTaskManager implements TaskManager {
    public static int taskCounter = 0;
    final Database database = new Database();
    final HistoryManager historyManager;

    public InMemoryTaskManager(HistoryManager historyManager) {
        this.historyManager = historyManager;
    }

    @Override
    public void addNewTask(NewParameter parameters) {
        final int id = taskCounter++;
        switch (parameters.getType()) {
            case TASK: {
                final Task newTask = new Task(
                        parameters.getName(),
                        parameters.getDescription(),
                        parameters.getStatus(),
                        parameters.getType(),
                        id);
                database.saveInDatabase(id, newTask);
                break;
            }
            case EPIC: {
                final Epic newTask = new Epic(
                        parameters.getName(),
                        parameters.getDescription(),
                        Status.NEW,
                        parameters.getType(),
                        id);
                database.saveInDatabase(id, newTask);
                break;
            }
            case SUB: {
                final HashMap<Integer, Object> tasks = database.getTaskDatabase();
                final Epic epicTask = (Epic) tasks.get(parameters.getEpicId());
                final int epicId = epicTask.getId();
                final Subtask newTask = new Subtask(
                        parameters.getName(),
                        parameters.getDescription(),
                        parameters.getStatus(),
                        parameters.getType(),
                        id,
                        epicId);
                final ArrayList<Integer> subtasks = epicTask.getSubtasks();
                subtasks.add(id);
                database.saveInDatabase(epicId, epicTask);
                database.saveInDatabase(id, newTask);
                break;
            }
        }
    }

    @Override
    public void removeTasksByType(Class<?> taskClass) {
        final HashMap<Integer, Object> tasks = database.getTaskDatabase();
        for (int i = tasks.size() - 1; i >= 0; i--) {
            final Object task = tasks.get(i);
            if (task.getClass() == taskClass) {
                if (task.getClass() == Task.class) {
                    tasks.remove(i);
                } else if (task.getClass() == Epic.class) {
                    final Epic epicTask = (Epic) task;
                    final ArrayList<Integer> subtasks = epicTask.getSubtasks();
                    for (Integer id : subtasks) tasks.remove(id);
                    tasks.remove(i);
                } else if (task.getClass() == Subtask.class) {
                    final Subtask subtask = (Subtask) task;
                    final int id = subtask.getId();
                    final int epicId = subtask.getEpicID();
                    final Epic epicTask = (Epic) tasks.get(epicId);
                    final ArrayList<Integer> subtasks = epicTask.getSubtasks();
                    subtasks.removeIf(currentSubtask -> currentSubtask.equals(id));
                    tasks.remove(id);
                }
            }
        }
    }

    @Override
    public void removeTaskById(int id) {
        database.deleteById(id);
    }

    @Override
    public Object getTaskById(int id) {
        final HashMap<Integer, Object> tasks = database.getTaskDatabase();
        historyManager.add((Task) tasks.get(id));
        return tasks.get(id);
    }

    @Override
    public ArrayList<Subtask> getSubtasksOfEpic(int epicId) {
        final HashMap<Integer, Object> tasks = database.getTaskDatabase();
        final Epic epicTask = (Epic) tasks.get(epicId);
        final ArrayList<Subtask> subtasks = new ArrayList<>();
        if (epicTask.getSubtasks() != null) {
            for (int subtaskId : epicTask.getSubtasks()) {
                subtasks.add((Subtask) tasks.get(subtaskId));
            }
        } else return null;

        return subtasks;
    }

    @Override
    public void updateAnyTask(NewParameter parameters, int id) {
        final HashMap<Integer, Object> tasks = database.getTaskDatabase();
        final Object task = tasks.get(id);
        final Task updTask;
        if (task.getClass() == Task.class) {
            updTask = (Task) task;
        } else if (task.getClass() == Epic.class) {
            updTask = (Epic) task;
        } else {
            updTask = (Subtask) task;
        }

        if (parameters.getName() != null) updTask.setName(parameters.getName());
        if (parameters.getDescription() != null) updTask.setDescription(parameters.getDescription());
        if (parameters.getStatus() != null && updTask.getType() != TaskType.EPIC)
            updTask.setStatus(parameters.getStatus());

        database.saveInDatabase(id, updTask);
    }

    @Override
    public ArrayList<Object> getCurrentClassTaskList(Class<?> taskClass) {
        final ArrayList<Object> currentClassTaskList = new ArrayList<>();
        final HashMap<Integer, Object> tasks = database.getTaskDatabase();
        for (Integer i : tasks.keySet()) {
            final Object task = tasks.get(i);
            if (task.getClass() == taskClass) currentClassTaskList.add(task);
        }
        return currentClassTaskList;
    }

    @Override
    public ArrayList<Task> getInMemoryHistory() {
        return historyManager.getHistory();
    }
}
