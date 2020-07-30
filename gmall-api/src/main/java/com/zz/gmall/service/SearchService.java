package com.zz.gmall.service;

import com.zz.gmall.bean.PmsSearchParam;
import com.zz.gmall.bean.PmsSearchSkuInfo;

import java.util.List;

public interface SearchService {
    public List<PmsSearchSkuInfo> list(PmsSearchParam pmsSearchParam);
}
