package com.yang.service;

import com.yang.dao.ClassworkDao;
import com.yang.entity.Classwork;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ClassworkServiceImpl implements ClassworkService {
    @Autowired
    private ClassworkDao classworkDao;
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Classwork> findAll() {
        return classworkDao.selectAll();
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Classwork findOne(String id) {
        return classworkDao.selectByPrimaryKey(id);
    }

    @Override
    public void deleteOne(String id) {
        classworkDao.deleteByPrimaryKey(id);

    }

    @Override
    public void addOne(Classwork classwork) {
            classworkDao.insert(classwork);
    }
}
