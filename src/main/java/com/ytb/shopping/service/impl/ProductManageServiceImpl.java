package com.ytb.shopping.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ytb.shopping.common.ServerResponse;
import com.ytb.shopping.dao.CategoryMapper;
import com.ytb.shopping.dao.ProductMapper;
import com.ytb.shopping.enums.CommonEnum;
import com.ytb.shopping.pojo.Category;
import com.ytb.shopping.pojo.Product;
import com.ytb.shopping.service.IProductManageService;
import com.ytb.shopping.util.DateUtils;
import com.ytb.shopping.util.PropertiesUtils;
import com.ytb.shopping.vo.ProductDetailVO;
import com.ytb.shopping.vo.ProductListVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
public class ProductManageServiceImpl implements IProductManageService {

    @Resource
    private ProductMapper productMapper;

    @Resource
    private CategoryMapper categoryMapper;

    @Override
    public ServerResponse save(Product product) {

        //参数判空
        if (product == null){
            return ServerResponse.serverResponseByFail(CommonEnum.INPUT_NULL.getMsg());
        }

        //设置商品主图，子图用“，”隔开，第一张为主图
        String subImages = product.getSubImages();
        if (subImages != null || !subImages.equals("")){
            String[] subImageArr = subImages.split(",");
            if (subImageArr.length > 0){
                product.setMainImage(subImageArr[0]);
            }
        }

        /**
         * 判断id，执行添加或更新
         */

        //id为空，添加
        if (product.getId() == null){
            int result = productMapper.insert(product);

            //返回
            if (result > 0){
                return ServerResponse.serverResponseBySuccess(null,"新增产品成功");
            }

        }else {
            int result = productMapper.updateByPrimaryKey(product);

            //返回
            if (result > 0){
                return ServerResponse.serverResponseBySuccess(null,"更新产品成功");
            }
        }


        return ServerResponse.serverResponseByFail(1,"更新产品失败");
    }

    @Override
    public ServerResponse setSaleStatus(Integer productId, Integer status) {

        if (productId == null || status == null){
            return ServerResponse.serverResponseByFail(CommonEnum.INPUT_NULL.getMsg());
        }

        Product product = productMapper.selectByPrimaryKey(productId);
        if (product == null){
            return ServerResponse.serverResponseByFail("商品id不存在");
        }

        Product p = new Product();
        p.setId(productId);
        p.setStatus(status);

        int result = productMapper.updateProductKeySelective(p);
        if (result > 0){
            return ServerResponse.serverResponseBySuccess(null,"修改产品状态成功");
        }

        return ServerResponse.serverResponseByFail(1,"修改产品状态失败");
    }

    @Override
    public ServerResponse detail(Integer productId) {

        //1、参数判空
        if (productId == null){
            return ServerResponse.serverResponseByFail(CommonEnum.INPUT_NULL.getMsg());
        }

        //2、根据商品id查询商品
        Product product = productMapper.selectByPrimaryKey(productId);
        if (product == null){
            return ServerResponse.serverResponseByFail("商品不存在");
        }

        //3、product-->productDetailVO
        ProductDetailVO productDetailVO = translateProductDetailVO(product);

        //4、返回

        return ServerResponse.serverResponseBySuccess(productDetailVO);
    }

    private ProductDetailVO translateProductDetailVO(Product product){
        ProductDetailVO productDetailVO = new ProductDetailVO();

        BeanUtils.copyProperties(product,productDetailVO);

        productDetailVO.setCreateTime(DateUtils.dateToStr(product.getCreateTime()));
        productDetailVO.setUpdateTime(DateUtils.dateToStr(product.getUpdateTime()));
        productDetailVO.setImageHost(PropertiesUtils.readByKey("imageHost"));

        Category category = categoryMapper.selectByPrimaryKey(product.getCategoryId());
        if (category != null){
            productDetailVO.setParentCategoryId(category.getParentId());
        }else {
            productDetailVO.setParentCategoryId(0);
        }

        return productDetailVO;
    }


    @Override
    public ServerResponse list(Integer pageNum, Integer pageSize) {

        PageHelper.startPage(pageNum,pageSize);

        //1、查询商品数据
        List<Product> productList = productMapper.selectAll();
        return getServerResponse(productList);
    }

    @Override
    public ServerResponse search(Integer productId, String productName, Integer pageNum, Integer pageSize) {

        PageHelper.startPage(pageNum,pageSize);

        if (productName != null && !productName.matches("[ ]*")){
            productName = "%" + productName + "%";
        }

        List<Product> productList = productMapper.findProductsByIdOrName(productId,productName);
        return getServerResponse(productList);
    }

    private ServerResponse getServerResponse(List<Product> productList) {
        List<ProductListVO> productListVOList = new ArrayList<>();
        if (productList != null && productList.size() != 0){
            for (Product p:productList
            ) {
                productListVOList.add(translateProductListVO(p));
            }
        }

        PageInfo pageInfo = new PageInfo(productListVOList);

        return ServerResponse.serverResponseBySuccess(pageInfo);
    }

    public static ProductListVO translateProductListVO(Product product){
        ProductListVO productListVO = new ProductListVO();

        BeanUtils.copyProperties(product,productListVO);

        return productListVO;
    }



    @Override
    public ServerResponse upload(MultipartFile file,String path) {

        if (file == null){
            return ServerResponse.serverResponseByFail();
        }

        //1、获取图片名字
        String originalFileName = file.getOriginalFilename();

        //2、获取文件扩展名
        String exName = originalFileName.substring(originalFileName.lastIndexOf("."));

        //3、生成图片唯一新名称
        String newName = UUID.randomUUID().toString() + exName;

        //4、文件路径是否存在
        File filePath = new File(path);
        if (!filePath.exists()) {
            filePath.setWritable(true);
            filePath.mkdirs();
        }

        File file1 = new File(path,newName);

        try {
            file.transferTo(file1);
            //上传到服务器

            Map<String,String> map = new HashMap<>();
            map.put("uri",newName);
            map.put("url",PropertiesUtils.readByKey("imageHost") + "/" + newName);

            return ServerResponse.serverResponseBySuccess(map);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ServerResponse.serverResponseByFail();
    }


}
