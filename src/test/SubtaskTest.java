package test;

import org.junit.jupiter.api.Test;
import taskInfo.Status;
import taskInfo.Subtask;

import static org.junit.jupiter.api.Assertions.*;

class SubtaskTest {

    @Test
    void getEpicId() {
        final Subtask subtask = new Subtask("Name", "Desk", Status.NEW, 1, 0);
        final int testEpicId = 0;
        final int savedEpicId = subtask.getEpicId();

        assertEquals(testEpicId, savedEpicId, "ID не совпадают.");
    }
}