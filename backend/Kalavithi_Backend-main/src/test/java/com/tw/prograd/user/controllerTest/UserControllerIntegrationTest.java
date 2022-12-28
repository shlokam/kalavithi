
package com.tw.prograd.user.controllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tw.prograd.config.AuthenticationEntryPoint;
import com.tw.prograd.config.EncoderConfig;
import com.tw.prograd.config.SecurityConfig;
import com.tw.prograd.dto.ErrorResponse;
import com.tw.prograd.dto.SuccessResponse;
import com.tw.prograd.user.dto.User;
import com.tw.prograd.user.dto.UserLoginDTO;
import com.tw.prograd.user.dto.UserPasswordDTO;
import com.tw.prograd.user.dto.UserRegisterDTO;
import com.tw.prograd.user.entity.UserAuthEntity;
import com.tw.prograd.user.entity.UserEntity;
import com.tw.prograd.user.exception.ConfirmNewPasswordDoesNotMatchException;
import com.tw.prograd.user.exception.InValidPasswordException;
import com.tw.prograd.user.repository.UserAuthRepository;
import com.tw.prograd.user.repository.UserRepository;
import com.tw.prograd.user.service.UserServiceChangePassword;
import com.tw.prograd.user.service.UserServiceRegistration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Base64;

import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Import({SecurityConfig.class, EncoderConfig.class, AuthenticationEntryPoint.class})
@AutoConfigureMockMvc
class UserControllerIntegrationTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserAuthRepository userAuthRepository;
    @MockBean
    private UserServiceRegistration userRegistrationService;
    @MockBean
    private UserServiceChangePassword userServiceChangePassword;

    private String validAuthorizationToken;
    private String invalidAuthorizationToken;

    private final String userName = "admin@test.com";
    private final String password = "Password@123";
    private final String mobileNumber = "9452678909";

    @BeforeEach
    void setUp() {

        UserLoginDTO userLoginDTO = UserLoginDTO.builder()
                .email(userName)
                .password(password).build();

        UserEntity userEntity = UserEntity.builder()
                .id(1)
                .email(userName)
                .mobile_number(mobileNumber).build();

        UserEntity savedUser = userRepository.save(userEntity);


        UserAuthEntity userAuthEntity = UserAuthEntity.builder()
                .id(savedUser.getId())
                .user(savedUser)
                .password(new BCryptPasswordEncoder().encode(password)).build();
        userAuthRepository.save(userAuthEntity);

        validAuthorizationToken = Base64.getEncoder().encodeToString((userName + ":" + password).getBytes());
        invalidAuthorizationToken = Base64.getEncoder().encodeToString(("user@unknown.com" + ":" + password).getBytes());
    }

    @Nested
    class Login {

        @Test
        void shouldBeAbleToLoginWithValidCredential() throws Exception {
            User user = User.builder()
                    .id(1)
                    .username(userName).build();

            mvc.perform(get("/users/login")
                            .header("Authorization", "Basic " + validAuthorizationToken))
                    .andExpect(status().isOk())
                    .andExpect(content().string(mapper.writeValueAsString(user)));
        }

        @Test
        void shouldNotBeAbleToLoginWithoutValidCredential() throws Exception {
            ErrorResponse error = ErrorResponse.builder()
//                .errorCode(UNAUTHORIZED.value())
                    .message(UNAUTHORIZED.getReasonPhrase()).build();

            mvc.perform(get("/users/login")
                            .header("Authorization", "Basic " + invalidAuthorizationToken))
                    .andExpect(status().isUnauthorized())
                    .andExpect(content().string(mapper.writeValueAsString(error)));
        }
    }

    @Nested
    class Register {
        @Test
        void shouldBeAbleToRegisterWhenValidDetailsGiven() throws Exception {
            UserRegisterDTO userRegisterDTO = UserRegisterDTO.builder()
                    .email(userName)
                    .mobile_number(mobileNumber)
                    .password(password).build();

            SuccessResponse success = SuccessResponse.builder()
                    .id(1).build();

            when(userRegistrationService.register(userRegisterDTO)).thenReturn(1);

            mvc.perform(post("/users")
                            .content(mapper.writeValueAsString(userRegisterDTO))
                            .header("content-type", "application/json"))
                    .andExpect(status().isCreated());

            verify(userRegistrationService).register(userRegisterDTO);
        }

        @Test
        void shouldThrowExceptionWhenEmailFieldIsEmpty() throws Exception {
            UserRegisterDTO userRegisterDTO = UserRegisterDTO.builder()
                    .email("")
                    .mobile_number(mobileNumber)
                    .password(password).build();


            mvc.perform(post("/users")
                            .content(mapper.writeValueAsString(userRegisterDTO))
                            .header("content-type", "application/json"))
                    .andExpect(status().isPreconditionFailed());

        }

        @Test
        void shouldThrowExceptionWhenMobileNumberFieldIsEmpty() throws Exception {
            UserRegisterDTO userRegisterDTO = UserRegisterDTO.builder()
                    .email(userName)
                    .mobile_number("")
                    .password(password).build();


            mvc.perform(post("/users")
                            .content(mapper.writeValueAsString(userRegisterDTO))
                            .header("content-type", "application/json"))
                    .andExpect(status().isPreconditionFailed());

        }

        @Test
        void shouldThrowExceptionWhenPasswordFieldIsEmpty() throws Exception {
            UserRegisterDTO userRegisterDTO = UserRegisterDTO.builder()
                    .email(userName)
                    .mobile_number(mobileNumber)
                    .password("").build();


            mvc.perform(post("/users")
                            .content(mapper.writeValueAsString(userRegisterDTO))
                            .header("content-type", "application/json"))
                    .andExpect(status().isPreconditionFailed());
        }

    }

    @Nested
    class ChangePassword {
        private final Integer id = 1;
        private final String currentPassword = "Password@123";
        private final String newPassword = "Password@1234";
        private final String confirmNewPassword = "Password@1234";

        @Test
        void shouldBeAbleToChangePasswordWhenLoggedInUserProvidesValidDetails() throws Exception {
            UserPasswordDTO userPasswordDTO = UserPasswordDTO.builder()
                    .id(id)
                    .currentPassword(currentPassword)
                    .newPassword(newPassword)
                    .confirmNewPassword(confirmNewPassword)
                    .build();

            when(userServiceChangePassword.changePassword(userPasswordDTO)).thenReturn(1);

            mvc.perform(put("/users")
                            .content(mapper.writeValueAsString(userPasswordDTO))
                            .header("content-type", "application/json")
                            .header("Authorization", "Basic " + validAuthorizationToken))
                    .andExpect(status().isOk());

            verify(userServiceChangePassword, times(1)).changePassword(userPasswordDTO);
        }

        @Test
        void shouldNotBeAbleToChangePasswordWhenUserIsNotLoggedIn() throws Exception {
            UserPasswordDTO userPasswordDTO = UserPasswordDTO.builder()
                    .id(id)
                    .currentPassword(currentPassword)
                    .newPassword(newPassword)
                    .confirmNewPassword(confirmNewPassword)
                    .build();

            when(userServiceChangePassword.changePassword(userPasswordDTO)).thenReturn(1);

            mvc.perform(put("/users")
                            .content(mapper.writeValueAsString(userPasswordDTO))
                            .header("content-type", "application/json"))
                    .andExpect(status().isUnauthorized());

            verify(userServiceChangePassword, times(0)).changePassword(userPasswordDTO);
        }


        @Test
        void shouldThrowExceptionWhenCurrentPasswordFieldIsEmpty() throws Exception {
            UserPasswordDTO userPasswordDTO = UserPasswordDTO.builder()
                    .id(id)
                    .currentPassword("")
                    .newPassword(newPassword)
                    .confirmNewPassword(confirmNewPassword)
                    .build();


            mvc.perform(put("/users")
                            .content(mapper.writeValueAsString(userPasswordDTO))
                            .header("content-type", "application/json")
                            .header("Authorization", "Basic " + validAuthorizationToken))
                    .andExpect(status().isPreconditionFailed());
        }

        @Test
        void shouldThrowExceptionWhenNewPasswordFieldIsEmpty() throws Exception {
            UserPasswordDTO userPasswordDTO = UserPasswordDTO.builder()
                    .id(id)
                    .currentPassword(currentPassword)
                    .newPassword("")
                    .confirmNewPassword(confirmNewPassword)
                    .build();

            mvc.perform(put("/users")
                            .content(mapper.writeValueAsString(userPasswordDTO))
                            .header("content-type", "application/json")
                            .header("Authorization", "Basic " + validAuthorizationToken))
                    .andExpect(status().isPreconditionFailed());
        }

        @Test
        void shouldThrowExceptionWhenConfirmNewPasswordFieldIsEmpty() throws Exception {
            UserPasswordDTO userPasswordDTO = UserPasswordDTO.builder()
                    .id(id)
                    .currentPassword(currentPassword)
                    .newPassword(newPassword)
                    .confirmNewPassword("")
                    .build();

            mvc.perform(put("/users")
                            .content(mapper.writeValueAsString(userPasswordDTO))
                            .header("content-type", "application/json")
                            .header("Authorization", "Basic " + validAuthorizationToken))
                    .andExpect(status().isPreconditionFailed());
        }

        @Test
        void shouldThrowExceptionWhenNewPasswordIsInvalid() throws Exception {
            UserPasswordDTO userPasswordDTO = UserPasswordDTO.builder()
                    .id(id)
                    .currentPassword(currentPassword)
                    .newPassword("password12")
                    .confirmNewPassword("password12")
                    .build();

            when(userServiceChangePassword.changePassword(userPasswordDTO)).thenThrow(InValidPasswordException.class);

            mvc.perform(put("/users")
                            .content(mapper.writeValueAsString(userPasswordDTO))
                            .header("content-type", "application/json")
                            .header("Authorization", "Basic " + validAuthorizationToken))
                    .andExpect(status().isUnprocessableEntity());
        }

        @Test
        void shouldThrowExceptionWhenNewPasswordAndConfirmNewPasswordDoesNotMatch() throws Exception {
            UserPasswordDTO userPasswordDTO = UserPasswordDTO.builder()
                    .id(id)
                    .currentPassword(currentPassword)
                    .newPassword(newPassword)
                    .confirmNewPassword("Password@1234")
                    .build();

            when(userServiceChangePassword.changePassword(userPasswordDTO)).thenThrow(ConfirmNewPasswordDoesNotMatchException.class);

            mvc.perform(put("/users")
                            .content(mapper.writeValueAsString(userPasswordDTO))
                            .header("content-type", "application/json")
                            .header("Authorization", "Basic " + validAuthorizationToken))
                    .andExpect(status().isPreconditionFailed());
        }
    }


}