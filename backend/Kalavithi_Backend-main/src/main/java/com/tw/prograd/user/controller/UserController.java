package com.tw.prograd.user.controller;

import com.tw.prograd.dto.SuccessResponse;
import com.tw.prograd.user.PasswordValidation;
import com.tw.prograd.user.dto.User;
import com.tw.prograd.user.dto.UserPasswordDTO;
import com.tw.prograd.user.dto.UserRegisterDTO;
import com.tw.prograd.user.entity.UserAuthEntity;
import com.tw.prograd.user.entity.UserEntity;
import com.tw.prograd.user.exception.ConfirmNewPasswordDoesNotMatchException;
import com.tw.prograd.user.exception.InValidPasswordException;
import com.tw.prograd.user.service.UserService;
import com.tw.prograd.user.service.UserServiceChangePassword;
import com.tw.prograd.user.service.UserServiceRegistration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserServiceRegistration userServiceRegistration;

    @Autowired
    private UserServiceChangePassword userServiceChangePassword;

    @Autowired
    private UserService userService;


    @GetMapping("/login")
    public User login(Authentication authentication) {
        UserAuthEntity user = (UserAuthEntity) authentication.getPrincipal();
        return User.builder()
                .id(user.getId())
                .username(user.getUsername()).build();

    }



    @PostMapping
    public ResponseEntity<SuccessResponse> create(@RequestBody UserRegisterDTO userRegisterDTO) throws Exception {
        if (userRegisterDTO.getEmail() == null || userRegisterDTO.getEmail().equals("")) {
            throw new IllegalArgumentException("Email field is required");
        }
        if (userRegisterDTO.getMobile_number() == null || userRegisterDTO.getMobile_number().equals("")) {
            throw new IllegalArgumentException("Mobile number field is required");
        }
        if (userRegisterDTO.getPassword() == null || userRegisterDTO.getPassword().equals("")) {
            throw new IllegalArgumentException("Password is required");
        }
        int id = userServiceRegistration.register(userRegisterDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse(id));
    }

    @PutMapping
    public ResponseEntity<SuccessResponse> changePassword(@RequestBody UserPasswordDTO userPasswordDTO, Authentication authentication) throws Exception {

        UserAuthEntity user = (UserAuthEntity) authentication.getPrincipal();

        if (userPasswordDTO.getCurrentPassword() == null || userPasswordDTO.getCurrentPassword().equals("")) {
            throw new IllegalArgumentException("Current Password field is required");
        }

        if (userPasswordDTO.getNewPassword() == null || userPasswordDTO.getNewPassword().equals("")) {
            throw new IllegalArgumentException("New Password field is required");
        }

        if (userPasswordDTO.getConfirmNewPassword() == null || userPasswordDTO.getConfirmNewPassword().equals("")) {
            throw new IllegalArgumentException("Confirm New Password is required");
        }

        PasswordValidation passwordValidation = new PasswordValidation();
        if (!passwordValidation.checkPassword(userPasswordDTO.getNewPassword())) {
            throw new InValidPasswordException("Please Enter Valid Password");
        }

        if (!userPasswordDTO.getConfirmNewPassword().equals(userPasswordDTO.getNewPassword())) {
            throw new ConfirmNewPasswordDoesNotMatchException("Password Confirmed Doesn't Match New Password");
        }

        userServiceChangePassword.changePassword(userPasswordDTO);
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse(user.getId()));
    }

    @GetMapping("/logout")
    public void logout(){}

    @GetMapping
    public List<UserEntity> readUser(){
        return userService.readUser();

    }
}
