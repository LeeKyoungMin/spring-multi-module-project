package com.jobis.refund.domain.User.dto;

import com.jobis.refund.domain.User.entity.SzsUser;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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

    public SzsUser toEntity(){
        return new SzsUser(this.id, this.userId, this.password, this.name, this.regNo);
    }
}
