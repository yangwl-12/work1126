package com.yang.service;

import com.yang.entity.Article;

import java.util.List;

public interface ArticleService  {
    //查询
    List<Article> findAll();
    List<Article> findAllf(Integer page,Integer size);
    Article findOne(String id);
    //修改
    void modifyOne(Article article);
    //添加
    void addOne(Article article);
    //删除
    void removeOne(String id);
}
