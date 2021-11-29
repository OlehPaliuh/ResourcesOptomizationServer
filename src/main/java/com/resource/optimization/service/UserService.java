package com.resource.optimization.service;

import com.resource.optimization.entity.Account;
import com.resource.optimization.entity.dto.AccountInfoDto;
import com.resource.optimization.exception.common.NotFoundException;
import com.resource.optimization.repository.AccountRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final AccountRepository accountRepository;
    private final ModelMapper modelMapper;

    public UserService(AccountRepository accountRepository, ModelMapper modelMapper) {
        this.accountRepository = accountRepository;
        this.modelMapper = modelMapper;
    }

    public AccountInfoDto getAccountById(Long userId) {
        Account account = accountRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format("User with id %s not found", userId)));

        return modelMapper.map(account, AccountInfoDto.class);
    }

    public Account getAccountByUsername(String username) {
        return accountRepository.getAccountByUsername(username)
                .orElseThrow(() -> new NotFoundException(String.format("User with username %s not found", username)));
    }

    public List<AccountInfoDto> getAllUsers() {
        return accountRepository.findAll().stream()
                .map(account -> modelMapper.map(account, AccountInfoDto.class))
                .collect(Collectors.toList());
    }
}
