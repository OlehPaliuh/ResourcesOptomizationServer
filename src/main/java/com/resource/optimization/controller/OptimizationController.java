package com.resource.optimization.controller;

import com.resource.optimization.entity.Project;
import com.resource.optimization.entity.ProjectOptimization;
import com.resource.optimization.service.ProjectOptimizationService;
import com.resource.optimization.service.ProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/user/projects")
public class OptimizationController {

    private final ProjectService projectService;
    private final ProjectOptimizationService projectOptimizationService;

    public OptimizationController(ProjectService projectService, ProjectOptimizationService projectOptimizationService) {
        this.projectService = projectService;
        this.projectOptimizationService = projectOptimizationService;
    }

    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @GetMapping(path = "/{projectId}/optimizations", produces = "application/json")
    public @ResponseBody
    ResponseEntity<ProjectOptimization> getProjectOptimizationByProjectId(@PathVariable Long projectId) {
        return new ResponseEntity<>(projectOptimizationService.getProjectOptimizationByProjectId(projectId), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @PostMapping(path = "/{projectId}/optimize", produces = "application/json")
    public @ResponseBody
    ResponseEntity<ProjectOptimization> optimizeProject(@PathVariable Long projectId) {
        return new ResponseEntity<>(projectService.optimizeProject(projectId), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @DeleteMapping(path = "/{projectId}/optimizations")
    public @ResponseBody
    void deleteProjectOptimizations(@PathVariable Long projectId) {
        projectOptimizationService.deleteProjectOptimizationsByProjectId(projectId);
    }
}
