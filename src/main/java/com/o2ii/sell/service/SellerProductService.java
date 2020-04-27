package com.o2ii.sell.service;

import com.o2ii.sell.dto.SellerProductDTO;

import org.springframework.stereotype.Service;

@Service
public interface SellerProductService {
    SellerProductDTO addProduct(SellerProductDTO sellerProductDTO);

    SellerProductDTO editProduct(SellerProductDTO sellerProductDTO);
}
