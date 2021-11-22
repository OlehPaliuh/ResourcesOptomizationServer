package com.resource.optimization.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.resource.optimization.entity.enums.PhaseType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Accessors(chain = true)
public class Task implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = FieldNames.NAME)
    private String name;

    @Column(name = FieldNames.TYPE)
    private PhaseType type;

    @Column(name = FieldNames.MIN_IMPLEMENTATION_COST)
    private Float minimumImplementationCost;

    @Column(name = FieldNames.MAX_IMPLEMENTATION_COST)
    private Float maximumImplementationCost;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "tasks")
    @JsonIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Project> projects;

    private static class FieldNames {
        public static final String NAME = "name";
        public static final String COST = "cost";
        public static final String TYPE = "type";
        public static final String MIN_IMPLEMENTATION_COST = "minimumImplementationCost";
        public static final String MAX_IMPLEMENTATION_COST = "maximumImplementationCost";
    }
}
