package test;

import org.junit.jupiter.api.Test;
import taskManager.TaskManager;
import util.Managers;

import static org.junit.jupiter.api.Assertions.*;

class ManagersTest {

    @Test
    void getDefault() {
        final TaskManager manager = Managers.getDefault();
        assertNotNull(manager, "Созданный менеджер равен <null>");
    }
}