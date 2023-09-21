package com.tsg.restfulservice.dao;

import com.tsg.restfulservice.model.ProjectWorker;
import com.tsg.restfulservice.model.Task;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;

@Repository
public class TaskDaoImpl implements TaskDAO {

    private JdbcTemplate jdbcTemplate;

    public TaskDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //*****************  Notes *******************
    // Need to add a projectId and workerId to ProjectWorker table
    // Add a ProjectWorker class to app
    // The current addTask is equal to adding a subTask
    //********************************************

    @Override
    public Task addTask(Task task) {

//        String projectId = task.getProjectId();
//        int workerId = task.getWorkerId();
//        addWorkerToProject(projectId, workerId);

        String sql = "INSERT INTO " +
                "Task(TaskTitle, TaskDetails, " +
                "TaskDueDate, TaskEstimatedHours, " +
                "ProjectId, WorkerId, " +
                "TaskTypeId, TaskStatusId, ParentTaskId) " +
                "VALUES(?,?,?,?,?,?,?,?,?)";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update((Connection conn) -> {
            PreparedStatement statement = conn.prepareStatement(
                    sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, task.getTaskTitle());
            statement.setString(2, task.getTaskDetails());
            statement.setDate(3, Date.valueOf(task.getTaskDueDate()));
            statement.setFloat(4, task.getTaskEstimatedHours());
            statement.setString(5, task.getProjectId());
            statement.setInt(6, task.getWorkerId());
            statement.setInt(7, task.getTaskTypeId());
            statement.setInt(8,task.getTaskStatusId());
            statement.setInt(9,task.getTaskParentId());
            return statement;
        }, keyHolder);
        task.setTaskId(keyHolder.getKey().intValue());

        return task;
    }

    @Override
    public List<Task> getAllTasks() {
        String sql = "Select * from task";
        List<Task> taskList = jdbcTemplate.query(sql, new TaskMapper());
        return taskList;
    }

    @Override
    public Task getTaskById(int id) {
        String sql = "Select * from task where taskId = ?";
        Task task = jdbcTemplate.queryForObject(sql, new TaskMapper(), id);
        return task;
    }

    @Override
    public Task updateTaskById(int id, Task task) {
        String sql = "update task set taskId = ?, TaskTitle = ?, " +
                "TaskDetails = ?, TaskDueDate = ?, " +
                "TaskEstimatedHours = ?, ProjectId = ?, " +
                "WorkerId = ?, TaskTypeId = ?, " +
                "TaskStatusId = ?, ParentTaskId = ? " +
                "Where taskId = ?";
        jdbcTemplate.update(sql, task.getTaskId(), task.getTaskTitle(),
                task.getTaskDetails(), task.getTaskDueDate(),
                task.getTaskEstimatedHours(), task.getProjectId(),
                task.getWorkerId(), task.getTaskTypeId(),
                task.getTaskStatusId(), task.getTaskParentId(),
                task.getTaskId());
        return task;
    }

    @Override
    public void deleteTaskById(int id) {
        String sql = "Delete from task where taskId = ?";
        jdbcTemplate.update(sql, id);
    }

    public class TaskMapper implements RowMapper<Task> {

        @Override
        public Task mapRow(ResultSet rs, int index) throws SQLException {
            Task task = new Task();
            task.setTaskId(rs.getInt("TaskId"));
            task.setTaskTitle(rs.getString("TaskTitle"));
            task.setTaskDetails(rs.getString("TaskDetails"));
            task.setTaskDueDate(rs.getDate("TaskDueDate").toLocalDate());
            task.setTaskEstimatedHours(rs.getFloat("TaskEstimatedHours"));
            task.setProjectId(rs.getString("ProjectId"));
            task.setWorkerId(rs.getInt("WorkerId"));
            task.setTaskTypeId(rs.getInt("TaskTypeId"));
            task.setTaskStatusId(rs.getInt("TaskStatusId"));
            task.setTaskParentId(rs.getInt("ParentTaskId"));
            return task;
        }
    }
}
