package test;

import org.junit.jupiter.api.Test;
import taskInfo.Epic;
import taskInfo.Status;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EpicTest {

    @Test
    void addSubtask() {
        final Epic epic = new Epic("Epic", "Desc", Status.NEW, 0);
        final List<Integer> subtasks = epic.getSubtasks();

        subtasks.add(0);
        subtasks.add(1);

        assertNotNull(subtasks, "Ссылки на сабтаски не добавлены");
        assertEquals(2, subtasks.size(), "Добавлено неверное кол-во ссылок на сабтаски");
    }

    @Test
    void getSubtasks() {
        final Epic epic = new Epic("Epic", "Desc", Status.NEW, 0);
        final List<Integer> subtasks = epic.getSubtasks();

        subtasks.add(0);
        subtasks.add(1);

        final List<Integer> savedSubtasks = epic.getSubtasks();

        final ArrayList<Integer> testSubtasks = new ArrayList<>();
        testSubtasks.add(0);
        testSubtasks.add(1);

        assertNotNull(savedSubtasks, "Ссылки на сабтаски не добавлены");
        assertEquals(testSubtasks, savedSubtasks, "Возвращен неверный список ссылок на сабтаски.");
    }

    @Test
    void removeSubtask() {
        final Epic epic = new Epic("Epic", "Desc", Status.NEW, 0);
        final List<Integer> subtasks = epic.getSubtasks();

        subtasks.add(0);
        subtasks.add(1);

        epic.removeSubtask(0);

        assertEquals(1, subtasks.size(), "Ссылка на сабтаск не удалена");
    }
}