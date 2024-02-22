package taskManager;

import taskInfo.Subtask;
import taskInfo.Task;
import dto.TaskParameters;

import java.util.List;

public interface TaskManager {
    int add(TaskParameters parameters);
    void update(Integer id, TaskParameters parameters);
    Task get(Integer id);
    List<Task> getAllByType(Class<?> type);
    List<Subtask> getSubtasks(int epicId);
    List<Task> getHistory();
    void delete(Integer id);
    void deleteAllByType(Class<?> type);
    void printAllTasks();
}
