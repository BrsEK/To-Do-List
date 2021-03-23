package com.krinitsky.todolist.controllers;

import com.krinitsky.todolist.exceptions.TaskNotFound;
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
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (taskService.saveTask(task)) {
            return new ResponseEntity<>(task, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("{id}")
    public ResponseEntity<Task> updateTask(@PathVariable("id") Long id, @RequestBody Task task) throws TaskNotFound {
        if (id == null || task == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Task updatedTask = taskService.updateTask(id, task);
        return new ResponseEntity<>(updatedTask, HttpStatus.NO_CONTENT);
    }

    @GetMapping("{id}")
    public ResponseEntity<Task> getTask(@PathVariable("id") Long id) throws TaskNotFound {
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Task task = taskService.getTask(id);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Task> deleteTask(@PathVariable(value = "id") Long id) throws TaskNotFound {
        Task deletedTask = taskService.removeTask(id);
        return new ResponseEntity<>(deletedTask, HttpStatus.NO_CONTENT);
    }

    @GetMapping()
    public ResponseEntity<List<Task>> getAll() {
        List<Task> tasks = taskService.getAllTasks();
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @GetMapping("byStatus/")
    public ResponseEntity<List<Task>> getTaskByStatus(@RequestParam("status") boolean status) throws TaskNotFound {
        List<Task> tasks = taskService.getTaskByStatus(status);
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

}
