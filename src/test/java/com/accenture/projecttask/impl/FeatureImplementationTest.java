package com.accenture.projecttask.impl;

import com.accenture.projecttask.exception.BadRequestException;
import com.accenture.projecttask.model.Feature;
import com.accenture.projecttask.model.User;
import com.accenture.projecttask.repository.FeatureRepository;
import com.accenture.projecttask.repository.UserRepository;
import com.accenture.projecttask.service.FeatureService;
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
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@SpringBootTest
class FeatureImplementationTest {
    @Autowired
    @Mock
    private FeatureRepository featureRepository;
    @Autowired
    private FeatureService underTest;
    private  AutoCloseable autoCloseable;
    @BeforeEach
    void setUp() {
        autoCloseable= MockitoAnnotations.openMocks(this);
        underTest = new FeatureImplementation(featureRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void canEnableFeature() {

        // given
        Feature feature = Feature.builder().name("Test Feature").build();
        // when
        underTest.addFeature(feature);

        // then
        ArgumentCaptor<Feature> featureArgumentCaptor =
                ArgumentCaptor.forClass(Feature.class);

        verify(featureRepository)
                .save(featureArgumentCaptor.capture());

        Feature capturedFeature = featureArgumentCaptor.getValue();

        assertThat(capturedFeature).isEqualTo(feature);
    }

    @Test
    void willThrowWhenUserFeatureIdTaken() {
        // given
       Feature feature = Feature.builder().name("Test Feature").build();
       String featureName="Test Feature";
       // when
        given(featureRepository.existsByName(anyString()))
                .willReturn(true);
        assertThatThrownBy(() -> underTest.addFeature(feature))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("Feature Name " + feature.getName() + " taken");

        // then
        verify(featureRepository, never()).save(any());

    }

}