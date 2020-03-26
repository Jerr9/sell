package com.o2ii.sell.service.impl;

import com.o2ii.sell.converter.OrderMaster2OrderDTOConverter;
import com.o2ii.sell.dataobject.OrderDetail;
import com.o2ii.sell.dataobject.OrderMaster;
import com.o2ii.sell.dataobject.ProductInfo;
import com.o2ii.sell.dto.CartDTO;
import com.o2ii.sell.dto.OrderDTO;
import com.o2ii.sell.enums.OrderStatusEnum;
import com.o2ii.sell.enums.PayStatusEnum;
import com.o2ii.sell.enums.ResultEnum;
import com.o2ii.sell.exception.SellException;
import com.o2ii.sell.repository.OrderDetailRepository;
import com.o2ii.sell.repository.OrderMasterRepository;
import com.o2ii.sell.repository.ProductInfoRepository;
import com.o2ii.sell.service.OrderMasterService;
import com.o2ii.sell.service.ProductInfoService;
import com.o2ii.sell.util.KeyUtil;
import com.o2ii.sell.vo.OrderDetailVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderMasterServiceImpl implements OrderMasterService {

    @Autowired
    ProductInfoRepository productInfoRepository;

    @Autowired
    OrderMasterRepository orderMasterRepository;

    @Autowired
    OrderDetailRepository orderDetailRepository;

    @Autowired
    ProductInfoService productInfoService;

    @Override
    @Transactional
    public OrderDTO createOrder(OrderDTO orderDTO) {
        // 生产orderId
        String orderId = KeyUtil.genUniqueKey();
        BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);
        // 查询下单的商品信息
        for(OrderDetail orderDetail : orderDTO.getOrderDetailList()) {
            ProductInfo productInfo = productInfoRepository.findByProductId(orderDetail.getProductId());
            // 产品是否存在
            if (productInfo == null) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
//            if (productInfo.getProductStock() < orderDetail.getProductQuantity()) {
//                throw new SellException(ResultEnum.PRODUCT_OUT_OF_STOCK);
//            }
            // 累计价格
            orderAmount = productInfo.getProductPrice().multiply(
                        new BigDecimal(orderDetail.getProductQuantity()
                    )).add(orderAmount);
            orderDetail.setDetailId(KeyUtil.genUniqueKey());
            orderDetail.setOrderId(orderId);
            BeanUtils.copyProperties(productInfo, orderDetail);
            orderDetailRepository.save(orderDetail);
        }
        // 写入订单数据库
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO, orderMaster);
        // 订单号在最后设置
        orderMaster.setOrderId(orderId);
        orderMasterRepository.save(orderMaster);

        // 扣减库存
        List<CartDTO> cartDTOList = new ArrayList<>();
        cartDTOList = orderDTO.getOrderDetailList()
                .stream()
                .map(e -> new CartDTO(e.getProductId(), e.getProductQuantity()))
                .collect(Collectors.toList());
        productInfoService.decreaseStock(cartDTOList);
        orderDTO.setOrderId(orderId);
        return orderDTO;
    }

    @Override
    public OrderDTO findOneOrder(String buyerOpenid, String orderId) {
        OrderMaster orderMaster = orderMasterRepository.findByBuyerOpenidAndOrderId(buyerOpenid, orderId);
        if (orderMaster == null) {
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(orderId);
        if (orderDetailList.size() == 0) {
            throw new SellException(ResultEnum.ORDERDETAIL_NOT_EXIST);
        }
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderDetailList(orderDetailList);
        BeanUtils.copyProperties(orderMaster, orderDTO);

        return orderDTO;
    }

    // 订单分页实现1，返回List<OrderDTO>
    @Override
    public List<OrderDTO> queryOrderList(String buyerOpenid, Integer pageIndex, Integer pageSize) {
        PageRequest request = PageRequest.of(pageIndex, pageSize, Sort.by("createTime"));

        Page<OrderMaster> result = orderMasterRepository.findByBuyerOpenid(buyerOpenid, request);
        // 可不用，没有回返回空
//        if (result.getNumber() == 0) {
//            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
//        }
        List<OrderMaster> orderMasterList = result.getContent();
        List<OrderDTO> orderDTOList = new ArrayList<>();
        for(OrderMaster orderMaster : orderMasterList) {
            OrderDTO orderDTO = new OrderDTO();
            BeanUtils.copyProperties(orderMaster, orderDTO);
            orderDTOList.add(orderDTO);
        }
        return orderDTOList;
    }

    // 订单分页实现2，返回Page<OrderDTO>
    @Override
    public Page<OrderDTO> queryOrderList2(String buyerOpenid, Pageable pageable) {

        Page<OrderMaster> result = orderMasterRepository.findByBuyerOpenid(buyerOpenid, pageable);

        List<OrderDTO> orderDTOList = OrderMaster2OrderDTOConverter.convert(result.getContent());

        return new PageImpl<OrderDTO>(orderDTOList, pageable, result.getTotalElements());
    }

    @Override
    @Transactional
    public OrderDTO cancelOrder(OrderDTO orderDTO) {
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO, orderMaster);
        // 判断订单状态
        if (orderMaster.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        // 修改订单状态
        orderMaster.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        BeanUtils.copyProperties(orderMaster, orderDTO);
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        if (updateResult == null) {
            throw new SellException(ResultEnum.ORDER_UPDATE_ERROR);
        }
        // 返回库存
        String id = orderMaster.getOrderId();
        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(orderMaster.getOrderId());
        List<CartDTO> cartDTOList = orderDetailList.stream().map(
                e -> new CartDTO(e.getProductId(), e.getProductQuantity())
        ).collect(Collectors.toList());

        productInfoService.increaseStock(cartDTOList);
        //如果已经支付，需要退款
        if (orderMaster.getPayStatus().equals(PayStatusEnum.PAYED.getCode())) {
            // TODO
        }
        return orderDTO;
    }

    @Override
    public OrderDTO paidOrder(OrderDTO orderDTO) {
        return null;
    }
}
