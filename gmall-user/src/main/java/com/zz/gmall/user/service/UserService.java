package com.zz.gmall.user.service;

import com.zz.gmall.user.bean.UmsMember;
import com.zz.gmall.user.bean.UmsMemberReceiveAddress;

import java.util.List;

public interface UserService {
    List<UmsMember> getAllUser();

    List<UmsMemberReceiveAddress> getReceiveAddressByMemberId(String memberId);

}
