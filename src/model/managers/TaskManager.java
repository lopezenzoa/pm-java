package model.managers;

import model.Task;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.UUID;

public class TaskManager {
    private LinkedList<Task> tasks;

    public TaskManager() {
        this.tasks = new LinkedList<>();
    }

    /**
     * Returns a collection of tasks IDs.
     * @return a HashSet made of tasks IDs.
     * */
    public HashSet<UUID> getTasksIDs() {
        HashSet<UUID> taskIDs = new HashSet<>();

        for (Task task : tasks)
            taskIDs.add(task.getID());

        return taskIDs;
    }

    /**
     * Searches a task in the list of project's tasks.
     * @param ID is the ID of the task that want to search.
     * @return a Task object if the ID corresponds with a task or null otherwise.
     * */
    private Task searchTaskByID(UUID ID) {
        for (Task task : tasks)
            if (task.getID().equals(ID))
                return task;
        return null;
    }

    /**
     * Returns a collection of tasks titles.
     * @return a HashSet made of tasks titles.
     * */
    public HashSet<String> getTasksTitles() {
        HashSet<String> titles = new HashSet<>();

        for (Task task : tasks)
            titles.add(task.getTitle());

        return titles;
    }

    /**
     * Addes a task to the list of project's tasks.
     * @param task is the object that want to add.
     * @return a boolean value depending on if the task could be added or not.
     * */
    public boolean addTask(Task task) {
        if (!tasks.contains(task))
            return tasks.add(task);
        return false;
    }

    /**
     * Removes a task from the list of project's tasks.
     * @param task is the object that want to remove.
     * @return a boolean value depending on if the task could be removed or not.
     * */
    public boolean removeTask(Task task) {
        return tasks.remove(task);
    }

    /**
     * Removes a task from the list of project's tasks given its ID.
     * @param ID is the ID of the task that want to remove.
     * @return a boolean value depending on if the task could be removed or not.
     * */
    public boolean removeTask(UUID ID) {
        Task toDelete = searchTaskByID(ID);

        if (toDelete != null)
            return tasks.remove(toDelete);

        return false;
    }

    /**
     * Checks if a task exists in the list of project's tasks.
     * @param task is the object that want to check its existence.
     * @return a boolean value depending on if the task exists or not.
     * */
    public boolean checkTask(Task task) {
        return tasks.contains(task);
    }

    /**
     * Checks if a task exists in the list of project's tasks given its ID.
     * @param ID is the ID of the task that want to check its existence.
     * @return a boolean value depending on if the task exists or not.
     * */
    public boolean checkTask(UUID ID) {
        return searchTaskByID(ID) != null;
    }
}
