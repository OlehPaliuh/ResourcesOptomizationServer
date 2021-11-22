package com.resource.optimization.entity.dto;

import com.resource.optimization.entity.enums.PhaseType;
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
    @NotNull(message = "Type is required")
    private PhaseType type;
    @NotNull
    private Float minimumImplementationCost;
    @NotNull
    private Float maximumImplementationCost;
}
