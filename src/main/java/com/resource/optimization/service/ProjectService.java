package com.resource.optimization.service;

import com.resource.optimization.entity.Project;
import com.resource.optimization.entity.dto.CreateProjectDto;
import com.resource.optimization.exception.ProjectNotFoundException;
import com.resource.optimization.repository.ProjectRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ModelMapper modelMapper;

    public ProjectService(ProjectRepository projectRepository, ModelMapper modelMapper) {
        this.projectRepository = projectRepository;
        this.modelMapper = modelMapper;
    }

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public Project getProjectById(Long projectId) {
        return projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException(String.format("Project with id %s not found", projectId)));
    }

    public Project createProject(CreateProjectDto projectDto) {
        System.out.println("projectDto: " + projectDto.toString());
        Project project = modelMapper.map(projectDto, Project.class);
        return projectRepository.save(project);
    }

    public Project updateProject(CreateProjectDto updateProject, Long projectId) {
        System.out.println("projectDto: " + updateProject.toString());
        Project project = getProjectById(projectId);
        update(project, modelMapper.map(updateProject, Project.class));
        return projectRepository.save(project);
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

//        if (updateProject.getTasks() != null && !currentProject.getTasks().equals(updateProject.getTasks())) {
//            currentProject.setTasks(updateProject.getTasks());
//        }
    }
}
