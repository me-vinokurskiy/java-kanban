import dto.TaskParameters;
import taskInfo.*;
import taskManager.TaskManager;
import util.Managers;

public class Main {
    public static void main(String[] args) {
        TaskManager manager = createManager();


        manager.deleteAllByType(Task.class);
        //manager.deleteAllByType(Subtask.class);
        //manager.deleteAllByType(Epic.class);



        manager.printAllTasks();

    }

    static TaskManager createManager() {
        TaskManager manager = Managers.getDefault();

        TaskParameters task0 = new TaskParameters();
        task0.setName("T0");
        task0.setDescription("D0");
        task0.setType(TaskType.TASK);

        TaskParameters task1 = new TaskParameters();
        task1.setName("T1");
        task1.setDescription("D1");
        task1.setType(TaskType.TASK);

        TaskParameters epic0 = new TaskParameters();
        epic0.setName("E0");
        epic0.setDescription("D2");
        epic0.setType(TaskType.EPIC);

        TaskParameters epic1 = new TaskParameters();
        epic1.setName("E1");
        epic1.setDescription("D3");
        epic1.setType(TaskType.EPIC);

        TaskParameters sub0 = new TaskParameters();
        sub0.setName("S0");
        sub0.setDescription("D4");
        sub0.setType(TaskType.SUB);
        sub0.setEpicId(2);

        TaskParameters sub1 = new TaskParameters();
        sub1.setName("S1");
        sub1.setDescription("D5");
        sub1.setType(TaskType.SUB);
        sub1.setEpicId(2);

        TaskParameters sub2 = new TaskParameters();
        sub2.setName("S2");
        sub2.setDescription("D6");
        sub2.setType(TaskType.SUB);
        sub2.setEpicId(3);

        TaskParameters sub3 = new TaskParameters();
        sub3.setName("S3");
        sub3.setDescription("D7");
        sub3.setType(TaskType.SUB);
        sub3.setEpicId(3);

        manager.add(task0);
        manager.add(task1);
        manager.add(epic0);
        manager.add(epic1);
        manager.add(sub0);
        manager.add(sub1);
        manager.add(sub2);
        manager.add(sub3);
        return manager;
    }
}
