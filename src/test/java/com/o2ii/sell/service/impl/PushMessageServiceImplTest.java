package com.o2ii.sell.service.impl;

import com.o2ii.sell.dto.OrderDTO;
import com.o2ii.sell.service.OrderMasterService;
import com.o2ii.sell.service.PushMessageService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class PushMessageServiceImplTest {
    @Autowired
    private OrderMasterService orderMasterService;

    @Autowired
    private PushMessageService pushMessageService;

    @Test
    void orderStatusTest() {
        OrderDTO orderDTO = orderMasterService.findOneOrder("oFtQywLXiiIJ5X52uxLYsYUSO22M", "1585487345383926086");
        pushMessageService.orderStatus(orderDTO);
    }
}