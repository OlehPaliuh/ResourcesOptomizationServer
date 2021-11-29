package com.resource.optimization.security.service;

import com.resource.optimization.entity.Account;
import com.resource.optimization.exception.IncorrectCredentialsException;
import com.resource.optimization.exception.UserDisabledException;
import com.resource.optimization.security.dto.JwtRequestDto;
import com.resource.optimization.security.dto.JwtResponseDto;
import com.resource.optimization.security.jwt.JwtTokenUtil;
import com.resource.optimization.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountLockedException;

@Service
public class AuthenticationService {

    @Autowired
    private AccountService userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    public JwtResponseDto authenticateUser(JwtRequestDto request) throws Exception {
        authenticate(request.getUsername(), request.getPassword());
        Account userDetails = userDetailsService.getByUsername(request.getUsername());
        if (userDetails.isDisabled()) {
            throw new UserDisabledException(" Account disabled. Contact system administrator.");
        }
        if (userDetails.isLocked()) {
            throw new AccountLockedException("This account is locked, contact administrator.");
        }
        return new JwtResponseDto(jwtTokenUtil.generateAccessToken(userDetails), jwtTokenUtil.generateRefreshToken(userDetails),
                                  userDetails.getId(), userDetails.getUsername(), userDetails.getRole());
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new IncorrectCredentialsException("Incorrect username of password", e);
        }
    }
}
