package com.zz.gmall.search.Controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.zz.gmall.bean.*;
import com.zz.gmall.service.AttrService;
import com.zz.gmall.service.SearchService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

@Controller
public class SearchController {

    @Reference
    SearchService searchService;

    @Reference
    AttrService attrService;

    @RequestMapping("list.html")
    public String list(PmsSearchParam pmsSearchParam, ModelMap modelMap){
        List<PmsSearchSkuInfo> pmsSearchSkuInfoList = searchService.list(pmsSearchParam);
        String[] valueIds = new String[pmsSearchParam.getSkuAttrValueList().size()];
        int i = 0;
        for(PmsSkuAttrValue pmsSkuAttrValue:pmsSearchParam.getSkuAttrValueList()){
            valueIds[i] = pmsSkuAttrValue.getValueId();
            i++;
        }

        modelMap.put("skuLsInfoList",pmsSearchSkuInfoList);

        Set<String> valueIdSet = new HashSet<>();
        for(PmsSearchSkuInfo pmsSearchSkuInfo:pmsSearchSkuInfoList){
            List<PmsSkuAttrValue> pmsSkuAttrValueList = pmsSearchSkuInfo.getSkuAttrValueList();
            for(PmsSkuAttrValue pmsSkuAttrValue:pmsSkuAttrValueList){
                String value = pmsSkuAttrValue.getValueId();
                valueIdSet.add(value);
            }
        }
        List<PmsBaseAttrInfo> pmsBaseAttrInfos = attrService.getAttrValueListByValueId(valueIdSet);
        modelMap.put("attrList", pmsBaseAttrInfos);

        String[] delValueIds = valueIds;
        if (delValueIds != null) {
            // 面包屑
            // pmsSearchParam
            // delValueIds
            List<PmsSearchCrumb> pmsSearchCrumbs = new ArrayList<>();
            for (String delValueId : delValueIds) {
                Iterator<PmsBaseAttrInfo> iterator = pmsBaseAttrInfos.iterator();
                PmsSearchCrumb pmsSearchCrumb = new PmsSearchCrumb();
                // 生成面包屑的参数
                pmsSearchCrumb.setValueId(delValueId);
                pmsSearchCrumb.setUrlParam(getUrlParamForCrumb(pmsSearchParam, delValueId));
                while (iterator.hasNext()) {
                    PmsBaseAttrInfo pmsBaseAttrInfo = iterator.next();
                    List<PmsBaseAttrValue> attrValueList = pmsBaseAttrInfo.getAttrValueList();
                    for (PmsBaseAttrValue pmsBaseAttrValue : attrValueList) {
                        String valueId = pmsBaseAttrValue.getId();
                        if (delValueId.equals(valueId)) {
                            // 查找面包屑的属性值名称
                            pmsSearchCrumb.setValueName(pmsBaseAttrValue.getValueName());
                            //删除该属性值所在的属性组
                            iterator.remove();
                        }
                    }
                }
                pmsSearchCrumbs.add(pmsSearchCrumb);
            }
            modelMap.put("attrValueSelectedList", pmsSearchCrumbs);
        }
        modelMap.put("urlParam",getUrlParam(pmsSearchParam));
        return "list";
    }

    @RequestMapping("index")
    public String index(){
        return "index";
    }

    private String getUrlParam(PmsSearchParam pmsSearchParam){

        String catalog3Id = pmsSearchParam.getCatalog3Id();
        String keyword = pmsSearchParam.getKeyword();
        String[] valueId = new String[pmsSearchParam.getSkuAttrValueList().size()];
        int i = 0;
        String urlParam = "";
        for(PmsSkuAttrValue pmsSkuAttrValue:pmsSearchParam.getSkuAttrValueList()){
            valueId[i] = pmsSkuAttrValue.getValueId();
            i++;
        }

        if (StringUtils.isNotBlank(keyword)) {
            if (StringUtils.isNotBlank(urlParam)) {
                urlParam = urlParam + "&";
            }
            urlParam = urlParam + "keyword=" + keyword;
        }
        if (StringUtils.isNotBlank(catalog3Id)) {
            if (StringUtils.isNotBlank(urlParam)) {
                urlParam = urlParam + "&";
            }
            urlParam = urlParam + "=catalog3Id" + catalog3Id;
        }
        if (valueId != null) {

            for (String pmsSkuAttrValue : valueId) {
                urlParam = urlParam + "&valueId=" + pmsSkuAttrValue;
            }
        }
        return urlParam;
    }

    private String getUrlParamForCrumb(PmsSearchParam pmsSearchParam, String delValueId) {
        String keyword = pmsSearchParam.getKeyword();
        String catalog3Id = pmsSearchParam.getCatalog3Id();
        String[] valueId = new String[pmsSearchParam.getSkuAttrValueList().size()];
        int i = 0;
        for(PmsSkuAttrValue pmsSkuAttrValue:pmsSearchParam.getSkuAttrValueList()){
            valueId[i] = pmsSkuAttrValue.getValueId();
            i++;
        }

        String urlParam = "";

        if (StringUtils.isNotBlank(keyword)) {
            if (StringUtils.isNotBlank(urlParam)) {
                urlParam = urlParam + "&";
            }
            urlParam = urlParam + "keyword=" + keyword;
        }

        if (StringUtils.isNotBlank(catalog3Id)) {
            if (StringUtils.isNotBlank(urlParam)) {
                urlParam = urlParam + "&";
            }
            urlParam = urlParam + "catalog3Id=" + catalog3Id;
        }

        if (valueId != null) {
            for (String pmsSkuAttrValue : valueId) {
                if (!pmsSkuAttrValue.equals(delValueId)) {
                    urlParam = urlParam + "&valueId=" + pmsSkuAttrValue;
                }
            }
        }

        return urlParam;
    }


}
