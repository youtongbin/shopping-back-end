package com.ytb.shopping.enums;

import lombok.Getter;

@Getter
public enum UserEnum {

    LOGIN_FAIL(1,"密码错误"),
    REGISTER_SUCCESS(0,"校验成功"),
    REGISTER_FAIL(1,"用户已存在"),
    CHECK_VALID_SUCCESS(0,"校验成功"),
    CHECK_VALID_FAIL(1,"用户名已存在"),
    GET_USER_INFO_FAIL(1,"用户未登录，无法获取当前用户信息"),
    FORGET_GET_QUESTION_FAIL(1,"该用户未设置找回密码问题"),
    FORGET_CHECK_ANSWER_FAIL(1,"问题答案错误"),
    FORGET_RESET_PASSWORD_SUCCESS(0,"修改密码成功"),
    FORGET_RESET_PASSWORD_FAIL_1(1,"修改密码操作失效"),
    FORGET_RESET_PASSWORD_FAIL_2(1,"token已经失效"),
    RESET_PASSWORD_SUCCESS(0,"修改密码成功"),
    RESET_PASSWORD_FAIL(1,"旧密码输入错误"),
    UPDATE_INFORMATION_SUCCESS(0,"更新个人信息成功"),
    UPDATE_INFORMATION_FAIL(1,"用户未登录"),
    GET_INFORMATION_FAIL(10,"用户未登录，无法获取当前用户信息,status=10强制退出"),
    LOGOUT_SUCCESS(0,"退出成功"),
    LOGOUT_FAIL(1,"服务端异常"),
    ;

    private int status;

    private String msg;

    UserEnum(int status,String msg){
        this.status = status;
        this.msg = msg;
    }
}
