package com.krinitsky.todolist.services;

import com.krinitsky.todolist.models.Task;
import com.krinitsky.todolist.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class TaskService {

    private final TaskRepository taskRepository;


    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }


    public ResponseEntity<Task> saveTask(Task task) {
        Optional<Task> optionalTask = taskRepository.findByText(task.getText());
        if (optionalTask.isEmpty()) {
            taskRepository.save(task);
            return new ResponseEntity<>(task, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<Task> getTask(Long id) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        return optionalTask.map(task -> new ResponseEntity<>(task, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    public ResponseEntity<Task> updateTask(Long id, Task task) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (optionalTask.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Task updatedTask = optionalTask.get();
        updatedTask.setText(task.getText());
        updatedTask.setCompleted(task.isCompleted());
        taskRepository.save(updatedTask);
        return new ResponseEntity<>(updatedTask, HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<Task> removeTask(Long id) {
        if (taskRepository.findById(id).isPresent()) {
            taskRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<List<Task>> getAllTasks() {
        List<Task> tasks = taskRepository.findAll();
        if (tasks.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    /**
     * Method finds tasks by their status
     *
     * @param status status of task (task completed or not completed)
     */
    public ResponseEntity<List<Task>> getTaskByStatus(boolean status) {
        List<Task> tasks = taskRepository.findByIsCompleted(status);
        if (tasks.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }
}
