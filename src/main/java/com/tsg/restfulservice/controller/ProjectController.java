package com.tsg.restfulservice.controller;

import com.tsg.restfulservice.dao.ProjectDAO;
import com.tsg.restfulservice.model.ClientProject;
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
        List<Project> project = projectDAO.getProjectById(id);
        if(project.isEmpty()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity(project, HttpStatus.OK);
        }
    }

    @PostMapping("/project")
    public ResponseEntity<Project> addProject(@RequestBody Project project) {
        try {
            projectDAO.addProject(project);
            return new ResponseEntity(HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/project/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable String id) {
        projectDAO.deleteProjectById(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping("/project/{id}")
    public ResponseEntity<Project> updateProject(@PathVariable String id, @RequestBody Project project) {
        Project updatedProject = projectDAO.updateProjectById(id, project);
        return new ResponseEntity(updatedProject, HttpStatus.OK);
    }

    @GetMapping("/client/projects/{id}")
    public ResponseEntity<List<Project>> getProjectsByClient(@PathVariable int id) {
        List<Project> project = projectDAO.getProjectsByClient(id);
        if(project.isEmpty()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity(project, HttpStatus.OK);
        }
    }

    @GetMapping("/client/projects")
    public ResponseEntity<List<ClientProject>> getClientProjects() {
        List<ClientProject> clientProjects = projectDAO.getClientProjects();
        if(clientProjects.isEmpty()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity(clientProjects, HttpStatus.OK);
        }
    }

}
