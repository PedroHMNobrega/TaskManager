package domain;

import domain.comparators.CompareByMemoryUsage;
import domain.comparators.TaskGroupsComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class TaskGroups
{
    private HashMap<String, ArrayList<Task>> taskGroups;

    public TaskGroups() {
        this.taskGroups = new HashMap<>();
    }

    public void add(String key, Task value) {
        taskGroups.putIfAbsent(key, new ArrayList<>());
        taskGroups.get(key).add(value);
    }

    public ArrayList<ArrayList<Task>> getTaskGroupsSorted()
    {
        ArrayList<ArrayList<Task>> tasksSorted = new ArrayList<>(taskGroups.values());
        TaskGroupsComparator comparator = new CompareByMemoryUsage();
        Collections.sort(tasksSorted, comparator);

        return tasksSorted;
    }
}
