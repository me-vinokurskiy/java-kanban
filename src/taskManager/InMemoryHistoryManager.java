package taskManager;

import taskInfo.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private final static int MAX_SIZE = 10;
    private final List<Task> history = new ArrayList<>(MAX_SIZE);

    @Override
    public void add(Task task) {
        if (history.contains(task)) {
            history.remove(task);
        }

        history.add(task);

        if (history.size() > MAX_SIZE) {
            history.removeFirst();
        }

    }

    @Override
    public List<Task> getHistory() {
        return new ArrayList<>(history);
    }
}
