package com.krinitsky.todolist.repositories;


import com.krinitsky.todolist.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
