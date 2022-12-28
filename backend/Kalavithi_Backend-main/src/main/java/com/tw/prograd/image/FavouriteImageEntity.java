package com.tw.prograd.image;

import lombok.*;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.Objects;
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity(name="favourite_image")
@IdClass(UserFavouriteId.class)
public class FavouriteImageEntity {
    @Id
    @Column(name="userid")
    private int userid;
    @Id
    @Column(name="imageid",nullable = false)
    private int imageid;

    @Column(nullable = false)
    private boolean favouritestatus;


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

    public boolean getFavouritestatus() {
        return favouritestatus;
    }

    public void setFavouritestatus(boolean favouritestatus) {
        this.favouritestatus = favouritestatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FavouriteImageEntity)) return false;
        FavouriteImageEntity that = (FavouriteImageEntity) o;
        return userid == that.userid && imageid == that.imageid && favouritestatus == that.favouritestatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userid, imageid, favouritestatus);
    }
}
