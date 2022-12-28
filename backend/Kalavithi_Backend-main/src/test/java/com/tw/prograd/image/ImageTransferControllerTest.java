package com.tw.prograd.image;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tw.prograd.config.AuthenticationEntryPoint;
import com.tw.prograd.config.EncoderConfig;
import com.tw.prograd.config.SecurityConfig;
import com.tw.prograd.image.dto.FavouriteImage;
import com.tw.prograd.image.dto.LikeImage;
import com.tw.prograd.image.dto.UploadImage;
import com.tw.prograd.image.exception.ImageNotFoundException;
import com.tw.prograd.image.exception.ImageStorageException;
import com.tw.prograd.user.dto.UserLoginDTO;
import com.tw.prograd.user.entity.UserAuthEntity;
import com.tw.prograd.user.entity.UserEntity;
import com.tw.prograd.user.repository.UserAuthRepository;
import com.tw.prograd.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockPart;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import java.io.ByteArrayInputStream;
import java.util.Base64;

import static org.mockito.Mockito.*;
import static org.springframework.http.HttpHeaders.CONTENT_DISPOSITION;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@Import({SecurityConfig.class, EncoderConfig.class, AuthenticationEntryPoint.class})
@AutoConfigureMockMvc
class ImageTransferControllerTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserAuthRepository userAuthRepository;

    private final int userId = 1;
    private final int imageId = 1;
    private final boolean status = true;
    private String validAuthorizationToken;
    private String invalidAuthorizationToken;

    private final String userName = "admin@test.com";
    private final String password = "Password@123";

    private final String mobileNumber = "9452678909";


    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private ImageTransferService service;

    private MockMultipartFile image;

    private MockPart description;

    private byte[] imageContent;


    @BeforeEach
    void setUp() {
        imageContent = "dummy image content".getBytes();
        image = new MockMultipartFile("image", "image.png", "multipart/form-data", imageContent);
        description = new MockPart("description", "hello".getBytes());


        UserLoginDTO userLoginDTO = UserLoginDTO.builder()
                .email(userName)
                .password(password).build();

        UserEntity userEntity = UserEntity.builder()
                .id(1)
                .email(userName)
                .mobile_number(mobileNumber).build();

        UserEntity savedUser = userRepository.save(userEntity);
        System.out.println(savedUser);

        UserAuthEntity userAuthEntity = UserAuthEntity.builder()
                .id(savedUser.getId())
                .password(new BCryptPasswordEncoder().encode(password))
                .user(savedUser).build();
        userAuthRepository.save(userAuthEntity);

        validAuthorizationToken = Base64.getEncoder().encodeToString((userName + ":" + password).getBytes());
    }

    @Nested
    class ImageLoading {

        @Test
        public void shouldReturnImageWhenExistingImageRequested() throws Exception {

            Resource resource = mock(Resource.class);
            when(resource.getInputStream()).thenReturn(new ByteArrayInputStream(imageContent));
            when(resource.getFilename()).thenReturn("image.png");
            when(service.imageByName("image.png")).thenReturn(resource);
            when(service.contentType(resource)).thenReturn("image/png");

            mvc.perform(get("/images/image.png"))
                    .andExpect(status().isOk())
                    .andExpect(header().stringValues(CONTENT_TYPE, "image/png"))
                    .andExpect(header().stringValues(CONTENT_DISPOSITION, "attachment; filename=\"image.png\""))
                    .andExpect(content().bytes(imageContent));


            verify(service).imageByName("image.png");
        }

        @Test
        @Disabled("GET and POST has same url, so url resolver could not resolve to correct route")
        public void shouldNotReturnAnythingWhenImageNameIsNotSent() throws Exception {

            mvc.perform(get("/images"))
                    .andExpect(status().isNotFound());

            verifyNoInteractions(service);
        }

        @Test
        public void shouldNotReturnImageWhenNonExistingImageRequested() throws Exception {

            when(service.imageByName("image.png")).thenThrow(ImageNotFoundException.class);

            mvc.perform(get("/images/image.png"))
                    .andExpect(status().isNotFound());

            verify(service).imageByName("image.png");
        }

        @Test
        public void shouldSaveImageWhenUploaded() throws Exception {

            UploadImage uploadImage = new UploadImage(1, "image.png", "hello");
            when(service.store(image, uploadImage.getDescription())).thenReturn(uploadImage);

            mvc.perform(multipart("/images").file(image).part(description))
                    .andExpect(status().isFound())
                    .andExpect(header().string("Location", "/images/image.png"))
                    .andExpect(content().json(mapper.writeValueAsString(uploadImage)));

            verify(service).store(image, "hello");
        }


        @Test
        public void shouldForbiddenTheUploadWhenFailedToStoreImage() throws Exception {

            doThrow(ImageStorageException.class).when(service).store(image, "hello");

            mvc.perform(multipart("/images").file(image).part(description))
                    .andExpect(status().isForbidden());

            verify(service).store(image, "hello");
        }
    }

    @Nested
    class LikeAnImage {

        @Test
        void shouldBeAbleToChangeLikeStatusWhenUserIsLoggedIn() throws Exception {
            LikeImage likeImage = LikeImage.builder()
                    .userid(userId)
                    .imageid(imageId)
                    .status(status).build();

            when(service.changeLikeStatus(likeImage)).thenReturn(1);

            mvc.perform(put("/images")
                            .content(mapper.writeValueAsString(likeImage))
                            .header("content-type", "application/json")
                            .header("Authorization", "Basic " + validAuthorizationToken))
                    .andExpect(status().isOk());

            verify(service, times(1)).changeLikeStatus(likeImage);
        }

        @Test
        void shouldNotBeAbleToChangeLikeStatusWhenUserIsLoggedOut() throws Exception {
            LikeImage likeImage = LikeImage.builder()
                    .userid(userId)
                    .imageid(imageId)
                    .status(status).build();

            mvc.perform(put("/images")
                            .content(mapper.writeValueAsString(likeImage))
                            .header("content-type", "application/json"))
                    .andExpect(status().isUnauthorized());

            verify(service, times(0)).changeLikeStatus(likeImage);

        }
    }

    @Nested
    class AddToFavorite {

        @Test
        void shouldBeAbleToChangeFavouriteStatusWhenUserIsLoggedIn() throws Exception {
            FavouriteImage favouriteImage = FavouriteImage.builder()
                    .userid(userId)
                    .imageid(imageId)
                    .favouritestatus(status).build();

            when(service.changeFavouriteStatus(favouriteImage)).thenReturn(1);

            mvc.perform(put("/images/favourite-image")
                            .content(mapper.writeValueAsString(favouriteImage))
                            .header("content-type", "application/json")
                            .header("Authorization", "Basic " + validAuthorizationToken))
                    .andExpect(status().isOk());

            verify(service, times(1)).changeFavouriteStatus(favouriteImage);
        }

        @Test
        void shouldNotBeAbleToChangeFavouriteStatusWhenUserIsLoggedOut() throws Exception {
            FavouriteImage favouriteImage = FavouriteImage.builder()
                    .userid(userId)
                    .imageid(imageId)
                    .favouritestatus(status).build();

            when(service.changeFavouriteStatus(favouriteImage)).thenReturn(1);

            mvc.perform(put("/images/favourite-image")
                            .content(mapper.writeValueAsString(favouriteImage))
                            .header("content-type", "application/json"))
                    .andExpect(status().isUnauthorized());

            verify(service, times(0)).changeFavouriteStatus(favouriteImage);
        }
    }

}