package com.omery.projectAtm.domain.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class AccountDto {
    private Long acc_id;

    private Long totalCredit;

    private UserDto userDto;

}
