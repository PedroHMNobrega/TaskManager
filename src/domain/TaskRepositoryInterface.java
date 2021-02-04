package domain;

import java.util.ArrayList;

public interface TaskRepositoryInterface
{
    public void add(Task task);
    public void addFromArray(ArrayList<Task> tasks);
    public void removeAll();
    public void remove(Task task);
    public TaskGroups getTaskGroups();
}
