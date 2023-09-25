package com.tsg.restfulservice.dao.mappers;

import com.tsg.restfulservice.model.Task;
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
}
