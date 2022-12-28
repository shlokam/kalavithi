package com.tw.prograd.image.comments;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tw.prograd.image.comments.dto.ImageCommentDTO;
import com.tw.prograd.image.comments.entity.ImageCommentEntity;
import com.tw.prograd.image.comments.service.ImageCommentService;
import com.tw.prograd.user.dto.UserLoginDTO;
import com.tw.prograd.user.entity.UserAuthEntity;
import com.tw.prograd.user.entity.UserEntity;
import com.tw.prograd.user.repository.UserAuthRepository;
import com.tw.prograd.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;


@SpringBootTest
@AutoConfigureMockMvc
public class ImageCommentControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private ImageCommentService imageCommentService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserAuthRepository userAuthRepository;


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

    @Test
    public void shouldBeAbleToAddCommentWhenUserIsLoggedIn() throws Exception {
        ImageCommentDTO imageCommentDTO = ImageCommentDTO.builder()
                .userId(1)
                .imageId(1)
                .comment("Image is beautiful").build();

        when(imageCommentService.addComment(imageCommentDTO)).thenReturn(1);

        mvc.perform(post("/images/comments")
                        .content(mapper.writeValueAsString(imageCommentDTO))
                        .header("content-type", "application/json")
                        .header("Authorization", "Basic " + validAuthorizationToken))
                .andExpect(status().isCreated());

        verify(imageCommentService).addComment(imageCommentDTO);


    }

    @Test
    public void shouldNotBeAbleToAddCommentWhenUserIsNotLoggedIn() throws Exception {
        ImageCommentDTO imageCommentDTO = ImageCommentDTO.builder()
                .userId(1)
                .imageId(1)
                .comment("Image is beautiful").build();


        when(imageCommentService.addComment(imageCommentDTO)).thenReturn(1);

        mvc.perform(post("/images/comments")
                        .content(mapper.writeValueAsString(imageCommentDTO))
                        .header("content-type", "application/json")
                        .header("Authorization", "Basic " + invalidAuthorizationToken))
                .andExpect(status().isUnauthorized());

        verify(imageCommentService, times(0)).addComment(imageCommentDTO);

    }

    @Test
    public void shouldThrowExceptionWhenUserIdIsNull() throws Exception {
        ImageCommentDTO imageCommentDTO = ImageCommentDTO.builder()
                .imageId(1)
                .comment("Image is beautiful").build();

        mvc.perform(post("/images/comments")
                        .content(mapper.writeValueAsString(imageCommentDTO))
                        .header("content-type", "application/json")
                        .header("Authorization", "Basic " + validAuthorizationToken))
                .andExpect(status().isPreconditionFailed());
    }

    @Test
    public void shouldThrowExceptionWhenUserIdEqualToZero() throws Exception {
        ImageCommentDTO imageCommentDTO = ImageCommentDTO.builder()
                .userId(0)
                .imageId(1)
                .comment("Image is beautiful").build();

        mvc.perform(post("/images/comments")
                        .content(mapper.writeValueAsString(imageCommentDTO))
                        .header("content-type", "application/json")
                        .header("Authorization", "Basic " + validAuthorizationToken))
                .andExpect(status().isPreconditionFailed());
    }

    @Test
    public void shouldThrowExceptionWhenUserIdLessThanZero() throws Exception {
        ImageCommentDTO imageCommentDTO = ImageCommentDTO.builder()
                .userId(-2)
                .imageId(1)
                .comment("Image is beautiful").build();

        mvc.perform(post("/images/comments")
                        .content(mapper.writeValueAsString(imageCommentDTO))
                        .header("content-type", "application/json")
                        .header("Authorization", "Basic " + validAuthorizationToken))
                .andExpect(status().isPreconditionFailed());
    }

    @Test
    public void shouldThrowExceptionWhenImageIdIsNull() throws Exception {
        ImageCommentDTO imageCommentDTO = ImageCommentDTO.builder()
                .userId(1)
                .comment("Image is beautiful").build();

        mvc.perform(post("/images/comments")
                        .content(mapper.writeValueAsString(imageCommentDTO))
                        .header("content-type", "application/json")
                        .header("Authorization", "Basic " + validAuthorizationToken))
                .andExpect(status().isPreconditionFailed());
    }

    @Test
    public void shouldThrowExceptionWhenImageIdLessThanZero() throws Exception {
        ImageCommentDTO imageCommentDTO = ImageCommentDTO.builder()
                .userId(1)
                .imageId(-1)
                .comment("Image is beautiful").build();

        mvc.perform(post("/images/comments")
                        .content(mapper.writeValueAsString(imageCommentDTO))
                        .header("content-type", "application/json")
                        .header("Authorization", "Basic " + validAuthorizationToken))
                .andExpect(status().isPreconditionFailed());
    }

    @Test
    public void shouldThrowExceptionWhenImageIdIsEqualToZero() throws Exception {
        ImageCommentDTO imageCommentDTO = ImageCommentDTO.builder()
                .userId(1)
                .imageId(0)
                .comment("Image is beautiful").build();

        mvc.perform(post("/images/comments")
                        .content(mapper.writeValueAsString(imageCommentDTO))
                        .header("content-type", "application/json")
                        .header("Authorization", "Basic " + validAuthorizationToken))
                .andExpect(status().isPreconditionFailed());
    }

    @Test
    public void shouldThrowExceptionWhenCommentIsNull() throws Exception {
        ImageCommentDTO imageCommentDTO = ImageCommentDTO.builder()
                .userId(1)
                .imageId(1)
                .build();

        mvc.perform(post("/images/comments")
                        .content(mapper.writeValueAsString(imageCommentDTO))
                        .header("content-type", "application/json")
                        .header("Authorization", "Basic " + validAuthorizationToken))
                .andExpect(status().isPreconditionFailed());
    }

    @Test
    public void shouldThrowExceptionWhenCommentIsEmpty() throws Exception {
        ImageCommentDTO imageCommentDTO = ImageCommentDTO.builder()
                .userId(1)
                .imageId(1)
                .comment("").build();

        mvc.perform(post("/images/comments")
                        .content(mapper.writeValueAsString(imageCommentDTO))
                        .header("content-type", "application/json")
                        .header("Authorization", "Basic " + validAuthorizationToken))
                .andExpect(status().isPreconditionFailed());
    }

    @Test
    public void shouldThrowExceptionWhenCommentCharacterIsGreaterThanFifty() throws Exception {
        ImageCommentDTO imageCommentDTO = ImageCommentDTO.builder()
                .userId(1)
                .imageId(1)
                .comment("Beautiful Beautiful Beautiful Beautiful Beautiful Beautiful Beautiful Beautiful Beautiful Beautiful Beautiful Beautiful Beautiful ").build();

        mvc.perform(post("/images/comments")
                        .content(mapper.writeValueAsString(imageCommentDTO))
                        .header("content-type", "application/json")
                        .header("Authorization", "Basic " + validAuthorizationToken))
                .andExpect(status().isPreconditionFailed());
    }

    @Test
    public void shouldBeAbleToGetCommentsByImageId() throws Exception {
        Calendar calendar = Calendar.getInstance();
        ImageCommentEntity imageCommentEntity = ImageCommentEntity.builder()
                .commentId(1)
                .userId(2)
                .imageId(5)
                .comment("Beautiful")
                .commentTime(String.valueOf(calendar.getTimeInMillis()))
                .build();

        List<ImageCommentEntity> imageCommentEntityList = new ArrayList<>();
        imageCommentEntityList.add(imageCommentEntity);

        when(imageCommentService.getComment(5)).thenReturn(imageCommentEntityList);

        mvc.perform(get("/images/comments/5")
                .header("Authorization", "Basic " + validAuthorizationToken))
                .andExpect(content().string(mapper.writeValueAsString(imageCommentEntityList)))
                .andExpect(status().isOk());

        verify(imageCommentService).getComment(5);
    }

    @Test
    public void shouldNotBeAbleToGetCommentsWhenUserIsNotLoggedIn() throws Exception {
        ImageCommentEntity imageCommentEntity = ImageCommentEntity.builder()
                .commentId(1)
                .userId(2)
                .imageId(5)
                .comment("Beautiful")
                .commentTime(String.valueOf(Calendar.getInstance().getTimeInMillis()))
                .build();

        List<ImageCommentEntity> imageCommentEntityList = new ArrayList<>();
        imageCommentEntityList.add(imageCommentEntity);

        when(imageCommentService.getComment(5)).thenReturn(imageCommentEntityList);

        mvc.perform(get("/images/comments/5")
                .header("Authorization", "Basic " + invalidAuthorizationToken))
                .andExpect(status().isUnauthorized());

        verify(imageCommentService,times(0)).getComment(5);
    }

}
