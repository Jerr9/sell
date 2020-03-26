package com.o2ii.sell.repository;


import com.o2ii.sell.dataobject.OrderDetail;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderDetailRepositoryTest {

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Test
    public void orderDetailTest() {
        List<OrderDetail> result = orderDetailRepository.findByOrderId("2020032222222222666666");
        Assert.assertNotEquals(0, result.size());
    }
}
