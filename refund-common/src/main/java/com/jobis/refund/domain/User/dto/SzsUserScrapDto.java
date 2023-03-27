package com.jobis.refund.domain.User.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SzsUserScrapDto {
    private String status;
    private Value data;
    private List<Errors> errors;
}
