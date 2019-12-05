package com.yang.service;

import com.yang.entity.Guru;

import java.util.List;

public interface GuruService {
    //查询
    List<Guru> findAll();
    //添加
    void addOne(Guru guru);

    //删除
    void removeOne(String id);
    //更新
    void modifyOne(Guru guru);
}
