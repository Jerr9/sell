package com.o2ii.sell.service;


import com.o2ii.sell.dto.OrderDTO;
import org.springframework.stereotype.Service;

/**
 * 消息推送
 */
@Service
public interface PushMessageService {
    /**
     * 订单状态变更消息
     * @param orderDTO
     */
    void orderStatus(OrderDTO orderDTO);
}
