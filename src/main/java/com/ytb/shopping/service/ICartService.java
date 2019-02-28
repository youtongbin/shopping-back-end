package com.ytb.shopping.service;

import com.ytb.shopping.common.ServerResponse;

import javax.servlet.http.HttpSession;

public interface ICartService {

    /**
     * 添加购物车
     */
    ServerResponse add(Integer userId,Integer productId, Integer count);

}
