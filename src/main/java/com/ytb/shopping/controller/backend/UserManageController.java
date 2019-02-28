package com.ytb.shopping.controller.backend;

import com.ytb.shopping.common.Const;
import com.ytb.shopping.common.ServerResponse;
import com.ytb.shopping.pojo.UserInfo;
import com.ytb.shopping.service.IUserManageService;
import com.ytb.shopping.service.IUserService;
import com.ytb.shopping.vo.UserManagerSimpleInfoVo;
import com.ytb.shopping.vo.UserSimpleInfoVO;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * 用户后台管理
 */
@RestController
@RequestMapping("/manage/user")
public class UserManageController {

    @Resource
    private IUserManageService userManageService;


    /**
     * 管理员登录
     */
    @RequestMapping("/login.do")
    public ServerResponse login(HttpSession session, String username, String password)
    {
        ServerResponse serverResponse = userManageService.login(username, password);
        if (serverResponse.isSuccess())
        {
            UserInfo userInfo = (UserInfo) serverResponse.getData();

            UserManagerSimpleInfoVo userManagerSimpleInfoVo = new UserManagerSimpleInfoVo();
            BeanUtils.copyProperties(userInfo, userManagerSimpleInfoVo);

            serverResponse.setData(userManagerSimpleInfoVo);

            session.setAttribute(Const.CURRENT_USER, userManagerSimpleInfoVo);
        }
        return serverResponse;
    }

}
