package com.ytb.shopping.service.impl;

import com.ytb.shopping.common.ServerResponse;
import com.ytb.shopping.dao.CategoryMapper;
import com.ytb.shopping.enums.CategoryEnum;
import com.ytb.shopping.enums.CommonEnum;
import com.ytb.shopping.pojo.Category;
import com.ytb.shopping.service.ICategoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Service
public class CategoryServiceImpl implements ICategoryService {

    @Resource
    private CategoryMapper categoryMapper;

    /**
     * 获取品类子节点（平级）
     * @param categoryId
     * @return
     */
    @Override
    public ServerResponse getCategory(Integer categoryId) {

        //非空校验
        if (categoryId == null){
            return ServerResponse.serverResponseByFail(CommonEnum.INPUT_NULL.getStatus(),CommonEnum.INPUT_NULL.getMsg());
        }

        //根据id查询类别
        Category category = categoryMapper.selectByPrimaryKey(categoryId);
        if (category == null){
            return ServerResponse.serverResponseByFail(CategoryEnum.GET_CATEGORY_FAIL_2.getStatus(),CategoryEnum.GET_CATEGORY_FAIL_2.getMsg());
        }

        //查询子类别
        List<Category> categories = categoryMapper.findChildCategory(categoryId);

        //返回结果

        return ServerResponse.serverResponseBySuccess(categories);
    }

    @Override
    public ServerResponse addCategory(Integer parentId, String categoryName) {

        //参数校验
        if (categoryName == null || categoryName.equals("")){
            return ServerResponse.serverResponseByFail(CommonEnum.INPUT_NULL.getMsg());
        }

        //添加
        Category category = new Category();
        category.setParentId(parentId);
        category.setName(categoryName);
        category.setStatus(1);
        int result = categoryMapper.insert(category);

        //返回结果
        if (result == 0){
            return ServerResponse.serverResponseByFail(1,"添加品类失败");
        }
        return ServerResponse.serverResponseBySuccess(null,"添加品类成功");
    }

    @Override
    public ServerResponse setCategoryName(Integer categoryId, String categoryName) {

        //参数判空
        if (categoryId == null || categoryName == null || categoryName.equals("")){
            return ServerResponse.serverResponseByFail(CommonEnum.INPUT_NULL.getMsg());
        }

        //判断类别是否存在
        Category category = categoryMapper.selectByPrimaryKey(categoryId);
        if (category == null){
            return ServerResponse.serverResponseByFail("该类别不存在");
        }

        //修改
        int result = categoryMapper.setCategoryName(categoryId,categoryName);

        //返回
        if (result == 0){
            return ServerResponse.serverResponseByFail(1,"更新品类名字失败");
        }

        return ServerResponse.serverResponseBySuccess(null,"更新品类名字成功");
    }

    @Override
    public ServerResponse getDeepCategory(Integer categoryId) {

        //参数判空
        if (categoryId == null){
            return ServerResponse.serverResponseByFail(CommonEnum.INPUT_NULL.getMsg());
        }

        //查询
        Set<Category> categorySet = new HashSet<>();

        categorySet = findAllChildCategory(categorySet,categoryId);

        Set<Integer> integerSet = new HashSet<>();

        Iterator<Category> categoryIterator = categorySet.iterator();
        while (categoryIterator.hasNext()){
            Category category = categoryIterator.next();
            integerSet.add(category.getId());
        }

        return ServerResponse.serverResponseBySuccess(integerSet);
    }

    /**
     * 递归查找类别id
     */
    private Set<Category> findAllChildCategory(Set<Category> categorySet,Integer categoryId){

        Category category = categoryMapper.selectByPrimaryKey(categoryId);
        if (category != null){
            categorySet.add(category);
        }

        List<Category> categoryList = categoryMapper.findChildCategory(categoryId);
        if (categoryList != null && categoryList.size() > 0){
            for (Category c :categoryList
                    ) {
                findAllChildCategory(categorySet,c.getId());
            }
        }

        return categorySet;
    }
}
