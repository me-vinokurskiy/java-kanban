package util;

import taskManager.HistoryManager;
import taskManager.InMemoryHistoryManager;
import taskManager.InMemoryTaskManager;
import taskManager.TaskManager;

public class Managers {

    public static TaskManager getDefault() {
        return new InMemoryTaskManager(getDefaultHIstoryManager());
    }
    private static HistoryManager getDefaultHIstoryManager() {
        return new InMemoryHistoryManager();
    }
}
