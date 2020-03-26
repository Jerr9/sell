package com.o2ii.sell.repository;

import com.o2ii.sell.dataobject.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sun.jvm.hotspot.debugger.Page;

import java.awt.print.Pageable;
import java.util.List;

@Repository
public interface ProductInfoRepository extends JpaRepository<ProductInfo, Integer> {

    ProductInfo findByProductId(String productId);

    List<ProductInfo> findByProductStatus(Integer productStatus);

}
