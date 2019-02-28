package com.ytb.shopping.controller.portal;

import com.ytb.shopping.common.ServerResponse;
import com.ytb.shopping.service.IProductService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Resource
    private IProductService productService;


    /**
     * 商品详情
     */
    @RequestMapping("/detail.do")
    public ServerResponse detail(Integer productId){
        return productService.detail(productId);
    }


    /**
     * 产品搜索及动态排序List
     * @param categoryId
     * @param keyword
     * @param pageNum
     * @param pageSize
     * @param orderBy
     * @return
     */
    @RequestMapping("/list.do")
    public ServerResponse list(@RequestParam(required = false) Integer categoryId,
                               @RequestParam(required = false) String keyword,
                               @RequestParam(required = false,defaultValue = "1") Integer pageNum,
                               @RequestParam(required = false,defaultValue = "10") Integer pageSize,
                               @RequestParam(required = false,defaultValue = "") String orderBy){


        return productService.list(categoryId,keyword,pageNum,pageSize,orderBy);

    }

}
