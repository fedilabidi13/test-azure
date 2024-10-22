package com.omery.projectAtm.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.omery.projectAtm.TestDataUtil;
import com.omery.projectAtm.domain.entities.AccountEntity;
import com.omery.projectAtm.domain.entities.UserEntity;
import com.omery.projectAtm.services.AccountService;
import com.omery.projectAtm.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
@ActiveProfiles("test")

public class AccountControllerIntegrationtest {
    private UserService userService;

    private AccountService accountService;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @Autowired
    public AccountControllerIntegrationtest(MockMvc mockMvc, AccountService accountService, UserService userService) {
        this.mockMvc = mockMvc;
        this.accountService = accountService;
        this.objectMapper = new ObjectMapper();
        this.userService = userService;
    }
    @Test
    public void testThatGetUserReturnsWhenAccountExist() throws Exception {
        UserEntity userEntity = TestDataUtil.createTestUserEntityB();
        userService.save(userEntity);
        AccountEntity testAccountEntityA = TestDataUtil.createTestAccountEntityA(userEntity);
        accountService.save(testAccountEntityA,testAccountEntityA.getUserEntity().getId());
        String accountJson = objectMapper.writeValueAsString(testAccountEntityA);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/users/"+ userEntity.getId()+"/accounts/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(accountJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }
    @Test
    public void testThatUserDeleteWithAccount() throws Exception{
        UserEntity testUserEntityB = TestDataUtil.createTestUserEntityB();
        userService.save(testUserEntityB);
        AccountEntity testAccountEntityA = TestDataUtil.createTestAccountEntityA(testUserEntityB);
        accountService.save(testAccountEntityA,testAccountEntityA.getUserEntity().getId());
        userService.delete(testUserEntityB.getId());
        // Assert: Try to retrieve the user and expect a 404 Not Found status
        mockMvc.perform(
                MockMvcRequestBuilders.get("/users/" + testUserEntityB.getId() + "/accounts/" +
                                testAccountEntityA.getAcc_id())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }
    @Test
    public void testThatGetAccountsReturnsWhenUserExist() throws Exception {
        UserEntity testUserEntityB = TestDataUtil.createTestUserEntityB();
        userService.save(testUserEntityB);
        AccountEntity testAccountEntityA = TestDataUtil.createTestAccountEntityA(testUserEntityB);
        accountService.save(testAccountEntityA,testAccountEntityA.getUserEntity().getId());

        mockMvc.perform(
                MockMvcRequestBuilders.get("/users/1/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].acc_id").value(1)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].totalCredit").value(0)
        );
    }

    @Test
    public void testThatDeleteWithAccount() throws Exception{
        UserEntity testUserEntityB = TestDataUtil.createTestUserEntityB();
        userService.save(testUserEntityB);
        AccountEntity testAccountEntityA = TestDataUtil.createTestAccountEntityA(testUserEntityB);
        accountService.save(testAccountEntityA,testAccountEntityA.getUserEntity().getId());
        accountService.deleteAccount(testAccountEntityA.getAcc_id());        // Assert: Try to retrieve the user and expect a 404 Not Found status
        mockMvc.perform(
                MockMvcRequestBuilders.get("/users/" + testUserEntityB.getId() + "/accounts/" +
                                testAccountEntityA.getAcc_id())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }
}

