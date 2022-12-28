package com.tw.prograd.user.repository;

import com.tw.prograd.user.entity.UserAuthEntity;
import com.tw.prograd.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserAuthRepository extends JpaRepository<UserAuthEntity, Integer> {
}
