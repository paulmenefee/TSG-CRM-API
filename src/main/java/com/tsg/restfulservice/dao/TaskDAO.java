package com.tsg.restfulservice.dao;

import com.tsg.restfulservice.model.Project;
import com.tsg.restfulservice.model.ProjectHours;
import com.tsg.restfulservice.model.Task;

import java.util.List;

public interface TaskDAO {

    Task addTask(Task task);

    List<Task> getAllTasks();

    List<Task> getTaskList();  // Use for display on main page replace ids

    List<Task> getTaskById(int id);

    Task updateTaskById(int id, Task task);

    void deleteTaskById(int id);

    //Reporting totals

    public float GetTotalHoursByProject(String projectId);

    public List<ProjectHours> GetProjectTotalHours();

    List<Task> getCurrentTasksForWorker(int workerId);

    List<Task> getResolvedTasks(String projectId);

    List<Task> getUnresolvedTasks(String projectId);

}
