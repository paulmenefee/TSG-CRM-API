package com.tsg.restfulservice.controller;

import com.tsg.restfulservice.dao.TaskDAO;
import com.tsg.restfulservice.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLIntegrityConstraintViolationException;
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
        Task task = taskDAO.getTaskById(id);
        if(task != null) {
            return new ResponseEntity(task, HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/task")
    public ResponseEntity<String> addTask(@RequestBody Task task) {
        //try {
            taskDAO.addTask(task);
            return ResponseEntity.status(HttpStatus.CREATED).body("New task created");
        //} catch (Exception ex) {
            //return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add task");
        //}
    }

    @DeleteMapping("/task/{id}")
    public ResponseEntity<String> deleteProject(@PathVariable int id) {
        taskDAO.deleteTaskById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Task " + id + " deleted");
    }

    @PutMapping("/task/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable int id, @RequestBody Task task) {
        Task updatedTask = taskDAO.updateTaskById(id, task);
        return new ResponseEntity(updatedTask, HttpStatus.OK);
    }
}
