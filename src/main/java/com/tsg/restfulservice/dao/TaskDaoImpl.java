package com.tsg.restfulservice.dao;

import com.tsg.restfulservice.model.Task;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TaskDaoImpl implements TaskDAO {

    @Override
    public Task addTask(Task task) {
        return null;
    }

    @Override
    public List<Task> getAllTasks() {
        return null;
    }

    @Override
    public Task getTaskById(int id) {
        return null;
    }

    @Override
    public Task updateTaskById(int id, Task task) {
        return null;
    }

    @Override
    public void deleteTaskById(int id) {

    }
}
