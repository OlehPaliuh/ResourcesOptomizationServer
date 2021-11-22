package com.resource.optimization.repository;

import com.resource.optimization.entity.ProjectOptimization;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProjectOptimizationRepository extends JpaRepository<ProjectOptimization, Long> {

    Optional<List<ProjectOptimization>> findAllByProjectId(Long projectId);

    Optional<ProjectOptimization> findByProjectId(Long projectId);
}
