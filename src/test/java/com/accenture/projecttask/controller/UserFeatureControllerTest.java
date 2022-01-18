package com.accenture.projecttask.controller;

import com.accenture.projecttask.model.Feature;
import com.accenture.projecttask.model.User;
import com.accenture.projecttask.model.UserFeatures;
import com.accenture.projecttask.repository.FeatureRepository;
import com.accenture.projecttask.repository.UserFeaturesRepository;
import com.accenture.projecttask.repository.UserRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.StringUtils;
import static org.mockito.Mockito.when;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserFeatureControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserFeaturesRepository userFeaturesRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FeatureRepository featuresRepository;
    private User testUser;
    private Feature testFeature;
@BeforeEach
void setUp() throws Exception {
    testUser=userRepository.save(User.builder().name("Test User").build());
   testFeature=featuresRepository.save(Feature.builder().name("Test Feature").build());
}
    @Test
    void getAllUserFeatures() throws Exception{
    // given
//      when(userFeaturesRepository.findAll()).thenReturn(
//              Arrays.asList()
//      );
//        RequestBuilder request= MockMvcRequestBuilders
//                .get("/userfeature").accept(MediaType.APPLICATION_JSON);
//        MvcResult result=mockMvc.perform(request)
//                .andExpect(status().isOk())
//                .andExpect(content().json("[]"))
//                .andReturn();
    MvcResult getUserResult = mockMvc.perform(get("/userfeature")
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn();

    String contentAsString = getUserResult
            .getResponse()
            .getContentAsString();

    List<UserFeatures> userFeature = objectMapper.readValue(
            contentAsString,
            new TypeReference<>() {
            }
    );
}
    @Test
    void getFeaturesByUser() throws Exception{
        long id=5l;
        MvcResult getUserResult = mockMvc.perform(get("/userfeature")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(id)))
                .andExpect(status().isOk())
                .andReturn();

        String contentAsString = getUserResult
                .getResponse()
                .getContentAsString();

        List<UserFeatures> users = objectMapper.readValue(
                contentAsString,
                new TypeReference<>() {
                }
        );
    }

    @Test
    @WithUserDetails("ahmed")
    void canRegisterNewUserFeature() throws Exception{
        // given
        UserFeatures userFeature = UserFeatures.builder().id(5l).user(testUser)
                .feature(testFeature).build();

        // when
        ResultActions resultActions = mockMvc
                .perform(post("/userfeature/enable")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userFeature)));
        // then
        resultActions.andExpect(status().isOk());
        List<UserFeatures> userFeatures = userFeaturesRepository.findAll();
        assertThat(userFeatures)
                .usingElementComparatorIgnoringFields("id")
                .contains(userFeature);
    }
    @Test
    @WithUserDetails("ahmed")
    void canDisableFeature() throws Exception{

        UserFeatures userFeature = UserFeatures.builder().id(6l).user(testUser)
                .feature(testFeature).build();

        mockMvc.perform(post("/userfeature/enable")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userFeature)))
                .andExpect(status().isOk());

        MvcResult getUserFeaturesResult = mockMvc.perform(get("/userfeature")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String contentAsString = getUserFeaturesResult
                .getResponse()
                .getContentAsString();

        List<UserFeatures> features = objectMapper.readValue(
                contentAsString,
                new TypeReference<>() {
                }
        );

        long id = features.stream()
                .filter(f -> f.getUser().equals(userFeature.getUser()) && f.getFeature().equals(userFeature.getFeature()))
                .map(UserFeatures::getId)
                .findFirst()
                .orElseThrow(() ->
                        new IllegalStateException(
                                "UserFeature with id: " + userFeature.getId() + " not found"));


        // when
        ResultActions resultActions = mockMvc
                .perform(delete("/userfeature/" + id));

        // then
        resultActions.andExpect(status().isOk());
        boolean exists = userFeaturesRepository.existsById(id);
        assertThat(exists).isFalse();
    }

}