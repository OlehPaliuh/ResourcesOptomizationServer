package com.resource.optimization.controller;

import com.resource.optimization.entity.Task;
import com.resource.optimization.entity.dto.CreateTaskDto;
import com.resource.optimization.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/api/user")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @GetMapping(path = "/task/{taskId}", produces = "application/json")
    public @ResponseBody
    ResponseEntity<Task> getTaskById(@PathVariable Long taskId) {
        return new ResponseEntity<>(taskService.getTaskById(taskId), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @GetMapping(path = "/tasks", produces = "application/json")
    public @ResponseBody
    ResponseEntity<List<Task>> getAllTasks() {
        System.out.println("getAllTasks request");
        return new ResponseEntity<>(taskService.getAllTasks(), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @PostMapping(path = "/tasks", produces = "application/json", consumes = "application/json")
    public @ResponseBody
    Task createTask(@Valid @RequestBody CreateTaskDto taskDto) {
        return taskService.createTask(taskDto);
    }

    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @PutMapping(path = "/task/{taskId}", produces = "application/json", consumes = "application/json")
    public @ResponseBody
    ResponseEntity<Task> updateTask(@PathVariable Long taskId, @RequestBody CreateTaskDto taskDto) {
        return new ResponseEntity<>(taskService.updateTask(taskDto, taskId), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @DeleteMapping(path = "/task/{taskId}")
    public @ResponseBody
    void deleteTask(@PathVariable Long taskId) {
        taskService.deleteTask(taskId);
    }
}
