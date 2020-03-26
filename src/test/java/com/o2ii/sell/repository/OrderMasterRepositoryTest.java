package com.o2ii.sell.repository;


import com.o2ii.sell.dataobject.OrderMaster;
import com.o2ii.sell.dto.OrderDTO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMasterRepositoryTest {

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Test
    public void saveOrderTest() {
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setBuyerOpenid("oFtQywLXiiIJ5X52uxLYsYUSO22M");
        orderMaster.setBuyerName("猫的名字丑哭了");
        orderMaster.setBuyerPhone("18316556113");
        orderMaster.setBuyerAddress("广东省**市**区**镇**路3-201");
        orderMaster.setOrderAmount(new BigDecimal(16999));
        orderMaster.setOrderId("2020032222222222666666");
        OrderMaster result = orderMasterRepository.save(orderMaster);
        Assert.assertNotNull(result);
    }

    @Test
    public void findByOrderIdTest() {
        OrderMaster orderMaster = orderMasterRepository.findById("2020032222222222666666").get();
        Assert.assertNotNull(orderMaster);
    }

    @Test
    public void findByBuyerOpenidTest() {
        PageRequest request = PageRequest.of(0, 2, Sort.by("createTime"));

        Page<OrderMaster> a = orderMasterRepository.findByBuyerOpenid("oFtQywLXiiIJ5X52uxLYsYUSO22M", request);
        System.out.println(a.getTotalElements());
        Assert.assertNotEquals(0, a.getTotalElements());
    }

}
