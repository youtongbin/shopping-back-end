// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   UserInfoMapper.java

package com.ytb.shopping.dao;

import com.ytb.shopping.pojo.UserInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserInfoMapper
{

	public abstract int deleteByPrimaryKey(Integer integer);

	public abstract int insert(UserInfo userinfo);

	public abstract UserInfo selectByPrimaryKey(Integer integer);

	public abstract List selectAll();

	public abstract int updateByPrimaryKey(UserInfo userinfo);

	public abstract int checkUsername(String username);

	public abstract int checkEmail(String email);

	public abstract UserInfo selectUserInfoByUsernameAndPassword(@Param("username") String s,@Param("password") String s1);

	public abstract int checkValid(@Param("str") String s,@Param("type") String s1);

	public abstract String selectQuestionByUserName(String username);

	public abstract int forgetCheckAnswer(@Param("username") String s,@Param("question") String s1,@Param("answer") String s2);

	public abstract int updateUserPassword(@Param("username") String s,@Param("passwordNew") String s1);

	public abstract int updateUserPasswordById(@Param("id") int i,@Param("passwordOld") String s,@Param("passwordNew") String s1);

	public abstract int updateUserInfo(UserInfo userinfo);
}
