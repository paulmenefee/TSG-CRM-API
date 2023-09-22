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

    @Override
    public Task addTask(Task task) {
        // Need to add project worker data to ProjectWorker table if it exists
        ProjectWorkerDaoImpl projectWorker = new ProjectWorkerDaoImpl(jdbcTemplate);
        String projectId = task.getProjectId();
        int workerId = task.getWorkerId();

        List<ProjectWorker> pwCheck = projectWorker.getProjectWorker(projectId, workerId);
        if (pwCheck.isEmpty()) {
            projectWorker.addProjectWorker(projectId, workerId);
        }

        // SQL strings for inserting parent tasks and subtasks
        String sqlSubTask = "INSERT INTO " +
                "Task(TaskTitle, TaskDetails, " +
                "TaskDueDate, TaskEstimatedHours, " +
                "ProjectId, WorkerId, " +
                "TaskTypeId, TaskStatusId, ParentTaskId) " +
                "VALUES(?,?,?,?,?,?,?,?,?)";

        String sqlParentTask = "INSERT INTO " +
                "Task(TaskTitle, TaskDetails, " +
                "TaskDueDate, TaskEstimatedHours, " +
                "ProjectId, WorkerId, " +
                "TaskTypeId, TaskStatusId) " +
                "VALUES(?,?,?,?,?,?,?,?)";

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        if(task.getTaskParentId() <= 0) {   // parent task
            jdbcTemplate.update((Connection conn) -> {
                PreparedStatement statement = conn.prepareStatement(
                        sqlParentTask, Statement.RETURN_GENERATED_KEYS);
                statement.setString(1, task.getTaskTitle());
                statement.setString(2, task.getTaskDetails());
                statement.setDate(3, Date.valueOf(task.getTaskDueDate()));
                statement.setFloat(4, task.getTaskEstimatedHours());
                statement.setString(5, task.getProjectId());
                statement.setInt(6, task.getWorkerId());
                statement.setInt(7, task.getTaskTypeId());
                statement.setInt(8,task.getTaskStatusId());
                return statement;
            }, keyHolder);
        } else {
            jdbcTemplate.update((Connection conn) -> {
                PreparedStatement statement = conn.prepareStatement(
                        sqlSubTask, Statement.RETURN_GENERATED_KEYS);
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
        }

        task.setTaskId(keyHolder.getKey().intValue());
        UpdateEstimatedHours();
        return task;
    }

    @Override
    public List<Task> getAllTasks() {
        String sql = "Select * from task";
        List<Task> taskList = jdbcTemplate.query(sql, new TaskMapper());
        return taskList;
    }

    @Override
    public List<Task> getTaskById(int id) {
        String sql = "Select * from task where taskId = ?";
        List<Task> task = jdbcTemplate.query(sql, new TaskMapper(), id);
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

    // Helper function to sum estimated hours for sub tasks
    public void UpdateEstimatedHours() {
        String sql = "update task as t1 " +
                "join (" +
                " Select parentTaskId, sum(TaskEstimatedHours) as TotalHours " +
                "    from task " +
                "    where ParentTaskId is not null " +
                "    group by ParentTaskId " +
                ") as t2 on t1.taskId = t2.parentTaskId " +
                "Set t1.TaskEstimatedHours = t2.TotalHours " +
                "where t1.parentTaskId is null;";
        jdbcTemplate.update(sql);
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
