package com.krinitsky.todolist.controllers;

import com.krinitsky.todolist.models.Task;
import com.krinitsky.todolist.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/tasks/")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping()
    public ResponseEntity<Task> addTask(@RequestBody Task task) {
        if (task == null) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return taskService.saveTask(task);
    }

    @PutMapping("{id}")
    public ResponseEntity<Task> updateTask(@PathVariable("id") Long id, @RequestBody Task task) {
        if (id == null || task == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return taskService.updateTask(id, task);
    }

    @GetMapping("{id}")
    public ResponseEntity<Task> getTask(@PathVariable("id") Long id) {
        if (id == null) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return taskService.getTask(id);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Task> deleteTask(@PathVariable(value = "id") Long id) {
        return taskService.removeTask(id);
    }

    @GetMapping()
    public ResponseEntity<List<Task>> getAll() {
        return taskService.getAllTasks();
    }

    @GetMapping("byStatus/")
    public ResponseEntity<List<Task>>getTaskByStatus(@RequestParam("status") boolean status){
        return taskService.getTaskByStatus(status);
    }

}
