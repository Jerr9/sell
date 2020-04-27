package com.o2ii.sell.service;

import com.o2ii.sell.dataobject.ProductInfo;
import com.o2ii.sell.dto.CartDTO;
import org.springframework.data.domain.Page; // 不是这个：sun.jvm.hotspot.debugger.Page
import org.springframework.data.domain.Pageable; // 不是这个：java.awt.print.Pageable
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductInfoService {
    /***
     * 新增商品
     * @param productInfo
     * @return
     */
    ProductInfo save(ProductInfo productInfo);

    /***
     * 查询商品详情
     * @param productId
     * @return
     */
    ProductInfo findByProductId(String productId);

    /***
     * 查询上架产品列表
     * @return
     */
    List<ProductInfo> findUpAll();

    /***
     * 查询产品列表
     * @param pageable
     * @return
     */
    Page<ProductInfo> findAll(Pageable pageable);

    /**
     * 加库存
     * @param cartDTOList
     */
    void increaseStock(List<CartDTO> cartDTOList);

    /**
     * 扣库存
     * @param cartDTOList
     */
    void decreaseStock(List<CartDTO> cartDTOList);

    /**
     * 上架
     * @param productId
     */
    ProductInfo onSale(String productId);
}
