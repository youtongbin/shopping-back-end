package com.ytb.shopping.enums;

import lombok.Getter;

@Getter
public enum CommonEnum {

    SUCCESS(0,"成功"),
    FAIL(100,"失败"),
    NEED_LOGIN(101,"需要登录"),
    NO_POWER(102,"没有权限"),
    INPUT_NULL(103,"输入不能为空"),
    ;

    private int status;

    private String msg;

    CommonEnum(int status, String msg){
        this.status = status;
        this.msg = msg;
    }

}
