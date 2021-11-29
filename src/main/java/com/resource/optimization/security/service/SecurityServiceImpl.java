package com.resource.optimization.security.service;

import com.resource.optimization.entity.Account;
import com.resource.optimization.security.dto.JwtResponseDto;
import com.resource.optimization.security.jwt.JwtTokenUtil;
import com.resource.optimization.service.AccountService;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SecurityServiceImpl {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private AccountService accountService;

    @Transactional
    public JwtResponseDto updateAccessTokens(String refreshToken) throws Exception {
        String username;
        try {
            username = jwtTokenUtil.getUsernameFromRefreshToken(refreshToken);
        } catch (ExpiredJwtException e) {
            throw new Exception("REFRESH_TOKEN_NOT_VALID");
        }
        Account account = accountService
                .getByUsername(username);

        if (account == null) {
            throw new Exception("User not found by username");
        }
        String newRefreshTokenKey = jwtTokenUtil.generateTokenKey();
        accountService.updateAccountRefreshToken(newRefreshTokenKey, account.getId());
        if (jwtTokenUtil.isTokenValid(refreshToken, account.getRefreshTokenKey())) {
            account.setRefreshTokenKey(newRefreshTokenKey);
            return new JwtResponseDto(
                    jwtTokenUtil.generateAccessToken(account),
                    jwtTokenUtil.generateRefreshToken(account),
                    account.getId(),
                    account.getUsername(),
                    account.getRole()
            );
        }
        throw new Exception("REFRESH_TOKEN_NOT_VALID");
    }
}
