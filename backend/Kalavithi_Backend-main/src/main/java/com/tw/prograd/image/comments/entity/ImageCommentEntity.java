package com.tw.prograd.image.comments.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity(name = "image_comment")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ImageCommentEntity {
    @Id
    @Column(name = "comment_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer commentId;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(name = "image_id", nullable = false)
    private Integer imageId;

    @Column(name = "comment", nullable = false)
    private String comment;


    @Column(name = "comment_time")
    private String commentTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImageCommentEntity that = (ImageCommentEntity) o;
        return Objects.equals(commentId, that.commentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(commentId);
    }

    @Override
    public String toString() {
        return "ImageCommentEntity{" +
                "commentId=" + commentId +
                ", userId=" + userId +
                ", imageId=" + imageId +
                ", comment='" + comment + '\'' +
                ", commentTime=" + commentTime +
                '}';
    }
}
