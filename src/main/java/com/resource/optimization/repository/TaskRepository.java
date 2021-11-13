package com.resource.optimization.repository;

import com.resource.optimization.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByName(String name);

    List<Task> findAll();
}
