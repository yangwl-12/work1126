package com.yang.dao;

import com.yang.entity.Picture;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.additional.idlist.DeleteByIdListMapper;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface PictureDao extends Mapper<Picture>, DeleteByIdListMapper<Picture,String> {
    //条件查询
    List<Picture> selectSearch(@Param("searchField") String searchField, @Param("searchString") String searchString,
                               @Param("searchOper") String searchOper, @Param("page") Integer page, @Param("rows") Integer rows);

    Long findTotalSearch(@Param("searchField") String searchField,@Param("searchString") String searchString ,@Param("searchOper") String searchOper);


}

