package com.yang.aspect;

import com.google.gson.Gson;
import com.yang.entity.MapVO;
import com.yang.service.UserService;
import io.goeasy.GoEasy;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Aspect
@Configuration
public class GoeasyAspect {
    @Autowired
    private UserService userService;


    @Around("@annotation(com.yang.annotaction.GoeasyAnnotaction)")
    public Object findSex(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        System.out.println(new Date());
        Object proceed = proceedingJoinPoint.proceed();
        System.out.println("asdada");

        Map<String, Object> map = new HashMap<>();
        map.put("man_oneDay",userService.findSex("sex = '男' and DATE_SUB(NOW(),INTERVAL 1 day) < createTime"));
        map.put("woman_oneDay",userService.findSex("sex = '女' and DATE_SUB(NOW(),INTERVAL 1 day) < createTime"));
        map.put("man_oneWeek",userService.findSex("sex = '男' and DATE_SUB(NOW(),INTERVAL 7 day) < createTime"));
        map.put("woman_oneWeek",userService.findSex("sex = '女' and DATE_SUB(NOW(),INTERVAL 7 day) < createTime"));
        map.put("man_oneMonth",userService.findSex("sex = '男' and DATE_SUB(NOW(),INTERVAL 30 day) < createTime"));
        map.put("woman_oneMonth",userService.findSex("sex = '女' and DATE_SUB(NOW(),INTERVAL 30 day) < createTime"));
        map.put("man_oneYear",userService.findSex("sex = '男' and DATE_SUB(NOW(),INTERVAL 365 day) < createTime"));
        map.put("woman_oneYear",userService.findSex("sex = '女' and DATE_SUB(NOW(),INTERVAL 365 day) < createTime"));
        List<MapVO> mapVOS = userService.selectByLoction();
        map.put("map",mapVOS);
        GoEasy goEasy = new GoEasy("http://rest-hangzhou.goeasy.io", "BC-63186ec9498d4f11bfd348f8a8e2773f");

        String s = new Gson().toJson(map);
        goEasy.publish("ywl",s);
        return null;
    }
}
