package com.tsg.restfulservice.controller;

import com.tsg.restfulservice.dao.ProjectWorkerDAO;
import com.tsg.restfulservice.model.ProjectWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tsg")
@CrossOrigin
public class ProjectWorkerController {

    @Autowired
    public ProjectWorkerDAO projectWorkerDAO;

    public ProjectWorkerController(ProjectWorkerDAO projectWorkerDAO) {
        this.projectWorkerDAO = projectWorkerDAO;
    }

    @PostMapping("/projectworker")
    public ResponseEntity<String> addProjectWorker(@RequestBody ProjectWorker projectWorker) {
        try {
            projectWorkerDAO.addProjectWorker(projectWorker);
            return ResponseEntity.status(HttpStatus.CREATED).body("New Project Created");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add project worker");
        }
    }

    @GetMapping("/project/{projectId}/worker/{workerId}")
    public ResponseEntity<List<ProjectWorker>> getProjectWorker(@PathVariable String projectId, @PathVariable int workerId) {
        List<ProjectWorker> projectWorker = projectWorkerDAO.getProjectWorker(projectId, workerId);
        if (projectWorker.isEmpty()) {
            return new ResponseEntity(projectWorker, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity(projectWorker, HttpStatus.OK);
        }
    }

    @DeleteMapping("/projectworker")
    public ResponseEntity<String> deleteProjectWorker(@RequestBody ProjectWorker projectWorker) {
        projectWorkerDAO.deleteProjectWorker(projectWorker);
        return ResponseEntity.status(HttpStatus.OK).body
                ("WorkerID: " + projectWorker.getWorkerId() +
                        " deleted from projectID: " + projectWorker.getProjectId());
    }
}
