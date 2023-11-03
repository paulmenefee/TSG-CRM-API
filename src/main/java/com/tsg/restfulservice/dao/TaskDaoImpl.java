package com.tsg.restfulservice.dao;

import com.tsg.restfulservice.dao.mappers.Mappers;
import com.tsg.restfulservice.model.Project;
import com.tsg.restfulservice.model.ProjectHours;
import com.tsg.restfulservice.model.ProjectWorker;
import com.tsg.restfulservice.model.Task;
import org.springframework.jdbc.core.JdbcTemplate;
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
        List<Task> taskList = jdbcTemplate.query(sql, new Mappers.TaskMapper());
        return taskList;
    }

    @Override
    public List<Task> getTaskList() {
        String sql = "select " +
                "taskId, taskTitle, taskdetails, taskDueDate, TaskEstimatedHours, ProjectId, " +
                "    w.workerFirstName, w.workerLastName, " +
                "    tt.TaskTypeName as taskType, " +
                "    ts.TaskStatusName as taskStatus, " +
                "    t.ParentTaskId " +
                "from task t " +
                "join worker w on t.workerId = w.WorkerId " +
                "join tasktype tt on t.TaskTypeId = tt.TaskTypeId " +
                "join taskstatus ts on t.TaskStatusId = ts.TaskStatusId;";
        List<Task> taskList = jdbcTemplate.query(sql, new Mappers.TaskMapper2());
        return taskList;
    }

    @Override
    public List<Task> getTaskById(int id) {
        String sql = "Select * from task where taskId = ?";
        List<Task> task = jdbcTemplate.query(sql, new Mappers.TaskMapper(), id);
        return task;
    }

    @Override
    public Task updateTaskById(int id, Task task) {
        String sql = "update task set taskId = ?, TaskTitle = ?, " +
                "TaskDetails = ?, TaskDueDate = ?, " +
                "TaskEstimatedHours = ?, ProjectId = ?, " +
                "WorkerId = ?, TaskTypeId = ?, " +
                "TaskStatusId = ? " +
                "Where taskId = ?";
        System.out.println(sql);
        jdbcTemplate.update(sql, task.getTaskId(), task.getTaskTitle(),
                task.getTaskDetails(), task.getTaskDueDate(),
                task.getTaskEstimatedHours(), task.getProjectId(),
                task.getWorkerId(), task.getTaskTypeId(),
                task.getTaskStatusId(),
                task.getTaskId());
        //UpdateEstimatedHours();
        return task;
    }

    @Override
    public void deleteTaskById(int id) {
        String sql = "Delete from task where taskId = ?";
        jdbcTemplate.update(sql, id);
        UpdateEstimatedHours();
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

    @Override
    public float GetTotalHoursByProject(String projectId) {
        String sql = "select p.projectId, TotalHours " +
                "from project p " +
                "join projectWorker pw on p.ProjectId = pw.ProjectId " +
                "join (" +
                    "select projectId, sum(TaskEstimatedHours) as TotalHours " +
                    "from task " +
                    "where ParentTaskId is null " +
                    "group by projectId " +
                    ") as t on pw.ProjectId = t.ProjectId " +
                "where p.projectId = ? " +
                "group by p.ProjectId;";

        float totalHours = jdbcTemplate.queryForObject(sql, new Mappers.TotalHoursMapper(), projectId);
        return totalHours;
    }

    @Override
    public List<ProjectHours> GetProjectTotalHours() {
        String sql = "select p.projectId, TotalHours " +
                "from project p " +
                "join projectWorker pw on p.ProjectId = pw.ProjectId " +
                "join (" +
                "select projectId, sum(TaskEstimatedHours) as TotalHours " +
                "from task " +
                "where ParentTaskId is null " +
                "group by projectId " +
                ") as t on pw.ProjectId = t.ProjectId " +
                "group by p.ProjectId;";

        List<ProjectHours> totalHoursList = jdbcTemplate.query(sql, new Mappers.ProjectHoursMapper());
        return totalHoursList;
    }

    @Override
    public List<Task> getCurrentTasksForWorker(int workerId) {
        //Current tasks for selected worker
        String sql = "Select * " +
                "from task " +
                "where TaskStatusId in(1,2,3,4) and workerId = ?;";
        List<Task> currentTasks = jdbcTemplate.query(sql, new Mappers.CurrentTasksMapper(), workerId);
        return currentTasks;
    }

    public List<Task> getResolvedTasks(String projectId) {
        String sql = "Select * " +
                "from task " +
                "where TaskStatusId in(5, 6, 7, 8) and " +
                "projectId = ?";
        List<Task> taskList = jdbcTemplate.query(sql, new Mappers.TaskMapper(), projectId);
        return taskList;
    }

    public List<Task> getUnresolvedTasks(String projectId) {
        String sql = "Select * " +
                "from task " +
                "where TaskStatusId in(1, 2, 3, 4) and " +
                "projectId = ?";
        List<Task> taskList = jdbcTemplate.query(sql, new Mappers.TaskMapper(), projectId);
        return taskList;
    }
}
