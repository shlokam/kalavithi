package com.tw.prograd.image.comments;

import com.tw.prograd.image.ImageRepository;
import com.tw.prograd.image.comments.dto.ImageCommentDTO;
import com.tw.prograd.image.comments.entity.ImageCommentEntity;
import com.tw.prograd.image.comments.exception.CommentsLimitExceedException;
import com.tw.prograd.image.comments.repository.ImageCommentRepository;
import com.tw.prograd.image.comments.service.ImageCommentService;
import com.tw.prograd.image.exception.ImageNotFoundException;
import com.tw.prograd.user.entity.UserEntity;
import com.tw.prograd.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ImageCommentServiceTest {

    @Mock
    private ImageCommentRepository imageCommentRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ImageRepository imageRepository;

    @InjectMocks
    private ImageCommentService imageCommentService;

    @Test
    public void saveImageComment() {
        ImageCommentDTO imageCommentDTO = ImageCommentDTO.builder()
                .userId(1)
                .imageId(1)
                .comment("Image is beautiful").build();

        ImageCommentEntity imageCommentEntity = ImageCommentEntity.builder()
                .userId(imageCommentDTO.getUserId())
                .imageId(imageCommentDTO.getImageId())
                .comment(imageCommentDTO.getComment())
                .build();

        ImageCommentEntity imageCommentEntityWithID = ImageCommentEntity.builder()
                .commentId(1)
                .userId(imageCommentDTO.getUserId())
                .imageId(imageCommentDTO.getImageId())
                .comment(imageCommentDTO.getComment())
                .commentTime(String.valueOf(Calendar.getInstance().getTimeInMillis()))
                .build();


        when(userRepository.existsById(imageCommentDTO.getUserId())).thenReturn(true);
        when(imageRepository.existsById(imageCommentDTO.getImageId())).thenReturn(true);


        when(imageCommentRepository.save(imageCommentEntity)).thenReturn(imageCommentEntityWithID);

        int id = imageCommentService.addComment(imageCommentDTO);

        assertThat(id).isSameAs(imageCommentEntityWithID.getCommentId());

        verify(userRepository).existsById(imageCommentDTO.getUserId());
        verify(imageRepository).existsById(imageCommentDTO.getImageId());
        verify(imageCommentRepository).save(imageCommentEntity);


    }

    @Test
    void shouldThrowExceptionWhenCommentIsMoreThanThree() {
        ImageCommentDTO imageCommentDTO = ImageCommentDTO.builder()
                .userId(2)
                .imageId(2)
                .comment("Image is beautiful").build();
        ImageCommentEntity imageCommentEntity = ImageCommentEntity.builder()
                .userId(imageCommentDTO.getUserId())
                .imageId(imageCommentDTO.getImageId())
                .comment(imageCommentDTO.getComment())
                .build();

        List<ImageCommentEntity> imageCommentEntityList = new ArrayList<>();
        imageCommentEntityList.add(imageCommentEntity);
        imageCommentEntityList.add(imageCommentEntity);
        imageCommentEntityList.add(imageCommentEntity);

        when(imageCommentRepository.findByUserIdAndImageId(imageCommentDTO.getUserId(), imageCommentDTO.getImageId())).thenReturn(imageCommentEntityList);

        assertThrows(CommentsLimitExceedException.class, () -> imageCommentService.addComment(imageCommentDTO));

        verify(imageCommentRepository).findByUserIdAndImageId(imageCommentDTO.getUserId(), imageCommentDTO.getImageId());
    }


    @Test
    void shouldGetCommentsByImageId(){
        ImageCommentDTO imageCommentDTO = ImageCommentDTO.builder()
                .userId(2)
                .imageId(2)
                .comment("Image is beautiful").build();
        ImageCommentEntity imageCommentEntity = ImageCommentEntity.builder()
                .userId(imageCommentDTO.getUserId())
                .imageId(imageCommentDTO.getImageId())
                .comment(imageCommentDTO.getComment())
                .build();

        List<ImageCommentEntity> imageCommentEntityList = new ArrayList<>();
        imageCommentEntityList.add(imageCommentEntity);
        imageCommentEntityList.add(imageCommentEntity);
        imageCommentEntityList.add(imageCommentEntity);

        when(imageCommentRepository.findByImageId(2)).thenReturn(imageCommentEntityList);

        assertThat(imageCommentService.getComment(2)).isEqualTo(imageCommentEntityList);

        verify(imageCommentRepository).findByImageId(2);
    }

    @Test
    void shouldThrowExceptionWhenUserIdDoesNotExists() {
        ImageCommentDTO imageCommentDTO = ImageCommentDTO.builder()
                .userId(100)
                .imageId(2)
                .comment("Image is beautiful").build();
        ImageCommentEntity imageCommentEntity = ImageCommentEntity.builder()
                .userId(imageCommentDTO.getUserId())
                .imageId(imageCommentDTO.getImageId())
                .comment(imageCommentDTO.getComment())
                .build();

        when(userRepository.existsById(imageCommentDTO.getUserId())).thenReturn(false);

        assertThrows(UsernameNotFoundException.class, () -> imageCommentService.addComment(imageCommentDTO));

        verify(userRepository).existsById(imageCommentDTO.getUserId());
    }

    @Test
    void shouldThrowExceptionWhenImageIdDoesNotExists() {

       // UserEntity userEntity
        ImageCommentDTO imageCommentDTO = ImageCommentDTO.builder()
                .userId(2)
                .imageId(200)
                .comment("Image is beautiful").build();
        ImageCommentEntity imageCommentEntity = ImageCommentEntity.builder()
                .userId(imageCommentDTO.getUserId())
                .imageId(imageCommentDTO.getImageId())
                .comment(imageCommentDTO.getComment())
                .build();


        when(userRepository.existsById(imageCommentDTO.getUserId())).thenReturn(true);
        when(imageRepository.existsById(imageCommentDTO.getImageId())).thenReturn(false);

        assertThrows(ImageNotFoundException.class, () -> imageCommentService.addComment(imageCommentDTO));

        verify(userRepository).existsById(imageCommentDTO.getUserId());
        verify(imageRepository).existsById(imageCommentDTO.getImageId());
    }
}
