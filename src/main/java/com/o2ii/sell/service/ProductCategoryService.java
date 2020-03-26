package com.o2ii.sell.service;

import java.util.List;
import com.o2ii.sell.dataobject.ProductCategory;

public interface ProductCategoryService {
    ProductCategory findOne(Integer categoryId);

    List<ProductCategory> findAll();

    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);

    ProductCategory save(ProductCategory productCategory);
}
