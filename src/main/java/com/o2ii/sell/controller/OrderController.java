package com.o2ii.sell.controller;

import com.o2ii.sell.dto.OrderDTO;
import com.o2ii.sell.enums.VOEnum;
import com.o2ii.sell.service.impl.OrderMasterServiceImpl;
import com.o2ii.sell.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {

    @Autowired
    private OrderMasterServiceImpl orderMasterService;

    @RequestMapping(value = "create", method = RequestMethod.POST)
    public ResultVO create(@RequestBody OrderDTO orderDTO) {

        OrderDTO result = orderMasterService.createOrder(orderDTO);
        log.info("【创建订单】result={}", result);
        log.warn("【创建订单】result={}", result);
        return new ResultVO(VOEnum.SUCCESS, result.getOrderId());
    }
}
