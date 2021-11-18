package com.resource.optimization.entity.dto;

import com.resource.optimization.entity.Task;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class CreateProjectDto {
    @NotEmpty
    private String name;

    @NotNull
    private Double finalCost;

    private List<Task> tasks;

    public String toString() {
        return "Name: " + this.name + "\n" +
                "Final Cost: " + this.finalCost + "\n" +
                this.tasks.stream()
                        .map(Task::toString)
                        .reduce(" ", String::join);
    }
}
