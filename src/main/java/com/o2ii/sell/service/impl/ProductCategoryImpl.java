package com.o2ii.sell.service.impl;

import com.o2ii.sell.dto.ProductCategoryDTO;
import com.o2ii.sell.enums.BusinessEnum;
import com.o2ii.sell.exception.GlobalException;
import com.o2ii.sell.repository.ProductCategoryRepository;
import com.o2ii.sell.service.ProductCategoryService;
import com.o2ii.sell.dataobject.ProductCategory;
import com.o2ii.sell.util.CopyPropertyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ProductCategoryImpl implements ProductCategoryService {

    @Autowired
    private ProductCategoryRepository repository;

    @Override
    public ProductCategory findOne(Integer categoryId) {
        return repository.findByCategoryId(categoryId);
    }

    @Override
    public List<ProductCategory> findAll() {
        return repository.findAll();
    }

    @Override
    public List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList) {
        return repository.findByCategoryTypeIn(categoryTypeList);
    }

    @Override
    public ProductCategory save(ProductCategory productCategory) {
        return repository.save(productCategory);
    }

    @Override
    public ProductCategoryDTO editCategory(ProductCategoryDTO productCategoryDTO) {
        Integer categoryType = productCategoryDTO.getCategoryType();
        ProductCategory productCategory = repository.findByCategoryId(categoryType);
        if (productCategory == null) {
            throw new GlobalException(BusinessEnum.PRODUCT_CATEGORY_NOT_EXIST);
        }
        CopyPropertyUtil.copyPropertiesIgnoreNull(productCategoryDTO, productCategory);
        repository.save(productCategory);
        return productCategoryDTO;
    }
}
