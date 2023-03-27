package com.jobis.refund.repository.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jobis.refund.domain.User.entity.SzsUser;

public interface SzsUserRepository extends JpaRepository<SzsUser, String>{
    
    Optional<SzsUser> findByUserId(String userId);

    Optional<SzsUser> findByUserIdAndPassword(String userId, String password);
}
