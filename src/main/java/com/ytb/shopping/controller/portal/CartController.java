package com.ytb.shopping.controller.portal;

import com.ytb.shopping.common.Const;
import com.ytb.shopping.common.ServerResponse;
import com.ytb.shopping.enums.UserEnum;
import com.ytb.shopping.service.ICartService;
import com.ytb.shopping.vo.UserSimpleInfoVO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Resource
    private ICartService cartService;

    @RequestMapping("/add.do")
    public ServerResponse add(HttpSession session,Integer productId,Integer count){

        //登录判断
        UserSimpleInfoVO userSimpleInfoVO = (UserSimpleInfoVO)session.getAttribute(Const.CURRENT_USER);
        if (userSimpleInfoVO == null){
            return ServerResponse.serverResponseByFail(10,"用户未登录，请登录");
        }

        Integer userId = userSimpleInfoVO.getId();

        return cartService.add(userId,productId,count);
    }

}
