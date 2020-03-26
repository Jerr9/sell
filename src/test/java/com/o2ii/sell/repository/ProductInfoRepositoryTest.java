package com.o2ii.sell.repository;

import com.o2ii.sell.dataobject.ProductInfo;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class ProductInfoRepositoryTest {

    @Autowired
    private ProductInfoRepository repository;

    @Test
    void saveTest() {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("123456");
        productInfo.setProductName("皮蛋瘦肉粥");
        productInfo.setProductPrice(new BigDecimal(11.99));
        productInfo.setProductStock(99);
        productInfo.setProductDescription("皮蛋瘦肉粥营养丰富，以切成小块的皮蛋及咸瘦肉为配料。");
        productInfo.setProductIcon("https://www.o2ii.com/cdn/static/product/images/pidanshourouzhou-74324543054.png");
        productInfo.setProductStatus(0);
        productInfo.setCategoryType(3);
        ProductInfo result = repository.save(productInfo);
        Assert.assertEquals(new String("123456"), result.getProductId());
    }

    @Test
    void findByProductId() {
        ProductInfo productInfo = repository.findByProductId("123455");
        System.out.print(productInfo.toString());
        Assert.assertEquals("123455", productInfo.getProductId());
    }

    @Test
    void findByProductStatus() {
        List<ProductInfo> result = repository.findByProductStatus(0);
        Assert.assertEquals(2, result.size());
    }
}