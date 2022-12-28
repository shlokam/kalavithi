package com.tw.prograd.image.dto;

import lombok.Builder;

import java.util.Objects;

@Builder
public class LikeImage {
    private int userid;
    private int imageid;
    private boolean status;

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getImageid() {
        return imageid;
    }

    public void setImageid(int imageid) {
        this.imageid = imageid;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public LikeImage(int user_id, int image_id, boolean status) {
        this.userid = user_id;
        this.imageid = image_id;
        this.status = status;
    }

    public LikeImage() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LikeImage likeImage = (LikeImage) o;
        return userid == likeImage.userid && imageid == likeImage.imageid && status == likeImage.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userid, imageid, status);
    }
}
