package com.ytb.shopping.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 购物车商品VO
 */
@Data
public class CartProductVO {

    private Integer id;

    private Integer userId;

    private Integer productId;

    private Integer quantity;//数量

    private String productName;

    private String productSubtitle;

    private String productMainImage;

    private BigDecimal productPrice;

    private Integer productStatus;

    private BigDecimal productTotalPrice;

    private Integer productStock;

    private Integer productChecked;//选中状态

    private String limitQuantity;//有货、无货

}
