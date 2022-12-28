package com.tw.prograd.image;


import java.io.Serializable;
import java.util.Objects;


public class UserFavouriteId implements Serializable {
    private int userid;
    private int imageid;

    public UserFavouriteId() {

    }


    public UserFavouriteId(int userid, int imageid) {
        this.userid = userid;
        this.imageid = imageid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserFavouriteId)) return false;
        UserFavouriteId that = (UserFavouriteId) o;
        return userid == that.userid && imageid == that.imageid;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userid, imageid);
    }
}
