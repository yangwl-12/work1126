package com.yang.service;

import com.yang.dao.GuruDao;
import com.yang.entity.Guru;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class GuruServiceImpl implements GuruService {

    @Autowired
    private GuruDao guruDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Guru> findAll() {
        return guruDao.selectAll();
    }

    @Override
    public void addOne(Guru guru) {
        guruDao.insert(guru);
    }

    @Override
    public void removeOne(String id) {
        guruDao.deleteByPrimaryKey(id);
    }

    @Override
    public void modifyOne(Guru guru) {
        guruDao.updateByPrimaryKeySelective(guru);

    }
}
