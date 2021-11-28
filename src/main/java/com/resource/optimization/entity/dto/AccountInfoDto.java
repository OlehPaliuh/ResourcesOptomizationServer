package com.resource.optimization.entity.dto;

import com.resource.optimization.entity.enums.RoleName;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@ToString
@Accessors(chain = true)
public class AccountInfoDto {

    private boolean isLocked = false;
    private String lockReason;

    private RoleName role;

    private String firstName;

    private String lastName;

    private String username;

    private String password;

    private String email;

    private String avatarPath;

    private LocalDateTime registeredAt;

    private LocalDateTime editedAt;

    private String phoneNumber;

    private LocalDateTime lastLoginTime;
}
