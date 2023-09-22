package com.tsg.restfulservice.dao;

import com.tsg.restfulservice.model.Worker;

import java.util.List;

public interface WorkerDAO {

    Worker addWorker(Worker worker);

    List<Worker> getAllWorkers();

    List<Worker> getWorkerById(int id);

    Worker updateWorkerById(int id, Worker worker);

    void deleteWorkerById(int id);
}
