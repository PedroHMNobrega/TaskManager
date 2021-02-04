package infra.adapters;

import domain.Task;
import domain.TaskGroups;
import application.TaskManagerAdapter;
import domain.TaskRepositoryInterface;
import domain.exceptions.CommandErrorException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class TaskManagerAdapterLinux extends TaskManagerAdapter
{
    public TaskManagerAdapterLinux(TaskRepositoryInterface taskRepository)
    {
        super(taskRepository);
    }

    @Override
    public void kill(Task task) throws CommandErrorException
    {
        executeCommand("kill -KILL " + task.getPid());
    }

    @Override
    public void killAll(String taskName) throws CommandErrorException
    {
        executeCommand("killall " + taskName);
    }

    @Override
    public TaskGroups getTasks() throws CommandErrorException, IOException
    {
        InputStream response = executeCommand("ps -axuce --sort -size");
        super.taskRepository.addFromArray(getTasksFromResponse(response));

        return super.taskRepository.getTaskGroups();
    }

    @Override
    protected InputStream executeCommand(String command) throws CommandErrorException
    {
        try {
            Process process = Runtime.getRuntime().exec(command);
            process.waitFor();
            return process.getInputStream();
        } catch (Exception e) {
            throw new CommandErrorException("Error Executing command");
        }
    }

    @Override
    protected ArrayList<Task> getTasksFromResponse(InputStream response) throws IOException
    {
        BufferedReader stdOutput = new BufferedReader(new InputStreamReader(response));
        ArrayList<Task> tasks = new ArrayList<>();
        String s;

        stdOutput.readLine();
        while((s = stdOutput.readLine()) != null) {
            String[] taskRaw = s.split("\\s+");
            tasks.add(new Task(
                    taskRaw[0],
                    taskRaw[10],
                    taskRaw[1],
                    Double.parseDouble(taskRaw[2]),
                    Double.parseDouble(taskRaw[3]),
                    taskRaw[8],
                    taskRaw[9]
            ));
        }

        return tasks;
    }
}
