package com.tw.prograd.user.service;

import com.tw.prograd.image.*;
import com.tw.prograd.user.EmailValidation;
import com.tw.prograd.user.MobileNumberValidation;
import com.tw.prograd.user.PasswordValidation;
import com.tw.prograd.user.dto.UserRegisterDTO;
import com.tw.prograd.user.entity.UserAuthEntity;
import com.tw.prograd.user.entity.UserEntity;
import com.tw.prograd.user.exception.UserAlreadyExistException;
import com.tw.prograd.user.repository.UserAuthRepository;
import com.tw.prograd.user.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service

public class UserServiceRegistration {


    private UserRepository userRepository;
    private UserAuthRepository userAuthRepository;

    private ImageEntity imageEntity;
    private LikeImageRepository likeImageRepository;

    private FavouriteImageRepository favouriteImageRepository;
    private ImageRepository imageRepository;
    private final PasswordValidation passwordValidation = new PasswordValidation();

    private final MobileNumberValidation mobileNumberValidation = new MobileNumberValidation();

    private final EmailValidation emailValidation = new EmailValidation();


    UserServiceRegistration(UserRepository userRepository, UserAuthRepository userAuthRepository, ImageEntity imageEntity, LikeImageRepository likeImageRepository, ImageRepository imageRepository, FavouriteImageRepository favouriteImageRepository) {

        this.userRepository = userRepository;
        this.userAuthRepository = userAuthRepository;
        this.imageEntity = imageEntity;
        this.likeImageRepository = likeImageRepository;
        this.imageRepository = imageRepository;
        this.favouriteImageRepository = favouriteImageRepository;
    }

    public int register(UserRegisterDTO userRegisterDTO) throws UserAlreadyExistException {
        UserEntity userEntity = UserEntity.builder()
                .email(userRegisterDTO.getEmail())
                .mobile_number(userRegisterDTO.getMobile_number()).build();

        Optional<UserEntity> user = userRepository.findByEmail(userRegisterDTO.getEmail());

        if (user.isPresent()) {
            throw new UserAlreadyExistException("User Already exists");
        }

        if (passwordValidation.checkPassword(userRegisterDTO.getPassword()) && mobileNumberValidation.isMobileNumberValid(userRegisterDTO.getMobile_number())
                && emailValidation.isEmailValid(userRegisterDTO.getEmail())) {
            userEntity = userRepository.save(userEntity);
            UserAuthEntity userAuthEntity = UserAuthEntity.builder().id(userEntity.getId()).password(userRegisterDTO.getPassword()).build();
            userAuthEntity.setPassword(new BCryptPasswordEncoder().encode(userAuthEntity.getPassword()));
            userAuthRepository.save(userAuthEntity);

            List<ImageEntity> imageEntities = imageRepository.findAll();
            System.out.println("entities" + imageEntities);
            for (ImageEntity imageEntity : imageEntities) {
                LikeImageEntity likeImageEntity = new LikeImageEntity(userEntity.getId(), imageEntity.getId(), false);
                likeImageRepository.save(likeImageEntity);
            }

            for (ImageEntity imageEntity : imageEntities) {
                FavouriteImageEntity favouriteImageEntity = new FavouriteImageEntity(userEntity.getId(), imageEntity.getId(), false);
                favouriteImageRepository.save(favouriteImageEntity);
            }
        }
        return userEntity.getId();
    }


}


