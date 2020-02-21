package com.depromeet.todo.domain.task;

import com.depromeet.todo.domain.room.Room;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskResults {

    private Map<Room, List<TaskCount>> rooms = new HashMap<>();

    public TaskResults() {
    }

    public void add(Room room, List<TaskCount> taskCounts) {
        rooms.put(room, taskCounts);
    }

    public long getTotalTasks() {
        return rooms.values()
                    .stream()
                    .flatMap(Collection::stream)
                    .mapToLong(TaskCount::getCountOfTasks)
                    .sum();
    }

    public long getTotalTasks(TaskState taskState) {
        return rooms.values()
                    .stream()
                    .flatMap(Collection::stream)
                    .filter(taskCount -> taskCount.equalsTaskState(taskState))
                    .mapToLong(TaskCount::getCountOfTasks)
                    .sum();
    }
}