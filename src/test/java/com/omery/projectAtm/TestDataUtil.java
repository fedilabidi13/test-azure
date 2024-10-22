package com.omery.projectAtm;

import com.omery.projectAtm.domain.dto.AccountDto;
import com.omery.projectAtm.domain.dto.UserDto;
import com.omery.projectAtm.domain.entities.AccountEntity;
import com.omery.projectAtm.domain.entities.UserEntity;

public final class TestDataUtil {
    private TestDataUtil(){
    }

    public static UserEntity createTestUserEntityA() {
        return UserEntity.builder()
                .id(1L)
                .firstName("Abigail")
                .lastName("Rose")
                .email("roseab@gmail.com")
                .password("123456")
                .build();
    }

    public static UserDto createTestUserDtoA() {
        return UserDto.builder()
                .id(1L)
                .firstName("Abigail")
                .lastName("Rose")
                .email("roseab@gmail.com")
                .password("123456")
                .build();
    }

    public static UserEntity createTestUserEntityB() {
        return UserEntity.builder()
                .id(2L)
                .firstName("Ada")
                .lastName("Yüce")
                .email("adayüce@gmail.com")
                .password("123456")
                .build();
    }
    public static UserEntity createTestUserEntityC() {
        return UserEntity.builder()
                .id(3L)
                .firstName("Ömer")
                .lastName("Yıldız")
                .email("yildizom@gmail.com")
                .password("123456")
                .build();
    }
    public  static AccountEntity createTestAccountEntityA(UserEntity user){
        return AccountEntity.builder()
                .acc_id(12345L)
                .totalCredit(0L)
                .userEntity(user)
                .build();
    }
    public  static AccountDto createTestAccountDtoA(UserDto user){
        return AccountDto.builder()
                .acc_id(12345L)
                .totalCredit(0L)
                .userDto(user)
                .build();
    }
}

