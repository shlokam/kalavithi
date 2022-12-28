package com.tw.prograd.image;


import java.io.Serializable;
import java.util.Objects;


public class UserLikedId implements Serializable {
    private int userid;
    private int imageid;

    public UserLikedId() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserLikedId that = (UserLikedId) o;
        return userid == that.userid && imageid == that.imageid;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userid, imageid);
    }

    public UserLikedId(int user_id, int image_id) {
        this.userid = user_id;
        this.imageid = image_id;
    }
}
