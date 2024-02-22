package test;

import dto.TaskParameters;
import org.junit.jupiter.api.Test;
import taskInfo.*;
import taskManager.TaskManager;
import util.Managers;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {

    @Test
    void add() {
        final TaskManager manager = Managers.getDefault();
        TaskParameters task = new TaskParameters();
        task.setName("taskName");
        task.setDescription("taskDescription");
        task.setType(TaskType.TASK);

        final int id0 = manager.add(task);

        TaskParameters epic = new TaskParameters();
        epic.setName("epicName");
        epic.setDescription("epicDescription");
        epic.setType(TaskType.EPIC);

        final int id1 = manager.add(epic);

        TaskParameters subtask = new TaskParameters();
        subtask.setName("subtaskName");
        subtask.setDescription("subtaskDescription");
        subtask.setType(TaskType.SUB);
        subtask.setEpicId(id1);

        final int id2 = manager.add(subtask);

        final Task savedTask = manager.get(id0);
        final Task testTask = new Task("taskName", "taskDescription", Status.NEW, 0);
        assertNotNull(savedTask, "Задача не найдена.");
        assertEquals(testTask, savedTask, "Задачи не совпадают.");

        final ArrayList<Integer> subtasks = new ArrayList<>();
        subtasks.add(2);

        final Task savedEpic = manager.get(id1);
        final Task testEpic = new Epic("epicName", "epicDescription", Status.NEW, id1, subtasks);
        assertNotNull(savedEpic, "Задача не найдена.");
        assertEquals(testEpic, savedEpic, "Задачи не совпадают.");

        final Task savedSubtask = manager.get(id2);
        final Task testSubtask = new Subtask("subtaskName", "subtaskDescription", Status.NEW, id2, id1);
        assertNotNull(savedSubtask, "Задача не найдена.");
        assertEquals(testSubtask, savedSubtask, "Задачи не совпадают.");
    }

    @Test
    void update() {
        final TaskManager manager = Managers.getDefault();
        TaskParameters dto = new TaskParameters();
        dto.setName("taskName");
        dto.setDescription("taskDescription");
        dto.setType(TaskType.TASK);

        final int id = manager.add(dto);

        final TaskParameters newTask = new TaskParameters();
        newTask.setName("taskNewName");
        newTask.setDescription("taskNewDescription");
        newTask.setStatus(Status.DONE);

        final Task updTask = new Task("taskNewName", "taskNewDescription", Status.DONE, 0);

        manager.update(0, newTask);
        final Task testTask = manager.get(id);

        assertNotNull(testTask, "Задача не найдена.");
        assertEquals(updTask, testTask, "Задачи не совпадают.");


    }

    @Test
    void get() {
        final TaskManager manager = Managers.getDefault();
        TaskParameters dto = new TaskParameters();
        dto.setName("taskName");
        dto.setDescription("taskDescription");
        dto.setType(TaskType.TASK);

        final int id = manager.add(dto);

        final Task savedTask = manager.get(id);
        final Task testTask = new Task("taskName", "taskDescription", Status.NEW, 0);
        assertNotNull(savedTask, "Задача не найдена.");
        assertEquals(testTask, savedTask, "Задачи не совпадают.");
    }

    @Test
    void getAllByType() {
        final TaskManager manager = Managers.getDefault();
        TaskParameters dto = new TaskParameters();
        dto.setName("taskName");
        dto.setDescription("taskDescription");
        dto.setType(TaskType.TASK);
        TaskParameters dto2 = new TaskParameters();
        dto2.setName("taskName");
        dto2.setDescription("taskDescription");
        dto2.setType(TaskType.TASK);

        manager.add(dto);
        manager.add(dto2);

        final Task testTask = new Task("taskName", "taskDescription", Status.NEW, 0);
        final Task testTask1 = new Task("taskName", "taskDescription", Status.NEW, 1);

        final ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(testTask);
        tasks.add(testTask1);

        final List<Task> testTasks = manager.getAllByType(Task.class);
        assertNotNull(testTasks, "Список задач не найден");
        assertEquals(tasks, testTasks, "Списки не совпадают");
    }

    @Test
    void getSubtasks() {
        final TaskManager manager = Managers.getDefault();
        final TaskParameters dto = new TaskParameters();
        dto.setName("Epic");
        dto.setDescription("Desc");
        dto.setType(TaskType.EPIC);

        int epicId = manager.add(dto);

        final TaskParameters dto1 = new TaskParameters();
        dto1.setName("Sub");
        dto1.setDescription("subDesc");
        dto1.setType(TaskType.SUB);
        dto1.setEpicId(epicId);

        int subtaskId = manager.add(dto1);

        final List<Subtask> savedSubtasks = manager.getSubtasks(epicId);
        final ArrayList<Subtask> testSubtasks = new ArrayList<>();
        final Subtask subtask = new Subtask("Sub", "subDesc", Status.NEW, subtaskId, epicId);
        testSubtasks.add(subtask);

        assertNotNull(savedSubtasks, "Список сабтасков не найден.");
        assertEquals(1, savedSubtasks.size(), "Кол-во сабтасков не совпадает.");
        assertEquals(testSubtasks, savedSubtasks, "Списки сабтасков не совпадают.");
    }

    @Test
    void getHistory() {
        final TaskManager manager = Managers.getDefault();
        final TaskParameters dto = new TaskParameters();
        dto.setName("Epic");
        dto.setDescription("Desc");
        dto.setType(TaskType.EPIC);

        int epicId0 = manager.add(dto);
        int epicId1 = manager.add(dto);
        int epicId2 = manager.add(dto);
        int epicId3 = manager.add(dto);

        final Task task0 = manager.get(epicId0);
        final Task task1 = manager.get(epicId1);
        final Task task2 = manager.get(epicId2);
        final Task task3 = manager.get(epicId3);

        final List<Task> testHistory = manager.getHistory();
        final List<Task> history = new ArrayList<>();
        history.add(task0);
        history.add(task1);
        history.add(task2);
        history.add(task3);

        assertNotNull(testHistory, "История пуста.");
        assertEquals(4, testHistory.size(), "Неверное кол-во задач.");
        assertEquals(history, testHistory, "Истории не совпадают.");
    }

    @Test
    void delete() {
        final TaskManager manager = Managers.getDefault();
        final TaskParameters dto = new TaskParameters();
        dto.setName("Task");
        dto.setDescription("Desc");
        dto.setType(TaskType.TASK);

        int taskId0 = manager.add(dto);

        final List<Task> tasks = manager.getAllByType(Task.class);
        assertEquals(1, tasks.size());
        manager.delete(taskId0);
        final List<Task> testTasks = manager.getAllByType(Task.class);
        assertNull(testTasks, "Задача не удалена.");
    }

    @Test
    void deleteAllByType() {
        final TaskManager manager = Managers.getDefault();
        TaskParameters dto = new TaskParameters();
        dto.setName("taskName");
        dto.setDescription("taskDescription");
        dto.setType(TaskType.TASK);
        TaskParameters dto2 = new TaskParameters();
        dto2.setName("taskName");
        dto2.setDescription("taskDescription");
        dto2.setType(TaskType.TASK);

        manager.add(dto);
        manager.add(dto2);

        final List<Task> tasks = manager.getAllByType(Task.class);
        assertEquals(2, tasks.size());

        manager.deleteAllByType(Task.class);

        final List<Task> testTasks = manager.getAllByType(Task.class);
        assertNull(testTasks, "Задачи не удалена.");
    }
}