package infra.controllers;

import application.TaskManagerAdapter;
import domain.Task;
import domain.TaskGroups;
import domain.TaskRepositoryInterface;
import gui.Components;
import gui.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MainFrameController implements Controller
{
    private MainFrame mainFrame;
    private TaskRepositoryInterface repositoryInterface;
    private TaskManagerAdapter taskManager;
    private Point scrollPoint;

    public MainFrameController(TaskRepositoryInterface repositoryInterface, TaskManagerAdapter taskManager)
    {
        this.repositoryInterface = repositoryInterface;
        this.taskManager = taskManager;
    }

    @Override
    public void execute()
    {
        mainFrame = new MainFrame("Task Manager", this);
    }

    public ArrayList<ArrayList<Task>> getTaskGroups()
    {
        try {
            TaskGroups taskGroups = taskManager.getTasks();
            return taskGroups.getTaskGroupsSorted();
        } catch (Exception e) {
            return null;
        }
    }

    public String getMemoryUsage(ArrayList<Task> taskGroup)
    {
        double sum = 0;
        for (Task task : taskGroup) {
            sum += task.getMemoryUsage();
        }
        return String.format("Memory: %.2f%%", sum);
    }

    public String getCpuUsage(ArrayList<Task> taskGroup)
    {
        double sum = 0;
        for (Task task : taskGroup) {
            sum += task.getCpuUsage();
        }
        return String.format("CPU: %.2f%%", sum);
    }

    public void refreshTasks()
    {
        JPanel tasksPanel = this.mainFrame.getTasksPanel();
        JScrollPane scroller = this.mainFrame.getScroller();
        this.scrollPoint = scroller.getViewport().getViewPosition();

        tasksPanel.removeAll();
        Components.panelRefresh(tasksPanel);
        this.repositoryInterface.removeAll();
        this.mainFrame.renderTasks();

        scroller = this.mainFrame.getScroller();
        scroller.getViewport().setViewPosition(this.scrollPoint);
    }

    public void openTaskGroup(ArrayList<Task> taskGroup)
    {
        Controller singleTasksController = new SingleTasksController(
                taskGroup,
                this.repositoryInterface,
                this.taskManager,
                this
        );
        singleTasksController.execute();
    }
}
