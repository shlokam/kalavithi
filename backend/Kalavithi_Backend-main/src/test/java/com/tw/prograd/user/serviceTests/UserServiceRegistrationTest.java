package com.tw.prograd.user.serviceTests;

import com.tw.prograd.image.ImageRepository;
import com.tw.prograd.user.dto.UserRegisterDTO;
import com.tw.prograd.user.entity.UserAuthEntity;
import com.tw.prograd.user.entity.UserEntity;
import com.tw.prograd.user.exception.InValidMobileNumberException;
import com.tw.prograd.user.exception.InValidPasswordException;
import com.tw.prograd.user.exception.UserAlreadyExistException;
import com.tw.prograd.user.repository.UserAuthRepository;
import com.tw.prograd.user.repository.UserRepository;
import com.tw.prograd.user.service.UserServiceRegistration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceRegistrationTest {
    private final String userEmail = "admin@test.com";
    private final String password = "Password@2";
    private final String mobileNumber = "9234567890";

    @Mock
    private UserRepository userRepository;
    @Mock
    private UserAuthRepository userAuthRepository;

    @Mock
    private ImageRepository imageRepository;
    @InjectMocks
    private UserServiceRegistration userRegistrationService;

    @Test
    void shouldBeAbleToRegisterUser() {
        UserRegisterDTO userRegisterDTO = UserRegisterDTO.builder()
                .email(userEmail)
                .mobile_number(mobileNumber)
                .password(password).build();

        UserEntity userEntityWithOutID = UserEntity.builder()
                .email(userEmail)
                .mobile_number(mobileNumber).build();

        UserEntity userEntityWithID = UserEntity.builder()
                .id(1)
                .email(userEmail)
                .mobile_number(mobileNumber).build();

        UserAuthEntity userAuthEntity = UserAuthEntity.builder()
                .id(userEntityWithID.getId())
                .password(userRegisterDTO.getPassword()).build();

        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntityWithID);
        when(userAuthRepository.save(any(UserAuthEntity.class))).thenReturn(userAuthEntity);

        int id = userRegistrationService.register(userRegisterDTO);

        assertThat(id).isSameAs(userEntityWithID.getId());

        verify(userRepository).save(userEntityWithOutID);
        verify(userAuthRepository).save(userAuthEntity);
    }

    @Test
    void shouldThrowExceptionWhenUserAlreadyExists() {
        UserRegisterDTO userRegisterDTO = UserRegisterDTO.builder()
                .email(userEmail).
                mobile_number(mobileNumber)
                .password(password).build();

        when(userRepository.findByEmail(userEmail)).thenThrow(UserAlreadyExistException.class);

        assertThrows(UserAlreadyExistException.class, () -> userRegistrationService.register(userRegisterDTO));

        verify(userRepository).findByEmail(userEmail);
    }


    @Test
    void shouldThrowExceptionWhenPasswordIsInvalid() {
        UserRegisterDTO userRegisterDTO = UserRegisterDTO.builder()
                .email(userEmail).
                mobile_number(mobileNumber)
                .password("Password").build();

        when(userRepository.findByEmail(userEmail)).thenReturn(Optional.empty());

        assertThrows(InValidPasswordException.class, () -> userRegistrationService.register(userRegisterDTO));

        verify(userRepository).findByEmail(userEmail);
    }

    @Test
    void shouldThrowExceptionWhenMobileNumberIsInvalid() {
        UserRegisterDTO userRegisterDTO = UserRegisterDTO.builder()
                .email(userEmail).
                mobile_number("9768742")
                .password(password).build();

        when(userRepository.findByEmail(userEmail)).thenReturn(Optional.empty());

        assertThrows(InValidMobileNumberException.class, () -> userRegistrationService.register(userRegisterDTO));

        verify(userRepository).findByEmail(userEmail);
    }

}
