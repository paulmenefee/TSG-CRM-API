package com.tsg.restfulservice.controller;

import com.tsg.restfulservice.dao.TaskDAO;
import com.tsg.restfulservice.model.ProjectHours;
import com.tsg.restfulservice.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/tsg")
@CrossOrigin
public class TaskController {

    @Autowired
    public TaskDAO taskDAO;

    public TaskController(TaskDAO taskDAO) {
        this.taskDAO = taskDAO;
    }

    @GetMapping("/tasks")
    public ResponseEntity<List<Task>> getTasks() {
        List<Task> taskList = taskDAO.getAllTasks();
        return ResponseEntity.status(HttpStatus.OK).body(taskList);
    }

    @GetMapping("/task/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable int id) {
        List<Task> task = taskDAO.getTaskById(id);
        if(task.isEmpty()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity(task, HttpStatus.OK);
        }
    }

    @PostMapping("/task")
    public ResponseEntity<Task> addTask(@RequestBody Task task) {
        try {
            taskDAO.addTask(task);
            return new ResponseEntity(HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/task/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable int id) {
        taskDAO.deleteTaskById(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping("/task/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable int id, @RequestBody Task task) {
        Task updatedTask = taskDAO.updateTaskById(id, task);
        return new ResponseEntity(updatedTask, HttpStatus.OK);
    }

    // ********** Reporting urls **********
    @GetMapping("/totalhours/{id}")
    public ResponseEntity<Task> getTotalHours(@PathVariable String id) {
        float task = taskDAO.GetTotalHoursByProject(id);
        if(task == 0) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity(task, HttpStatus.OK);
        }
    }

    @GetMapping("/project/totalhours")
    public ResponseEntity<List<ProjectHours>> getTotalHours() {
        List<ProjectHours> projectHours = taskDAO.GetProjectTotalHours();
        if(projectHours.isEmpty()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity(projectHours, HttpStatus.OK);
        }
    }
    @GetMapping("/currenttasks/{id}")
    public ResponseEntity<List<Task>> currentTasks(@PathVariable int id) {
        List<Task> currentTasks = taskDAO.getCurrentTasksForWorker(id);
        if(currentTasks.isEmpty()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity(currentTasks, HttpStatus.OK);
        }
    }

    @GetMapping("/task/resolved/{id}")
    public ResponseEntity<List<Task>> resolvedTasks(@PathVariable String id) {
        List<Task> currentTasks = taskDAO.getResolvedTasks(id);
        if(currentTasks.isEmpty()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity(currentTasks, HttpStatus.OK);
        }
    }

    @GetMapping("/task/unresolved/{id}")
    public ResponseEntity<List<Task>> currentTasks(@PathVariable String id) {
        List<Task> currentTasks = taskDAO.getUnresolvedTasks(id);
        if(currentTasks.isEmpty()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity(currentTasks, HttpStatus.OK);
        }
    }
}
