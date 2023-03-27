package com.jobis.refund.domain.User.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.hibernate.annotations.GenericGenerator;

import com.jobis.refund.domain.User.dto.TaxAmountDto;

import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
public class TaxAmount {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private String calculatedTaxAmount;

    @OneToOne
    @JoinColumn(name = "szs_user_id")
    private SzsUser szsUser;

    TaxAmount(){}

    @Builder
    public TaxAmount(String id, String calculatedTaxAmount){
        this.id = id;
        this.calculatedTaxAmount = calculatedTaxAmount;
    }

    public static TaxAmountDto toDto(TaxAmount taxAmount) {
        return new TaxAmountDto(taxAmount.getId(), taxAmount.getCalculatedTaxAmount(),
                taxAmount.getSzsUser().getId());
    }
}
