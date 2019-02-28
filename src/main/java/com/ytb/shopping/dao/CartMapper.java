package com.ytb.shopping.dao;

import com.ytb.shopping.pojo.Cart;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CartMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table shopping_cart
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table shopping_cart
     *
     * @mbggenerated
     */
    int insert(Cart record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table shopping_cart
     *
     * @mbggenerated
     */
    Cart selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table shopping_cart
     *
     * @mbggenerated
     */
    List<Cart> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table shopping_cart
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(Cart record);

    /**
     * 查询购物车
     * @param userId
     * @param productId
     * @return
     */
    Cart selectCartByUserIDAndProductId(@Param("userId") Integer userId,
                                        @Param("productId") Integer productId);


    /**
     * 查询用户购物车信息集合
     */
    List<Cart> selectCartsByUserId(Integer userId);

    /**
     * 统计购物车信息是否全选
     * return > 0 未全选
     */
    int isCheckedAll(Integer userId);
}