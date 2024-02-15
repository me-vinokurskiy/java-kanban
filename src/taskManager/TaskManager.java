package taskManager;

import taskInfo.Subtask;
import dto.NewParameter;
import taskInfo.Task;

import java.util.ArrayList;

public interface TaskManager {
    void addNewTask(NewParameter parameters);

    void removeTasksByType(Class<?> taskClass);

    void removeTaskById(int id);

    Object getTaskById(int id);

    ArrayList<Subtask> getSubtasksOfEpic(int epicId);

    void updateAnyTask(NewParameter parameters, int id);

    ArrayList<Object> getCurrentClassTaskList(Class<?> taskClass);

    ArrayList<Task> getInMemoryHistory();
}
