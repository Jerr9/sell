package com.o2ii.sell.controller;

import com.o2ii.sell.dataobject.ProductCategory;
import com.o2ii.sell.dto.ProductCategoryDTO;
import com.o2ii.sell.enums.BusinessEnum;
import com.o2ii.sell.exception.GlobalException;
import com.o2ii.sell.form.CategoryForm;
import com.o2ii.sell.service.ProductCategoryService;
import com.o2ii.sell.result.ResponseData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("seller/category")
@Slf4j
public class SellerCategoryController {

    @Autowired
    ProductCategoryService productCategoryService;

    @RequestMapping(value = "addCategory", method = RequestMethod.POST)
    public ResponseData addCategory(@Valid @RequestBody CategoryForm categoryForm, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            throw new GlobalException(BusinessEnum.PARAM_ERROR.getCode(), bindingResult.getFieldError().getDefaultMessage());
        }
        ProductCategory productCategory = new ProductCategory();
        BeanUtils.copyProperties(categoryForm, productCategory);

        ProductCategory categoryResult = productCategoryService.save(productCategory);
        System.out.println("?????");
        if (categoryResult == null) {
            throw new GlobalException(BusinessEnum.PRODUCT_CATEGORY_ADD_ERROR);
        }
        return new ResponseData();
    }

    @RequestMapping(value = "editCategory")
    public ResponseData editCategory(@Valid @RequestBody CategoryForm categoryForm) throws Exception {
        if (categoryForm.getCategoryType() == null) {
            throw new GlobalException(BusinessEnum.PARAM_ERROR);
        }
        ProductCategoryDTO productCategoryDTO = new ProductCategoryDTO();
        BeanUtils.copyProperties(categoryForm, productCategoryDTO);
        log.info("【修改品类信息】 = {}", productCategoryDTO);
        ProductCategoryDTO categoryResult = productCategoryService.editCategory(productCategoryDTO);
        if (categoryResult == null) {
            throw new GlobalException(BusinessEnum.PRODUCT_CATEGORY_EDIT_ERROR);
        }
        return null;
    }
}
