package com.tsg.restfulservice.dao;

import com.tsg.restfulservice.model.Project;

import java.util.List;

public interface ProjectDAO {

    Project addProject(Project project);

    List<Project> getAllProjects();

    Project getProjectById(String id);

    Project updateProjectById(String id, Project project);

    void deleteProjectById(String id);
}
