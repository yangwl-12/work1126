package com.yang.service;

import com.yang.entity.Admin;

public interface AdminService {
    //登录
    Admin findOneByName(String name);

}
