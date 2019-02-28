package com.ytb.shopping.controller.backend;

import com.ytb.shopping.common.Const;
import com.ytb.shopping.common.ServerResponse;
import com.ytb.shopping.dao.CategoryMapper;
import com.ytb.shopping.enums.CategoryEnum;
import com.ytb.shopping.enums.CommonEnum;
import com.ytb.shopping.enums.RoleEnum;
import com.ytb.shopping.pojo.UserInfo;
import com.ytb.shopping.service.ICategoryService;
import com.ytb.shopping.vo.UserManagerSimpleInfoVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/manage/category")
public class CategoryController {

    @Resource
    private ICategoryService categoryService;

    /**
     * 获取品类子节点（平级）
     */
    @RequestMapping("/get_category.do")
    public ServerResponse getCategory(HttpSession session, Integer categoryId){

        //登录判断
        UserManagerSimpleInfoVo userManagerSimpleInfoVo = (UserManagerSimpleInfoVo) session.getAttribute(Const.CURRENT_USER);
        if (userManagerSimpleInfoVo == null){
            return ServerResponse.serverResponseByFail(CategoryEnum.GET_CATEGORY_FAIL_1.getStatus(),CategoryEnum.GET_CATEGORY_FAIL_1.getMsg());
        }

        //权限判断
        if (userManagerSimpleInfoVo.getRole() != RoleEnum.ROLE_ADMIN.getCode()){
            return ServerResponse.serverResponseByFail(CommonEnum.NO_POWER.getStatus(),CommonEnum.NO_POWER.getMsg());
        }

        return categoryService.getCategory(categoryId);
    }

    /**
     * 增加节点
     */
    @RequestMapping("/add_category.do")
    public ServerResponse addCategory(HttpSession session,@RequestParam(required = false,defaultValue = "0") Integer parentId, String categoryName){

        //登录判断
        UserManagerSimpleInfoVo userManagerSimpleInfoVo = (UserManagerSimpleInfoVo) session.getAttribute(Const.CURRENT_USER);
        if (userManagerSimpleInfoVo == null){
            return ServerResponse.serverResponseByFail(CategoryEnum.GET_CATEGORY_FAIL_1.getStatus(),CategoryEnum.GET_CATEGORY_FAIL_1.getMsg());
        }

        //权限判断
        if (userManagerSimpleInfoVo.getRole() != RoleEnum.ROLE_ADMIN.getCode()){
            return ServerResponse.serverResponseByFail(CommonEnum.NO_POWER.getStatus(),CommonEnum.NO_POWER.getMsg());
        }

        return categoryService.addCategory(parentId,categoryName);

    }

    /**
     * 修改品类名字
     */
    @RequestMapping("/set_category_name.do")
    public ServerResponse setCategoryName(HttpSession session, Integer categoryId, String categoryName){

        //登录判断
        UserManagerSimpleInfoVo userManagerSimpleInfoVo = (UserManagerSimpleInfoVo) session.getAttribute(Const.CURRENT_USER);
        if (userManagerSimpleInfoVo == null){
            return ServerResponse.serverResponseByFail(CategoryEnum.GET_CATEGORY_FAIL_1.getStatus(),CategoryEnum.GET_CATEGORY_FAIL_1.getMsg());
        }

        //权限判断
        if (userManagerSimpleInfoVo.getRole() != RoleEnum.ROLE_ADMIN.getCode()){
            return ServerResponse.serverResponseByFail(CommonEnum.NO_POWER.getStatus(),CommonEnum.NO_POWER.getMsg());
        }

        return categoryService.setCategoryName(categoryId,categoryName);
    }

    /**
     * 获取当前分类id及递归子节点categoryId
     */
    @RequestMapping("/get_deep_category.do")
    public ServerResponse getDeepCategory(HttpSession session, Integer categoryId){

        //登录判断
        UserManagerSimpleInfoVo userManagerSimpleInfoVo = (UserManagerSimpleInfoVo) session.getAttribute(Const.CURRENT_USER);
        if (userManagerSimpleInfoVo == null){
            return ServerResponse.serverResponseByFail(CategoryEnum.GET_CATEGORY_FAIL_1.getStatus(),CategoryEnum.GET_CATEGORY_FAIL_1.getMsg());
        }

        //权限判断
        if (userManagerSimpleInfoVo.getRole() != RoleEnum.ROLE_ADMIN.getCode()){
            return ServerResponse.serverResponseByFail(1,"无权限");
        }

        return categoryService.getDeepCategory(categoryId);
    }

}
