package com.yang.service;

import com.yang.dao.AlbumDao;
import com.yang.entity.Album;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class AlbumServiceImpl implements AlbumService {
    @Autowired
    private AlbumDao albumDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Album findOne(String id) {
        return albumDao.selectByPrimaryKey(id);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
//    @LogAnnotaction("展示专辑列表")
    public List<Album> findAll() {
        return albumDao.selectAll();
    }

    @Override
    public void addOne(Album album) {
        albumDao.insert(album);
    }

    @Override
    public void removeOne(String id) {
        albumDao.deleteByPrimaryKey(id);
    }

    @Override
    public void modifyOne(Album album) {
        albumDao.updateByPrimaryKeySelective(album);
    }

    @Override
    public List<Album> findAllf(Integer page, Integer rows) {
        return albumDao.selectByRowBounds(new Album(),new RowBounds((page-1)*rows,rows));
    }
}
