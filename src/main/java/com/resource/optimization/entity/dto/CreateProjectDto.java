package com.resource.optimization.entity.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@ToString
public class CreateProjectDto {
    @NotEmpty
    private String name;

    @NotNull
    private Float finalCost;

    private List<CreateTaskDto> tasks;
}
