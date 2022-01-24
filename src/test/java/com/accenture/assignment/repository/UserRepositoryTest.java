package com.accenture.assignment.repository;

import com.accenture.assignment.model.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    UserRepository underTest;

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void findByName() {
        User user = underTest.save(User.builder().name("Test User").password("123456").build());
        // when
        User savedUser = underTest.save(user);
        // then
        Assertions.assertThat(savedUser).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(user);
    }

    @Test
    void itShouldCheckWhenUserDoesNotExists() {

            // given
            String userName="mohamed";
            User user = User.builder().name(userName).build();
            // when
            Boolean expected = underTest.existsByName(userName);

            // then
            assertThat(expected).isFalse();
        }

}