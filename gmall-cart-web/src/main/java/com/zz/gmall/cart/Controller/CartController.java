package com.zz.gmall.cart.Controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.zz.gmall.bean.OmsCartItem;
import com.zz.gmall.bean.PmsSkuInfo;
import com.zz.gmall.service.SkuService;
import com.zz.gmall.util.CookieUtil;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.EAN;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CartController {
    @Reference
    SkuService skuService;


    @RequestMapping("addToCart")
    public String addCart(String skuId, long quantity, HttpServletRequest resquest, HttpServletResponse response){
        PmsSkuInfo  skuInfo= skuService.getSkuByIdFromDb(skuId);
        OmsCartItem omsCartItem = new OmsCartItem();
        String member ="";
        if(StringUtils.isNotBlank(member)){
            //用户没有登陆
            List<OmsCartItem> omsCartItems = new ArrayList();
           // CookieUtil.setCookie(resquest,response,);
        }

        return "";
    }
}
