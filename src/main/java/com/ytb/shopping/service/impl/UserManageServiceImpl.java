package com.ytb.shopping.service.impl;

import com.ytb.shopping.common.ServerResponse;
import com.ytb.shopping.dao.UserInfoMapper;
import com.ytb.shopping.service.IUserManageService;
import com.ytb.shopping.service.IUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserManageServiceImpl implements IUserManageService {

    @Resource
    private IUserService userService;

    @Resource
    private UserInfoMapper userInfoMapper;

    @Override
    public ServerResponse login(String username, String password) {
        return userService.login(username,password);
    }
}
