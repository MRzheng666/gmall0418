package com.zz.gmall.passport.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.zz.gmall.bean.UmsMember;
import com.zz.gmall.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PassportController {
    @Reference
    UserService userService;

    @RequestMapping("verify")
    @ResponseBody
    public String verify(String token){

        // 通过jwt校验token真假

        return "success";
    }


    @RequestMapping("login")
    @ResponseBody
    public String login(UmsMember umsMember){
        UmsMember umsMemberLogin = userService.login(umsMember);
        return "token";
    }

    @RequestMapping("index")
    @ResponseBody
    public String index(String ReturnUrl, ModelMap map){
        map.put("ReturnUrl",ReturnUrl);
        return "index";
    }

}
