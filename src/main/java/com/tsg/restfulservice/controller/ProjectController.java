package com.tsg.restfulservice.controller;

import com.tsg.restfulservice.dao.ProjectDAO;
import com.tsg.restfulservice.model.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tsg")
@CrossOrigin
public class ProjectController {

    @Autowired
    public ProjectDAO projectDAO;

    public ProjectController(ProjectDAO projectDAO) {
        this.projectDAO = projectDAO;
    }

    @GetMapping("/projects")
    public ResponseEntity<List<Project>> getProjects() {
        List<Project> projectList = projectDAO.getAllProjects();
        return ResponseEntity.status(HttpStatus.OK).body(projectList);
    }

    @GetMapping("/project/{id}")
    public ResponseEntity<Project> getProjectById(@PathVariable String id) {
        Project project = projectDAO.getProjectById(id);
        if(project != null) {
            return new ResponseEntity<>(project, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/project")
    public ResponseEntity<String> addProject(@RequestBody Project project) {
        try {
            projectDAO.addProject(project);
            return ResponseEntity.status(HttpStatus.CREATED).body("New Project created");
        } catch (Exception ex) {
            return ResponseEntity.status((HttpStatus.INTERNAL_SERVER_ERROR)).body("Failed to add project");
        }
    }

    @DeleteMapping("/project/{id}")
    public ResponseEntity<String> deleteProject(@PathVariable String id) {
        return null;
    }

}
