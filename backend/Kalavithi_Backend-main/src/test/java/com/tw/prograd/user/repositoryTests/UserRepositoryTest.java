package com.tw.prograd.user.repositoryTests;

import com.tw.prograd.user.entity.UserEntity;
import com.tw.prograd.user.repository.UserAuthRepository;
import com.tw.prograd.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@ExtendWith(SpringExtension.class)
@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    @MockBean
    private UserAuthRepository userAuthRepository;

    @Test
    void whenSaved_thenFindByEmail() {
        UserEntity toBeSavedUser = UserEntity.builder().email("some@test.net").build();

        userRepository.save(toBeSavedUser);

        var savedUser = userRepository.findByEmail("some@test.net");

        assertTrue(savedUser.isPresent());
        assertEquals(toBeSavedUser, savedUser.get());
    }
}