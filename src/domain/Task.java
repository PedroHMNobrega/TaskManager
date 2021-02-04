package domain;

import java.util.Objects;

public class Task
{
    private final String pid;
    private final String user;
    private final String name;
    private final double cpuUsage;
    private final double memoryUsage;
    private final String start;
    private final String time;

    public Task(String user, String name, String pid, double cpuUsage, double memoryUsage, String start, String time)
    {
        this.user = user;
        this.name = name;
        this.pid = pid;
        this.cpuUsage = cpuUsage;
        this.memoryUsage = memoryUsage;
        this.start = start;
        this.time = time;
    }

    public String getPid()
    {
        return pid;
    }

    public double getCpuUsage()
    {
        return cpuUsage;
    }

    public double getMemoryUsage()
    {
        return memoryUsage;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof Task)) return false;
        Task task = (Task) o;
        return pid.equals(task.pid);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(pid);
    }

    public String getName()
    {
        return name;
    }
}
