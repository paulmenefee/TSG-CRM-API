package com.tsg.restfulservice.controller;

import com.tsg.restfulservice.dao.WorkerDAO;
import com.tsg.restfulservice.model.Worker;
import com.tsg.restfulservice.model.WorkerByProject;
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
    public ResponseEntity<Worker> addWorker(@RequestBody Worker worker) {
        workerDAO.addWorker(worker);
        return ResponseEntity.status(HttpStatus.CREATED).body(worker);
    }

    @DeleteMapping("/worker/{id}")
    public ResponseEntity<Void> deleteWorker(@PathVariable int id) {
        workerDAO.deleteWorkerById(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping("/worker/{id}")
    public ResponseEntity<Worker> updateWorker(@PathVariable int id, @RequestBody Worker worker) {
        Worker updatedWorker = workerDAO.updateWorkerById(id, worker);
        return new ResponseEntity(updatedWorker, HttpStatus.OK);
    }

    @GetMapping("/worker/project/{projectId}")
    public ResponseEntity<List<Worker>> getWorkerByProject(@PathVariable String projectId) {
        List<Worker> workerList = workerDAO.getWorkerByProject(projectId);
        if(workerList.isEmpty()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity(workerList, HttpStatus.OK);
        }
    }

    @GetMapping("/project/workers")
    public ResponseEntity<List<WorkerByProject>> getWorkersByProject() {
        List<WorkerByProject> workerByProjectList = workerDAO.projectWorkerList();
        if(workerByProjectList.isEmpty()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity(workerByProjectList, HttpStatus.OK);
        }
    }

}
