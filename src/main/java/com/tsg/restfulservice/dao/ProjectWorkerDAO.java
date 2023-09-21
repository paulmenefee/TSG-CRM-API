package com.tsg.restfulservice.dao;

import com.tsg.restfulservice.model.Client;
import com.tsg.restfulservice.model.ProjectWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tsg")
@CrossOrigin
public interface ProjectWorkerDAO {

    void addProjectWorker(ProjectWorker projectWorker);

    ProjectWorker getProjectWorker(String ProjectId, int projectId);

    void deleteProjectWorker(ProjectWorker projectWorker);
}
