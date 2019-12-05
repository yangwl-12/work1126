package com.yang.dao;

import com.yang.entity.MapVO;
import com.yang.entity.User;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface UserDao extends Mapper<User> {

    List<User> selectSex(String sex);

    List<MapVO> selectByLoction();


}
