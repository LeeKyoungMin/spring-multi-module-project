package com.jobis.refund.domain.User.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RefundDto {
    private String 이름;
    private String 결정세액;
    private String 퇴직연금세액공제;
}
