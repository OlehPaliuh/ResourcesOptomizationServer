package com.resource.optimization.service;

import com.resource.optimization.entity.Account;
import com.resource.optimization.entity.enums.RoleName;
import com.resource.optimization.exception.UserAlreadyExistException;
import com.resource.optimization.repository.AccountRepository;
import com.resource.optimization.security.dto.RegisterAccountDto;
import com.resource.optimization.security.jwt.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class AccountService {

    private static final String SUPPORT_USERNAME= "support";

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    public Account updateLastLoginTime(Long id, LocalDateTime time) {
        Account account = accountRepository.getAccountById(id);
        account.setLastLoginTime(time);
        return accountRepository.save(account);
    }

    public int updateAccountRefreshToken(String refreshTokenKey, Long id) {
        return accountRepository.updateAccountRefreshToken(refreshTokenKey, id);
    }

    public Account getByUsername(String username) {
        return accountRepository.getAccountByUsername(username)
                .orElse(null);
    }

    public void createAccount(RegisterAccountDto account) {
        if (getByUsername(account.getUsername()) != null) {
            throw new UserAlreadyExistException(String.format("User with username %s already exist.", account.getUsername()));
        }

        Account newAccount = new Account()
                .setFirstName(account.getFirstName())
                .setLastName(account.getLastName())
                .setUsername(account.getUsername())
                .setEmail(account.getEmail())
                .setPhoneNumber(account.getPhoneNumber())
                .setPassword(bcryptEncoder.encode(account.getPassword()))
                .setRole(RoleName.USER)
                .setDisabled(false)
                .setActivationCode(UUID.randomUUID().toString())
                .setRefreshTokenKey(jwtTokenUtil.generateTokenKey())
                .setLocked(false);
        saveAccount(newAccount);

//        if (!StringUtils.isEmpty(savedAccount.getEmail())) {
//            String message = String.format(
//                    "Hello, %s %s! \n" +
//                            "Welcome to Rent Service. Please, visit next link: http://localhost:8080/api/activate/%s",
//                    savedAccount.getFirstName(),
//                    savedAccount.getLastName(),
//                    savedAccount.getActivationCode());
//
//            mailSender.send(savedAccount.getEmail(), "Account activation", message);
//        }
    }

    public Account saveAccount(Account account) {
        return accountRepository.save(account);
    }

    public String activateAccount(String code) {
//        Account account = accountRepository.getAccountByActivationCode(code);
//        if (account == null) {
//            return "Activation link is not valid anymore";
//        }
//
//        account.setDisabled(false);
//        account.setActivationCode(null);
//
//        accountRepository.save(account);
        return "User activated";
    }
}
