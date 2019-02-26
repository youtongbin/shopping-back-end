// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ServerResponse.java

package com.ytb.shopping.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ytb.shopping.enums.CommonEnum;
import lombok.Data;


@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@Data
public class ServerResponse <T> {

	private int status;
	private T data;
	private String msg;

	private ServerResponse(int status)
	{
		this.status = status;
	}

	private ServerResponse(int status, T data)
	{
		this.status = status;
		this.data = data;
	}

	private ServerResponse(int status, String msg)
	{
		this.status = status;
		this.msg = msg;
	}

	private ServerResponse(int status, T data, String msg)
	{
		this.status = status;
		this.data = data;
		this.msg = msg;
	}

	public static ServerResponse serverResponseBySuccess()
	{
		return new ServerResponse(CommonEnum.SUCCESS.getStatus());
	}

	public static <T> ServerResponse serverResponseBySuccess(T data)
	{
		return new ServerResponse(CommonEnum.SUCCESS.getStatus(), data);
	}

	public static <T> ServerResponse serverResponseBySuccess(T data, String msg)
	{
		return new ServerResponse(CommonEnum.SUCCESS.getStatus(), data, msg);
	}

	public static <T> ServerResponse serverResponseBySuccess(int status, T data, String msg)
	{
		return new ServerResponse(status, data, msg);
	}

	public static ServerResponse serverResponseByFail()
	{
		return new ServerResponse(CommonEnum.FAIL.getStatus());
	}

	public static ServerResponse serverResponseByFail(String msg)
	{
		return new ServerResponse(CommonEnum.FAIL.getStatus(), msg);
	}

	public static ServerResponse serverResponseByFail(int status)
	{
		return new ServerResponse(status);
	}

	public static ServerResponse serverResponseByFail(int status, String msg)
	{
		return new ServerResponse(status, msg);
	}

	@JsonIgnore
	public boolean isSuccess()
	{
		return status == CommonEnum.SUCCESS.getStatus();
	}

}
