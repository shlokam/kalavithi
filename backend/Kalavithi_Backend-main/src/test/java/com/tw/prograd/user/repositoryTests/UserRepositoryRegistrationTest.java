package com.tw.prograd.user.repositoryTests;

import com.tw.prograd.user.dto.UserRegisterDTO;
import com.tw.prograd.user.entity.UserEntity;
import com.tw.prograd.user.repository.UserAuthRepository;
import com.tw.prograd.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class UserRepositoryRegistrationTest {

    private final String userName = "admin@test.com";
    private final String password = "password";
    private final String mobileNumber = "1234567890";

    @Autowired
    private UserRepository userRepository;

    @MockBean
    private UserAuthRepository userAuthRepository;

    @Test
    void shouldSaveUserDetails() {
        UserRegisterDTO userRegisterDTO = UserRegisterDTO.builder()
                .email(userName)
                .mobile_number(mobileNumber)
                .password(password).build();

        UserEntity userEntity = UserEntity.builder()
                .id(1200)
                .email(userRegisterDTO.getEmail())
                .mobile_number(userRegisterDTO.getMobile_number()).build();

        userEntity = userRepository.save(userEntity);

        assertThat(userEntity.getId()).isGreaterThan(0);
    }
}
