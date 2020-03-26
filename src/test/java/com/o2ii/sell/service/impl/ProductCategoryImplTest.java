package com.o2ii.sell.service.impl;

import com.o2ii.sell.dataobject.ProductCategory;
import com.o2ii.sell.service.impl.ProductCategoryImpl;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
class ProductCategoryImplTest {

    @Autowired
    private ProductCategoryImpl categoryService;

    @Test
    void findOne() throws Exception {
        ProductCategory productCategory = categoryService.findOne(1);
        System.out.println(productCategory.toString());
        Assert.assertEquals(new Integer(1), productCategory.getCategoryId());
    }

    @Test
    void findAll() {
        List<ProductCategory> list = categoryService.findAll();
        Assert.assertNotEquals(0, list.size());
    }

    @Test
    void findByCategoryTypeIn() {
        List<ProductCategory> list = categoryService.findByCategoryTypeIn(Arrays.asList(1000, 2001));
        Assert.assertEquals(2, list.size());
    }

    @Test
    @Transactional
    void save() {
        ProductCategory productCategory = new ProductCategory(5000, "老年福利");
        ProductCategory result = categoryService.save(productCategory);
        System.out.println("new Id: " + result.getCategoryId());
        Assert.assertNotNull(result);
    }
}