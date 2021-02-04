package infra.database;

import domain.Task;
import domain.TaskGroups;
import domain.TaskRepositoryInterface;

import java.util.ArrayList;

public class TaskRepositoryMemory implements TaskRepositoryInterface
{
    private ArrayList<Task> tasks;

    public TaskRepositoryMemory()
    {
        this.tasks = new ArrayList<>();
    }

    @Override
    public void add(Task task)
    {
        tasks.add(task);
    }

    @Override
    public void addFromArray(ArrayList<Task> tasksToAdd)
    {
        this.tasks.addAll(tasksToAdd);
    }

    @Override
    public void removeAll()
    {
        tasks = new ArrayList<>();
    }

    @Override
    public void remove(Task task)
    {
        tasks.remove(task);
    }

    @Override
    public TaskGroups getTaskGroups()
    {
        TaskGroups taskGroups = new TaskGroups();
        for (Task task : tasks) {
            taskGroups.add(task.getName(), task);
        }
        return taskGroups;
    }
}
