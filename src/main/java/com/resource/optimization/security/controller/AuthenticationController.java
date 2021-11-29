package com.resource.optimization.security.controller;

import com.resource.optimization.security.dto.JwtRequestDto;
import com.resource.optimization.security.dto.RegisterAccountDto;
import com.resource.optimization.security.service.AuthenticationService;
import com.resource.optimization.security.service.SecurityServiceImpl;
import com.resource.optimization.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;

@RestController
@RequestMapping(path = "/api")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private SecurityServiceImpl securityService;

    @PostMapping(path = "/register")
    public ResponseEntity<Object> registration(@Validated @RequestBody RegisterAccountDto account) throws Exception {
        accountService.createAccount(account);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping(path = "/authenticate")
    public ResponseEntity<?> authentication(@RequestBody JwtRequestDto authenticationRequest) throws Exception {
        return ResponseEntity.ok(authenticationService.authenticateUser(authenticationRequest));
    }

    @GetMapping(path = "/updateAccessToken")
    public ResponseEntity<Object> updateAccessToken(@RequestParam @NotBlank String refreshToken) throws Exception {
        return ResponseEntity.ok().body(securityService.updateAccessTokens(refreshToken));
    }

    @GetMapping(path = "/activate/{code}")
    public String activateAccount(@PathVariable("code") String code) throws Exception {
        return accountService.activateAccount(code);
    }
}
