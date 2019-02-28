package com.ytb.shopping.enums;

import lombok.Getter;

@Getter
public enum CartCheckedEnum {

    PRODUCT_CHECKED(1,"已勾选"),
    PRODUCT_UNCHECKED(0,"未勾选"),
    ;

    private Integer code;

    private String msg;

    CartCheckedEnum(Integer code,String msg){
        this.code = code;
        this.msg = msg;
    }

}
