package com.resource.optimization.service;

import com.resource.optimization.entity.Task;
import com.resource.optimization.entity.dto.CreateTaskDto;
import com.resource.optimization.exception.common.NotFoundException;
import com.resource.optimization.repository.TaskRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final ModelMapper modelMapper;

    public TaskService(TaskRepository taskRepository, ModelMapper modelMapper) {
        this.taskRepository = taskRepository;
        this.modelMapper = modelMapper;
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Task getTaskById(Long taskId) {
        return taskRepository.findById(taskId)
                .orElseThrow(() -> new NotFoundException(String.format("Task with id %s not found", taskId)));
    }

    public Task createTask(CreateTaskDto taskDto) {
        System.out.println("taskDto: " + taskDto.toString());
        Task task = modelMapper.map(taskDto, Task.class);
        return taskRepository.save(task);
    }

    public Task updateTask(CreateTaskDto updateTask, Long taskId) {
        Task task = getTaskById(taskId);
        update(task, modelMapper.map(updateTask, Task.class));
        return taskRepository.save(task);
    }

    public void deleteTask(Long taskId) {
        taskRepository.delete(getTaskById(taskId));
    }

    private void update(Task currentTask, Task updateTask) {
        if (updateTask.getCost() != null && !currentTask.getCost().equals(updateTask.getCost())) {
            currentTask.setCost(updateTask.getCost());
        }

        if (updateTask.getName() != null && !currentTask.getName().equals(updateTask.getName())) {
            currentTask.setName(updateTask.getName());
        }

        if (updateTask.getType() != null && !currentTask.getType().equals(updateTask.getType())) {
            currentTask.setType(updateTask.getType());
        }
    }
}
