package com.o2ii.sell.service.impl;

import com.o2ii.sell.dataobject.OrderDetail;
import com.o2ii.sell.dataobject.OrderMaster;
import com.o2ii.sell.dto.OrderDTO;
import com.o2ii.sell.service.OrderMasterService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMasterServiceTest {

    @Autowired
    private OrderMasterService orderMasterService;

    private final String OPENID = "oFtQywLXiiIJ5X52uxLYsYUSO22M";

    private final String ORDERID = "1584973529238175429";

    @Test
    public void createOrderTest() throws Exception {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerOpenid(OPENID);
        orderDTO.setBuyerName("猫的名字丑哭了");
        orderDTO.setBuyerPhone("18316556113");
        orderDTO.setBuyerAddress("广东省**市**县**镇**三路4号");
        orderDTO.setOrderAmount(new BigDecimal(12998.00));
        List<OrderDetail> orderDetailList = new ArrayList<>();
        OrderDetail orderDetailOne = new OrderDetail();
        orderDetailOne.setProductId("400001");
        orderDetailOne.setProductQuantity(1);

        OrderDetail orderDetailTwo = new OrderDetail();
        orderDetailTwo.setProductId("223456");
        orderDetailTwo.setProductQuantity(1);

        orderDetailList.add(orderDetailOne);
        orderDetailList.add(orderDetailTwo);
        orderDTO.setOrderDetailList(orderDetailList);
        System.out.println(orderDTO.toString() + "=======");
        orderMasterService.createOrder(orderDTO);
//        Assert();
    }

    @Test
    public void findOneOrderTest() {
        OrderDTO orderDTO = orderMasterService.findOneOrder("oFtQywLXiiIJ5X52uxLYsYUSO22M", "1584973529238175429");
        System.out.println(orderDTO);
        Assert.assertNotNull(orderDTO);
    }

    @Test
    public void queryOrderListTest() {

        List<OrderDTO> orderDTOList = orderMasterService.queryOrderList("oFtQywLXiiIJ5X52uxLYsYUSO22M", 1, 1);

        Assert.assertEquals(0, orderDTOList.size());
    }

    @Test
    public void queryOrderList2Test() throws Exception {
        PageRequest request = PageRequest.of(1,1, Sort.by("createTime"));

        Page<OrderDTO> orderDTOPage = orderMasterService.queryOrderList2("oFtQywLXiiIJ5X52uxLYsYUSO22M", request);

        Assert.assertEquals(1, orderDTOPage.getNumber());
//        Assert.assertEquals(2, orderDTOPage.getTotalElements());
    }

    @Test
    public void cancelOrderTest() throws Exception {
        OrderDTO orderDTO = orderMasterService.findOneOrder(OPENID, ORDERID);
        OrderDTO result = orderMasterService.cancelOrder(orderDTO);
        Assert.assertEquals(new Integer(2), result.getOrderStatus());
    }
}
