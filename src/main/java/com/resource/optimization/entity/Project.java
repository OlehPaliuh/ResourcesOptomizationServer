package com.resource.optimization.entity;

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
@Accessors(chain = true)
public class Project implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = FieldNames.NAME)
    private String name;

    @Column(name = FieldNames.FINAL_COST)
    private Double finalCost;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id")
    private Account owner;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Task> tasks;

    private static class FieldNames {
        public static final String NAME = "name";
        public static final String FINAL_COST = "finalCost";
    }
}
