package com.jobis.refund.domain.User.dto;

import java.util.Objects;

import javax.validation.constraints.NotNull;

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

    public SzsUserCommand() {}

    public SzsUserCommand(String userId, String password, String name, String regNo){
        this.userId = Objects.requireNonNull(userId, "userId must not be null");
        this.password = Objects.requireNonNull(password, "password must not be null");
        this.name = Objects.requireNonNull(name, "name must not be null");
        this.regNo = Objects.requireNonNull(regNo, "regNo must not be null");
    }

}

