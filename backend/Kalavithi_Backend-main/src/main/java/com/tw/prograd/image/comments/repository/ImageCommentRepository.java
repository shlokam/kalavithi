package com.tw.prograd.image.comments.repository;

import com.tw.prograd.image.comments.entity.ImageCommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageCommentRepository extends JpaRepository<ImageCommentEntity, Integer> {

    List<ImageCommentEntity> findByUserIdAndImageId(Integer user_id, Integer image_id);

    List<ImageCommentEntity> findByImageId(Integer imageId);
}
