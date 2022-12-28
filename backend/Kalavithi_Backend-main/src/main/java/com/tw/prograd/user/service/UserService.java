package com.tw.prograd.user.service;

import com.tw.prograd.user.entity.UserEntity;
import com.tw.prograd.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserEntity> readUser() {
        return userRepository.findAll();


    }
}
