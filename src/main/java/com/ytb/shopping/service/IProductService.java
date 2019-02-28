package com.ytb.shopping.service;

import com.ytb.shopping.common.ServerResponse;

/**
 * 前台_产品品接口
 */
public interface IProductService {

    /**
     * 产品详情
     */
    ServerResponse detail(Integer productId);


    /**
     * 前台商品搜索
     */
    ServerResponse list(Integer categoryId,String keyword,Integer pageNum,Integer pageSize,String orderBy);

}
