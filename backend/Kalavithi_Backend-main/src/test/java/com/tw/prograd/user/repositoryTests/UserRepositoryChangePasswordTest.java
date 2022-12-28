package com.tw.prograd.user.repositoryTests;

import com.tw.prograd.user.entity.UserAuthEntity;
import com.tw.prograd.user.entity.UserEntity;
import com.tw.prograd.user.repository.UserAuthRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
public class UserRepositoryChangePasswordTest {
    @Autowired
    UserAuthRepository userAuthRepository;

    @Test
    void shouldSaveTheNewPassword() {
        UserEntity user = UserEntity.builder().id(1)
                .email("admin@kalavithi.com")
                .mobile_number("9825416732")
                .build();
        UserAuthEntity userAuthEntity = UserAuthEntity.builder()
                .id(1)
                .password("password")
                .user(user)
                .build();
        userAuthRepository.save(userAuthEntity);

        UserAuthEntity savedUserAuthEntity = userAuthRepository.getById(1);

        assertNotNull(savedUserAuthEntity);
    }
}
