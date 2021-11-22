package com.resource.optimization.entity.dto;

import com.resource.optimization.entity.OptimizationItem;
import com.resource.optimization.entity.Project;
import com.resource.optimization.entity.Task;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

public class OptimizationItemDto {

    private Long id;

    private Task task;

    private Float value;
}
