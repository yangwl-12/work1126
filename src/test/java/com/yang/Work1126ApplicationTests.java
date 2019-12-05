package com.yang;

import com.yang.dao.AdminDao;
import com.yang.entity.Admin;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
 public  class Work1126ApplicationTests {

    @Autowired
    private AdminDao adminDao;


    @Test
     public void contextLoads() {
        List<Admin> admins = adminDao.selectAll();
        for (Admin admin : admins) {
            System.out.println(admin);
        }
    }

}
