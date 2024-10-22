package com.omery.projectAtm.controller;

import com.omery.projectAtm.domain.dto.AccountDto;
import com.omery.projectAtm.domain.dto.UserDto;
import com.omery.projectAtm.domain.entities.AccountEntity;
import com.omery.projectAtm.domain.entities.UserEntity;
import com.omery.projectAtm.mappers.Mapper;
import com.omery.projectAtm.services.AccountService;
import com.omery.projectAtm.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController

public class AccountController {

    private AccountService accountService;

    private Mapper<AccountEntity, AccountDto> accountMapper;

    public AccountController(AccountService accountService, Mapper<AccountEntity, AccountDto> accountMapper) {
        this.accountService = accountService;
        this.accountMapper = accountMapper;
    }
    @PostMapping(path = "/users/{id}/accounts/new")
    public ResponseEntity<UserDto> createAccount(@RequestBody AccountDto account,
                                              @PathVariable("id") Long id) {

        AccountEntity accountEntity = accountMapper.mapFrom(account);
        AccountEntity savedAccountEntity = accountService.save(accountEntity,id);
        return new ResponseEntity(accountMapper.mapTo(savedAccountEntity), HttpStatus.CREATED);
    }
    @GetMapping(path = "/users/{id}/accounts")
    public List<AccountDto> listAccounts(@PathVariable("id") Long id) {
        List<AccountEntity> accounts = accountService.findByUserId(id);
        return accounts.stream()
                .map(accountMapper::mapTo)
                .collect(Collectors.toList());
    }

    @GetMapping(path = "/users/{id}/accounts/{acc_id}")
    public ResponseEntity<AccountDto> getUser(@PathVariable("id") Long id,@PathVariable("acc_id") Long acc_id) {
        Optional<AccountEntity> foundAccount = accountService.findOneAcc(acc_id);
        return foundAccount.map(accountEntity -> {
            AccountDto accountDto = accountMapper.mapTo(accountEntity);
            return new ResponseEntity<>(accountDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @DeleteMapping(path = "/users/{id}/accounts/{acc_id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable("id") Long id,@PathVariable("acc_id") Long acc_id) {
        try {
            if (accountService.isExists(acc_id)) {
                accountService.deleteAccount(acc_id);
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            // Log the error details
            System.err.println("Error deleting account: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
