package com.accenture.projecttask.controller;

import com.accenture.projecttask.model.Feature;
import com.accenture.projecttask.model.User;
import com.accenture.projecttask.repository.FeatureRepository;
import com.accenture.projecttask.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.StringUtils;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class FeatureControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private FeatureRepository featureRepository;

    @Test
    @WithUserDetails("ahmed")
    void canRegisterNewFeature() throws Exception {
        // given
        Feature feature = Feature.builder().name("Test Feature").build();

        // when
        ResultActions resultActions = mockMvc
                .perform(post("/feature/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(feature)));
        // then
        resultActions.andExpect(status().isOk());
        List<Feature> features = featureRepository.findAll();
        assertThat(features)
                .usingElementComparatorIgnoringFields("id")
                .contains(feature);
    }

}