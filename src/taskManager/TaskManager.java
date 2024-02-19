package taskManager;

import taskInfo.Subtask;
import taskInfo.Task;
import dto.TaskParameters;

import java.util.ArrayList;

public interface TaskManager {
    void add(TaskParameters parameters);
    void update(int id, TaskParameters parameters);
    Task get(int id);
    ArrayList<Task> getAllByType(Class<?> type);
    ArrayList<Subtask> getSubtasks(int epicId);
    ArrayList<Task> getHistory();
    void delete(Integer id);
    void deleteAllByType(Class<?> type);
    void printAllTasks();
}
