package com.o2ii.sell.repository;

import com.o2ii.sell.dataobject.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer> {

    ProductCategory findByCategoryId(Integer id);

    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryList);

}
