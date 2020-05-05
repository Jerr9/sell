package com.o2ii.sell.converter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.o2ii.sell.dataobject.OrderDetail;
import com.o2ii.sell.dto.OrderDTO;
import com.o2ii.sell.enums.BusinessEnum;
import com.o2ii.sell.exception.GlobalException;
import com.o2ii.sell.form.OrderForm;

import java.util.ArrayList;
import java.util.List;

public class OrderForm2OrderDTOConverter {
    public static OrderDTO converter(OrderForm orderForm) {

        OrderDTO orderDTO = new OrderDTO();

        orderDTO.setBuyerName(orderForm.getUserName());
        orderDTO.setBuyerPhone(orderForm.getPhone());
        orderDTO.setBuyerAddress(orderForm.getAddress());
        orderDTO.setBuyerOpenid(orderForm.getOpenId());

        List<OrderDetail> orderDetailList = new ArrayList<>();

        // usage: 1
//        List<OrderDetail> list = orderForm.getItems();
//        for(OrderDetail item : list) {
//            OrderDetail od = new OrderDetail();
//            BeanUtils.copyProperties(item, od);
//            orderDetailList.add(od);
//        }

        // usage: 2
        Gson gson = new Gson();
        try {
            orderDetailList = gson.fromJson(orderForm.getItems(), new TypeToken<List<OrderDetail>>() {}.getType());
        }
        catch (Exception e) {
            throw new GlobalException(BusinessEnum.PARAM_ERROR);
        }
        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }


}
