package com.o2ii.sell.service.impl;

import com.o2ii.sell.dataobject.ProductInfo;
import com.o2ii.sell.dto.CartDTO;
import com.o2ii.sell.enums.ProductStatusEnum;
import com.o2ii.sell.enums.BusinessEnum;
import com.o2ii.sell.exception.GlobalException;
import com.o2ii.sell.repository.ProductInfoRepository;
import com.o2ii.sell.service.ProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ProductInfoServiceImpl implements ProductInfoService {
    @Autowired
    private ProductInfoRepository productRepository;

    @Override
    public ProductInfo save(ProductInfo productInfo) {
        return productRepository.save(productInfo);
    }

    @Override
    public ProductInfo findByProductId(String productId) {
        return productRepository.findByProductId(productId);
    }

    @Override
    public List<ProductInfo> findUpAll() {
        return productRepository.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public void increaseStock(List<CartDTO> cartDTOList) {
        for(CartDTO cartDTO : cartDTOList) {
            ProductInfo productInfo = productRepository.findByProductId(cartDTO.getProductId());
            productInfo.setProductStock(productInfo.getProductStock() + cartDTO.getProductQuantity());

            productRepository.save(productInfo);
        }
    }

    @Override
    @Transactional
    public void decreaseStock(List<CartDTO> cartDTOList) {
        for(CartDTO cartDTO : cartDTOList) {
            ProductInfo productInfo = productRepository.findByProductId(cartDTO.getProductId());
            if (productInfo == null) {
                throw new GlobalException(BusinessEnum.PRODUCT_NOT_EXIST);
            }
            Integer result = productInfo.getProductStock() - cartDTO.getProductQuantity();
            if (result < 0) {
                throw new GlobalException(BusinessEnum.PRODUCT_OUT_OF_STOCK);
            }
            productInfo.setProductStock(result);
            productRepository.save(productInfo);
        }
    }

    @Override
    public ProductInfo onSale(String productId) {
        ProductInfo productInfo = productRepository.findByProductId(productId);
        if (productInfo == null) {
            throw new GlobalException(BusinessEnum.PRODUCT_NOT_EXIST);
        }
        if (productInfo.getProductStatus() == ProductStatusEnum.UP.getCode()) {
            throw new GlobalException(BusinessEnum.PRODUCT_STATUS_ERROR);
        }
        productInfo.setProductStatus(ProductStatusEnum.UP.getCode());
        return productRepository.save(productInfo);
    }
}
