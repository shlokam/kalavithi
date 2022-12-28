package com.tw.prograd.image.comments;

import com.tw.prograd.image.comments.dto.ImageCommentDTO;
import com.tw.prograd.image.comments.entity.ImageCommentEntity;
import com.tw.prograd.image.comments.repository.ImageCommentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ImageCommentRepositoryTest {
    @Autowired
    private ImageCommentRepository imageCommentRepository;

    @Test
    void shouldSaveComment() {
        ImageCommentDTO imageCommentDTO = ImageCommentDTO.builder()
                .userId(2)
                .imageId(2)
                .comment("Beautiful").build();

        ImageCommentEntity imageCommentEntity = ImageCommentEntity.builder()
                .userId(imageCommentDTO.getUserId())
                .imageId(imageCommentDTO.getImageId())
                .comment(imageCommentDTO.getComment())
                .build();


        imageCommentEntity = imageCommentRepository.save(imageCommentEntity);

        assertThat(imageCommentEntity.getCommentId()).isGreaterThan(0);
    }

    @Test
    void shouldReturnListOfImageCommentEntityWhenUserIdAndImageIdIsGiven() {
        ImageCommentDTO imageCommentDTO = ImageCommentDTO.builder()
                .userId(2)
                .imageId(2)
                .comment("Beautiful").build();

        ImageCommentEntity imageCommentEntity = ImageCommentEntity.builder()
                .userId(imageCommentDTO.getUserId())
                .imageId(imageCommentDTO.getImageId())
                .comment(imageCommentDTO.getComment())
                .build();

        imageCommentRepository.save(imageCommentEntity);

        List<ImageCommentEntity> imageCommentEntityList = imageCommentRepository.findByUserIdAndImageId(imageCommentDTO.getUserId(), imageCommentDTO.getImageId());

        assertThat(imageCommentEntityList.size()).isGreaterThan(0);
    }

    @Test
    void shouldReturnListOfImageCommentEntityWhenImageIdIsGiven() {
        ImageCommentDTO imageCommentDTO = ImageCommentDTO.builder()
                .userId(2)
                .imageId(2)
                .comment("Beautiful").build();

        ImageCommentEntity imageCommentEntity = ImageCommentEntity.builder()
                .userId(imageCommentDTO.getUserId())
                .imageId(imageCommentDTO.getImageId())
                .comment(imageCommentDTO.getComment())
                .build();

        imageCommentRepository.save(imageCommentEntity);

        List<ImageCommentEntity> imageCommentEntityList = imageCommentRepository.findByImageId(imageCommentDTO.getImageId());

        assertThat(imageCommentEntityList.size()).isGreaterThan(0);
    }


}
