package com.jobis.refund.service;

import org.springframework.stereotype.Service;

import com.jobis.refund.domain.User.dto.SzsUserDto;
import com.jobis.refund.domain.User.entity.SzsUser;
import com.jobis.refund.repository.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    
    private final UserRepository userRepository;

    public SzsUserDto getUsers() {

        SzsUser user = userRepository.findById("test").orElseThrow();

        SzsUserDto userDto = user.toDto();

        return userDto;
    }
    
}
