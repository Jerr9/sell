package com.o2ii.sell.controller;

import com.o2ii.sell.vo.ProductInfoVO;
import com.o2ii.sell.vo.ProductVO;
import com.o2ii.sell.vo.ResultVO;
import com.o2ii.sell.dataobject.ProductCategory;
import com.o2ii.sell.dataobject.ProductInfo;
import com.o2ii.sell.service.ProductCategoryService;
import com.o2ii.sell.service.ProductInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/buyer/product")
public class BuyerProductController {

    @Autowired
    private ProductInfoService productInfoService;

    @Autowired
    private ProductCategoryService productCategoryService;

    @RequestMapping(value = "/list")
    public ResultVO list() {
        // 1.查询所有上架产品
        List<ProductInfo> productInfoList = productInfoService.findUpAll();
        // 2.查询所有类目
//        List<Integer> categoryTypeList = new ArrayList<>();
//        // 传统lowB方法
//        for(ProductInfo productInfo : productInfoList) {
//            categoryTypeList.add(productInfo.getCategoryType());
//        }
        // 精简方法（Java8-lambda表达式)
        List<Integer> categoryTypeList = productInfoList.stream()
                .map(e -> e.getCategoryType())
                .collect(Collectors.toList());
        // 拿到上架的品类
        List<ProductCategory> productCategoryList = productCategoryService.findByCategoryTypeIn(categoryTypeList);
        // 3.数据拼装
        List<ProductVO> productVOList = new ArrayList<> ();
        for(ProductCategory productCategory : productCategoryList) {
            ProductVO productVO = new ProductVO();
            productVO.setCategoryName(productCategory.getCategoryName());
            productVO.setCategoryType(productCategory.getCategoryType());
            List<ProductInfoVO> list = new ArrayList<>();
            for(ProductInfo productInfo : productInfoList) {
                ProductInfoVO productInfoVO = new ProductInfoVO();
                if (productInfo.getCategoryType().equals(productCategory.getCategoryType())) {
                    /**
                     * lowB方法
                     */
//                    productInfoVO.setProductId(productInfo.getProductId());
//                    productInfoVO.setProductName(productInfo.getProductName());
//                    productInfoVO.setProductDescription(productInfo.getProductDescription());
//                    productInfoVO.setProductIcon(productInfo.getProductIcon());
//                    productInfoVO.setProductPrice(productInfo.getProductPrice());


                    /**
                     * 来个优雅点的, springframework提供，BeanUtils.copyProperties(source, target);
                     */
                    BeanUtils.copyProperties(productInfo, productInfoVO);
                    list.add(productInfoVO);
                }
            }
            productVO.setProductInfoVOList(list);
            productVOList.add(productVO);
        }

        ResultVO resultVO = new ResultVO();
        ProductInfoVO productInfoVO = new ProductInfoVO();

        ProductVO productVO = new ProductVO();
        productVO.setProductInfoVOList(Arrays.asList(productInfoVO));

        resultVO.setCode(0);
        resultVO.setMsg("操作成功");
//        resultVO.setData(Arrays.asList(productVO));
        resultVO.setData(productVOList);
        return resultVO;
    }
}
