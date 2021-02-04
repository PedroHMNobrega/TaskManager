package infra.controllers;

import application.TaskManagerAdapter;
import domain.Task;
import domain.TaskRepositoryInterface;
import domain.exceptions.CommandErrorException;
import gui.Components;
import gui.SingleTasksFrame;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class SingleTasksController implements Controller
{
    private SingleTasksFrame frame;
    private ArrayList<Task> tasks;
    private String name;
    private TaskRepositoryInterface repositoryInterface;
    private TaskManagerAdapter taskManager;
    private MainFrameController mainFrameController;

    public SingleTasksController(
            ArrayList<Task> tasks,
            TaskRepositoryInterface repositoryInterface,
            TaskManagerAdapter taskManager,
            MainFrameController mainFrameController
    )
    {
        this.tasks = tasks;
        this.name = tasks.get(0).getName();
        this.repositoryInterface = repositoryInterface;
        this.taskManager = taskManager;
        this.mainFrameController = mainFrameController;
    }

    @Override
    public void execute()
    {
        frame = new SingleTasksFrame(this.name, this);
    }

    public String getName()
    {
        return name;
    }

    public ArrayList<Task> getTasks()
    {
        return tasks;
    }

    public void kill(Task task)
    {
        try {
            this.taskManager.kill(task);
            JPanel tasksPanel = this.frame.getTasksPanel();

            tasksPanel.removeAll();
            Components.panelRefresh(tasksPanel);
            this.repositoryInterface.remove(task);
            this.tasks.remove(task);
            this.frame.renderTasks();

        } catch (CommandErrorException e) {
            e.printStackTrace();
        }
    }

    public void killAll()
    {
        try {
            this.taskManager.killAll(this.name);
            Thread.sleep(500);
            this.closeWindow();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void closeWindow()
    {
        this.frame.dispatchEvent(new WindowEvent(this.frame, WindowEvent.WINDOW_CLOSING));
        this.mainFrameController.refreshTasks();
    }
}
