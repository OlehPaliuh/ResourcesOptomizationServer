package com.resource.optimization.entity.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.resource.optimization.entity.OptimizationItem;
import com.resource.optimization.entity.Project;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

public class ProjectOptimizationDto {

    private Long id;

    private List<OptimizationItemDto> optimizationItems;

    private Project project;
}
