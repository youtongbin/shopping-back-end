package com.ytb.shopping.enums;

import lombok.Getter;

@Getter
public enum RoleEnum {

    ROLE_ADMIN(0,"管理员"),
    ROLE_CUSTOMER(1,"普通用户"),
    ;

    private int code;

    private String desc;

    RoleEnum(int code,String desc){
        this.code = code;
        this.desc = desc;
    }
}
