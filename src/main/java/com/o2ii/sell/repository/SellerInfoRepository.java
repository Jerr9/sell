package com.o2ii.sell.repository;

import com.o2ii.sell.dataobject.SellerInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SellerInfoRepository extends JpaRepository<SellerInfo, String> {

//    SellerInfo findByOpenid(String openid);
}
