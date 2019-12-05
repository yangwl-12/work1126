package com.yang.service;

import com.yang.annotaction.GoeasyAnnotaction;
import com.yang.dao.UserDao;
import com.yang.entity.MapVO;
import com.yang.entity.User;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<User> findAll() {
        return userDao.selectAll();
    }

    @Override
    public User findOne(String id) {
        return userDao.selectByPrimaryKey(id);
    }


    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<User> findAllf(Integer page, Integer size) {
        return userDao.selectByRowBounds(new User(),new RowBounds((page-1)*size,size));
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Integer findSex(String key) {
        Example example = new Example(User.class);
        example.createCriteria().andCondition(key);
        int count = userDao.selectCountByExample(example);
        return count;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public User findOneByPhone(String phone) {
        return userDao.selectOne(new User().setPhone(phone));
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<MapVO> selectByLoction() {
        return userDao.selectByLoction();
    }

    @Override
    @GoeasyAnnotaction
    public void addOne(User user) {
            userDao.insert(user);
    }

    @Override
    @GoeasyAnnotaction
    public void removeOne(String id) {
            userDao.deleteByPrimaryKey(id);
    }

    @Override
    @GoeasyAnnotaction
    public void updateOne(User user) {
        userDao.updateByPrimaryKeySelective(user);

    }

    @Override
    public User login(String phone, String password) {
        return userDao.selectOne(new User().setPhone(phone).setPassWord(password));
    }

    @Override
    public void regist( String code) {

    }
}
