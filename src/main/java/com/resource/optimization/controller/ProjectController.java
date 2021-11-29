package com.resource.optimization.controller;

import com.resource.optimization.entity.PhaseOptimizationItem;
import com.resource.optimization.entity.Project;
import com.resource.optimization.entity.ProjectOptimization;
import com.resource.optimization.entity.Task;
import com.resource.optimization.entity.dto.CreateProjectDto;
import com.resource.optimization.service.ProjectOptimizationService;
import com.resource.optimization.service.ProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/api")
public class ProjectController {

    private final ProjectService projectService;
    private final ProjectOptimizationService projectOptimizationService;

    public ProjectController(ProjectService projectService, ProjectOptimizationService projectOptimizationService) {
        this.projectService = projectService;
        this.projectOptimizationService = projectOptimizationService;
    }

    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @GetMapping(path = "/user/projects/{projectId}", produces = "application/json")
    public @ResponseBody
    ResponseEntity<Project> getProjectsById(@PathVariable Long projectId) {
        return new ResponseEntity<>(projectService.getProjectById(projectId), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @GetMapping(path = "/user/projects", produces = "application/json")
    public @ResponseBody
    ResponseEntity<List<Project>> getAllProjectsByUser() {
        System.out.println("getAllProjects request");
        return new ResponseEntity<>(projectService.getAllProjectsByUserId(), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @PostMapping(path = "/user/projects", produces = "application/json", consumes = "application/json")
    public @ResponseBody
    Project createProject(@Valid @RequestBody CreateProjectDto projectDto) {
        return projectService.createProject(projectDto);
    }

    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @PutMapping(path = "/user/projects/{projectId}", produces = "application/json", consumes = "application/json")
    public @ResponseBody
    ResponseEntity<Project> updateProject(@PathVariable Long projectId, @RequestBody CreateProjectDto projectDto) {
        return new ResponseEntity<>(projectService.updateProject(projectDto, projectId), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @DeleteMapping(path = "/user/projects/{projectId}")
    public @ResponseBody
    void deleteProject(@PathVariable Long projectId) {
        projectService.deleteProject(projectId);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping(path = "/admin/projects", produces = "application/json")
    public @ResponseBody
    ResponseEntity<List<Project>> getAllProjects() {
        System.out.println("getAllProjects request");
        return new ResponseEntity<>(projectService.getAllProjects(), HttpStatus.OK);
    }
}
