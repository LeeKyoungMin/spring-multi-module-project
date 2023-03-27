package com.jobis.refund.domain.User.dto;

import java.util.Objects;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SzsUserReadCommand {

    @NotNull
    private String name;

    @NotNull
    private String regNo;

    public SzsUserReadCommand() {}

    public SzsUserReadCommand(String userId, String password, String name, String regNo){
        this.name = Objects.requireNonNull(name, "name must not be null");
        this.regNo = Objects.requireNonNull(regNo, "regNo must not be null");
    }

}

