package com.jobis.refund.domain.User.dto;

import lombok.Getter;

@Getter
public class TaxAmountDto {
    private String id;
    private String calculatedTaxAmount;
    private String szsUserId;

    public TaxAmountDto(String id, String calculatedTaxAmount, String szsUserId) {
        this.id = id;
        this.calculatedTaxAmount = calculatedTaxAmount;
        this.szsUserId = szsUserId;
    }

}

