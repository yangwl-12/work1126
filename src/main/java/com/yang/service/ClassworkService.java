package com.yang.service;

import com.yang.entity.Classwork;

import java.util.List;

public interface ClassworkService {
    //查询
    List<Classwork> findAll();
    Classwork findOne(String id);
    //删除
    void deleteOne(String id);
    //添加
    void addOne(Classwork classwork);

}
