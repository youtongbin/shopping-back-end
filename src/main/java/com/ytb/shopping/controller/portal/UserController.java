// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   UserController.java

package com.ytb.shopping.controller.portal;

import com.ytb.shopping.common.Const;
import com.ytb.shopping.common.ServerResponse;
import com.ytb.shopping.pojo.UserInfo;
import com.ytb.shopping.service.IUserService;
import com.ytb.shopping.vo.UserSimpleInfoVO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/user")
public class UserController
{

	@Resource
	private IUserService userService;

	@RequestMapping("/login.do")
	public ServerResponse login(HttpSession session, String username, String password)
	{
		ServerResponse serverResponse = userService.login(username, password);
		if (serverResponse.isSuccess())
		{
			UserSimpleInfoVO userSimpleInfoVO = (UserSimpleInfoVO)serverResponse.getData();
			session.setAttribute("current_user", userSimpleInfoVO);
		}
		return serverResponse;
	}

	@RequestMapping("/register.do")
	public ServerResponse register(HttpSession session, UserInfo userInfo)
	{
		return userService.register(userInfo);
	}

	@RequestMapping("/check_valid.do")
	public ServerResponse checkValid(String str, String type)
	{
		return userService.checkValid(str, type);
	}

	@RequestMapping("/get_user_info.do")
	public ServerResponse getUserInfo(HttpSession session)
	{
		ServerResponse serverResponse = userService.getUserInfo(session);
		if (serverResponse.isSuccess())
			session.setAttribute("current_user", (UserSimpleInfoVO)serverResponse.getData());
		return serverResponse;
	}

	@RequestMapping("/forget_get_question.do")
	public ServerResponse forgetGetQuestion(String username)
	{
		return userService.forgetGetQuestion(username);
	}

	@RequestMapping("/forget_check_answer.do")
	public ServerResponse forgetCheckAnswer(String username, String question, String answer)
	{
		return userService.forgetCheckAnswer(username, question, answer);
	}

	@RequestMapping("/forget_reset_password.do")
	public ServerResponse forgetResetPassword(String username, String passwordNew, String forgetToken)
	{
		return userService.forgetResetPassword(username, passwordNew, forgetToken);
	}

	@RequestMapping("/reset_password.do")
	public ServerResponse resetPassword(HttpSession session, String passwordOld, String passwordNew)
	{
		return userService.resetPassword(session, passwordOld, passwordNew);
	}

	@RequestMapping("/update_information.do")
	public ServerResponse updateInformation(HttpSession session, UserInfo userInfo)
	{
		return userService.updateInformation(session, userInfo);
	}

	@RequestMapping("/get_information.do")
	public ServerResponse getInformation(HttpSession session)
	{
		return userService.getInformation(session);
	}

	@RequestMapping("/logout.do")
	public ServerResponse logout(HttpSession session)
	{
		return userService.logout(session);
	}
}
