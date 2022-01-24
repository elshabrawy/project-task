package com.accenture.assignment.impl;

import com.accenture.assignment.dto.UserFeatureDTO;
import com.accenture.assignment.exception.BadRequestException;
import com.accenture.assignment.model.Feature;
import com.accenture.assignment.model.Role;
import com.accenture.assignment.model.User;
import com.accenture.assignment.repository.FeatureRepository;
import com.accenture.assignment.repository.RoleRepository;
import com.accenture.assignment.repository.UserRepository;
import com.accenture.assignment.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;


@SpringBootTest
class UserImplementationTest {
    @Mock
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService underTest;
    @Mock
    @Autowired
    private RoleRepository roleRepository;
    @Mock
    @Autowired
    private FeatureRepository featureRepository;
    private AutoCloseable autoCloseable;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @BeforeEach
    void setUp() {
        org.springframework.security.core.userdetails.User.withDefaultPasswordEncoder().username("user").password("user").roles("USER").build();
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new UserImplementation(userRepository, roleRepository, featureRepository,passwordEncoder);

    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void canAddUser() {
        // given
        User user = User.builder().name("Test User").password("123456").build();
        // when
        given(userRepository.save(user)).willReturn(user);
        underTest.addUser(user);

        // then
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);

        verify(userRepository).save(userArgumentCaptor.capture());

        User capturedUser = userArgumentCaptor.getValue();

        assertThat(capturedUser).isEqualTo(user);
    }

    @Test
    void willThrowWhenUserNameTaken() {
        // given
        User user = User.builder().name("Test User").password("123456").build();

        given(userRepository.existsByName(anyString())).willReturn(true);
        // then
        assertThatThrownBy(() -> underTest.addUser(user)).isInstanceOf(BadRequestException.class).hasMessageContaining("Name " + user.getName() + " taken");
        verify(userRepository, never()).save(any());

    }

    @Test
    void canAddRole() {
        // given
        Role role = Role.builder().name("Test Role").build();
        // when
        underTest.addRole(role);

        // then
        ArgumentCaptor<Role> roleArgumentCaptor = ArgumentCaptor.forClass(Role.class);

        verify(roleRepository).save(roleArgumentCaptor.capture());

        Role capturedRole = roleArgumentCaptor.getValue();

        assertThat(capturedRole).isEqualTo(role);
    }

    @Test
    void willThrowWhenRoleNameTaken() {
        // given
        Role role = Role.builder().name("Test Role").build();

        given(roleRepository.existsByName(anyString())).willReturn(true);
        // then
        assertThatThrownBy(() -> underTest.addRole(role))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("Name " + role.getName() + " taken");
        verify(roleRepository, never()).save(any());

    }

    @Test
    void canAddFeature() {
        // given
        Feature feature = Feature.builder().name("Test Feature").build();
        // when
        underTest.addFeature(feature);

        // then
        ArgumentCaptor<Feature> featureArgumentCaptor = ArgumentCaptor.forClass(Feature.class);

        verify(featureRepository).save(featureArgumentCaptor.capture());

        Feature capturedFeature1 = featureArgumentCaptor.getValue();

        assertThat(capturedFeature1).isEqualTo(feature);
    }

    @Test
    void willThrowWhenFeatureNameTaken() {
        // given
        Feature feature = Feature.builder().name("Test Feature").build();

        given(featureRepository.existsByName(anyString())).willReturn(true);
        // then
        assertThatThrownBy(() -> underTest.addFeature(feature))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("Name " + feature.getName() + " taken");
        verify(featureRepository, never()).save(any());
    }

    @Test
    void findUserFeaturesWithOtherEnabledFeatures() {
        //given
        Feature featureOne = Feature.builder().name("Feature One").build();
        Feature featureTwo = Feature.builder().name("Feature Two").build();

        User userOne = User.builder().name("User One").password("123456").features(List.of(featureOne)).build();
        User userTwo = User.builder().name("User Two").password("123456").features(List.of(featureTwo)).build();

        List<User> userList = List.of(userOne, userTwo);

        given(userRepository.findAll()).willReturn(userList);

        String userName = "User One";

        UserFeatureDTO userFeatureDTO = UserFeatureDTO.builder().userName(userName)
                .allEnabledFeatures(List.of("Feature One", "Feature Two")).
                allUsersEnabledFeatures(List.of("Feature One")).build();
        //when
        UserFeatureDTO capturedUserFeatureDTO = underTest.findUserFeaturesWithOtherEnabledFeatures(userName);
        //then
        assertThat(capturedUserFeatureDTO).isEqualTo(userFeatureDTO);

    }

    @Test
    void canFindAllUsers() {

        //given
        User userOne = User.builder().name("User One").password("123456").build();
        User userTwo = User.builder().name("User Two").password("123111").build();
        List<User> userList = List.of(userOne, userTwo);
        given(userRepository.findAll()).willReturn(userList);

        //when
        List<User> capturedUsers = underTest.findAllUser();
        //then
        assertThat(capturedUsers.size()).isEqualTo(userList.size());
    }

    @Test
    void addRoleToUser() {
        Role role = Role.builder().name("Test Role").build();
        underTest.addRole(role);

        User user = User.builder().name("Test User").password("123456").roles(List.of(role)).build();
        given(userRepository.save(user)).willReturn(user);
        //when
        underTest.addUser(user);
        // then
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userArgumentCaptor.capture());
        User capturedUser = userArgumentCaptor.getValue();
        assertThat(capturedUser.getRoles().contains(role));
    }

    @Test
    void enableFeatureToUser() {
        // given
        Feature feature = Feature.builder().name("Test Feature").build();
        underTest.addFeature(feature);

        User user = User.builder().name("Test User").password("123456").features(List.of(feature)).build();
        given(userRepository.save(user)).willReturn(user);
        //when
        underTest.addUser(user);
        // then
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userArgumentCaptor.capture());
        User capturedUser = userArgumentCaptor.getValue();
        assertThat(capturedUser.getFeatures().contains(feature)).isTrue();
    }

    @Test
    void disableFeatureToUser() {
        // given
        Feature feature = Feature.builder().id(1l).name("Test Feature").build();
        underTest.addFeature(feature);

        User user = User.builder().name("Test User").password("123456").features(List.of(feature)).build();
        given(userRepository.findByName("Test User")).willReturn(user);
        given(featureRepository.findByName("Test Feature")).willReturn(feature);
        given(user.getFeatures().remove(feature)).willReturn(true);

        underTest.disableFeatureToUser("Test User", "Test Feature");
        // then
        verify(user).getFeatures().remove(feature);
    }

}