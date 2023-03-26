package com.jobis.refund.config.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    public ResponseEntity<ErrorResponse> methodValidException(MethodArgumentNotValidException exception, HttpServletRequest httpServletRequest){
        
        log.error("MethodArgumentNotValidException url:{}", httpServletRequest.getRequestURI());
        
        BindingResult bindingResult = exception.getBindingResult();
        StatusEnum statusEnum = StatusEnum.BAD_REQUEST;

        StringBuilder builder = new StringBuilder();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            builder.append("[");
            builder.append(fieldError.getField());
            builder.append("](은)는 ");
            builder.append(fieldError.getDefaultMessage());
            builder.append(". ");
        }
        
        builder.deleteCharAt(builder.length() - 1);

        ErrorResponse response = new ErrorResponse(statusEnum.getCode(), StatusEnum.BAD_REQUEST.getDescription());
        return new ResponseEntity<>(response, HttpStatus.valueOf(statusEnum.getStatus()));
    }

    @ExceptionHandler(RefundException.class)
    protected ResponseEntity<ErrorResponse> handleRefundException(RefundException e) {
        StatusEnum statusEnum = e.getStatusEnum();
        ErrorResponse response = new ErrorResponse(statusEnum.getCode(), e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.valueOf(statusEnum.getStatus()));
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handleException(Exception e) {
        StatusEnum statusEnum = StatusEnum.INTERNAL_SERVER_ERROR;
        ErrorResponse response = new ErrorResponse(statusEnum.getCode(), e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.valueOf(statusEnum.getStatus()));
    }
}
