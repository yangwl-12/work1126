package com.yang.service;

import com.yang.entity.Count;

import java.util.List;

public interface CountService {
    //查询
    List<Count> findAll();
    Count findOne(String id);

    //添加
    void addOne(Count count);

    //删除
    void deleteOne(String id);

    //更新
    void updateOne(Count count);
}
