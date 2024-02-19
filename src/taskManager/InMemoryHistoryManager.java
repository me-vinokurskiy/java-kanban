package taskManager;

import taskInfo.Task;
import java.util.ArrayList;

public class InMemoryHistoryManager implements HistoryManager {
    private final static int MAX_SIZE = 10;
    private final ArrayList<Task> history = new ArrayList<>(MAX_SIZE);
    @Override
    public void add(Task task) {
        history.add(task);
        if (history.size() > MAX_SIZE) {
            history.removeFirst();
        }
    }
    @Override
    public ArrayList<Task> getHistory() {
        return history;
    }
}
