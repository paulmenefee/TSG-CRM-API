package com.tsg.restfulservice.dao.mappers;

import com.tsg.restfulservice.model.*;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Mappers {
    public static class TotalHoursMapper implements RowMapper<Float> {
        @Override
        public Float mapRow(ResultSet rs, int index) throws SQLException {
            Task task = new Task();
            task.setProjectId(rs.getString("ProjectId"));
            task.setTaskEstimatedHours(rs.getFloat("TotalHours"));
            return task.getTaskEstimatedHours();
        }
    }

    public static class CurrentTasksMapper implements RowMapper<Task> {

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

    public static class TaskMapper implements RowMapper<Task> {

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

    public static class TaskMapper2 implements RowMapper<Task> {

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
            task.setTaskTypeId(rs.getInt("TaskType"));
            task.setTaskStatusId(rs.getInt("TaskStatus"));
            task.setTaskParentId(rs.getInt("ParentTaskId"));
            return task;
        }
    }

    public static class ClientProjectMapper implements RowMapper<ClientProject> {

        @Override
        public ClientProject mapRow(ResultSet rs, int index) throws SQLException {
            ClientProject clientProject = new ClientProject();
            clientProject.setClientId(rs.getInt("clientId"));
            clientProject.setClientName(rs.getString("clientName"));
            clientProject.setProjectId(rs.getString("projectId"));
            clientProject.setDueDate(rs.getDate("projectDueDate"));

            return clientProject;
        }
    }

    public static class ProjectHoursMapper implements RowMapper<ProjectHours> {

        @Override
        public ProjectHours mapRow(ResultSet rs, int index) throws SQLException {
            ProjectHours projectHours = new ProjectHours();
            projectHours.setProjectId(rs.getString("projectId"));
            projectHours.setTotalHours(rs.getFloat("totalHours"));

            return projectHours;
        }
    }

    public static class WorkerByProjectMapper implements RowMapper<WorkerByProject> {

        @Override
        public WorkerByProject mapRow(ResultSet rs, int index) throws SQLException {
            WorkerByProject projectWorkers = new WorkerByProject();
            projectWorkers.setProjectId(rs.getString("projectId"));
            projectWorkers.setFirstName(rs.getString("workerFirstName"));
            projectWorkers.setLastName(rs.getString("workerLastName"));
            projectWorkers.setEmail(rs.getString("workerEmail"));

            return projectWorkers;
        }
    }

}
