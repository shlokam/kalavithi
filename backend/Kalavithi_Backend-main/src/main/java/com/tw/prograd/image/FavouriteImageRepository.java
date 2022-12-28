package com.tw.prograd.image;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository

    public interface FavouriteImageRepository extends JpaRepository<FavouriteImageEntity, Integer> {
        List<FavouriteImageEntity> findByUserid(int userid);
        FavouriteImageEntity getByUseridAndImageid(int userid,int imageid);


    }
