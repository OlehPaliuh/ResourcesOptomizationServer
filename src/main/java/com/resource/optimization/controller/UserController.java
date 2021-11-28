package com.resource.optimization.controller;

import com.resource.optimization.entity.dto.AccountInfoDto;
import com.resource.optimization.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/user/account")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @GetMapping(path = "/{userId}", produces = "application/json")
    public @ResponseBody
    ResponseEntity<AccountInfoDto> getUserById(@PathVariable Long userId) {
        return new ResponseEntity<>(userService.getAccountById(userId), HttpStatus.OK);
    }
}
