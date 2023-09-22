package com.tsg.restfulservice.controller;

import com.tsg.restfulservice.dao.WorkerDAO;
import com.tsg.restfulservice.model.Worker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tsg")
@CrossOrigin
public class WorkerController {
    @Autowired
    WorkerDAO workerDAO;

    public WorkerController(WorkerDAO workerDAO) {
        this.workerDAO = workerDAO;
    }

    @GetMapping("/workers")
    public ResponseEntity<List<Worker>> getWorkers() {
        List<Worker> workerList = workerDAO.getAllWorkers();
        return ResponseEntity.status(HttpStatus.OK).body(workerList);
    }

    @GetMapping("/worker/{id}")
    public ResponseEntity<Worker> getWorkerById(@PathVariable int id) {
        List<Worker> worker = workerDAO.getWorkerById(id);
        if(worker.isEmpty()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity(worker, HttpStatus.OK);
        }
    }

    @PostMapping("/worker")
    public ResponseEntity<String> addWorker(@RequestBody Worker worker) {
        try {
            workerDAO.addWorker(worker);
            return ResponseEntity.status(HttpStatus.CREATED).body("New Worker added");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add worker");
        }
    }

    @DeleteMapping("/worker/{id}")
    public ResponseEntity<String> deleteWorker(@PathVariable int id) {
        workerDAO.deleteWorkerById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Worker " + id + " deleted");
    }

    @PutMapping("/worker/{id}")
    public ResponseEntity<Worker> updateWorker(@PathVariable int id, @RequestBody Worker worker) {
        Worker updatedWorker = workerDAO.updateWorkerById(id, worker);
        return new ResponseEntity(updatedWorker, HttpStatus.OK);
    }

}
