package com.ytb.shopping.service;

import com.ytb.shopping.common.ServerResponse;
import com.ytb.shopping.pojo.Product;
import org.springframework.web.multipart.MultipartFile;

public interface IProductManageService {

    /**
     * 新增OR更新产品
     */
    ServerResponse save(Product product);

    /**
     * 产品上下架
     * @param productId
     * @param status
     * @return
     */
    ServerResponse setSaleStatus(Integer productId,Integer status);

    /**
     * 查看商品详情
     * @param productId
     * @return
     */
    ServerResponse detail(Integer productId);


    /**
     * 后台商品列表
     * @param pageNum
     * @param pageSize
     * @return
     */
    ServerResponse list(Integer pageNum,Integer pageSize);


    /**
     * 后台搜索商品
     * @param productId
     * @param productName
     * @param pageNum
     * @param pageSize
     * @return
     */
    ServerResponse search(Integer productId,String productName,Integer pageNum,Integer pageSize);


    /**
     * 图片上传
     * @param file
     * @return
     */
    ServerResponse upload(MultipartFile file,String path);



}
