//package com.resource.optimization.entity;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.resource.optimization.entity.enums.RoleName;
//import lombok.Data;
//import lombok.EqualsAndHashCode;
//import lombok.ToString;
//
//import javax.persistence.*;
//import javax.validation.constraints.NotNull;
//import java.util.List;
//
//@Entity
//@Data
//@ToString
//public class Role {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "role_id")
//    private Long id;
//
//    @NotNull
//    @Enumerated(EnumType.STRING)
//    private RoleName name;
//
//    @ToString.Exclude
//    @JsonIgnore
//    @EqualsAndHashCode.Exclude
//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "role")
//    List<Account> accounts;
//}
