package taskManager;


import taskInfo.Task;

import java.util.ArrayList;

public class InMemoryHistoryManager implements HistoryManager {
    private final ArrayList<Task> taskHistory = new ArrayList<>(HISTORY_SIZE);
    private static final int HISTORY_SIZE = 10;

    @Override
    public void add(Task task) {
        taskHistory.add(task);
        if (taskHistory.size() > HISTORY_SIZE) taskHistory.remove(0);
    }

    @Override
    public ArrayList<Task> getHistory() {
        return (!taskHistory.isEmpty() ? taskHistory : null );
    }
}
