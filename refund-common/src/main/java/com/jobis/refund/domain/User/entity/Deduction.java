package com.jobis.refund.domain.User.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;

import com.jobis.refund.domain.User.dto.DeductionDto;

import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
public class Deduction {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private String price;

    private String gubun;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "szs_user_id")
    private SzsUser szsUser;

    Deduction(){}

    @Builder
    public Deduction(String id, String price, String gubun){
        this.id = id;
        this.price = price;
        this.gubun = gubun;
    }

    public static DeductionDto toDto(Deduction deduction) {
        return new DeductionDto(deduction.getId(), deduction.getPrice(),
                deduction.getGubun(), deduction.getSzsUser().getId());
    }
}
