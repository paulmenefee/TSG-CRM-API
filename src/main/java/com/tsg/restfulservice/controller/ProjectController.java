package com.tsg.restfulservice.controller;

import com.tsg.restfulservice.dao.ProjectDAO;
import com.tsg.restfulservice.model.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
