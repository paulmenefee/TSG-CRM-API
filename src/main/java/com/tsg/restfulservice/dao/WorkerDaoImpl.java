package com.tsg.restfulservice.dao;

import com.tsg.restfulservice.model.Worker;
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
    public Worker getWorkerById(int id) {
        String sql = "select * from worker where workerId = ?";
        Worker worker = jdbcTemplate.queryForObject(sql, new WorkerMapper(), id);
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
