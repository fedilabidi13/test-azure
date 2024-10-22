package com.omery.projectAtm.repositories;


import com.omery.projectAtm.TestDataUtil;
import com.omery.projectAtm.domain.entities.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ActiveProfiles("test")

public class UserRepoIntegrationTest {

    private UserRepository underTest;

    @Autowired
    public UserRepoIntegrationTest(UserRepository underTest) {
        this.underTest = underTest;
    }

    @Test
    public void testThatAuthorCanBeCreatedAndRecalled() {
        UserEntity userEntity = TestDataUtil.createTestUserEntityA();
        underTest.save(userEntity);
        Optional<UserEntity> result = underTest.findById(userEntity.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(userEntity);
    }
//    @Test
//   public void testThatMultipleAuthorsCanBeCreatedAndRecalled() {
//
//        UserEntity authorEntityB = TestDataUtil.createTestUserEntityB();
//        underTest.save(authorEntityB);
//        UserEntity authorEntityC = TestDataUtil.createTestUserEntityC();
//        underTest.save(authorEntityC);
//
//        Iterable<UserEntity> result = underTest.findAll();
//        assertThat(result)
//                .hasSize(2).
//                containsExactly(authorEntityB, authorEntityC);
//    }
}
