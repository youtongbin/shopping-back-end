package com.ytb.shopping.enums;

import lombok.Getter;

@Getter
public enum CommonEnum {

    SUCCESS(0,"成功"),
    FAIL(100,"失败"),
    ;

    private int status;

    private String msg;

    CommonEnum(int status, String msg){
        this.status = status;
        this.msg = msg;
    }

}
