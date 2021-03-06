package com.resource.optimization.service;

import com.resource.optimization.entity.*;
import com.resource.optimization.entity.dto.CreateProjectDto;
import com.resource.optimization.entity.enums.PhaseType;
import com.resource.optimization.exception.ProjectNotFoundException;
import com.resource.optimization.repository.ProjectRepository;
import com.resource.optimization.service.minimization.MinimizationForTasks;
import com.resource.optimization.service.minimization.MinimizationService;
import com.resource.optimization.service.minimization.MinimizationServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProjectService {

    private final OptimizationItemService optimizationItemService;
    private final ProjectOptimizationService projectOptimizationService;
    private final PhaseOptimizationItemService phaseOptimizationItemService;
    private final ProjectRepository projectRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;

    public ProjectService(OptimizationItemService optimizationItemService,
                          ProjectOptimizationService projectOptimizationService,
                          PhaseOptimizationItemService phaseOptimizationItemService, ProjectRepository projectRepository,
                          UserService userService, ModelMapper modelMapper) {
        this.optimizationItemService = optimizationItemService;
        this.projectOptimizationService = projectOptimizationService;
        this.phaseOptimizationItemService = phaseOptimizationItemService;
        this.projectRepository = projectRepository;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public List<Project> getAllProjectsByUserId() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String username = userDetails.getUsername();
        return projectRepository.findAllByOwner(userService.getAccountByUsername(username));
    }

    public Project getProjectById(Long projectId) {
        return projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException(String.format("Project with id %s not found", projectId)));
    }

    public Project createProject(CreateProjectDto projectDto) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String username = userDetails.getUsername();
        System.out.println("Username " + username);
        System.out.println("projectDto: " + projectDto.toString());
        Project project = modelMapper.map(projectDto, Project.class);
        project.setOwner(userService.getAccountByUsername(username));
        return projectRepository.save(project);
    }

    public Project updateProject(CreateProjectDto updateProject, Long projectId) {
        System.out.println("projectDto: " + updateProject.toString());
        Project project = getProjectById(projectId);
        update(project, modelMapper.map(updateProject, Project.class));
        return projectRepository.save(project);
    }

    public ProjectOptimization optimizeProject(Long projectId) {
        System.out.println("optimizeProject: " + projectId);
        Project project = getProjectById(projectId);

        List<PhaseType> phaseTypes = Arrays.asList(PhaseType.values());

        List<PhaseOptimizationItem> phaseOptimizationItems = new ArrayList<>();

        phaseTypes.forEach(phaseType -> {
            List<Task> tasks = project.getTasks().stream()
                    .filter(task -> task.getType() == phaseType)
                    .collect(Collectors.toList());

            if (!tasks.isEmpty()) {
                phaseOptimizationItems.add(buildPhaseOptimizationItem(phaseType, tasks));
            }
        });

        System.out.println("Start Optimization");

        long startTime = System.nanoTime();


        MinimizationService minimizationService = new MinimizationServiceImpl(phaseOptimizationItems, project.getFinalCost());
        List<PhaseOptimizationItem> resultPhaseOptimizationItems = minimizationService.calculateMinimization();

        MinimizationForTasks minimizationForTasks = new MinimizationForTasks(resultPhaseOptimizationItems, project);
        ProjectOptimization projectOptimization = minimizationForTasks.calculateMinimization();

        System.out.println("End Optimization");
        long endTime = System.nanoTime();

        long duration = (endTime - startTime) / 1000000;

        System.out.println("Execution Time in milliseconds " + duration);
        log.info("Execution Time in milliseconds " + duration);
        // Delete old project optimizations
        projectOptimizationService.deleteProjectOptimizationsByProjectId(projectId);

        Long projectOptimizationId = saveProjectOptimizationElements(projectOptimization);

        return projectOptimizationService.getProjectOptimizationById(projectOptimizationId);
    }

    private Long saveProjectOptimizationElements(ProjectOptimization projectOptimization) {
        ProjectOptimization savedProjectOptimization = projectOptimizationService.createProjectOptimization(projectOptimization);

        projectOptimization.getPhaseOptimizationItems()
                .forEach(phaseOptimizationItem -> phaseOptimizationItem
                        .setOptimizationItems(phaseOptimizationItem.getOptimizationItems().stream()
                                .map(optimizationItem -> optimizationItem.setPhaseOptimizationItem(phaseOptimizationItem))
                                .map(optimizationItemService::createOptimizationItem)
                                .collect(Collectors.toList())));

        projectOptimization.setPhaseOptimizationItems(
                projectOptimization.getPhaseOptimizationItems().stream()
                        .map(phaseOptimizationItem -> phaseOptimizationItem.setProjectOptimization(savedProjectOptimization))
                        .map(phaseOptimizationItemService::createPhaseOptimizationItem)
                        .collect(Collectors.toList()));

        return savedProjectOptimization.getId();
    }

    private PhaseOptimizationItem buildPhaseOptimizationItem(PhaseType phaseType, List<Task> tasks) {
        return new PhaseOptimizationItem()
                .setType(phaseType)
                .setOptimizationItems(tasks.stream()
                        .map(task -> new OptimizationItem()
                                .setTask(task)).collect(Collectors.toList()))
                .setMinimumCost(tasks.stream()
                        .map(Task::getMinimumImplementationCost)
                        .reduce(0f, Float::sum))
                .setMaximumCost(tasks.stream()
                        .map(Task::getMaximumImplementationCost)
                        .reduce(0f, Float::sum));
    }

    public void deleteProject(Long projectId) {
        projectRepository.delete(getProjectById(projectId));
    }

    private void update(Project currentProject, Project updateProject) {
        if (updateProject.getFinalCost() != null && !currentProject.getFinalCost().equals(updateProject.getFinalCost())) {
            currentProject.setFinalCost(updateProject.getFinalCost());
        }

        if (updateProject.getName() != null && !currentProject.getName().equals(updateProject.getName())) {
            currentProject.setName(updateProject.getName());
        }

        currentProject.setTasks(updateProject.getTasks());
    }
}
