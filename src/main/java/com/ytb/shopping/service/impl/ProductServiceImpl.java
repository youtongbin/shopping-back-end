package com.ytb.shopping.service.impl;

import com.ytb.shopping.dao.ProductMapper;
import com.ytb.shopping.service.IProductService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ProductServiceImpl implements IProductService {

    @Resource
    private ProductMapper productMapper;



}
