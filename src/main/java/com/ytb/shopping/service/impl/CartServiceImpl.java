package com.ytb.shopping.service.impl;

import com.google.common.collect.Lists;
import com.ytb.shopping.common.ServerResponse;
import com.ytb.shopping.dao.CartMapper;
import com.ytb.shopping.dao.ProductMapper;
import com.ytb.shopping.enums.CartCheckedEnum;
import com.ytb.shopping.enums.CommonEnum;
import com.ytb.shopping.pojo.Cart;
import com.ytb.shopping.pojo.Product;
import com.ytb.shopping.service.ICartService;
import com.ytb.shopping.util.BigDecimalUtils;
import com.ytb.shopping.vo.CartProductVO;
import com.ytb.shopping.vo.CartVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

@Service
public class CartServiceImpl implements ICartService {

    @Resource
    private CartMapper cartMapper;

    @Resource
    private ProductMapper productMapper;

    @Override
    public ServerResponse add(Integer userId,Integer productId, Integer count) {

        //1、参数判空
        if (productId == null || count == null){
            return ServerResponse.serverResponseByFail(CommonEnum.INPUT_NULL.getMsg());
        }

        //2、根据uId和pId查询购物车商品
        Cart cart = cartMapper.selectCartByUserIDAndProductId(userId,productId);
        if (cart == null){
            //3、信息不存在，添加信息->购物车

            Cart cart1 = new Cart();
            cart1.setProductId(productId);
            cart1.setUserId(userId);
            cart1.setQuantity(count);
            cart1.setChecked(CartCheckedEnum.PRODUCT_CHECKED.getCode());

            cartMapper.insert(cart1);

        }else {
            //4、商品信息存在，修改数量

            Cart cart1 = new Cart();
            cart1.setId(cart.getId());
            cart1.setUserId(userId);
            cart1.setProductId(productId);
            cart1.setQuantity(count);
            cart1.setChecked(CartCheckedEnum.PRODUCT_CHECKED.getCode());

            cartMapper.updateByPrimaryKey(cart1);

        }

        CartVO cartVO = getCartVOLimit(userId);

        return ServerResponse.serverResponseBySuccess(cartVO);
    }

    /**
     * CartVO转换
     * @param userId
     * @return
     */
    private CartVO getCartVOLimit(Integer userId){
        CartVO cartVO = new CartVO();
        BigDecimal cartTotalPrice = new BigDecimal("0");

        //1、根据用户id查询购物车信息
        List<Cart> cartList = cartMapper.selectCartsByUserId(userId);

        //2、List<Cart> --> List<CartVO>
        List<CartProductVO> cartProductVOList = Lists.newArrayList();
        if (cartList != null && cartList.size() > 0){
            for (Cart c:cartList
                 ) {
                CartProductVO cartProductVO = new CartProductVO();

                cartProductVO.setId(c.getId());
                cartProductVO.setQuantity(c.getQuantity());
                cartProductVO.setUserId(c.getUserId());
                cartProductVO.setProductChecked(c.getChecked());


                Product product = productMapper.selectByPrimaryKey(c.getProductId());
                if (product != null){
                    cartProductVO.setProductId(product.getId());
                    cartProductVO.setProductName(product.getName());
                    cartProductVO.setProductMainImage(product.getMainImage());
                    cartProductVO.setProductStatus(product.getStatus());
                    cartProductVO.setProductPrice(product.getPrice());
                    cartProductVO.setProductSubtitle(product.getSubtitle());

                    int stock = product.getStock();
                    cartProductVO.setProductStock(stock);
                    int limitProductCount = 0;
                    if (stock >= c.getQuantity()){
                        limitProductCount = c.getQuantity();
                        cartProductVO.setLimitQuantity("LIMIT_NUM_SUCCESS");
                    }else {//库存不足
                        limitProductCount = stock;

                        //更新购物车商品数
                        Cart cart = new Cart();
                        BeanUtils.copyProperties(c,cart);
                        cart.setQuantity(stock);
                        cartMapper.updateByPrimaryKey(cart);

                        cartProductVO.setLimitQuantity("LIMIT_NUM_FAIL");
                    }

                    cartProductVO.setQuantity(limitProductCount);

                    cartProductVO.setProductTotalPrice(BigDecimalUtils.mul(product.getPrice().doubleValue(),Double.valueOf(cartProductVO.getQuantity())));


                }

                cartTotalPrice = BigDecimalUtils.add(cartProductVO.getProductTotalPrice().doubleValue(),cartTotalPrice.doubleValue());

                cartProductVOList.add(cartProductVO);
            }
        }

        //3、计算总价
        cartVO.setCartTotalPrice(cartTotalPrice);

        cartVO.setCartProductVOList(cartProductVOList);

        //4、判断购物车是否全选
        int result = cartMapper.isCheckedAll(userId);
        if (result > 0){
            cartVO.setAllChecked(false);
        }else {
            cartVO.setAllChecked(true);
        }

        //5、返回

        return cartVO;
    }
}
