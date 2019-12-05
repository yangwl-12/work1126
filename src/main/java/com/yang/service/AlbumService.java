package com.yang.service;

import com.yang.entity.Album;

import java.util.List;

public interface AlbumService {
    //查询
    Album findOne(String id);
    List<Album> findAll();
    //增加
    void addOne(Album album);
    //删除
    void removeOne(String id);
    //修改
    void modifyOne(Album album);
    //分页cx
    List<Album> findAllf(Integer page,Integer rows);
}
