package com.yang.service;

import com.yang.dao.LogDao;
import com.yang.entity.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class LogServiceImpl implements LogService {
    @Autowired
    private LogDao logDao;
    @Override
    public void addOne(Log log) {
        logDao.insert(log);

    }
}
