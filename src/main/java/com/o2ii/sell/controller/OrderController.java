package com.o2ii.sell.controller;

import com.o2ii.sell.converter.OrderForm2OrderDTOConverter;
import com.o2ii.sell.dataobject.OrderMaster;
import com.o2ii.sell.dto.OrderDTO;
import com.o2ii.sell.enums.ResultEnum;
import com.o2ii.sell.enums.VOEnum;
import com.o2ii.sell.exception.SellException;
import com.o2ii.sell.form.OrderForm;
import com.o2ii.sell.service.OrderMasterService;
import com.o2ii.sell.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {

    @Autowired
    private OrderMasterService orderMasterService;

    /**
     * 创建订单
     * @param ResultVO<Map<String, String>>
     * @return
     */
    @RequestMapping(value = "create", method = RequestMethod.POST)
//    public ResultVO<Map<String, String>> create(@RequestBody @Valid OrderForm orderForm, BindingResult bindingResult) {
    public ResultVO<Map<String, String>> create(@Valid OrderForm orderForm, BindingResult bindingResult) {
        // @Valid 校验表单
        // 然后OrderForm转OrderDTO
//        System.out.println(orderForm.toString());

        if (bindingResult.hasErrors()) {
            throw new SellException(ResultEnum.PARAM_ERROR.getCode(), bindingResult.getFieldError().getDefaultMessage());
        }

        OrderDTO orderDTO = OrderForm2OrderDTOConverter.converter(orderForm);
        System.out.println(orderDTO.getOrderDetailList().size());
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())) {
            throw new SellException(ResultEnum.CART_EMPTY);
        }
        OrderDTO result = orderMasterService.createOrder(orderDTO);
        log.info("【创建订单】result={}", result);
        log.warn("【创建订单】result={}", result);
        Map<String, String> map = new HashMap<>();
        map.put("orderId", result.getOrderId());
        return new ResultVO(VOEnum.SUCCESS, map);
    }

    @RequestMapping(value = "orderList", method = RequestMethod.GET)
    public ResultVO<List<OrderDTO>> orderList(@RequestParam("openId") String openid,
                                              @RequestParam("page") Integer page,
                                              @RequestParam("size") Integer size
                                              ) {
        if (StringUtils.isEmpty(openid)) {
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        List<OrderDTO> orderMasterList = orderMasterService.queryOrderList(openid, page, size);
        ResultVO<List<OrderDTO>> result = new ResultVO(VOEnum.SUCCESS, orderMasterList);
        return result;
    }

    @RequestMapping(value = "orderList2", method = RequestMethod.POST)
    public ResultVO<List<OrderDTO>> orderList2(@RequestParam("openId") String openid,
                                              @RequestParam("page") Integer page,
                                              @RequestParam("size") Integer size
    ) {
        if (StringUtils.isEmpty(openid)) {
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        PageRequest pageable = PageRequest.of(page, size, Sort.by("createTime"));
        Page<OrderDTO> orderMasterList = orderMasterService.queryOrderList2(openid, pageable);

        ResultVO<List<OrderDTO>> result = new ResultVO(VOEnum.SUCCESS, orderMasterList.get());
        return result;
    }

    @RequestMapping(value = "orderDetail", method = RequestMethod.POST)
    public ResultVO<OrderDTO> orderDetail(@RequestParam("openId") String openId,
                                               @RequestParam("orderId") String orderId
    ) {
        if (StringUtils.isEmpty(openId) || StringUtils.isEmpty(orderId)) {
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        OrderDTO orderDTO = orderMasterService.findOneOrder(openId, orderId);
        ResultVO<OrderDTO> result = new ResultVO(VOEnum.SUCCESS, orderDTO);
        return result;
    }

    @RequestMapping(value = "cancelOrder", method = RequestMethod.POST)
    public ResultVO<OrderDTO> cancelOrder(@RequestParam("openId") String openId,
                                          @RequestParam("orderId") String orderId
    ) {
        if (StringUtils.isEmpty(openId) || StringUtils.isEmpty(orderId)) {
            throw new SellException(ResultEnum.PARAM_ERROR);
        }

        OrderDTO orderDTO = orderMasterService.findOneOrder(openId, orderId);
        OrderDTO cancelOrderDTO = orderMasterService.cancelOrder(orderDTO);
        return new ResultVO(VOEnum.SUCCESS);
    }

}
