package com.jobis.refund.domain.User.dto;

import lombok.Getter;

@Getter
public class DeductionDto {
    private String id;
    private String price;
    private String gubun;
    private String szsUserId;

    public DeductionDto(String id, String price, String gubun, String szsUserId) {
        this.id = id;
        this.price = price;
        this.gubun = gubun;
        this.szsUserId = szsUserId;
    }

}
