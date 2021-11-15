package com.resource.optimization.repository;

import com.resource.optimization.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Account getAccountById(Long accountId);

    Optional<Account> getAccountByUsername(String username);

    @Modifying
    @Query(value = "UPDATE Account SET refreshTokenKey=:refreshTokenKey WHERE id=:id")
    int updateAccountRefreshToken(String refreshTokenKey, Long id);

}
