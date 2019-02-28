package com.ytb.shopping.service;

import com.ytb.shopping.common.ServerResponse;

public interface IUserManageService {

    /**
     * 登录
     */
    ServerResponse login(String username, String password);
}
