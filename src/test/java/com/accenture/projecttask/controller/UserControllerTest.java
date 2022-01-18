package com.accenture.projecttask.controller;

import com.accenture.projecttask.model.User;
import com.accenture.projecttask.repository.UserRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.StringUtils;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest

@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;
    @AfterEach
    void tearDown() {
//        userRepository.deleteAll();
    }
    @Test
    void canRegisterNewUser() throws Exception {
        // given
        User user = User.builder().name("Test User").build();

        // when
        ResultActions resultActions = mockMvc
                .perform(post("/user/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)));
        // then
        resultActions.andExpect(status().isOk());
        List<User> users = userRepository.findAll();
        assertThat(users)
                .usingElementComparatorIgnoringFields("id")
                .contains(user);
    }

    @Test
    void getAllUsers() throws Exception{
        // given

        MvcResult getUserResult = mockMvc.perform(get("/user")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String contentAsString = getUserResult
                .getResponse()
                .getContentAsString();

        List<User> users = objectMapper.readValue(
                contentAsString,
                new TypeReference<>() {
                }
        );
    }

}