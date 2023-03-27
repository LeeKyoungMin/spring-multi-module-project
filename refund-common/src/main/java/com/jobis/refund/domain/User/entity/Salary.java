package com.jobis.refund.domain.User.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

import com.jobis.refund.domain.User.dto.SalaryDto;

import lombok.Builder;
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

    Salary(){}

    @Builder
    public Salary(String id, String incomeStatement, String totalPayment, String workStartDate, String company, String name, String paymentDate, String workEndDate, String regNo, String businessNo){
        this.id = id;
        this.incomeStatement = incomeStatement;
        this.totalPayment = totalPayment;
        this.workStartDate = workStartDate;
        this.workEndDate = workEndDate;
        this.company = company;
        this.name = name;
        this.paymentDate = paymentDate;
        this.workEndDate = workEndDate;
        this.workStartDate = workStartDate;
        this.regNo = regNo;
        this.businessNo = businessNo;
    }

    public static SalaryDto toDto(Salary salary) {
        return new SalaryDto(salary.getId(), salary.getIncomeStatement(), salary.getTotalPayment(),
                salary.getWorkStartDate(), salary.getCompany(), salary.getName(),
                salary.getPaymentDate(), salary.getWorkEndDate(), salary.getRegNo(),
                salary.getBusinessNo());
    }
}
