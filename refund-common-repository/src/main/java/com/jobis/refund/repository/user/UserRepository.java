package com.jobis.refund.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jobis.refund.domain.User.entity.SzsUser;

public interface UserRepository extends JpaRepository<SzsUser, String>{
    
}
