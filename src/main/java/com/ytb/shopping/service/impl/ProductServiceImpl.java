package com.ytb.shopping.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.ytb.shopping.common.ServerResponse;
import com.ytb.shopping.dao.CategoryMapper;
import com.ytb.shopping.dao.ProductMapper;
import com.ytb.shopping.enums.CommonEnum;
import com.ytb.shopping.enums.ProductStatusEnum;
import com.ytb.shopping.pojo.Category;
import com.ytb.shopping.pojo.Product;
import com.ytb.shopping.service.ICategoryService;
import com.ytb.shopping.service.IProductService;
import com.ytb.shopping.vo.ProductListVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class ProductServiceImpl implements IProductService {

    @Resource
    private ProductMapper productMapper;

    @Resource
    private CategoryMapper categoryMapper;

    @Resource
    private ICategoryService categoryService;


    @Override
    public ServerResponse detail(Integer productId) {

        if (productId == null){
            return ServerResponse.serverResponseByFail(CommonEnum.INPUT_NULL.getMsg());
        }

        Product product = productMapper.selectByPrimaryKey(productId);
        if (product == null){
            return ServerResponse.serverResponseByFail("该商品不存在");
        }

        if (!product.getStatus().equals(ProductStatusEnum.PRODUCT_ONLINE.getStatus())){
            return ServerResponse.serverResponseByFail(1,"该商品已下架或删除");
        }

        return ServerResponse.serverResponseBySuccess(product);
    }

    @Override
    public ServerResponse list(Integer categoryId, String keyword, Integer pageNum, Integer pageSize,String orderBy) {

        //1、参数校验，categoryId和keyword不能同时为空
        if (categoryId == null && (keyword == null || keyword.equals(""))){
            return ServerResponse.serverResponseByFail("参数错误");
        }

        //2、categoryId
        Set<Integer> integerSet = Sets.newHashSet();
        if (categoryId != null){
            Category category = categoryMapper.selectByPrimaryKey(categoryId);
            if (category == null && (keyword == null || keyword.equals(""))){
                //没有商品数据
                PageHelper.startPage(pageNum,pageSize);
                List<ProductListVO> productListVOList = Lists.newArrayList();
                PageInfo pageInfo = new PageInfo(productListVOList);
                return ServerResponse.serverResponseBySuccess(pageInfo);
            }

            ServerResponse serverResponse = categoryService.getDeepCategory(categoryId);

            if (serverResponse.isSuccess()){
                integerSet = (Set<Integer>) serverResponse.getData();
            }

        }

        //3、keyword
        if (keyword != null && !keyword.equals("")){
            keyword = "%" + keyword + "%";
        }

        if (orderBy.equals("")){
            PageHelper.startPage(pageNum,pageSize);
        }else {

            String[] orderByArr = orderBy.split("_");
            if (orderByArr.length > 1){
                PageHelper.startPage(pageNum,pageSize,orderByArr[0] + " " + orderByArr[1]);

            }else {
                PageHelper.startPage(pageNum,pageSize);
            }

        }

        List<Product> productList = productMapper.searchProduct(integerSet,keyword);

        //4、List<Product> --> List<ProductListVO>
        List<ProductListVO> productListVOList = Lists.newArrayList();
        if (productList != null && productList.size() > 0){
            for (Product p:productList
                 ) {
                ProductListVO productListVO = ProductManageServiceImpl.translateProductListVO(p);
                productListVOList.add(productListVO);
            }
        }

        //5、分页
        PageInfo pageInfo = new PageInfo(productListVOList);

        //6、返回
        return ServerResponse.serverResponseBySuccess(pageInfo);
    }
}
