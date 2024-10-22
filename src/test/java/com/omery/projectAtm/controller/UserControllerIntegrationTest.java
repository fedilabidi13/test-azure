package com.omery.projectAtm.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.omery.projectAtm.TestDataUtil;
import com.omery.projectAtm.domain.dto.UserDto;
import com.omery.projectAtm.domain.entities.UserEntity;
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

public class UserControllerIntegrationTest {

    @Autowired
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    public UserControllerIntegrationTest(MockMvc mockMvc, UserService userService) {
        this.mockMvc = mockMvc;
        this.userService = userService;
        this.objectMapper = new ObjectMapper();
    }
    @Test
    public void testThatCreateUserSuccessfullyReturnsHttp201Created() throws Exception {
        UserEntity TestUserA = TestDataUtil.createTestUserEntityB();
        String userJson = objectMapper.writeValueAsString(TestUserA);
        TestUserA.setId(null);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/users/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }
    @Test
    public void testThatListUsersReturnsListOfAuthors() throws Exception {
        UserEntity testAuthorEntityA = TestDataUtil.createTestUserEntityA();
        userService.save(testAuthorEntityA);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/users")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].firstName").value("Abigail")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].lastName").value("Rose")
        );
    }
    @Test
    public void testThatGetUserReturnsWhenUserExist() throws Exception {
        UserEntity testUserEntityA = TestDataUtil.createTestUserEntityA();
        userService.save(testUserEntityA);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(1)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.firstName").value("Abigail")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.lastName").value("Rose")
        );
    }
    @Test
    public void testThatDeleteUserRemovesUser() throws Exception {
        // Arrange: Create and save a test user
        UserEntity testUserEntityA = TestDataUtil.createTestUserEntityA();
        userService.save(testUserEntityA);

        // Act: Delete the user by ID
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/users/" + testUserEntityA.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNoContent() // Assuming your delete returns a 204 No Content status
        );

        // Assert: Try to retrieve the user and expect a 404 Not Found status
        mockMvc.perform(
                MockMvcRequestBuilders.get("/users/" + testUserEntityA.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatGetUserReturnsHttpStatus404WhenNoAuthorExists() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/users/99")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testThatFullUpdateUserReturnsHttpStatus4200WhenAuthorExists() throws Exception {
        UserEntity testAuthorEntityA = TestDataUtil.createTestUserEntityA();
        UserEntity savedUser = userService.save(testAuthorEntityA);

        UserDto testUserDtoA = TestDataUtil.createTestUserDtoA();
        String userDtoJson = objectMapper.writeValueAsString(testUserDtoA);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/users/" + savedUser.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userDtoJson)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }
    @Test
    public void testThatPartialUpdateExistingAuthorReturnsHttpStatus20Ok() throws Exception {
        UserEntity testAuthorEntityA = TestDataUtil.createTestUserEntityA();
        UserEntity savedUser = userService.save(testAuthorEntityA);

        UserDto testUserDtoA = TestDataUtil.createTestUserDtoA();
        testUserDtoA.setFirstName("UPDATED");
        String userDtoJson = objectMapper.writeValueAsString(testUserDtoA);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/users/" + savedUser.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userDtoJson)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }
}
