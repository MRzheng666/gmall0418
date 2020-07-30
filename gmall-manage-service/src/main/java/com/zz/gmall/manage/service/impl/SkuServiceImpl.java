package com.zz.gmall.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.zz.gmall.bean.PmsSkuAttrValue;
import com.zz.gmall.bean.PmsSkuImage;
import com.zz.gmall.bean.PmsSkuInfo;
import com.zz.gmall.bean.PmsSkuSaleAttrValue;
import com.zz.gmall.manage.mapper.PmsSkuAttrValueMapper;
import com.zz.gmall.manage.mapper.PmsSkuImageMapper;
import com.zz.gmall.manage.mapper.PmsSkuInfoMapper;
import com.zz.gmall.manage.mapper.PmsSkuSaleAttrValueMapper;
import com.zz.gmall.service.SkuService;
import com.zz.gmall.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.UUID;

@Service
public class SkuServiceImpl implements SkuService {

    @Autowired
    PmsSkuInfoMapper pmsSkuInfoMapper;

    @Autowired
    PmsSkuAttrValueMapper pmsSkuAttrValueMapper;

    @Autowired
    PmsSkuSaleAttrValueMapper pmsSkuSaleAttrValueMapper;

    @Autowired
    PmsSkuImageMapper pmsSkuImageMapper;

//    @Autowired
//    RedisUtil redisUtil;


    @Override
    public void saveSkuInfo(PmsSkuInfo pmsSkuInfo) {

        // 插入skuInfo
        int i = pmsSkuInfoMapper.insertSelective(pmsSkuInfo);
        String skuId = pmsSkuInfo.getId();

        // 插入平台属性关联
        List<PmsSkuAttrValue> skuAttrValueList = pmsSkuInfo.getSkuAttrValueList();
        for (PmsSkuAttrValue pmsSkuAttrValue : skuAttrValueList) {
            pmsSkuAttrValue.setSkuId(skuId);
            pmsSkuAttrValueMapper.insertSelective(pmsSkuAttrValue);
        }

        // 插入销售属性关联
        List<PmsSkuSaleAttrValue> skuSaleAttrValueList = pmsSkuInfo.getSkuSaleAttrValueList();
        for (PmsSkuSaleAttrValue pmsSkuSaleAttrValue : skuSaleAttrValueList) {
            pmsSkuSaleAttrValue.setSkuId(skuId);
            pmsSkuSaleAttrValueMapper.insertSelective(pmsSkuSaleAttrValue);
        }

        // 插入图片信息
        List<PmsSkuImage> skuImageList = pmsSkuInfo.getSkuImageList();
        for (PmsSkuImage pmsSkuImage : skuImageList) {
            pmsSkuImage.setSkuId(skuId);
            pmsSkuImageMapper.insertSelective(pmsSkuImage);
        }


    }


    @Override
    public PmsSkuInfo getSkuByIdFromDb(String skuId){
        // sku商品对象
        PmsSkuInfo pmsSkuInfo = new PmsSkuInfo();
        pmsSkuInfo.setId(skuId);
        PmsSkuInfo skuInfo = pmsSkuInfoMapper.selectOne(pmsSkuInfo);

        // sku的图片集合
//        PmsSkuImage pmsSkuImage = new PmsSkuImage();
//        pmsSkuImage.setSkuId(skuId);
//        List<PmsSkuImage> pmsSkuImages = pmsSkuImageMapper.select(pmsSkuImage);
//        skuInfo.setSkuImageList(pmsSkuImages);
        return skuInfo;
    }

    //redis查找item信息
    public PmsSkuInfo getSkiById(String skuId){
//        PmsSkuInfo pmsSkuInfo = new PmsSkuInfo();
//
//        //链接缓存
//        Jedis jedis = redisUtil.getJedis();
//
//        //查询缓存
//        String key = "sku"+skuId+"info";
//        String skuIson = jedis.get(key);
//
//        if(StringUtils.isNotBlank(skuIson)){   //如果在缓存中查询到
//            pmsSkuInfo = JSON.parseObject(skuIson,PmsSkuInfo.class);
//        }else {     //没查询到
//
//            //为防止缓存击穿 设置分布式锁
//            String token = UUID.randomUUID().toString();
//            String ok = jedis.set("sku"+skuId+"lock",token, "nx", "px", 10*1000);
//            if(ok.equals("OK")&&StringUtils.isNotBlank(ok)){
//                pmsSkuInfo = getSkuByIdFromDb(skuId);
//                if(pmsSkuInfo!=null){     //数据库中有值 写入缓存
//                    jedis.set("sku"+skuId+"info",JSON.toJSONString(pmsSkuInfo));
//                }else {
//                    //为防止缓存穿透  把空值也写入缓存  并设置过期时间
//                    jedis.setex("sku"+skuId+"info",60*3,JSON.toJSONString(""));
//                }
//                String token2 = jedis.get("sku"+skuId+"lock");//保证删除的是自己的锁
//                //
//                if(StringUtils.isNotBlank(token2)&&token2.equals(token)){
//                    jedis.del("sku"+skuId+"lock");
//                }
//            }else {    //还没查询完数据库，进行自旋等待
//                try {
//                    Thread.sleep(3000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                return getSkiById(skuId);
//            }
//        }
//        jedis.close();
//        return pmsSkuInfo;
        return null;
    }

    @Override
    public List<PmsSkuInfo> getAllSku(String catalog3Id) {
        List<PmsSkuInfo> pmsSkuInfos = pmsSkuInfoMapper.selectAll();
        for (PmsSkuInfo pmsSkuInfo : pmsSkuInfos) {
            String skuId = pmsSkuInfo.getId();

            PmsSkuAttrValue pmsSkuAttrValue = new PmsSkuAttrValue();
            pmsSkuAttrValue.setSkuId(skuId);
            List<PmsSkuAttrValue> select = pmsSkuAttrValueMapper.select(pmsSkuAttrValue);

            pmsSkuInfo.setSkuAttrValueList(select);
        }
        return pmsSkuInfos;
    }

    @Override
    public List<PmsSkuInfo> getSkuSaleAttrValueListBySpu(String productId) {
        List<PmsSkuInfo> pmsSkuInfos = pmsSkuInfoMapper.selectSkuSaleAttrValueListBySpu(productId);
        return pmsSkuInfos;
    }


}
