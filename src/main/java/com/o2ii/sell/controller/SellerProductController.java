package com.o2ii.sell.controller;

import com.o2ii.sell.dto.SellerProductDTO;
import com.o2ii.sell.enums.BasicEnum;
import com.o2ii.sell.enums.BusinessEnum;
import com.o2ii.sell.exception.GlobalException;
import com.o2ii.sell.form.ProductForm;
import com.o2ii.sell.result.ResponseData;
import com.o2ii.sell.service.SellerProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "seller/product")
@Slf4j
public class SellerProductController {

    @Autowired
    SellerProductService sellerProductService;

    @RequestMapping(value = "add")
    public ResponseData addProduct(@Valid @RequestBody ProductForm productForm, BindingResult bindingResult) throws Exception {
        try {
            log.info("【卖家添加产品】= {}", productForm);
            if (bindingResult.hasErrors()) {
                throw new GlobalException(BusinessEnum.PARAM_ERROR.getCode(), bindingResult.getFieldError().getDefaultMessage());
            }
            SellerProductDTO sellerProductDTO = new SellerProductDTO();
            BeanUtils.copyProperties(productForm, sellerProductDTO);
            SellerProductDTO resultDTO = sellerProductService.addProduct(sellerProductDTO);
            log.info("【产品添加完毕】= {}", resultDTO);
            return new ResponseData(BasicEnum.SUCCESS);
        }
        catch (GlobalException e) {
            return new ResponseData();
        }
    }

    @RequestMapping(value = "edit")
    @CacheEvict(cacheNames = "productList", key = "123")
    public ResponseData editProduct(@RequestBody ProductForm productForm, BindingResult bindingResult) throws Exception {
        if ("".equals(productForm.getProductId())) {
            throw new GlobalException(BusinessEnum.PARAM_ERROR.getCode(), "productId 不能为空");
        }
        SellerProductDTO sellerProductDTO = new SellerProductDTO();
        BeanUtils.copyProperties(productForm, sellerProductDTO);
        SellerProductDTO resultDTO = sellerProductService.editProduct(sellerProductDTO);
        log.info("【卖家修改产品】= {}", productForm);
        return new ResponseData(BasicEnum.SUCCESS);
    }
}
