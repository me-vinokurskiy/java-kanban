package user;

import taskInfo.Status;
import taskManager.TaskManager;

public class Main {
    public static void main(String[] args) {

        TaskManager taskManager = new TaskManager();

        //taskManager.addNewTaskToDatabase("SIMP_1", "DESC_1", taskInfo.TaskType.SIMPLE, taskInfo.Status.NEW);
        taskManager.addNewTaskToDatabase("EPIC_1", "DESC_2", taskInfo.TaskType.EPIC, taskInfo.Status.NEW);
        taskManager.addNewSubTask("SUB_1", "DESC_3", taskInfo.TaskType.SUB, taskInfo.Status.NEW, 0);
        taskManager.addNewSubTask("SUB_2", "DESC_4", taskInfo.TaskType.SUB, taskInfo.Status.NEW, 0);

        NewParameters parameters1 = new NewParameters();
        parameters1.setName("NEW_NAME");
        parameters1.setDescription("NEW_DESC");
        parameters1.setStatus(Status.IN_PROGRESS);

        taskManager.update(1, parameters1);

        NewParameters parameters2 = new NewParameters();
        parameters2.setName("NEW_NAME");
        //parameters2.setDescription("NEW_DESCRIPTION");
        parameters2.setStatus(Status.DONE);

        taskManager.update(2, parameters2);

        //taskManager.removeAllTasks();
        //System.out.println(taskManager.getAllTasks());
        //System.out.println(taskManager.getTaskFromDatabaseById(1));
        //taskManager.removeTaskFromDatabaseById(1);
        //taskManager.removeTaskFromDatabaseById(2);
        System.out.println(taskManager.getSubTasksListByEpicId(0));
        //System.out.println(taskManager.getAllTasks());


    }
}
