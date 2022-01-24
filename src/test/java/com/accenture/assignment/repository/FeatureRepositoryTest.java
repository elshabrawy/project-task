package com.accenture.assignment.repository;

import com.accenture.assignment.model.Feature;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class FeatureRepositoryTest {

    @Autowired
    FeatureRepository underTest;

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void findByName() {
        Feature feature = underTest.save(Feature.builder().name("Test Feature").build());
        // when
        Feature savedFeature = underTest.save(feature);
        // then
        Assertions.assertThat(savedFeature).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(feature);
    }

    @Test
    void itShouldCheckWhenUserDoesNotExists() {

        // given
        String featureName="adding";
        Feature feature = Feature.builder().name(featureName).build();
        // when
        Boolean expected = underTest.existsByName(featureName);

        // then
        assertThat(expected).isFalse();
    }
}