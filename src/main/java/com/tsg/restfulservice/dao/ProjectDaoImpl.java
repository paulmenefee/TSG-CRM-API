package com.tsg.restfulservice.dao;

import com.tsg.restfulservice.model.Project;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import java.sql.*;
import java.util.List;
import java.util.Objects;

@Repository
public class ProjectDaoImpl implements ProjectDAO {

    private JdbcTemplate jdbcTemplate;

    public ProjectDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Project addProject(Project project) {
        String sql = "insert into " +
                "Project(ProjectId, ProjectName, ClientId, ProjectSummary, ProjectDueDate, ProjectIsActive) " +
                "Values(?,?,?,?,?,?)";

        jdbcTemplate.update(sql, project.getProjectId(), project.getProjectName(),
            project.getClientId(), project.getSummary(),
            project.getDueDate(), project.isActive());
        return project;
    }

    @Override
    public List<Project> getAllProjects() {
        String sql = "Select * from Project";
        List<Project> projectList = jdbcTemplate.query(sql, new ProjectMapper());
        return projectList;
    }

    @Override
    public Project getProjectById(String id) {
        String sql = "Select * from project where projectId = ?";
        Project project = jdbcTemplate.queryForObject(sql, new ProjectMapper(), id);
        return project;
    }

    @Override
    public Project updateProjectById(String id, Project project) {
        return null;
    }

    @Override
    public void deleteProjectById(String id) {

    }

    public class ProjectMapper implements RowMapper<Project> {

        @Override
        public Project mapRow(ResultSet rs, int index) throws SQLException {
            Project project = new Project();
            project.setProjectId(rs.getString("ProjectId"));
            project.setProjectName(rs.getString("ProjectName"));
            project.setClientId(rs.getInt("ClientId"));
            project.setSummary(rs.getString("ProjectSummary"));
            project.setDueDate(rs.getDate("ProjectDueDate"));
            project.setActive(rs.getBoolean("ProjectIsActive"));
            return project;
        }
    }
}
