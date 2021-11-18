package com.resource.optimization.controller;

import com.resource.optimization.entity.Project;
import com.resource.optimization.entity.dto.CreateProjectDto;
import com.resource.optimization.service.ProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/api/user/projects")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @GetMapping(path = "/{projectId}", produces = "application/json")
    public @ResponseBody
    ResponseEntity<Project> getProjectsById(@PathVariable Long projectId) {
        return new ResponseEntity<>(projectService.getProjectById(projectId), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @GetMapping(produces = "application/json")
    public @ResponseBody
    ResponseEntity<List<Project>> getAllProjects() {
        System.out.println("getAllProjects request");
        return new ResponseEntity<>(projectService.getAllProjects(), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @PostMapping(produces = "application/json", consumes = "application/json")
    public @ResponseBody
    Project createProject(@Valid @RequestBody CreateProjectDto projectDto) {
        return projectService.createProject(projectDto);
    }

    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @PutMapping(path = "/{projectId}", produces = "application/json", consumes = "application/json")
    public @ResponseBody
    ResponseEntity<Project> updateProject(@PathVariable Long projectId, @RequestBody CreateProjectDto projectDto) {
        return new ResponseEntity<>(projectService.updateProject(projectDto, projectId), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @DeleteMapping(path = "/{projectId}")
    public @ResponseBody
    void deleteProject(@PathVariable Long projectId) {
        projectService.deleteProject(projectId);
    }
}
