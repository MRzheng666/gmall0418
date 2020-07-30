package com.zz.gmall.service;

import com.zz.gmall.bean.PmsSkuInfo;

import java.util.List;

public interface SkuService {
    void saveSkuInfo(PmsSkuInfo pmsSkuInfo);

    PmsSkuInfo getSkuByIdFromDb(String skuId);


    List<PmsSkuInfo> getSkuSaleAttrValueListBySpu(String productId);

    public PmsSkuInfo getSkiById(String skuId);

    List<PmsSkuInfo> getAllSku(String s);
}
