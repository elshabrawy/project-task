package com.accenture.projecttask.impl;

import com.accenture.projecttask.exception.BadRequestException;
import com.accenture.projecttask.model.User;
import com.accenture.projecttask.repository.UserRepository;
import com.accenture.projecttask.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;


@SpringBootTest
class UserImplementationTest {
    @Autowired
    @Mock
    private UserRepository userRepository;
    @Autowired
    private UserService underTest;
    private AutoCloseable autoCloseable;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new UserImplementation(userRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void canAddUser() {
        // given
        User user = User.builder().name("Test User").build();
        // when
        underTest.addUser(user);

        // then
        ArgumentCaptor<User> userArgumentCaptor =
                ArgumentCaptor.forClass(User.class);

        verify(userRepository)
                .save(userArgumentCaptor.capture());

        User capturedUser = userArgumentCaptor.getValue();

        assertThat(capturedUser).isEqualTo(user);
    }

    @Test
    void willThrowWhenUserUserNameTaken() {
        // given
        User user = User.builder().name("Test User").build();
        String name = "Test User";

        given(userRepository.existsByName(anyString())).willReturn(true);
        // then
        assertThatThrownBy(() -> underTest.addUser(user))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("Name " + user.getName() + " taken");

        verify(userRepository, never()).save(any());

    }


    @Test
    void canFindAllUsers() {
        //when
        underTest.findAll();
        //then
        verify(userRepository).findAll();
    }
}