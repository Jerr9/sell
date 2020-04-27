package com.o2ii.sell.service.impl;

import com.o2ii.sell.dataobject.ProductInfo;
import com.o2ii.sell.service.ProductInfoService;
import org.junit.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoServiceImplTest {

    @Autowired
    private ProductInfoService productInfoService;

    @Test
    public void findUpAll() {
        List<ProductInfo> result = productInfoService.findUpAll();
        System.out.println(result.size());
        Assert.assertEquals(2, result.size());
    }

    @Test
    public void findAll() throws Exception {
        // 通过库存查看数量
        PageRequest request = PageRequest.of(1, 2, Sort.by("productStock"));
        Page<ProductInfo> productInfoPage = productInfoService.findAll(request);
        System.out.println("所有产品数量：" + productInfoPage.getTotalElements());
        System.out.println("一共有几页：" + productInfoPage.getTotalPages());
        System.out.println("一页的大小：" + productInfoPage.getSize());
        System.out.println("当前页数量：" + productInfoPage.getNumber());
        System.out.println("当前页面的数据列表：" + productInfoPage.getContent());
        System.out.println("当前字段|正、反序：" + productInfoPage.getSort());
    }

    @Test
    public void onSaleTest() throws Exception {
        ProductInfo productInfo = productInfoService.onSale("223457");
        Assert.assertEquals(productInfo.getProductStatus(), new Integer(1));
    }
}