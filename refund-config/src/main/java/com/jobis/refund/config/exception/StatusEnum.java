package com.jobis.refund.config.exception;

import lombok.Getter;

@Getter
public enum StatusEnum {

    BAD_REQUEST(400, "REFUDN-400", "잘못된 요청입니다.")
    
    ,TOKEN_NOT_EXIST(404, "REFUND-401", "토큰이 존재하지 않습니다.")

    ,INTERNAL_SERVER_ERROR(500, "REFUND-500", "내부 서버 에러 입니다.")

    ,USERID_ALREAD_EXIST(600, "REFUND-600", "해당 유저아이디는 존재합니다.")

    ,USER_LOGIN_FAIL(601, "REFUND-601", "로그인에 실패했습니다.")

    ,USER_NOT_FOUND(602, "REFUND-602", "유저를 찾을 수 없습니다.")
    
    ,SALARY_NOT_FOUND(700, "REFUND-700", "급여내역을 찾을 수 없습니다.")

    ,DEDUCTION_NOT_FOUND(701, "REFUND-701", "소득공제 내역을 찾을 수 없습니다.")

    ,TAXAMOUNT_NOT_FOUND(702, "REFUND-702", "산출세액 내역을 찾을 수 없습니다.")
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
