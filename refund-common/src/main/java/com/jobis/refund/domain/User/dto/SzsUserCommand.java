package com.jobis.refund.domain.User.dto;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SzsUserCommand {

    @NotNull
    private String userId;

    @NotNull
    private String password;

    @NotNull
    private String name;

    @NotNull
    private String regNo;

    @Builder
    public SzsUserCommand(String userId, String password, String name, String regNo){
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.regNo = regNo;
    }

    SzsUserCommand(){}
}

