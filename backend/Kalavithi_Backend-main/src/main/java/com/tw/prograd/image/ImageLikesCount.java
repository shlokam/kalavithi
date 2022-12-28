package com.tw.prograd.image;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class ImageLikesCount {
    @Id
    private int imageid;
    private  int likescount;

    public ImageLikesCount(int imageid, int count) {
        this.imageid = imageid;
        this.likescount = count;
    }

    public ImageLikesCount() {
    }

    public int getImageid() {
        return imageid;
    }

    public void setImageid(int imageid) {
        this.imageid = imageid;
    }

    public int getLikescount() {
        return likescount;
    }

    public void setLikescount(int likescount) {
        this.likescount = likescount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImageLikesCount that = (ImageLikesCount) o;
        return imageid == that.imageid && likescount == that.likescount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(imageid, likescount);
    }
}
