package com.resource.optimization.entity.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class CreateTaskDto {
    private Integer taskId;
    @NotEmpty
    private String name;
    @NotEmpty(message = "Type is required")
    private String type;
    @NotNull
    private Double cost;
    @NotNull
    private Integer minimumImplementationCost;
    @NotNull
    private Integer maximumImplementationCost;
}
