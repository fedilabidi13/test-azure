package com.omery.projectAtm.services;

import com.omery.projectAtm.domain.entities.AccountEntity;
import com.omery.projectAtm.domain.entities.UserEntity;

import java.util.List;
import java.util.Optional;

public interface AccountService {
    AccountEntity save(AccountEntity accountEntity, Long id);
    boolean isExists(Long id);
    void deleteAccount(Long id);
    List<AccountEntity> findByUserId(Long id);
    Optional<AccountEntity> findOneAcc(Long id);

    void deleteAccountsByUserId(Long userId);

}
