package com.o2ii.sell.service.impl;

import com.o2ii.sell.dataobject.ProductCategory;
import com.o2ii.sell.dataobject.ProductInfo;
import com.o2ii.sell.dto.SellerProductDTO;
import com.o2ii.sell.enums.BusinessEnum;
import com.o2ii.sell.exception.GlobalException;
import com.o2ii.sell.repository.ProductInfoRepository;
import com.o2ii.sell.service.ProductCategoryService;
import com.o2ii.sell.service.ProductInfoService;
import com.o2ii.sell.service.SellerProductService;
import com.o2ii.sell.util.CopyPropertyUtil;
import com.o2ii.sell.util.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Slf4j
@Transactional
public class SellerProductServiceImpl implements SellerProductService {
    @Autowired
    ProductInfoRepository productInfoRepository;

    @Autowired
    ProductCategoryService productCategoryService;

    @Autowired
    ProductInfoService productInfoService;

    @Override
    public SellerProductDTO addProduct(SellerProductDTO sellerProductDTO) {
        Integer categoryType = sellerProductDTO.getCategoryType();
        // 查询是否存在产品类型
        ProductCategory productCategory = productCategoryService.findOne(categoryType);
        if (productCategory == null) {
            throw new GlobalException(BusinessEnum.PRODUCT_CATEGORY_NOT_EXIST);
        }

        ProductInfo productInfo = new ProductInfo();

        BeanUtils.copyProperties(sellerProductDTO, productInfo);
        productInfo.setProductId(KeyUtil.genShortKey());
        log.info("【产品信息】", productInfo);
        ProductInfo resultProductInfo = productInfoRepository.save(productInfo);
        resultProductInfo.setProductId(resultProductInfo.getProductId());
        return sellerProductDTO;
    }

    @Override
    public SellerProductDTO editProduct(SellerProductDTO sellerProductDTO) {
        String productId = sellerProductDTO.getProductId();
        ProductInfo productInfo = productInfoService.findByProductId(productId);
        CopyPropertyUtil.copyPropertiesIgnoreNull(sellerProductDTO, productInfo);

        ProductInfo resultProductInfo = productInfoRepository.save(productInfo);
        BeanUtils.copyProperties(resultProductInfo, sellerProductDTO);
        return sellerProductDTO;
    }
}
