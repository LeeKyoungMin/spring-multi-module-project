package com.jobis.refund.domain.User.dto;

import lombok.Getter;

@Getter
public class SalaryDto {
    private String id;
    private String incomeStatement;
    private String totalPayment;
    private String workStartDate;
    private String company;
    private String name;
    private String paymentDate;
    private String workEndDate;
    private String regNo;
    private String businessNo;

    public SalaryDto(String id, String incomeStatement, String totalPayment, String workStartDate,
                     String company, String name, String paymentDate, String workEndDate,
                     String regNo, String businessNo) {
        this.id = id;
        this.incomeStatement = incomeStatement;
        this.totalPayment = totalPayment;
        this.workStartDate = workStartDate;
        this.company = company;
        this.name = name;
        this.paymentDate = paymentDate;
        this.workEndDate = workEndDate;
        this.regNo = regNo;
        this.businessNo = businessNo;
    }

}

