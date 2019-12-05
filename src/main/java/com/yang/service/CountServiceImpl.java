package com.yang.service;

import com.yang.dao.CountDao;
import com.yang.entity.Count;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class CountServiceImpl implements CountService {
    @Autowired
    private CountDao countDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Count> findAll() {
        return countDao.selectAll();
    }

    @Override
    public Count findOne(String id) {
        return countDao.selectByPrimaryKey(id);
    }

    @Override
    public void addOne(Count count) {
            countDao.insert(count);
    }

    @Override
    public void deleteOne(String id) {
        countDao.deleteByPrimaryKey(id);

    }

    @Override
    public void updateOne(Count count) {
        countDao.updateByPrimaryKeySelective(count);
    }
}
