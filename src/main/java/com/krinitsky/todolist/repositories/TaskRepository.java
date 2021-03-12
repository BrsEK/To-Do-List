package com.krinitsky.todolist.repositories;


import com.krinitsky.todolist.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {
    Optional<Task>findByText(String text);
    List<Task>findByIsCompleted(boolean isCompleted);
}
