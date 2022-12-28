package com.tw.prograd.image.comments.service;

import com.tw.prograd.image.ImageRepository;
import com.tw.prograd.image.comments.dto.ImageCommentDTO;
import com.tw.prograd.image.comments.entity.ImageCommentEntity;
import com.tw.prograd.image.comments.exception.CommentsLimitExceedException;
import com.tw.prograd.image.comments.repository.ImageCommentRepository;
import com.tw.prograd.image.exception.ImageNotFoundException;
import com.tw.prograd.user.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class ImageCommentService {

    private final ImageCommentRepository imageCommentRepository;
    private final UserRepository userRepository;
    private final ImageRepository imageRepository;

    public ImageCommentService(ImageCommentRepository imageCommentRepository, UserRepository userRepository, ImageRepository imageRepository) {
        this.imageCommentRepository = imageCommentRepository;
        this.userRepository = userRepository;
        this.imageRepository = imageRepository;
    }

    public int addComment(ImageCommentDTO imageCommentDTO) {
        System.out.println(imageCommentDTO);
        Calendar calendar = Calendar.getInstance();
        //Returns current time in millis
        long timeMilli2 = calendar.getTimeInMillis();
        System.out.println(timeMilli2);
        ImageCommentEntity imageCommentEntity = ImageCommentEntity.builder()

                .userId(imageCommentDTO.getUserId())
                .imageId(imageCommentDTO.getImageId())
                .comment(imageCommentDTO.getComment()).commentTime(String.valueOf(calendar.getTimeInMillis())).
                build();

        List<ImageCommentEntity> listOfComments = imageCommentRepository.findByUserIdAndImageId(imageCommentDTO.getUserId(), imageCommentDTO.getImageId());
        if (listOfComments.size() == 3) {
            throw new CommentsLimitExceedException("More than three comments not allowed");
        }

        if(!userRepository.existsById(imageCommentDTO.getUserId())){
            throw new UsernameNotFoundException("User not exits");

        }
        if(!imageRepository.existsById(imageCommentDTO.getImageId())){
            throw new ImageNotFoundException("Image does not exits");
        }



        imageCommentEntity = imageCommentRepository.save(imageCommentEntity);

        System.out.println(imageCommentEntity);

        return imageCommentEntity.getCommentId();
    }

    public List<ImageCommentEntity> getComment(int imageId) {
        return imageCommentRepository.findByImageId(imageId);

    }
}
