package com.tsg.restfulservice.dao;

import com.tsg.restfulservice.model.ProjectWorker;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ProjectWorkerDaoImpl implements ProjectWorkerDAO {

    private JdbcTemplate jdbcTemplate;

    public ProjectWorkerDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addProjectWorker(ProjectWorker projectWorker) {
        String sql = "Insert into projectWorker(projectId, workerId) " +
                "values (?, ?)";
        jdbcTemplate.update(sql, projectWorker.getProjectId(), projectWorker.getWorkerId());
    }

    @Override
    public void addProjectWorker(String ProjectId, int workerId) {
        String sql = "Insert into projectWorker(projectId, workerId) " +
                "values (?, ?)";
        jdbcTemplate.update(sql, ProjectId, workerId);
    }

    @Override
    public List<ProjectWorker> getProjectWorker(String projectId, int workerId) {
        String sql = "Select * from projectWorker where projectId = ? and workerId = ?";
        List<ProjectWorker> projectWorker = jdbcTemplate.query(sql, new ProjectWorkerMapper(), projectId, workerId);
        return projectWorker;
    }

    @Override
    public void deleteProjectWorker(ProjectWorker projectWorker) {
        String sql = "Delete from projectWorker where projectId = ? and workerId = ?";
        jdbcTemplate.update(sql, projectWorker.getProjectId(), projectWorker.getWorkerId());
    }

    public class ProjectWorkerMapper implements RowMapper<ProjectWorker> {

        @Override
        public ProjectWorker mapRow(ResultSet rs, int index) throws SQLException {
            ProjectWorker projectWorker = new ProjectWorker();
            projectWorker.setProjectId(rs.getString("ProjectId"));
            projectWorker.setWorkerId(rs.getInt("WorkerId"));
            return projectWorker;
        }
    }
}
