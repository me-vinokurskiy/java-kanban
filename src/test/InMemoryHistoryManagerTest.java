package test;

import org.junit.jupiter.api.Test;
import taskInfo.Status;
import taskInfo.Task;
import taskManager.HistoryManager;
import taskManager.InMemoryHistoryManager;
import util.Managers;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {


    @Test
    void add() {
        final HistoryManager historyManager = new InMemoryHistoryManager();
        historyManager.add(new Task("Name", "Desc", Status.NEW, 0));
        historyManager.add(new Task("Name", "Desc", Status.NEW, 1));
        final ArrayList<Task> history = historyManager.getHistory();
        assertNotNull(history, "History is not empty");
        assertEquals(2,history.size());
    }

    @Test
    void getHistory() {
        final HistoryManager historyManager = new InMemoryHistoryManager();
        historyManager.add(new Task("Name", "Desc", Status.NEW, 0));
        historyManager.add(new Task("Name", "Desc", Status.NEW, 1));
        historyManager.add(new Task("Name", "Desc", Status.NEW, 2));
        historyManager.add(new Task("Name", "Desc", Status.NEW, 3));
        historyManager.add(new Task("Name", "Desc", Status.NEW, 4));
        historyManager.add(new Task("Name", "Desc", Status.NEW, 5));
        historyManager.add(new Task("Name", "Desc", Status.NEW, 6));
        historyManager.add(new Task("Name", "Desc", Status.NEW, 7));
        historyManager.add(new Task("Name", "Desc", Status.NEW, 8));
        historyManager.add(new Task("Name", "Desc", Status.NEW, 9));
        historyManager.add(new Task("Name", "Desc", Status.NEW, 10));
        historyManager.add(new Task("Name", "Desc", Status.NEW, 11));
        final ArrayList<Task> history = historyManager.getHistory();
        final ArrayList<Task> testHistory = new ArrayList<>();
        testHistory.add(new Task("Name", "Desc", Status.NEW, 2));
        testHistory.add(new Task("Name", "Desc", Status.NEW, 3));
        testHistory.add(new Task("Name", "Desc", Status.NEW, 4));
        testHistory.add(new Task("Name", "Desc", Status.NEW, 5));
        testHistory.add(new Task("Name", "Desc", Status.NEW, 6));
        testHistory.add(new Task("Name", "Desc", Status.NEW, 7));
        testHistory.add(new Task("Name", "Desc", Status.NEW, 8));
        testHistory.add(new Task("Name", "Desc", Status.NEW, 9));
        testHistory.add(new Task("Name", "Desc", Status.NEW, 10));
        testHistory.add(new Task("Name", "Desc", Status.NEW, 11));


        assertEquals(testHistory, history, "Return wrong history");
    }
}