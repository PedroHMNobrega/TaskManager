package domain.comparators;

import domain.Task;

import java.util.ArrayList;

public class CompareByCpuUsage implements TaskGroupsComparator
{
    @Override
    public int compare(Object group1, Object group2)
    {
        return getTasksSum((ArrayList<Task>) group2).compareTo(getTasksSum((ArrayList<Task>) group1));
    }

    @Override
    public Double getTasksSum(ArrayList<Task> tasks)
    {
        double sum = 0;
        for(Task task : tasks) {
            sum += task.getCpuUsage();
        }
        return sum;
    }
}
