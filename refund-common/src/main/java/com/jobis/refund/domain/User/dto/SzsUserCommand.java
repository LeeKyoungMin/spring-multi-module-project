package com.jobis.refund.domain.User.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SzsUserCommand {

    private String userId;

    private String password;

    private String name;

    private String regNo;

}

