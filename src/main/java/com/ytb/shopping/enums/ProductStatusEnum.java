package com.ytb.shopping.enums;

import lombok.Getter;

@Getter
public enum ProductStatusEnum {

    PRODUCT_ONLINE(1,"在售"),
    PRODUCT_OFFLINE(2,"下架"),
    PRODUCT_DELETE(3,"删除"),
    ;

    private Integer status;

    private String msg;

    ProductStatusEnum(Integer status,String msg){
        this.status = status;
        this.msg = msg;
    }
}
