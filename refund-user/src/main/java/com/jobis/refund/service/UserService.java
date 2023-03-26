package com.jobis.refund.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jobis.refund.config.exception.RefundException;
import com.jobis.refund.config.exception.StatusEnum;
import com.jobis.refund.config.userlist.SzsUserList;
import com.jobis.refund.domain.User.dto.SzsUserCommand;
import com.jobis.refund.domain.User.dto.SzsUserDto;
import com.jobis.refund.domain.User.entity.SzsUser;
import com.jobis.refund.repository.user.SzsUserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    
    private final SzsUserRepository szsUserRepository;

    private final PasswordEncoder passwordEncoder;

    public SzsUserDto getUsers() {

        SzsUser user = szsUserRepository.findById("test").orElseThrow();

        SzsUserDto userDto = user.toDto();

        return userDto;
    }

    public SzsUserDto createUser(SzsUserCommand command) {

        if(!SzsUserList.getNames().contains(command.getName()) || !SzsUserList.getRegNos().contains(command.getRegNo())){
            throw new RefundException(StatusEnum.BAD_REQUEST, StatusEnum.BAD_REQUEST.getDescription());
        }

        Optional<SzsUser> existedUser = szsUserRepository.findByUserId(command.getUserId());
        if(existedUser.isPresent()){
            throw new RefundException(StatusEnum.USERID_ALREAD_EXIST, StatusEnum.USERID_ALREAD_EXIST.getDescription());
        }

        SzsUser szsUser = SzsUser.builder()
                                 .userId(command.getUserId())
                                 .name(command.getName())
                                 .password(passwordEncoder.encode(command.getPassword()))
                                 .regNo(command.getRegNo())
                                 .build();

        SzsUser user = szsUserRepository.save(szsUser);

        SzsUserDto szsUserDto = user.toDto();

        return szsUserDto;
    }
    
}
