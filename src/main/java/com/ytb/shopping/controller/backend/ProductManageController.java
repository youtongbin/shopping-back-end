package com.ytb.shopping.controller.backend;

import com.ytb.shopping.common.Const;
import com.ytb.shopping.common.ServerResponse;
import com.ytb.shopping.enums.CategoryEnum;
import com.ytb.shopping.enums.CommonEnum;
import com.ytb.shopping.enums.RoleEnum;
import com.ytb.shopping.pojo.Product;
import com.ytb.shopping.service.IProductManageService;
import com.ytb.shopping.vo.UserManagerSimpleInfoVo;
import com.ytb.shopping.vo.UserSimpleInfoVO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/manage/product")
public class ProductManageController {

    @Resource
    private IProductManageService productManageService;


    /**
     * 新增OR更新产品
     */
    @RequestMapping("/save.do")
    public ServerResponse save(HttpSession session, Product product){

        //登录状态判断
        UserManagerSimpleInfoVo userManagerSimpleInfoVo = (UserManagerSimpleInfoVo) session.getAttribute(Const.CURRENT_USER);
        if (userManagerSimpleInfoVo == null){
            return ServerResponse.serverResponseByFail(CommonEnum.NEED_LOGIN.getMsg());
        }

        //权限判断
        if (userManagerSimpleInfoVo.getRole() != RoleEnum.ROLE_ADMIN.getCode()){
            return ServerResponse.serverResponseByFail(CommonEnum.NO_POWER.getStatus(),CommonEnum.NO_POWER.getMsg());
        }

        return productManageService.save(product);
    }

    /**
     * 产品上下架
     */
    @RequestMapping("/set_sale_status.do")
    public ServerResponse setSaleStatus(HttpSession session,Integer productId,Integer status){

        //登录状态判断
        UserManagerSimpleInfoVo userManagerSimpleInfoVo = (UserManagerSimpleInfoVo) session.getAttribute(Const.CURRENT_USER);
        if (userManagerSimpleInfoVo == null){
            return ServerResponse.serverResponseByFail(CommonEnum.NEED_LOGIN.getMsg());
        }

        //权限判断
        if (userManagerSimpleInfoVo.getRole() != RoleEnum.ROLE_ADMIN.getCode()){
            return ServerResponse.serverResponseByFail(CommonEnum.NO_POWER.getStatus(),CommonEnum.NO_POWER.getMsg());
        }

        return productManageService.setSaleStatus(productId,status);

    }

    /**
     * 查看商品詳情
     */
    @RequestMapping("/detail.do")
    public ServerResponse detail(HttpSession session,Integer productId){

        //登录状态判断
        UserManagerSimpleInfoVo userManagerSimpleInfoVo = (UserManagerSimpleInfoVo) session.getAttribute(Const.CURRENT_USER);
        if (userManagerSimpleInfoVo == null){
            return ServerResponse.serverResponseByFail(CommonEnum.NEED_LOGIN.getMsg());
        }

        //权限判断
        if (userManagerSimpleInfoVo.getRole() != RoleEnum.ROLE_ADMIN.getCode()){
            return ServerResponse.serverResponseByFail(CommonEnum.NO_POWER.getStatus(),CommonEnum.NO_POWER.getMsg());
        }

        return productManageService.detail(productId);
    }


    /**
     * 产品list
     */
    @RequestMapping("/list.do")
    public ServerResponse list(HttpSession session,
                               @RequestParam(value = "pageNum", required = false,defaultValue = "1") Integer pageNum,
                               @RequestParam(value = "pageSize",required = false,defaultValue = "10") Integer pageSize){

        //登录状态判断

        UserSimpleInfoVO userSimpleInfoVO = (UserSimpleInfoVO) session.getAttribute(Const.CURRENT_USER);
        if (userSimpleInfoVO != null){
            return ServerResponse.serverResponseByFail(CommonEnum.NO_POWER.getMsg());
        }

        UserManagerSimpleInfoVo userManagerSimpleInfoVo = (UserManagerSimpleInfoVo) session.getAttribute(Const.CURRENT_USER);
        if (userManagerSimpleInfoVo == null){
            return ServerResponse.serverResponseByFail(10,"用户未登录，请登录");
        }

        //权限判断
        if (userManagerSimpleInfoVo.getRole() != RoleEnum.ROLE_ADMIN.getCode()){
            return ServerResponse.serverResponseByFail(CommonEnum.NO_POWER.getStatus(),CommonEnum.NO_POWER.getMsg());
        }

        return productManageService.list(pageNum,pageSize);
    }


    /**
     * 产品搜索
     */
    @RequestMapping("/search.do")
    public ServerResponse search(HttpSession session,
                               @RequestParam(value = "productId",required = false) Integer productId,
                               @RequestParam(value = "productName",required = false) String productName,
                               @RequestParam(value = "pageNum", required = false,defaultValue = "1") Integer pageNum,
                               @RequestParam(value = "pageSize",required = false,defaultValue = "10") Integer pageSize){

        //登录状态判断
        UserManagerSimpleInfoVo userManagerSimpleInfoVo = (UserManagerSimpleInfoVo) session.getAttribute(Const.CURRENT_USER);
        if (userManagerSimpleInfoVo == null){
            return ServerResponse.serverResponseByFail(10,"用户未登录，请登录");
        }

        //权限判断
        if (userManagerSimpleInfoVo.getRole() != RoleEnum.ROLE_ADMIN.getCode()){
            return ServerResponse.serverResponseByFail(CommonEnum.NO_POWER.getStatus(),CommonEnum.NO_POWER.getMsg());
        }

        return productManageService.search(productId,productName,pageNum,pageSize);
    }


    /**
     * 图片上传
     */
    @RequestMapping(value = "/upload.do",method = RequestMethod.POST)
    public ServerResponse upload(HttpSession session,
                                 @RequestParam(value = "upload_file",required = false)MultipartFile file){

        //登录状态判断
        UserManagerSimpleInfoVo userManagerSimpleInfoVo = (UserManagerSimpleInfoVo) session.getAttribute(Const.CURRENT_USER);
        if (userManagerSimpleInfoVo == null){
            return ServerResponse.serverResponseByFail(10,"用户未登录，请登录");
        }

        //权限判断
        if (userManagerSimpleInfoVo.getRole() != RoleEnum.ROLE_ADMIN.getCode()){
            return ServerResponse.serverResponseByFail(CommonEnum.NO_POWER.getStatus(),CommonEnum.NO_POWER.getMsg());
        }

        String path = "D://图片上传测试";

        return productManageService.upload(file,path);

    }

}
