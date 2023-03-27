package com.jobis.refund.domain.User.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

import com.jobis.refund.domain.User.dto.SalaryDto;

import lombok.Getter;

@Entity
@Getter
public class Salary {
    
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
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

    public static SalaryDto toDto(Salary salary) {
        return new SalaryDto(salary.getId(), salary.getIncomeStatement(), salary.getTotalPayment(),
                salary.getWorkStartDate(), salary.getCompany(), salary.getName(),
                salary.getPaymentDate(), salary.getWorkEndDate(), salary.getRegNo(),
                salary.getBusinessNo());
    }
}
