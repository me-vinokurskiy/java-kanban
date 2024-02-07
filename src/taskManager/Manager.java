package taskManager;


import taskInfo.*;
import user.NewParameter;

import java.util.ArrayList;
import java.util.HashMap;

public class Manager {
    public static int taskCounter = 0;
    Database database = new Database();

    public void addNewTask(NewParameter parameters) {
        final int id = taskCounter++;
        if (parameters.getType() == TaskType.TASK) {
            Task newTask = new Task(
                    parameters.getName(),
                    parameters.getDescription(),
                    parameters.getStatus(),
                    parameters.getType(),
                    id);
            database.saveInDatabase(id, newTask);
        }
        if (parameters.getType() == TaskType.EPIC) {
            Epic newTask = new Epic(
                    parameters.getName(),
                    parameters.getDescription(),
                    Status.NEW,
                    parameters.getType(),
                    id);
            database.saveInDatabase(id, newTask);
        }
        if (parameters.getType() == TaskType.SUB) {
            HashMap<Integer, Object> tasks = database.getTaskDatabase();
            Epic epicTask = (Epic) tasks.get(parameters.getEpicId());
            int epicId = epicTask.getId();
            Subtask newTask = new Subtask(
                    parameters.getName(),
                    parameters.getDescription(),
                    parameters.getStatus(),
                    parameters.getType(),
                    id,
                    epicId);
            ArrayList<Integer> subtasks = epicTask.getSubtasks();
            subtasks.add(id);
            database.saveInDatabase(epicId, epicTask);
            database.saveInDatabase(id, newTask);
        }
    }

    public void removeTasksByType(Class<?> taskClass) {
        HashMap<Integer, Object> tasks = database.getTaskDatabase();
        for (int i = tasks.size()-1 ; i >= 0; i--) {
            Object task = tasks.get(i);
            if (task.getClass() == Task.class && task.getClass() == taskClass) {
                tasks.remove(i);
            } else if (task.getClass() == Epic.class && task.getClass() == taskClass) {
                Epic epicTask = (Epic) task;
                ArrayList<Integer> subtasks = epicTask.getSubtasks();
                for (Integer id : subtasks) tasks.remove(id);
                tasks.remove(i);
            } else if (task.getClass() == Subtask.class && task.getClass() == taskClass) {
                Subtask subtask = (Subtask) task;
                int id = subtask.getId();
                int epicId = subtask.getEpicID();
                Epic epicTask = (Epic) tasks.get(epicId);
                ArrayList<Integer> subtasks = epicTask.getSubtasks();
                subtasks.removeIf(currentSubtask -> currentSubtask.equals(id));
                tasks.remove(id);


            }

        }

    }

    public void removeTaskById(int id) {
        database.deleteById(id);

    }

    public Object getTaskById(int id) {
        HashMap<Integer, Object> tasks = database.getTaskDatabase();
        return  tasks.get(id);
    }

    public ArrayList<Subtask> getSubtasksOfEpic(int epicId) {
        HashMap<Integer,Object> tasks = database.getTaskDatabase();
        Epic epicTask = (Epic) tasks.get(epicId);

        ArrayList<Subtask> subtasks = new ArrayList<>();

        if (epicTask.getSubtasks() != null) {
            for (int subtaskId : epicTask.getSubtasks()) {
                subtasks.add((Subtask) tasks.get(subtaskId));
            }
        } else return null;

        return subtasks;
    }

    public void updateAnyTask(NewParameter parameters, int id) {
        HashMap<Integer, Object> tasks = database.getTaskDatabase();
        Object task = tasks.get(id);
        AbstractTask updTask;

        if (task.getClass() == Task.class) {
            updTask = (Task) task;
        } else if (task.getClass() == Epic.class) {
            updTask = (Epic) task;
        } else {
            updTask = (Subtask) task;
        }

        if (parameters.getName() != null) updTask.setName(parameters.getName());
        if (parameters.getDescription() != null) updTask.setDescription(parameters.getDescription());
        if (parameters.getStatus() != null && updTask.getType() != TaskType.EPIC) updTask.setStatus(parameters.getStatus());

        database.saveInDatabase(id, updTask);
    }

    public ArrayList<Object> getCurrentClassTaskList(Class<?> taskClass) {
        ArrayList<Object> currentClassTaskList = new ArrayList<>();
        HashMap<Integer, Object> tasks = database.getTaskDatabase();
        for (Integer i : tasks.keySet()) {
            Object task = tasks.get(i);
            if (task.getClass() == taskClass) currentClassTaskList.add(task);
        }
        return currentClassTaskList;
    }
}
