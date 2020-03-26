package com.o2ii.sell.repository;

import com.o2ii.sell.dataobject.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryTest {
    @Autowired
    private ProductCategoryRepository repository;

    @Test
    public void findOne() {
        ProductCategory productCategory = repository.findByCategoryId(1);
        System.out.println(productCategory.toString());
    }

    @Test
    @Transactional
    public void saveTest() {
//        ProductCategory productCategory = repository.findByCategoryId(2);
//        productCategory.setCategoryType(2001);
        ProductCategory productCategory = new ProductCategory(2005, "儿童文学");
        ProductCategory result = repository.save(productCategory);
        Assert.assertNotNull(result);
    }

    @Test
    public void findByCategoryTypeInTest() {
        List<Integer> categoryList = Arrays.asList(1000, 2001, 3000);
        List<ProductCategory> resultList = repository.findByCategoryTypeIn(categoryList);
        Assert.assertNotEquals(0, resultList.size());
        System.out.println("findByCategoryTypeInTest:" + resultList.size());
        for(ProductCategory category : resultList) {
            System.out.println(category.toString());
        }
    }
}
