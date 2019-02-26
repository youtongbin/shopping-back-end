// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   UserSimpleInfoVO.java

package com.ytb.shopping.vo;

import lombok.Data;

import java.util.Date;

@Data
public class UserSimpleInfoVO {

	private int id;
	private String username;
	private String email;
	private String phone;
	private Date createTime;
	private Date updateTime;

}
