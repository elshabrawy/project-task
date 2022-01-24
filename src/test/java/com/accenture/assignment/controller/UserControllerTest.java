package com.accenture.assignment.controller;

import com.accenture.assignment.dto.UserFeatureDTO;
import com.accenture.assignment.model.Feature;
import com.accenture.assignment.model.Role;
import com.accenture.assignment.model.User;
import com.accenture.assignment.repository.FeatureRepository;
import com.accenture.assignment.repository.RoleRepository;
import com.accenture.assignment.repository.UserRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(
         locations = "classpath:application-it.properties"
)
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FeatureRepository featureRepository;

    @Autowired
    private RoleRepository roleRepository;
    private AutoCloseable autoCloseable;
    private Feature feature;
    private Role role;
    private User user;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);

       user = userRepository.findByName("new user");
       if(user!= null) {
           user.setFeatures(new ArrayList<>());
           user.setRoles((new ArrayList<>()));
           userRepository.save(user);
           userRepository.deleteByName("new user");
       }


        feature = Feature.builder().name("new feature").build();

        featureRepository.deleteByName("new feature");

        feature=featureRepository.save(feature);
        role = Role.builder().name("new role").build();
        roleRepository.deleteByName("new role");
        role=roleRepository.save(role);
        user = User.builder().name("new user").password("111111").features(List.of(feature)).roles(List.of(role)).build();

        User savedUser = userRepository.save(user);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    @WithMockUser(username = "Test User", roles = {"ADMIN"})
    @WithUserDetails("mohamed")
    void addUser() throws Exception {
        // when

        ResultActions resultActions = mockMvc
                .perform(post("/api/addUser")
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
    @WithMockUser(username = "Test User", roles = {"ADMIN"})
    void addFeature() throws Exception {
        // when

        ResultActions resultActions = mockMvc
                .perform(post("/api/addFeature")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(feature)));
        // then
        resultActions.andExpect(status().isOk());
        List<Feature> features = featureRepository.findAll();
        assertThat(features)
                .usingElementComparatorIgnoringFields("id")
                .contains(feature);
    }

    @Test
    @WithMockUser(username = "Test User", roles = {"ADMIN"})
    void addRole() throws Exception {
        // when

        ResultActions resultActions = mockMvc
                .perform(post("/api/addRole")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(role)));
        // then
        resultActions.andExpect(status().isOk());
        List<Role> roles = roleRepository.findAll();
        assertThat(roles)
                .usingElementComparatorIgnoringFields("id")
                .contains(role);
    }

    @Test
    @WithMockUser(username = "Test User", roles = {"ADMIN"})
    void getAllUsers() throws Exception {
        // given

        MvcResult getUserResult = mockMvc.perform(get("/api/getAllUser").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();

        String contentAsString = getUserResult.getResponse().getContentAsString();

        List<User> users = objectMapper.readValue(contentAsString, new TypeReference<>() {
        });

    }

    @Test
    @WithMockUser(username = "Test User", roles = {"ADMIN"})
    void getAllUserFeature() throws Exception {
        String userName = "Test User";
        MvcResult getUserResult = mockMvc.perform(get("/api/getAllUserFeature/" + userName).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();

        String contentAsString = getUserResult.getResponse().getContentAsString();
        UserFeatureDTO userFeatureDTO = objectMapper.readValue(contentAsString, new TypeReference<>() {
        });

    }

    @Test
    @WithMockUser(username = "Test User", roles = {"ADMIN"})
    @WithUserDetails("admin")
    void enableFeatureToUser() throws Exception {
        // given
        String userName = "Test User";
        String featureName = "Test Feature";
        Feature feature = Feature.builder().name("Test Feature").build();
        given(featureRepository.findByName(featureName)).willReturn(feature);

        User user = User.builder().name("Test User").password("11112222").features(List.of(feature)).build();

        given(userRepository.findByName(userName)).willReturn(user);

        // when
        String url="/api/enableFeatrue/" + userName + "/" + featureName;
        ResultActions resultActions = mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON).header("Role", "admin").content(objectMapper.writeValueAsString(user)));
//        // then
        resultActions.andExpect(status().isOk());

    }

    @Test
    @WithMockUser(username = "Test User", roles = {"ADMIN"})
    void disableFeatureToUser() throws Exception {

    }
}