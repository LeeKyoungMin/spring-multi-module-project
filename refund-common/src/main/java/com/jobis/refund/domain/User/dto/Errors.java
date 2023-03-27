package com.jobis.refund.domain.User.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Errors {
    private String code;
    private String message;
}
