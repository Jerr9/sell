package com.o2ii.sell.service;

import com.o2ii.sell.dto.OrderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderMasterService {

    OrderDTO createOrder(OrderDTO orderDTO);

    OrderDTO findOneOrder(String buyerOpenid, String orderId);

    /**
     * 查询一个用户的所有订单
     * @param buyerOpenid
     * @return
     */
    List<OrderDTO> queryOrderList(String buyerOpenid, Integer pageIndex, Integer pageSize);

    Page<OrderDTO> queryOrderList2(String buyerOpenid, Pageable pageable);

    OrderDTO cancelOrder(OrderDTO orderDTO);

    OrderDTO paidOrder(OrderDTO orderDTO);
}
