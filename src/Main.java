import taskInfo.*;

import taskManager.TaskManager;
import dto.NewParameter;
import util.Managers;

public class Main {
    public static void main(String[] args) {
        final TaskManager taskManager = Managers.getDefault();

//  Создание обычного таска
        NewParameter task_0 = new NewParameter();
        task_0.setName("T1");
        task_0.setDescription("D1");
        task_0.setType(TaskType.TASK);
        task_0.setStatus(Status.NEW);
//  Создание эпика 1
        NewParameter epic_1 = new NewParameter();
        epic_1.setName("E1");
        epic_1.setDescription("D2");
        epic_1.setType(TaskType.EPIC);
//  Создание сабтаска 1
        NewParameter sub_1 = new NewParameter();
        sub_1.setName("S1");
        sub_1.setDescription("D3");
        sub_1.setType(TaskType.SUB);
        sub_1.setStatus(Status.NEW);
        sub_1.setEpicId(1);
//  Создание сабтаска 2
        NewParameter sub_2 = new NewParameter();
        sub_2.setName("S2");
        sub_2.setDescription("D4");
        sub_2.setType(TaskType.SUB);
        sub_2.setStatus(Status.DONE);
        sub_2.setEpicId(1);

//  Сохранение всех созданных задач
        taskManager.addNewTask(task_0);
        taskManager.addNewTask(epic_1);
        taskManager.addNewTask(sub_1);
        taskManager.addNewTask(sub_2);
//  Вывод каждого таска по ID

        System.out.println(taskManager.getTaskById(0));
        System.out.println(taskManager.getTaskById(1));
        System.out.println(taskManager.getTaskById(2));
        System.out.println(taskManager.getTaskById(3));
        System.out.println(taskManager.getTaskById(3));
        System.out.println(taskManager.getTaskById(2));
        System.out.println(taskManager.getTaskById(1));
        System.out.println(taskManager.getTaskById(0));
        System.out.println(taskManager.getTaskById(0));
        System.out.println(taskManager.getTaskById(1));
        System.out.println(taskManager.getTaskById(2));
        System.out.println(taskManager.getTaskById(3));



//  Создание новых данных для, например, сабтаска 2
        NewParameter newSubtask = new NewParameter();
        newSubtask.setName("S3");
        newSubtask.setDescription("D5");
        newSubtask.setType(TaskType.SUB);
        newSubtask.setStatus(Status.DONE); // изменился статус -> изменится статус эпика 1
//  Обновление данных сабтаска 2
        taskManager.updateAnyTask(newSubtask, 2);

        printAllTasks(taskManager);
    }

    private static void printAllTasks(TaskManager manager) {
        System.out.println("Задачи:");
        for (Object task : manager.getCurrentClassTaskList(Task.class)) {
            System.out.println(task);
        }
        System.out.println("Эпики:");
        for (Object epic : manager.getCurrentClassTaskList(Epic.class)) {
            System.out.println(epic);
        }
        System.out.println("Подзадачи:");
        for (Object subtask : manager.getCurrentClassTaskList(Subtask.class)) {
            System.out.println(subtask);
        }
        System.out.println("История:");
        for (Task task : manager.getInMemoryHistory()) {
            System.out.println(task);
        }
    }
}