package com.tsg.restfulservice.dao;

import com.tsg.restfulservice.model.Project;
import com.tsg.restfulservice.model.Task;

import java.util.List;

public interface TaskDAO {

    Task addTask(Task task);

    List<Task> getAllTasks();

    Task getTaskById(int id);

    Task updateTaskById(int id, Task task);

    void deleteTaskById(int id);
}
