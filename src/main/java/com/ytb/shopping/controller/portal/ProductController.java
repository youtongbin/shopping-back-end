package com.ytb.shopping.controller.portal;

import com.ytb.shopping.common.ServerResponse;
import com.ytb.shopping.service.IProductService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Resource
    private IProductService productService;

    /**
     * 产品搜索及动态排序List
     * @return
     */
    /*@RequestMapping("/list.do")
    public ServerResponse list(){




    }*/

}
