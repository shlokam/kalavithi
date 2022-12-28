package com.tw.prograd.image.comments.controller;

import com.tw.prograd.dto.SuccessResponse;
import com.tw.prograd.image.comments.dto.ImageCommentDTO;
import com.tw.prograd.image.comments.entity.ImageCommentEntity;
import com.tw.prograd.image.comments.service.ImageCommentService;
import com.tw.prograd.user.entity.UserAuthEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/images")
public class ImageCommentController {

    @Autowired
    private ImageCommentService imageCommentService;

    @PostMapping("/comments")
    public ResponseEntity<Object> create(@RequestBody ImageCommentDTO imageCommentDTO, Authentication authentication) throws Exception {
        UserAuthEntity user = (UserAuthEntity) authentication.getPrincipal();

        if (imageCommentDTO.getUserId() == null || imageCommentDTO.getUserId() <= 0) {
            throw new IllegalArgumentException("User id cannot be null");
        }

        if (imageCommentDTO.getImageId() == null || imageCommentDTO.getImageId() <= 0) {
            throw new IllegalArgumentException("Image id cannot be null");
        }

        if (imageCommentDTO.getComment() == null || imageCommentDTO.getComment().isEmpty()) {
            throw new IllegalArgumentException("Comment cannot be empty");
        }

        if (imageCommentDTO.getComment().length() > 50) {
            throw new IllegalArgumentException("Max limit of character is 50 for comment");
        }

        int id = imageCommentService.addComment(imageCommentDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse(id));
    }

    @GetMapping("/comments/{imageId}")
    public ResponseEntity<List<ImageCommentEntity>> readComments(@PathVariable Integer imageId, Authentication authentication){

        UserAuthEntity user = (UserAuthEntity) authentication.getPrincipal();

        List<ImageCommentEntity> imageCommentEntityList = imageCommentService.getComment(imageId);
        return ResponseEntity.status(HttpStatus.OK).body(imageCommentEntityList);
    }
}
