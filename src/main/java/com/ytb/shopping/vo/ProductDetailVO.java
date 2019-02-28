package com.ytb.shopping.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class ProductDetailVO implements Serializable {

    private Integer id;
    private Integer categoryId;
    private Integer parentCategoryId;
    private String name;
    private String subtitle;
    private String imageHost;
    private String mainImage;
    private String subImages;
    private String detail;
    private BigDecimal price;
    private Integer stock;
    private Integer status;
    private String createTime;
    private String updateTime;

}
