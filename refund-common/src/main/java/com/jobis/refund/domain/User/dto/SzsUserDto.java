package com.jobis.refund.domain.User.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SzsUserDto {
    
    private String id;

    private String userId;

    private String password;

    private String name;

    private String regNo;

    @Builder
    public SzsUserDto(String id, String userId, String password, String name, String regNo){
        this.id = id;
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.regNo = regNo;
    }
}
