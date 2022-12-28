package com.tw.prograd.user.service;

import com.tw.prograd.user.dto.UserPasswordDTO;
import com.tw.prograd.user.entity.UserAuthEntity;
import com.tw.prograd.user.exception.ConfirmNewPasswordDoesNotMatchException;
import com.tw.prograd.user.exception.CurrentPasswordSameAsNewPasswordException;
import com.tw.prograd.user.exception.InValidPasswordException;
import com.tw.prograd.user.exception.IncorrectOldPasswordException;
import com.tw.prograd.user.repository.UserAuthRepository;
import com.tw.prograd.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceChangePassword {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserAuthRepository userAuthRepository;

    public int changePassword(UserPasswordDTO userPasswordDTO) throws IncorrectOldPasswordException, CurrentPasswordSameAsNewPasswordException, InValidPasswordException, ConfirmNewPasswordDoesNotMatchException {

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        UserAuthEntity userAuth = userAuthRepository.getById(userPasswordDTO.getId());

        String currentPasswordInUserAuthRepository = userAuth.getPassword();

        if(!bCryptPasswordEncoder.matches(userPasswordDTO.getCurrentPassword(), currentPasswordInUserAuthRepository)) {
            throw new IncorrectOldPasswordException("Entered Current Password Is Incorrect");
        }

        if(bCryptPasswordEncoder.matches(userPasswordDTO.getNewPassword(), currentPasswordInUserAuthRepository)){
            throw new CurrentPasswordSameAsNewPasswordException("Password Was Used Previously");
        }

        userAuth.setPassword(bCryptPasswordEncoder.encode(userPasswordDTO.getNewPassword()));
        userAuthRepository.save(userAuth);

        return userPasswordDTO.getId();
    }
}