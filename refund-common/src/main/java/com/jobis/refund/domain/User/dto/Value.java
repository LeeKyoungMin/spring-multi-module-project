package com.jobis.refund.domain.User.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Value {
    private JsonList jsonList;
    private String appVer;
    private String errMsg;
    private String company;
    private String svcCd;
    private String hostNm;
    private String workerResDt;
    private String workerReqDt;
}
