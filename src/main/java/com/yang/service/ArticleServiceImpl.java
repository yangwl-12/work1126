package com.yang.service;

import com.yang.dao.ArtcleDao;
import com.yang.entity.Article;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArtcleDao artcleDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Article> findAll() {
        return artcleDao.selectAll();
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Article> findAllf(Integer page, Integer size) {
        return artcleDao.selectByRowBounds(new Article(),new RowBounds((page-1)*size,size));
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Article findOne(String id) {
        return artcleDao.selectByPrimaryKey(id);
    }

    @Override
    public void modifyOne(Article article) {
        artcleDao.updateByPrimaryKeySelective(article);
    }

    @Override
    public void addOne(Article article) {
        artcleDao.insert(article);
    }

    @Override
    public void removeOne(String id) {
            artcleDao.deleteByPrimaryKey(id);
    }
}
