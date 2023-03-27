package com.jobis.refund.domain.User.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SzsUserTokenDto {
    private String id;
    
    private String userId;

    private String password;

    private String name;

    private String regNo;

    private String accessToken;

    private String refreshToken;

    @Builder
    public SzsUserTokenDto(String id, String userId, String password, String name, String regNo, String accessToken, String refreshToken){
        this.id = id;
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.regNo = regNo;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
