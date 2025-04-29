package com.kidaro.kael.repository;
import com.kidaro.kael.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
    // Add custom query methods if needed
}
