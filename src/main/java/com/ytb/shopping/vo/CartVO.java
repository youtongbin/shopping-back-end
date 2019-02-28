package com.ytb.shopping.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 购物车VO
 */
@Data
public class CartVO {

    //商品信息集合
    private List<CartProductVO> cartProductVOList;

    //是否全选
    private Boolean allChecked;

    //总价
    private BigDecimal cartTotalPrice;

}
