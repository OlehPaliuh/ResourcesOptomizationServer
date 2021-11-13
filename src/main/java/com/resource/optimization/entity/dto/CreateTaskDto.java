package com.resource.optimization.entity.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CreateTaskDto {
    @NotEmpty
    private String name;
    @NotEmpty(message = "Type is required")
    private String type;
    @NotNull
    private Double cost;

    public String toString() {
        return "Name: " + this.name + "\n" +
                "Type: " + this.type + "\n" +
                "Cost: " + this.cost + "\n";
    }
}
