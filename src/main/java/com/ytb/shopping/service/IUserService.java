// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   IUserService.java

package com.ytb.shopping.service;

import com.ytb.shopping.common.ServerResponse;
import com.ytb.shopping.pojo.UserInfo;
import javax.servlet.http.HttpSession;

public interface IUserService
{

	public abstract ServerResponse login(String s, String s1);

	public abstract ServerResponse register(UserInfo userinfo);

	public abstract ServerResponse checkValid(String s, String s1);

	public abstract ServerResponse getUserInfo(HttpSession httpsession);

	public abstract ServerResponse forgetGetQuestion(String s);

	public abstract ServerResponse forgetCheckAnswer(String s, String s1, String s2);

	public abstract ServerResponse forgetResetPassword(String s, String s1, String s2);

	public abstract ServerResponse resetPassword(HttpSession httpsession, String s, String s1);

	public abstract ServerResponse updateInformation(HttpSession httpsession, UserInfo userinfo);

	public abstract ServerResponse getInformation(HttpSession httpsession);

	public abstract ServerResponse logout(HttpSession httpsession);
}
