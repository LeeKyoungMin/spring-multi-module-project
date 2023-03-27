package com.jobis.refund.service;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.jobis.refund.config.apilist.ApiWhiteList;
import com.jobis.refund.config.exception.RefundException;
import com.jobis.refund.config.exception.StatusEnum;
import com.jobis.refund.config.userlist.SzsUserList;
import com.jobis.refund.domain.User.dto.SzsUserCommand;
import com.jobis.refund.domain.User.dto.SzsUserDto;
import com.jobis.refund.domain.User.dto.SzsUserTokenDto;
import com.jobis.refund.domain.User.entity.SzsUser;
import com.jobis.refund.repository.user.SzsUserRepository;
import com.jobis.refund.security.jwt.JwtProvider;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    
    private final SzsUserRepository szsUserRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtProvider jwtProvider;

    public SzsUserDto getUsers(HttpServletRequest request) {

        String accessToken = request.getHeader("Authorization");
        String userId = jwtProvider.getUserInfo(accessToken);

        SzsUser szsUser = szsUserRepository.findByUserId(userId)
                                            .orElseThrow(() -> new RefundException(StatusEnum.USER_NOT_FOUND, StatusEnum.USER_NOT_FOUND.getDescription()));

        SzsUserDto szsUserDto = szsUser.toDto();
        return szsUserDto;
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
                                 .regNo(passwordEncoder.encode(command.getRegNo()))
                                 .build();

        SzsUser user = szsUserRepository.save(szsUser);

        SzsUserDto szsUserDto = user.toDto();

        return szsUserDto;
    }

    public SzsUserTokenDto login(SzsUserDto szsUserDto) {

        if(!StringUtils.hasText(szsUserDto.getUserId()) || !StringUtils.hasText(szsUserDto.getPassword())){
            throw new RefundException(StatusEnum.USER_LOGIN_FAIL, StatusEnum.USER_LOGIN_FAIL.getDescription());
        }

        SzsUser existedUser = szsUserRepository.findByUserIdAndPassword(szsUserDto.getUserId(), szsUserDto.getPassword())
                                                .orElseThrow(() -> new RefundException(StatusEnum.USER_NOT_FOUND, StatusEnum.USER_NOT_FOUND.getDescription()));

        String accessToken = jwtProvider.createToken(szsUserDto.getUserId());
        String refreshToken = jwtProvider.createRefreshToken(szsUserDto.getUserId());

        SzsUserDto currentUser = existedUser.toDto();

        SzsUserTokenDto szsUserTokenDto = SzsUserTokenDto.builder()
                                                         .userId(currentUser.getUserId())
                                                         .name(currentUser.getName())
                                                         .accessToken(accessToken)
                                                         .refreshToken(refreshToken)
                                                         .build();
        return szsUserTokenDto;    
    }
    
}
