package com.zz.gmall.user.service.impl;

import com.zz.gmall.user.bean.UmsMember;
import com.zz.gmall.user.bean.UmsMemberReceiveAddress;
import com.zz.gmall.user.mapper.UmsMemberReceiveAddressMapper;
import com.zz.gmall.user.mapper.UserMapper;
import com.zz.gmall.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;
    @Autowired
    UmsMemberReceiveAddressMapper umsMemberReceiveAddressMapper;

    @Override
    public List<UmsMember> getAllUser() {

        return null;
    }

    @Override
    public List<UmsMemberReceiveAddress> getReceiveAddressByMemberId(String memberId) {
        UmsMemberReceiveAddress umsMemberReceiveAddress = new UmsMemberReceiveAddress();
        umsMemberReceiveAddress.setMemberId(memberId);
        List<UmsMemberReceiveAddress> umsMemberReceiveAddresses = umsMemberReceiveAddressMapper.select(umsMemberReceiveAddress);
        return  umsMemberReceiveAddresses;
    }
}
