package com.jobis.refund.config.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResponse {
    
    private final String code;
    private final String messsge;
}
