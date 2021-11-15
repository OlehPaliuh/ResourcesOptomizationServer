package com.resource.optimization.security.service;

import com.resource.optimization.entity.Account;
import com.resource.optimization.exception.UserNotFoundException;
import com.resource.optimization.repository.AccountRepository;
import com.resource.optimization.security.dto.JwtUserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        Account account = accountRepository.getAccountByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found with username: " + username));

        List<GrantedAuthority> list = new ArrayList<>();
        list.add(new SimpleGrantedAuthority(account.getRole().toString()));

        return new JwtUserDto(
                account.getId(),
                account.getUsername(),
                account.getPassword(),
                list,
                false
        );
    }
}
