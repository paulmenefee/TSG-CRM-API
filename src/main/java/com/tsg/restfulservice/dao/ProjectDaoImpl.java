package com.tsg.restfulservice.dao;

import com.tsg.restfulservice.model.Project;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ProjectDaoImpl implements ProjectDAO {

    private JdbcTemplate jdbcTemplate;

    public ProjectDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public Project addProject(Project project) {
        return null;
    }

    @Override
    public List<Project> getAllProjects() {
        String sql = "Select * from Project";
        List<Project> projectList = jdbcTemplate.query(sql, new ProjectMapper());
        return projectList;
    }

    @Override
    public Project getProjectById(String id) {
        return null;
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
            Project project = new Project()
            return null;
        }
    }
}
