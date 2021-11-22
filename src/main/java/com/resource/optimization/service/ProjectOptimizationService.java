package com.resource.optimization.service;

import com.resource.optimization.entity.ProjectOptimization;
import com.resource.optimization.exception.ProjectNotFoundException;
import com.resource.optimization.repository.ProjectOptimizationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectOptimizationService {

    private final ProjectOptimizationRepository projectOptimizationRepository;

    public ProjectOptimizationService(ProjectOptimizationRepository projectOptimizationRepository) {
        this.projectOptimizationRepository = projectOptimizationRepository;
    }

    public List<ProjectOptimization> getAllProjectOptimizations() {
        return projectOptimizationRepository.findAll();
    }

    public ProjectOptimization getProjectOptimizationById(Long projectOptimizationId) {
        return projectOptimizationRepository.findById(projectOptimizationId)
                .orElseThrow(() -> new ProjectNotFoundException(String.format("Project Optimization with id %s not found", projectOptimizationId)));
    }

    public ProjectOptimization getProjectOptimizationByProjectId(Long projectId) {
        return projectOptimizationRepository.findByProjectId(projectId)
                .orElseThrow(() -> new ProjectNotFoundException(String.format("Project Optimization with project id %s not found", projectId)));
    }

    public List<ProjectOptimization> getAllProjectOptimizationByProjectId(Long projectId) {
        return projectOptimizationRepository.findAllByProjectId(projectId)
                .orElseThrow(() -> new ProjectNotFoundException(String.format("Project Optimizations with project id %s not found", projectId)));
    }

    public ProjectOptimization createProjectOptimization(ProjectOptimization projectOptimization) {
        System.out.println("projectOptimization: " + projectOptimization.toString());
        return projectOptimizationRepository.save(projectOptimization);
    }

    public void deleteProjectOptimization(Long projectOptimizationId) {
        ProjectOptimization projectOptimization = getProjectOptimizationById(projectOptimizationId);

//        projectOptimization.getPhaseOptimizationItems().stream().map(phaseOptimizationItem -> {
//            phaseOptimizationItem.getOptimizationItems().stream().map(optimizationItem -> {
//                optimizationItemService.delete()
//            })
//        })

        projectOptimizationRepository.delete(projectOptimization);
    }

    public void deleteProjectOptimizationsByProjectId(Long projectId) {
        List<ProjectOptimization> projectOptimizations = getAllProjectOptimizationByProjectId(projectId);
        projectOptimizationRepository.deleteAll(projectOptimizations);
    }
}