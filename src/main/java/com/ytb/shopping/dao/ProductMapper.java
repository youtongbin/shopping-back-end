package com.ytb.shopping.dao;

import com.ytb.shopping.pojo.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface ProductMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table shopping_product
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table shopping_product
     *
     * @mbggenerated
     */
    int insert(Product record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table shopping_product
     *
     * @mbggenerated
     */
    Product selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table shopping_product
     *
     * @mbggenerated
     */
    List<Product> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table shopping_product
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(Product record);


    /**
     * 更新商品状态
     */
    int updateProductKeySelective(Product product);


    /**
     * 根据商品id查询或商品名模糊查询
     */
    List<Product> findProductsByIdOrName(@Param("productId") Integer productId,
                                         @Param("productName") String productName);


    /**
     * 前台搜索商品
     * @param integerSet
     * @param keyword
     * @return
     */
    List<Product> searchProduct(@Param("integerSet") Set<Integer> integerSet,
                                @Param("keyword") String keyword);

}