package com.omery.projectAtm.mappers.impl;


import com.omery.projectAtm.domain.dto.AccountDto;
import com.omery.projectAtm.domain.entities.AccountEntity;
import com.omery.projectAtm.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccountMapperImpl implements Mapper<AccountEntity, AccountDto> {

    private final ModelMapper modelMapper;

    @Autowired
    public AccountMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public AccountDto mapTo(AccountEntity accountEntity) {
        return modelMapper.map(accountEntity, AccountDto.class);
    }

    @Override
    public AccountEntity mapFrom(AccountDto accountDto) {
        return modelMapper.map(accountDto, AccountEntity.class);
    }
}

