package com.accenture.assignment.repository;

import com.accenture.assignment.model.Role;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class RoleRepositoryTest {

    @Autowired
    RoleRepository underTest;

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void findByName() {
        Role role = underTest.save(Role.builder().name("Test Role").build());
        // when
        Role savedRole = underTest.save(role);
        // then
        Assertions.assertThat(savedRole).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(role);
    }

    @Test
    void itShouldCheckWhenRoleDoesNotExists() {

        // given
        String roleName="Test Role";
        Role role = Role.builder().name(roleName).build();
        // when
        Boolean expected = underTest.existsByName(roleName);

        // then
        assertThat(expected).isFalse();
    }

}