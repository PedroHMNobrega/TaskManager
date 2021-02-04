package main;

import application.TaskManagerAdapter;
import infra.adapters.TaskManagerAdapterLinux;
import infra.controllers.Controller;
import infra.controllers.MainFrameController;
import infra.database.TaskRepositoryMemory;

public class Main {
    public static void main(String[] args) {
        TaskRepositoryMemory repository = new TaskRepositoryMemory();
        TaskManagerAdapter taskManager = new TaskManagerAdapterLinux(repository);

        Controller controller = new MainFrameController(repository, taskManager);
        controller.execute();
    }
}