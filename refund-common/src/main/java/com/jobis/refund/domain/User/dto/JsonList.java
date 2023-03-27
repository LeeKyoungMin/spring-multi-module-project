package com.jobis.refund.domain.User.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JsonList {
    private List<SalaryDto> 급여;
    private TaxAmountDto 산출세액;
    private List<DeductionDto> 소득공제;
}
