package com.ytb.shopping.enums;

import lombok.Getter;

@Getter
public enum CategoryEnum {

    GET_CATEGORY_FAIL_1(10,"用户未登录，请登录"),
    GET_CATEGORY_FAIL_2(1,"未找到该品类"),
    ;

    private int status;

    private String msg;

    CategoryEnum(int status,String msg){
        this.status = status;
        this.msg = msg;
    }
}
