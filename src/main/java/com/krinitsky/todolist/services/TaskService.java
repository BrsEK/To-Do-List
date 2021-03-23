package com.krinitsky.todolist.services;

import com.krinitsky.todolist.exceptions.TaskNotFound;
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
    private static final String TASK_NOT_FOUND = "Task not found";


    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }


    public boolean saveTask(Task task) {
        Optional<Task> optionalTask = taskRepository.findByText(task.getText());
        if (optionalTask.isEmpty()) {
            taskRepository.save(task);
            return true;
        }
        return false;
    }

    public Task getTask(Long id) throws TaskNotFound {
        Optional<Task> optionalTask = taskRepository.findById(id);
        return taskRepository.findById(id).orElseThrow(() -> new TaskNotFound(TASK_NOT_FOUND));
    }

    public Task updateTask(Long id, Task task) throws TaskNotFound {
        Task updatedTask = taskRepository.findById(id).orElseThrow(() -> new TaskNotFound(TASK_NOT_FOUND));
        updatedTask.setText(task.getText());
        updatedTask.setCompleted(task.isCompleted());
        taskRepository.save(updatedTask);
        return task;
    }

    public Task removeTask(Long id) throws TaskNotFound {
        return taskRepository.findById(id).map((task) -> {
            taskRepository.deleteById(id);
            return task;
        }).orElseThrow(() -> new TaskNotFound(TASK_NOT_FOUND));
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    /**
     * Method finds tasks by their status
     *
     * @param status status of task (task completed or not completed)
     */
    public List<Task> getTaskByStatus(boolean status) throws TaskNotFound {
        List<Task> tasks = taskRepository.findByIsCompleted(status);
        if (tasks.isEmpty()) {
            throw new TaskNotFound(TASK_NOT_FOUND);
        }
        return tasks;
    }
}
