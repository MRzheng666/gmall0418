package com.zz.gmall.service;

import com.zz.gmall.bean.PmsProductImage;
import com.zz.gmall.bean.PmsProductInfo;
import com.zz.gmall.bean.PmsProductSaleAttr;
import com.zz.gmall.bean.PmsSkuInfo;

import java.util.List;

public interface SpuService {
    List<PmsProductInfo> spuList(String catalog3Id);

    void saveSpuInfo(PmsProductInfo pmsProductInfo);

    List<PmsProductSaleAttr> spuSaleAttrList(String spuId);

    List<PmsProductImage> spuImageList(String spuId);

    List<PmsProductSaleAttr> spuSaleAttrListCheckBySku(String productId,String skuId);
}
