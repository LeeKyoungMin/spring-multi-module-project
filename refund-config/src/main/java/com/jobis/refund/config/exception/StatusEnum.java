package com.jobis.refund.config.exception;

import lombok.Getter;

@Getter
public enum StatusEnum {

    BAD_REQUEST(400, "REFUDN-400", "잘못된 요청입니다.")
    
    ,TOKEN_NOT_EXIST(404, "REFUND-401", "토큰이 존재하지 않습니다.")

    ,INTERNAL_SERVER_ERROR(500, "REFUND-500", "내부 서버 에러 입니다.")

    ,USERID_ALREAD_EXIST(600, "REFUND-600", "해당 유저아이디는 존재합니다.")
    ;

    private int status;
    private String code;
    private String description;

    StatusEnum(int status, String code, String description) {
        this.status = status;
        this.code = code;
        this.description = description;
    }

}
