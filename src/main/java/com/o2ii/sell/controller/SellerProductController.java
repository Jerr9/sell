package com.o2ii.sell.controller;

import com.o2ii.sell.dto.SellerProductDTO;
import com.o2ii.sell.enums.ResultEnum;
import com.o2ii.sell.enums.VOEnum;
import com.o2ii.sell.exception.SellException;
import com.o2ii.sell.form.ProductForm;
import com.o2ii.sell.service.SellerProductService;
import com.o2ii.sell.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Random;

@RestController
@RequestMapping(value = "seller/product")
@Slf4j
public class SellerProductController {

    @Autowired
    SellerProductService sellerProductService;

    @RequestMapping(value = "add")
    public ResultVO addProduct(@Valid @RequestBody ProductForm productForm, BindingResult bindingResult) throws Exception {
        try {
            log.info("【卖家添加产品】= {}", productForm);
            if (bindingResult.hasErrors()) {
                throw new SellException(ResultEnum.PARAM_ERROR.getCode(), bindingResult.getFieldError().getDefaultMessage());
            }
            SellerProductDTO sellerProductDTO = new SellerProductDTO();
            BeanUtils.copyProperties(productForm, sellerProductDTO);
            SellerProductDTO resultDTO = sellerProductService.addProduct(sellerProductDTO);
            log.info("【产品添加完毕】= {}", resultDTO);
            return new ResultVO(VOEnum.SUCCESS);
        }
        catch (SellException e) {
            return new ResultVO();
        }
    }

    @RequestMapping(value = "edit")
    public ResultVO editProduct(@RequestBody ProductForm productForm, BindingResult bindingResult) throws Exception {
        if ("".equals(productForm.getProductId())) {
            throw new SellException(ResultEnum.PARAM_ERROR.getCode(), "productId 不能为空");
        }
        SellerProductDTO sellerProductDTO = new SellerProductDTO();
        BeanUtils.copyProperties(productForm, sellerProductDTO);
        SellerProductDTO resultDTO = sellerProductService.editProduct(sellerProductDTO);
        log.info("【卖家修改产品】= {}", productForm);
        return new ResultVO(VOEnum.SUCCESS);
    }
}
