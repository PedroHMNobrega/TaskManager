package application;

import domain.Task;
import domain.TaskGroups;
import domain.TaskRepositoryInterface;
import domain.exceptions.CommandErrorException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public abstract class TaskManagerAdapter
{
    protected TaskRepositoryInterface taskRepository;

    public TaskManagerAdapter(TaskRepositoryInterface taskRepository)
    {
        this.taskRepository = taskRepository;
    }

    public abstract void kill(Task task) throws CommandErrorException;
    public abstract void killAll(String taskName) throws CommandErrorException;
    public abstract TaskGroups getTasks() throws CommandErrorException, IOException;
    protected abstract InputStream executeCommand(String command) throws CommandErrorException;
    protected abstract ArrayList<Task> getTasksFromResponse(InputStream response) throws IOException;
}
