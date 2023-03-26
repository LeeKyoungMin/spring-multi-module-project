package com.jobis.refund.config.exception;

import lombok.Getter;

@Getter
public class RefundException extends RuntimeException{
    private StatusEnum statusEnum;
    private String message;

    public RefundException(StatusEnum statusEnum, String message) {
        this.statusEnum = statusEnum;
        this.message = message;
    }
}
