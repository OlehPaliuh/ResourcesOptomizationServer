package com.resource.optimization.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.resource.optimization.entity.enums.RoleName;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@ToString
@Accessors(chain = true)
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Account is locked
     */
    @NotNull
    private boolean isLocked = false;
    private String lockReason;
    private LocalDateTime lockTimestamp;
    private LocalDateTime unlockTimestamp;

    /**
     * Account is created but not activated OR account is deleted
     */
    @NotNull
    private boolean isDisabled = false;

    @Column(name = "refresh_token_key", nullable = false)
    private String refreshTokenKey;

    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Project> ownProjects;

//    @ManyToOne
//    @NotNull
//    @JoinColumn(name = "account_role_id")
//    private Role role;

//    @ManyToOne
//    @NotNull
//    @JoinColumn(name = "account_role_id")
    private RoleName role;

    private String firstName;

    private String lastName;

    @Column(unique = true)
    private String username;

    private String password;

    private String email;

    private String activationCode;

    private String avatarPath;

    @CreationTimestamp
    private LocalDateTime registeredAt;
    @UpdateTimestamp
    private LocalDateTime editedAt;

    @Length(max = 13)
    private String phoneNumber;

    private LocalDateTime lastLoginTime;
}
