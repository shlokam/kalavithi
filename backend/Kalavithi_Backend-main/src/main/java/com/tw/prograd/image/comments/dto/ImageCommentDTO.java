package com.tw.prograd.image.comments.dto;

import lombok.*;

import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Getter
@Setter
public class ImageCommentDTO {
    private Integer userId;
    private Integer imageId;
    private String comment;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImageCommentDTO that = (ImageCommentDTO) o;
        return Objects.equals(userId, that.userId) && Objects.equals(imageId, that.imageId) && Objects.equals(comment, that.comment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, imageId, comment);
    }
}
