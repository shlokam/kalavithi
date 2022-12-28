package com.tw.prograd.image;

import com.tw.prograd.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface LikeImageRepository extends JpaRepository<LikeImageEntity, Integer> {
   List<LikeImageEntity> findByUserid(int userid);
   LikeImageEntity getByUseridAndImageid(int userid,int imageid);

   @Query( value="Select count(imageid), imageid from like_image where status=true group by imageid",nativeQuery = true)
   List<Object[]> findLikeCount();
}
