package com.tsg.restfulservice.dao;

import com.tsg.restfulservice.dao.mappers.Mappers;
import com.tsg.restfulservice.model.Worker;
import com.tsg.restfulservice.model.WorkerByProject;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;

@Repository
public class WorkerDaoImpl implements WorkerDAO {

    private JdbcTemplate jdbcTemplate;

    public WorkerDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Worker addWorker(Worker worker) {
        String sql = "Insert into " +
                "Worker(WorkerId, WorkerFirstName, WorkerLastName, WorkerEmail) " +
                "values(?,?,?,?)";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update((Connection conn) -> {
            PreparedStatement statement = conn.prepareStatement(
                    sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, worker.getWorkerId());
            statement.setString(2, worker.getWorkerFirstName());
            statement.setString(3, worker.getWorkerLastName());
            statement.setString(4, worker.getWorkerEmail());
            return statement;
        }, keyHolder);
        worker.setWorkerId(keyHolder.getKey().intValue());

        return worker;
    }

    @Override
    public List<Worker> getAllWorkers() {
        String sql = "Select * from worker";
        List<Worker> workerList = jdbcTemplate.query(sql, new WorkerMapper());
        return workerList;
    }

    @Override
    public List<Worker> getWorkerById(int id) {
        String sql = "select * from worker where workerId = ?";
        List<Worker> worker = jdbcTemplate.query(sql, new WorkerMapper(), id);
        return worker;
    }

    @Override
    public Worker updateWorkerById(int id, Worker worker) {
        String sql = "Update worker set workerId = ?, " +
                "WorkerFirstName = ?, " +
                "WorkerLastName = ?, " +
                "WorkerEmail = ? " +
                "Where workerId = ?";
        jdbcTemplate.update(sql, worker.getWorkerId(),
                worker.getWorkerFirstName(), worker.getWorkerLastName(),
                worker.getWorkerEmail(), worker.getWorkerId());
        return worker;
    }

    @Override
    public void deleteWorkerById(int id) {
        String sql = "Delete from Worker where workerId = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public List<Worker> getWorkerByProject(String projectId) {
        String sql = "SELECT * " +
                "FROM worker w " +
                "join projectWorker pw on pw.WorkerId = w.WorkerId " +
                "where projectId = ?";
        List<Worker> workerList = jdbcTemplate.query(sql, new WorkerMapper(), projectId);
        return workerList;
    }

    @Override
    public List<WorkerByProject> projectWorkerList() {
        String sql = "select p.projectId, w.workerFirstName, w.workerLastname, w.workerEmail " +
                "from project p " +
                "join projectWorker pw on p.projectId = pw.projectId " +
                "join worker w on pw.workerId = w.workerId;";
        List<WorkerByProject> workersByProject = jdbcTemplate.query(sql, new Mappers.WorkerByProjectMapper());

        return workersByProject;
    }

    public class WorkerMapper implements RowMapper<Worker> {

        @Override
        public Worker mapRow(ResultSet rs, int index) throws SQLException {
            Worker worker = new Worker();
            worker.setWorkerId(rs.getInt("WorkerId"));
            worker.setWorkerFirstName(rs.getString("WorkerFirstName"));
            worker.setWorkerLastName(rs.getString("WorkerLastName"));
            worker.setWorkerEmail(rs.getString("WorkerEmail"));
            return worker;
        }
    }
}
