package com.resource.optimization.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.resource.optimization.entity.enums.PhaseType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Accessors(chain = true)
public class PhaseOptimizationItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private PhaseType type;

    private Float minimumCost;

    private Float maximumCost;

    private Float value;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "project_optimization_id")
    @JsonIgnore
    private ProjectOptimization projectOptimization;

    @OneToMany(mappedBy = "phaseOptimizationItem", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<OptimizationItem> optimizationItems;
}
