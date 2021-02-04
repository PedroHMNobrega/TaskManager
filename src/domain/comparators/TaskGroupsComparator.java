package domain.comparators;

import domain.Task;

import java.util.ArrayList;
import java.util.Comparator;

public interface TaskGroupsComparator extends Comparator
{
    @Override
    int compare(Object group1, Object group2);
    Double getTasksSum(ArrayList<Task> tasks);
}
