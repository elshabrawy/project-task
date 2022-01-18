package com.accenture.projecttask.impl;

import com.accenture.projecttask.exception.BadRequestException;
import com.accenture.projecttask.exception.UserFeatureNotFoundException;
import com.accenture.projecttask.model.Feature;
import com.accenture.projecttask.model.User;
import com.accenture.projecttask.model.UserFeatures;
import com.accenture.projecttask.repository.FeatureRepository;
import com.accenture.projecttask.repository.UserFeaturesRepository;
import com.accenture.projecttask.repository.UserRepository;
import com.accenture.projecttask.service.UserFeaturesService;
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
class UserFeaturesImplementationTest {
    @Autowired
    @Mock
    private UserFeaturesRepository userFeaturesRepository;
    @Autowired
    private UserFeaturesService underTest;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FeatureRepository featureRepository;
    private AutoCloseable autoCloseable;
    private User user;
    private Feature feature;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        user = userRepository.save(User.builder().name("Test User").build());
        feature = featureRepository.save(Feature.builder().name("Test Feature").build());
        underTest = new UserFeaturesImplementation(userFeaturesRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void canFindAllUSerFeatures() {
        //when
        underTest.findAll();
        //then
        verify(userFeaturesRepository).findAll();
    }

    @Test
    void getAllFeaturesByUserId() {
        // given

        long id = 10;
        underTest.enableFeature(UserFeatures.builder().id(id).user(user).feature(feature).build());
        // when
        underTest.getAllFeaturesByUserId(id);
        // then
        verify(userFeaturesRepository).getUserFeaturesByUserId(id);
    }

    @Test
    void canEnableFeature() {
        // given
        UserFeatures userFeatures = UserFeatures.builder().user(user).feature(feature).build();

        // when
        underTest.enableFeature(userFeatures);

        // then
        ArgumentCaptor<UserFeatures> userFeaturesArgumentCaptor =
                ArgumentCaptor.forClass(UserFeatures.class);

        verify(userFeaturesRepository)
                .save(userFeaturesArgumentCaptor.capture());

        UserFeatures capturedUserFeatures = userFeaturesArgumentCaptor.getValue();

        assertThat(capturedUserFeatures).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(userFeatures);
    }

    @Test
    void willThrowWhenUserFeatureIdTaken() {
        // given
        UserFeatures userFeatures = UserFeatures.builder().id(5l).user(User.builder().name("Test User").build()).feature(Feature.builder().name("Test Feature").build()).build();

        given(userFeaturesRepository.existsById(any()))
                .willReturn(true);

        // then
        assertThatThrownBy(() -> underTest.enableFeature(userFeatures))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("Id " + userFeatures.getId() + " taken");

        verify(userFeaturesRepository, never()).save(any());

    }

    @Test
    void disableFeature() {
        // given
        long id = 10;

        given(userFeaturesRepository.existsById(id))
                .willReturn(true);
        // when
        underTest.disableFeature(id);

        // then
        verify(userFeaturesRepository).deleteById(id);
    }

    @Test
    void willThrowWhenDeleteUserFeatureNotFound() {
        // given
        long id = 10;
        given(userFeaturesRepository.existsById(id))
                .willReturn(false);

        // then
        assertThatThrownBy(() -> underTest.disableFeature(id))
                .isInstanceOf(UserFeatureNotFoundException.class)
                .hasMessageContaining("User Feature with id " + id + " does not exists");

        verify(userFeaturesRepository, never()).deleteById(any());
    }
}