package com.tw.prograd.image;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity(name="like_image")
@IdClass(UserLikedId.class)
public class LikeImageEntity {
    @Id
    @Column(name="userid")
    private int userid;
    @Id
    @Column(nullable = false)
    private int imageid;

    @Column(nullable = false)
    private boolean status;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LikeImageEntity that = (LikeImageEntity) o;
        return userid == that.userid && imageid == that.imageid && status==that.status;
    }


    @Override
    public int hashCode() {
        return Objects.hash(userid, imageid,status);
    }

    public int getUser_id() {
        return userid;
    }

    public void setUser_id(int user_id) {
        this.userid = user_id;
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
}
