// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   UserServiceImpl.java

package com.ytb.shopping.service.impl;

import com.ytb.shopping.common.Const;
import com.ytb.shopping.common.ServerResponse;
import com.ytb.shopping.dao.UserInfoMapper;
import com.ytb.shopping.enums.RoleEnum;
import com.ytb.shopping.enums.UserEnum;
import com.ytb.shopping.pojo.UserInfo;
import com.ytb.shopping.service.IUserService;
import com.ytb.shopping.util.MD5Utils;
import com.ytb.shopping.util.TokenCache;
import com.ytb.shopping.vo.UserSimpleInfoVO;
import java.util.UUID;
import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService {

	@Resource
	private UserInfoMapper userInfoMapper;

	public ServerResponse login(String username, String password)
	{
		if (username == null || username.equals(""))
			return ServerResponse.serverResponseByFail("用户名不能为空");
		if (password == null || password.equals(""))
			return ServerResponse.serverResponseByFail("密码不能为空");
		if (userInfoMapper.checkUsername(username) == 0)
			return ServerResponse.serverResponseByFail("用户不存在");
		UserInfo userInfo = userInfoMapper.selectUserInfoByUsernameAndPassword(username, MD5Utils.toMD5(password));
		if (userInfo == null)
		{
			return ServerResponse.serverResponseByFail(UserEnum.LOGIN_FAIL.getStatus(), UserEnum.LOGIN_FAIL.getMsg());
		} else
		{
			return ServerResponse.serverResponseBySuccess(userInfo);
		}
	}

	public ServerResponse register(UserInfo userInfo)
	{
		if (userInfo == null)
			return ServerResponse.serverResponseByFail("输入不能为空");
		if (userInfoMapper.checkUsername(userInfo.getUsername()) > 0)
			return ServerResponse.serverResponseByFail(UserEnum.REGISTER_FAIL.getStatus(), UserEnum.REGISTER_FAIL.getMsg());
		if (userInfoMapper.checkEmail(userInfo.getEmail()) > 0)
			return ServerResponse.serverResponseByFail("邮箱已注册");
		userInfo.setRole(RoleEnum.ROLE_CUSTOMER.getCode());
		userInfo.setPassword(MD5Utils.toMD5(userInfo.getPassword()));
		int result = userInfoMapper.insert(userInfo);
		if (result > 0)
			return ServerResponse.serverResponseBySuccess(null, UserEnum.REGISTER_SUCCESS.getMsg());
		else
			return ServerResponse.serverResponseByFail("注册失败");
	}

	public ServerResponse checkValid(String str, String type)
	{
		if (str == null || str.equals("") || type == null || type.equals(""))
			return ServerResponse.serverResponseByFail("输入不能为空");
		int result = userInfoMapper.checkValid(str, type);
		if (result == 0)
			return ServerResponse.serverResponseBySuccess(null, UserEnum.CHECK_VALID_SUCCESS.getMsg());
		else
			return ServerResponse.serverResponseByFail(UserEnum.CHECK_VALID_FAIL.getStatus(), UserEnum.CHECK_VALID_FAIL.getMsg());
	}

	public ServerResponse getUserInfo(HttpSession session)
	{
		UserSimpleInfoVO userSimpleInfoVO = (UserSimpleInfoVO)session.getAttribute(Const.CURRENT_USER);
		if (userSimpleInfoVO == null)
		{
			return ServerResponse.serverResponseByFail(UserEnum.GET_USER_INFO_FAIL.getStatus(), UserEnum.GET_USER_INFO_FAIL.getMsg());
		} else
		{
			UserInfo userInfo = userInfoMapper.selectByPrimaryKey(Integer.valueOf(userSimpleInfoVO.getId()));
			BeanUtils.copyProperties(userInfo, userSimpleInfoVO);
			return ServerResponse.serverResponseBySuccess(userSimpleInfoVO);
		}
	}

	public ServerResponse forgetGetQuestion(String username)
	{
		if (username == null || username.equals(""))
			return ServerResponse.serverResponseByFail("用户名不能为空");
		int result = userInfoMapper.checkUsername(username);
		if (result == 0)
			return ServerResponse.serverResponseByFail("用户不存在");
		String question = userInfoMapper.selectQuestionByUserName(username);
		if (question == null || question.equals(""))
			return ServerResponse.serverResponseByFail(UserEnum.FORGET_GET_QUESTION_FAIL.getStatus(), UserEnum.FORGET_GET_QUESTION_FAIL.getMsg());
		else
			return ServerResponse.serverResponseBySuccess(question);
	}

	public ServerResponse forgetCheckAnswer(String username, String question, String answer)
	{
		if (username == null || username.equals("") || question == null || question.equals("") || answer == null || answer.equals(""))
			return ServerResponse.serverResponseByFail("输入不能为空");
		int result = userInfoMapper.forgetCheckAnswer(username, question, answer);
		if (result == 0)
		{
			return ServerResponse.serverResponseByFail(UserEnum.FORGET_CHECK_ANSWER_FAIL.getStatus(), UserEnum.FORGET_CHECK_ANSWER_FAIL.getMsg());
		} else
		{
			String forgetToken = UUID.randomUUID().toString();
			TokenCache.setKey(username, forgetToken);
			return ServerResponse.serverResponseBySuccess(forgetToken);
		}
	}

	public ServerResponse forgetResetPassword(String username, String passwordNew, String forgetToken)
	{
		if (username == null || username.equals(""))
			return ServerResponse.serverResponseByFail("用户名不能为空");
		if (passwordNew == null || passwordNew.equals(""))
			return ServerResponse.serverResponseByFail("新密码不能为空");
		if (forgetToken == null || forgetToken.equals(""))
			return ServerResponse.serverResponseByFail("token不能为空");
		String token = TokenCache.getKey(username);
		if (token == null)
			return ServerResponse.serverResponseByFail(UserEnum.FORGET_RESET_PASSWORD_FAIL_2.getStatus(), UserEnum.FORGET_RESET_PASSWORD_FAIL_2.getMsg());
		if (!token.equals(forgetToken))
			return ServerResponse.serverResponseByFail("token校验失败");
		int result = userInfoMapper.updateUserPassword(username, MD5Utils.toMD5(passwordNew));
		if (result > 0)
			return ServerResponse.serverResponseBySuccess(UserEnum.FORGET_RESET_PASSWORD_SUCCESS.getMsg());
		else
			return ServerResponse.serverResponseByFail(UserEnum.FORGET_RESET_PASSWORD_FAIL_1.getStatus(), UserEnum.FORGET_RESET_PASSWORD_FAIL_1.getMsg());
	}

	public ServerResponse resetPassword(HttpSession session, String passwordOld, String passwordNew)
	{
		if (passwordOld == null || passwordOld.equals(""))
			return ServerResponse.serverResponseByFail("旧密码不能为空");
		if (passwordNew == null || passwordNew.equals(""))
			return ServerResponse.serverResponseByFail("新密码不能为空");
		UserSimpleInfoVO userSimpleInfoVO = (UserSimpleInfoVO)session.getAttribute("current_user");
		if (userSimpleInfoVO == null)
			return ServerResponse.serverResponseByFail("登录状态失效");
		int id = userSimpleInfoVO.getId();
		int result = userInfoMapper.updateUserPasswordById(id, MD5Utils.toMD5(passwordOld), MD5Utils.toMD5(passwordNew));
		if (result > 0)
			return ServerResponse.serverResponseBySuccess(null, UserEnum.RESET_PASSWORD_SUCCESS.getMsg());
		else
			return ServerResponse.serverResponseByFail(UserEnum.RESET_PASSWORD_FAIL.getStatus(), UserEnum.RESET_PASSWORD_FAIL.getMsg());
	}

	public ServerResponse updateInformation(HttpSession session, UserInfo userInfo)
	{
		if (userInfo == null)
			return ServerResponse.serverResponseByFail("输入不能为空");
		UserSimpleInfoVO userSimpleInfoVO = (UserSimpleInfoVO)session.getAttribute(Const.CURRENT_USER);
		if (userSimpleInfoVO == null)
			return ServerResponse.serverResponseByFail(UserEnum.UPDATE_INFORMATION_FAIL.getStatus(), UserEnum.UPDATE_INFORMATION_FAIL.getMsg());
		userInfo.setId(Integer.valueOf(userSimpleInfoVO.getId()));
		int result = userInfoMapper.updateUserInfo(userInfo);
		if (result > 0)
			return ServerResponse.serverResponseBySuccess(null, UserEnum.UPDATE_INFORMATION_SUCCESS.getMsg());
		else
			return ServerResponse.serverResponseByFail("修改信息失败");
	}

	public ServerResponse getInformation(HttpSession session)
	{
		UserSimpleInfoVO userSimpleInfoVO = (UserSimpleInfoVO)session.getAttribute(Const.CURRENT_USER);
		if (userSimpleInfoVO == null)
			return ServerResponse.serverResponseByFail(UserEnum.GET_INFORMATION_FAIL.getStatus(), UserEnum.GET_INFORMATION_FAIL.getMsg());
		int id = userSimpleInfoVO.getId();
		UserInfo userInfo = userInfoMapper.selectByPrimaryKey(Integer.valueOf(id));
		if (userInfo == null)
			return ServerResponse.serverResponseByFail("用户信息获取失败");
		else
			return ServerResponse.serverResponseBySuccess(userInfo);
	}

	public ServerResponse logout(HttpSession session)
	{
		Object o = session.getAttribute(Const.CURRENT_USER);
		if (o == null)
		{
			return ServerResponse.serverResponseByFail(UserEnum.LOGOUT_FAIL.getStatus(), UserEnum.LOGOUT_FAIL.getMsg());
		} else
		{
			session.invalidate();
			return ServerResponse.serverResponseBySuccess(null, UserEnum.LOGOUT_SUCCESS.getMsg());
		}
	}
}
