package com.yang.service;

import com.yang.entity.Picture;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PictureService {
    //查询所有
    List<Picture> findAll();
    //添加
    void addOne(Picture picture);
    //删除
    void removeOne(String id);
    //查询一个
    Picture findOne(String id);
    //更新
    void modifyOne(Picture picture);
    //分页查询
    List<Picture> findAllByPage(Integer page,Integer size);

    //条件查询
    List<Picture> selectSearch(@Param("searchField") String searchField, @Param("searchString") String searchString,
                               @Param("searchOper") String searchOper, @Param("page") Integer page, @Param("rows") Integer rows);

    Long findTotalSearch(@Param("searchField") String searchField,@Param("searchString") String searchString ,@Param("searchOper") String searchOper);
    //批量删除
    void deleteAll(List<String> list);

}
