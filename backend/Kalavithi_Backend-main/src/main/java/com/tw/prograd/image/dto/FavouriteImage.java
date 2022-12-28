package com.tw.prograd.image.dto;

import java.util.Objects;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Getter
@Setter
public class FavouriteImage {
    private int userid;
    private int imageid;
    private boolean favouritestatus;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FavouriteImage)) return false;
        FavouriteImage that = (FavouriteImage) o;
        return userid == that.userid && imageid == that.imageid && favouritestatus == that.favouritestatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userid, imageid, favouritestatus);
    }
}