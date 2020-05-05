package com.o2ii.sell.controller;

import com.o2ii.sell.converter.OrderForm2OrderDTOConverter;
import com.o2ii.sell.dto.OrderDTO;
import com.o2ii.sell.enums.BasicEnum;
import com.o2ii.sell.enums.BusinessEnum;
import com.o2ii.sell.exception.GlobalException;
import com.o2ii.sell.form.OrderForm;
import com.o2ii.sell.result.ResponseData;
import com.o2ii.sell.service.OrderMasterService;
import com.o2ii.sell.service.PushMessageService;
import com.o2ii.sell.service.WebSocket;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @Autowired
    private PushMessageService pushMessageService;

    @Autowired
    private WebSocket webSocket;

    /**
     * 创建订单
     * @return ResponseData
     */
    @RequestMapping(value = "create", method = RequestMethod.POST)
//    public ResultVO<Map<String, String>> create(@RequestBody @Valid OrderForm orderForm, BindingResult bindingResult) {
    public ResponseData<Map<String, String>> create(@Valid OrderForm orderForm) {
        // @Valid 校验表单
        // 然后OrderForm转OrderDTO
//        System.out.println(orderForm.toString());
        // 此次可放到 GlobalExceptionHandler 中处理
//        if (bindingResult.hasErrors()) {
//            throw new GlobalException(BusinessEnum.PARAM_ERROR.getCode(), bindingResult.getFieldError().getDefaultMessage());
//        }

        OrderDTO orderDTO = OrderForm2OrderDTOConverter.converter(orderForm);
        System.out.println(orderDTO.getOrderDetailList().size());
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())) {
            throw new GlobalException(BusinessEnum.CART_EMPTY);
        }
        OrderDTO result = orderMasterService.createOrder(orderDTO);
        // 推送微信模板消息
        pushMessageService.orderStatus(orderDTO);
        log.info("【创建订单】result={}", result);
        Map<String, String> map = new HashMap<>();
        map.put("orderId", result.getOrderId());
        webSocket.sendMessage("您有一个新订单");
        return new ResponseData(BasicEnum.SUCCESS, map);
    }

    @RequestMapping(value = "orderList", method = RequestMethod.GET)
    public ResponseData<List<OrderDTO>> orderList(@RequestParam("openId") String openid,
                                                  @RequestParam("page") Integer page,
                                                  @RequestParam("size") Integer size
                                              ) {
        if (StringUtils.isEmpty(openid)) {
            throw new GlobalException(BusinessEnum.PARAM_ERROR);
        }
        List<OrderDTO> orderMasterList = orderMasterService.queryOrderList(openid, page, size);
        ResponseData<List<OrderDTO>> result = new ResponseData(BasicEnum.SUCCESS, orderMasterList);
        return result;
    }

    @RequestMapping(value = "orderList2", method = RequestMethod.POST)
    public ResponseData<List<OrderDTO>> orderList2(@RequestParam("openId") String openid,
                                                   @RequestParam("page") Integer page,
                                                   @RequestParam("size") Integer size
    ) {
        if (StringUtils.isEmpty(openid)) {
            throw new GlobalException(BusinessEnum.PARAM_ERROR);
        }
        PageRequest pageable = PageRequest.of(page, size, Sort.by("createTime"));
        Page<OrderDTO> orderMasterList = orderMasterService.queryOrderList2(openid, pageable);

        ResponseData<List<OrderDTO>> result = new ResponseData(BasicEnum.SUCCESS, orderMasterList.get());
        return result;
    }

    @RequestMapping(value = "orderDetail", method = RequestMethod.POST)
    public ResponseData<OrderDTO> orderDetail(@RequestParam("openId") String openId,
                                              @RequestParam("orderId") String orderId
    ) {
        if (StringUtils.isEmpty(openId) || StringUtils.isEmpty(orderId)) {
            throw new GlobalException(BusinessEnum.PARAM_ERROR);
        }
        OrderDTO orderDTO = orderMasterService.findOneOrder(openId, orderId);
        ResponseData<OrderDTO> result = new ResponseData(BasicEnum.SUCCESS, orderDTO);
        return result;
    }

    @RequestMapping(value = "cancelOrder", method = RequestMethod.POST)
    public ResponseData<OrderDTO> cancelOrder(@RequestParam("openId") String openId,
                                              @RequestParam("orderId") String orderId
    ) {
        if (StringUtils.isEmpty(openId) || StringUtils.isEmpty(orderId)) {
            throw new GlobalException(BusinessEnum.PARAM_ERROR);
        }

        OrderDTO orderDTO = orderMasterService.findOneOrder(openId, orderId);
        OrderDTO cancelOrderDTO = orderMasterService.cancelOrder(orderDTO);
        return new ResponseData(BasicEnum.SUCCESS);
    }

}
