package com.tw.prograd.user.serviceTests;

import com.tw.prograd.user.dto.UserPasswordDTO;
import com.tw.prograd.user.entity.UserAuthEntity;
import com.tw.prograd.user.exception.ConfirmNewPasswordDoesNotMatchException;
import com.tw.prograd.user.exception.CurrentPasswordSameAsNewPasswordException;
import com.tw.prograd.user.exception.InValidPasswordException;
import com.tw.prograd.user.exception.IncorrectOldPasswordException;
import com.tw.prograd.user.repository.UserAuthRepository;
import com.tw.prograd.user.repository.UserRepository;
import com.tw.prograd.user.service.UserServiceChangePassword;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.criteria.CriteriaBuilder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceChangePasswordTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserAuthRepository userAuthRepository;
    @InjectMocks
    private UserServiceChangePassword userServiceChangePassword;

    private final Integer id = 1;
    private final String currentPassword = "password";
    private final String newPassword = "Password@123";
    private final String confirmNewPassword = "Password@123";

    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Test
    void shouldBeAbleToChangePasswordWhenValidDetailsAreProvided() throws CurrentPasswordSameAsNewPasswordException, ConfirmNewPasswordDoesNotMatchException, IncorrectOldPasswordException {

        UserPasswordDTO userPasswordDTO = UserPasswordDTO.builder()
                .id(id)
                .currentPassword(currentPassword)
                .newPassword(newPassword)
                .confirmNewPassword(confirmNewPassword)
                .build();

        UserAuthEntity userAuthEntity = UserAuthEntity.builder()
                .id(id)
                .password(bCryptPasswordEncoder.encode(currentPassword))
                .build();

        when(userAuthRepository.getById(userPasswordDTO.getId())).thenReturn(userAuthEntity);
        int responseId = userServiceChangePassword.changePassword(userPasswordDTO);

        assertThat(responseId).isEqualTo(userPasswordDTO.getId());
        verify(userAuthRepository).save(userAuthEntity);
    }

    @Test
    void shouldThrowExceptionWhenOldPasswordIsIncorrect() throws IncorrectOldPasswordException {
        UserPasswordDTO userPasswordDTO = UserPasswordDTO.builder()
                .id(id)
                .currentPassword("password11")
                .newPassword(newPassword)
                .confirmNewPassword(confirmNewPassword)
                .build();

        UserAuthEntity userAuthEntity = UserAuthEntity.builder()
                .id(id)
                .password(bCryptPasswordEncoder.encode(currentPassword))
                .build();

        when(userAuthRepository.getById(userPasswordDTO.getId())).thenReturn(userAuthEntity);
        assertThrows(IncorrectOldPasswordException.class, () -> userServiceChangePassword.changePassword(userPasswordDTO));

        verify(userAuthRepository, times(1)).getById(id);
        verify(userAuthRepository, times(0)).save(any());
    }

    @Test
    void shouldThrowExceptionWhenCurrentPasswordIsSameAsNewPassword() throws CurrentPasswordSameAsNewPasswordException {
        UserPasswordDTO userPasswordDTO = UserPasswordDTO.builder()
                .id(id)
                .currentPassword(currentPassword)
                .newPassword(currentPassword)
                .confirmNewPassword(currentPassword)
                .build();

        UserAuthEntity userAuthEntity = UserAuthEntity.builder()
                .id(id)
                .password(bCryptPasswordEncoder.encode(currentPassword))
                .build();

        when(userAuthRepository.getById(userPasswordDTO.getId())).thenReturn(userAuthEntity);
        assertThrows(CurrentPasswordSameAsNewPasswordException.class, () -> userServiceChangePassword.changePassword(userPasswordDTO));

        verify(userAuthRepository, times(1)).getById(id);
        verify(userAuthRepository, times(0)).save(any());
    }

}