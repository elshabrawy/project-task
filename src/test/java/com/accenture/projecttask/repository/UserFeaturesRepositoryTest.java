package com.accenture.projecttask.repository;

import com.accenture.projecttask.model.Feature;
import com.accenture.projecttask.model.User;
import com.accenture.projecttask.model.UserFeatures;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class UserFeaturesRepositoryTest {

    @Autowired
    UserFeaturesRepository underTest;
    @Autowired
    UserRepository userRepository;
    @Autowired
    FeatureRepository featureRepository;
    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }



    @Test
    void getUserFeaturesByUserId() {
        // given
        User user = userRepository.save(User.builder().name("Test User").build());
        Feature feature = featureRepository.save(Feature.builder().name("Test Feature").build());
        UserFeatures userFeatures = UserFeatures.builder().user(user)
                .feature(feature).build();
        // when
        UserFeatures savedUserFeatures = underTest.save(userFeatures);
        // then
        Assertions.assertThat(savedUserFeatures).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(userFeatures);

    }

    @Test
    void itShouldCheckWhenUserFeatureDoesNotExists() {
        // given
        long userFeatureId=10l;
        UserFeatures userFeatures = UserFeatures.builder().id(userFeatureId).build();

        // when
        Boolean expected = userRepository.existsById(userFeatureId);

        // then
        assertThat(expected).isFalse();
    }

}