package com.tsg.restfulservice.dao;

import com.tsg.restfulservice.model.Project;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.sql.*;
import java.util.List;

@Repository
public class ProjectDaoImpl implements ProjectDAO {

    private JdbcTemplate jdbcTemplate;

    public ProjectDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Project addProject(Project project) {
        // ProjectId must NOT contain any spaces.
        Project checkedProject = project.CheckProjectID(project);
        String sql = "insert into " +
                "Project(ProjectId, ProjectName, ClientId, ProjectSummary, ProjectDueDate, ProjectIsActive) " +
                "Values(?,?,?,?,?,?)";

        jdbcTemplate.update(sql, checkedProject.getProjectId(), checkedProject.getProjectName(),
                checkedProject.getClientId(), checkedProject.getSummary(),
                checkedProject.getDueDate(), checkedProject.isActive());
        return checkedProject;
    }

    @Override
    public List<Project> getAllProjects() {
        String sql = "Select * from Project";
        List<Project> projectList = jdbcTemplate.query(sql, new ProjectMapper());
        return projectList;
    }

    @Override
    public List<Project> getProjectById(String id) {
        String sql = "Select * from project where projectId = ?";
        List<Project> project = jdbcTemplate.query(sql, new ProjectMapper(), id);
        return project;
    }

    @Override
    public Project updateProjectById(String id, Project project) {
        String sql = "update project set ProjectId = ?, ProjectName = ?, " +
                "ClientId = ?, " +
                "ProjectSummary = ?, " +
                "ProjectDueDate = ?, " +
                "ProjectIsActive = ? " +
                "where ProjectId = ? ";
        jdbcTemplate.update(sql, project.getProjectId(),
                project.getProjectName(), project.getClientId(),
                project.getSummary(), project.getDueDate(),
                project.isActive(), project.getProjectId());
        return project;
    }

    @Override
    public void deleteProjectById(String id) {
        String sql = "Delete from project where projectId = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public List<Project> getProjectsByClient(int id) {
        String sql = "Select * from project where clientId = ?";
        List<Project> projectList = jdbcTemplate.query(sql, new ProjectDaoImpl.ProjectMapper(), id);
        return projectList;
    }


    public static class ProjectMapper implements RowMapper<Project> {

        @Override
        public Project mapRow(ResultSet rs, int index) throws SQLException {
            Project project = new Project();
            project.setProjectId(rs.getString("ProjectId"));
            project.setProjectName(rs.getString("ProjectName"));
            project.setClientId(rs.getInt("ClientId"));
            project.setSummary(rs.getString("ProjectSummary"));
            project.setDueDate(rs.getDate("ProjectDueDate").toLocalDate());
            project.setActive(rs.getBoolean("ProjectIsActive"));
            return project;
        }
    }
}
