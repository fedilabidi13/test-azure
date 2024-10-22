package com.omery.projectAtm.services.impl;

import com.omery.projectAtm.domain.entities.AccountEntity;
import com.omery.projectAtm.domain.entities.UserEntity;
import com.omery.projectAtm.repositories.AccountRepository;
import com.omery.projectAtm.repositories.UserRepository;
import com.omery.projectAtm.services.AccountService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class AccountServiceImpl implements AccountService {
    private AccountRepository accountRepository;
    private UserRepository userRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository,UserRepository userRepository){
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;}

    @Override
    public AccountEntity save(AccountEntity accountEntity, Long userId) {
        // Find the UserEntity by the provided userId
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User with id " + userId + " not found"));

        // Set the found UserEntity to the accountEntity
        accountEntity.setUserEntity(userEntity);

        // Save the accountEntity and return it
        return accountRepository.save(accountEntity);
    }

    @Override
    public boolean isExists(Long id) {
        return accountRepository.existsById(id);
    }

    @Override
    public void deleteAccount(Long id){accountRepository.deleteById(id);}

    @Override
    public  List<AccountEntity> findByUserId(Long id){
        List<AccountEntity> accounts = accountRepository.getAccounts(id);
        return StreamSupport.stream(accounts
                                .spliterator(),
                        false)
                .collect(Collectors.toList());
    }
    @Override
    public Optional<AccountEntity> findOneAcc(Long id) {
        return accountRepository.findById(id);
    }



    public void deleteAccountsByUserId(Long userId) {
        accountRepository.deleteAccountEntitiesByUserId(userId);
    }

}
