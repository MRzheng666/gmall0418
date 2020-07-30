package com.zz.gmall.service;

import com.zz.gmall.bean.PmsBaseAttrInfo;

import java.util.List;
import java.util.Set;


public interface AttrService {
    List<PmsBaseAttrInfo> attrInfoList(String catalog3Id);

    List<PmsBaseAttrInfo> getAttrValueListByValueId(Set<String> valueIdSet);
}
